/*
 * Copyright 2010-2013 JetBrains s.r.o.
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

package org.jetbrains.jet.resolve;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.resolve.AbstractResolveBaseTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/resolve/references")
public class ReferenceResolveTestGenerated extends AbstractResolveBaseTest {
    public void testAllFilesPresentInReferences() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/resolve/references"), Pattern.compile("^(.+)\\.kt$"), true);
    }
    
    @TestMetadata("ClassReferenceInImport.kt")
    public void testClassReferenceInImport() throws Exception {
        doTest("idea/testData/resolve/references/ClassReferenceInImport.kt");
    }
    
    @TestMetadata("CtrlClickResolve.kt")
    public void testCtrlClickResolve() throws Exception {
        doTest("idea/testData/resolve/references/CtrlClickResolve.kt");
    }
    
    @TestMetadata("PackageReferenceInImport.kt")
    public void testPackageReferenceInImport() throws Exception {
        doTest("idea/testData/resolve/references/PackageReferenceInImport.kt");
    }
    
    @TestMetadata("ResolveClass.kt")
    public void testResolveClass() throws Exception {
        doTest("idea/testData/resolve/references/ResolveClass.kt");
    }
    
    @TestMetadata("ResolvePackageInProperty.kt")
    public void testResolvePackageInProperty() throws Exception {
        doTest("idea/testData/resolve/references/ResolvePackageInProperty.kt");
    }
    
    @TestMetadata("SamAdapter.kt")
    public void testSamAdapter() throws Exception {
        doTest("idea/testData/resolve/references/SamAdapter.kt");
    }
    
    @TestMetadata("SamConstructor.kt")
    public void testSamConstructor() throws Exception {
        doTest("idea/testData/resolve/references/SamConstructor.kt");
    }
    
    @TestMetadata("SamConstructorTypeArguments.kt")
    public void testSamConstructorTypeArguments() throws Exception {
        doTest("idea/testData/resolve/references/SamConstructorTypeArguments.kt");
    }
    
    @TestMetadata("SeveralOverrides.kt")
    public void testSeveralOverrides() throws Exception {
        doTest("idea/testData/resolve/references/SeveralOverrides.kt");
    }
    
}
