package com.intellij.psi.impl.source.tree.java;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiSwitchLabelStatement;
import com.intellij.psi.PsiSwitchStatement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.lang.ASTNode;

public class PsiSwitchLabelStatementImpl extends CompositePsiElement implements PsiSwitchLabelStatement {
  private static final Logger LOG = Logger.getInstance("#com.intellij.psi.impl.source.tree.java.PsiSwitchLabelStatementImpl");

  public PsiSwitchLabelStatementImpl() {
    super(SWITCH_LABEL_STATEMENT);
  }

  public boolean isDefaultCase() {
    return findChildByRoleAsPsiElement(ChildRole.DEFAULT_KEYWORD) != null;
  }

  public PsiExpression getCaseValue() {
    return (PsiExpression)findChildByRoleAsPsiElement(ChildRole.CASE_EXPRESSION);
  }

  public PsiSwitchStatement getEnclosingSwitchStatement() {
    final CompositeElement guessedSwitch = getTreeParent().getTreeParent();
    return guessedSwitch != null && guessedSwitch.getElementType() == SWITCH_STATEMENT
           ? (PsiSwitchStatement)SourceTreeToPsiMap.treeElementToPsi(guessedSwitch)
           : null;
  }

  public ASTNode findChildByRole(int role) {
    LOG.assertTrue(ChildRole.isUnique(role));
    switch(role){
      default:
        return null;

      case ChildRole.CASE_KEYWORD:
        return TreeUtil.findChild(this, CASE_KEYWORD);

      case ChildRole.DEFAULT_KEYWORD:
        return TreeUtil.findChild(this, DEFAULT_KEYWORD);

      case ChildRole.CASE_EXPRESSION:
        return TreeUtil.findChild(this, EXPRESSION_BIT_SET);

      case ChildRole.COLON:
        return TreeUtil.findChild(this, COLON);
    }
  }

  public int getChildRole(ASTNode child) {
    LOG.assertTrue(child.getTreeParent() == this);
    IElementType i = child.getElementType();
    if (i == CASE_KEYWORD) {
      return ChildRole.CASE_KEYWORD;
    }
    else if (i == DEFAULT_KEYWORD) {
      return ChildRole.DEFAULT_KEYWORD;
    }
    else if (i == COLON) {
      return ChildRole.COLON;
    }
    else {
      if (EXPRESSION_BIT_SET.isInSet(child.getElementType())) {
        return ChildRole.CASE_EXPRESSION;
      }
      else {
        return ChildRole.NONE;
      }
    }
  }

  public void accept(PsiElementVisitor visitor) {
    visitor.visitSwitchLabelStatement(this);
  }

  public String toString() {
    return "PsiSwitchLabelStatement";
  }
}
