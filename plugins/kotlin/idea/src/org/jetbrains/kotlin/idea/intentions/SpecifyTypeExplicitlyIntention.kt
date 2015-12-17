/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.intentions

import com.intellij.codeInsight.intention.LowPriorityAction
import com.intellij.codeInsight.template.*
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiDocumentManager
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.idea.util.IdeDescriptorRenderers
import org.jetbrains.kotlin.idea.util.ShortenReferences
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.getElementTextWithContext
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.types.ErrorUtils
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeUtils
import java.util.*

public class SpecifyTypeExplicitlyIntention : SelfTargetingIntention<KtCallableDeclaration>(KtCallableDeclaration::class.java, "Specify type explicitly"), LowPriorityAction {
    override fun isApplicableTo(element: KtCallableDeclaration, caretOffset: Int): Boolean {
        if (element.containingFile is KtCodeFragment) return false
        if (element is KtFunctionLiteral) return false // TODO: should JetFunctionLiteral be JetCallableDeclaration at all?
        if (element is KtConstructor<*>) return false
        if (element.typeReference != null) return false

        if (getTypeForDeclaration(element).isError) return false

        val initializer = (element as? KtWithExpressionInitializer)?.initializer
        if (initializer != null && initializer.textRange.containsOffset(caretOffset)) return false

        if (element is KtNamedFunction && element.hasBlockBody()) return false

        text = if (element is KtFunction) "Specify return type explicitly" else "Specify type explicitly"

        return true
    }

    override fun applyTo(element: KtCallableDeclaration, editor: Editor) {
        val type = getTypeForDeclaration(element)
        addTypeAnnotation(editor, element, type)
    }

    companion object {
        public fun getTypeForDeclaration(declaration: KtCallableDeclaration): KotlinType {
            val descriptor = declaration.analyze()[BindingContext.DECLARATION_TO_DESCRIPTOR, declaration]
            val type = (descriptor as? CallableDescriptor)?.returnType
            return type ?: ErrorUtils.createErrorType("null type")
        }

        public fun createTypeExpressionForTemplate(exprType: KotlinType): Expression {
            val descriptor = exprType.constructor.declarationDescriptor
            val isAnonymous = descriptor != null && DescriptorUtils.isAnonymousObject(descriptor)

            val allSupertypes = TypeUtils.getAllSupertypes(exprType)
            val types = if (isAnonymous) ArrayList<KotlinType>() else arrayListOf(exprType)
            types.addAll(allSupertypes)

            return object : ChooseValueExpression<KotlinType>(types, types.first()) {
                override fun getLookupString(element: KotlinType) = IdeDescriptorRenderers.SOURCE_CODE_SHORT_NAMES_IN_TYPES.renderType(element)
                override fun getResult(element: KotlinType) = IdeDescriptorRenderers.SOURCE_CODE.renderType(element)
            }
        }

        public fun addTypeAnnotation(editor: Editor?, declaration: KtCallableDeclaration, exprType: KotlinType) {
            if (editor != null) {
                addTypeAnnotationWithTemplate(editor, declaration, exprType)
            }
            else {
                declaration.setType(exprType)
            }
        }

        public fun createTypeReferencePostprocessor(declaration: KtCallableDeclaration): TemplateEditingAdapter {
            return object : TemplateEditingAdapter() {
                override fun templateFinished(template: Template?, brokenOff: Boolean) {
                    val typeRef = declaration.typeReference
                    if (typeRef != null && typeRef.isValid) {
                        ShortenReferences.DEFAULT.process(typeRef)
                    }
                }
            }
        }

        private fun addTypeAnnotationWithTemplate(editor: Editor, declaration: KtCallableDeclaration, exprType: KotlinType) {
            assert(!exprType.isError) { "Unexpected error type, should have been checked before: " + declaration.getElementTextWithContext() + ", type = " + exprType }

            val project = declaration.project
            val expression = createTypeExpressionForTemplate(exprType)

            declaration.setType(KotlinBuiltIns.FQ_NAMES.any.asString())

            PsiDocumentManager.getInstance(project).commitAllDocuments()
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(editor.document)

            val newTypeRef = declaration.typeReference!!
            val builder = TemplateBuilderImpl(newTypeRef)
            builder.replaceElement(newTypeRef, expression)

            editor.caretModel.moveToOffset(newTypeRef.node.startOffset)

            TemplateManager.getInstance(project).startTemplate(editor, builder.buildInlineTemplate(), createTypeReferencePostprocessor(declaration))
        }
    }
}

