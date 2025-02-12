// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.openapi.wm.impl.welcomeScreen.projectActions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.ProjectsGroupItem
import com.intellij.openapi.wm.impl.welcomeScreen.recentProjects.RecentProjectItem
import java.awt.datatransfer.StringSelection

/**
 * @author gregsh
 */
class CopyProjectPathAction : RecentProjectsWelcomeScreenActionBase() {
  override fun update(event: AnActionEvent) {
    val item = getSelectedItem(event) ?: return
    event.presentation.isEnabled = item is RecentProjectItem || item is ProjectsGroupItem
  }

  override fun actionPerformed(event: AnActionEvent) {
    val item = getSelectedItem(event) ?: return
    val copiedText = when (item) {
      is RecentProjectItem -> FileUtil.toSystemDependentName(item.projectPath)
      else -> item.displayName()
    }

    CopyPasteManager.getInstance().setContents(StringSelection(copiedText))
  }
}