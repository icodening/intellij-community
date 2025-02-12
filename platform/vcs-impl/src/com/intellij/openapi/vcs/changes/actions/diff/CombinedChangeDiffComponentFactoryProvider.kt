// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.openapi.vcs.changes.actions.diff

import com.intellij.diff.tools.combined.*
import com.intellij.openapi.ListSelection
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.vcs.changes.ui.ChangesComparator
import com.intellij.openapi.vcs.changes.ui.PresentableChange

class CombinedChangeDiffComponentFactoryProvider : CombinedDiffComponentFactoryProvider {
  override fun create(model: CombinedDiffModel): CombinedDiffComponentFactory = MyFactory(model)

  private inner class MyFactory(model: CombinedDiffModel) : CombinedDiffComponentFactory(model) {

    init {
      model.init()
    }

    val filePathComparator = ChangesComparator.getFilePathComparator(true)

    override val requestsComparator: Comparator<CombinedDiffModel.RequestData> =
      Comparator { r1, r2 ->
        val id1 = r1.blockId
        val id2 = r2.blockId
        when {
          id1 is CombinedPathBlockId && id2 is CombinedPathBlockId -> filePathComparator.compare(id1.path, id2.path)
          else -> -1
        }
      }

    override fun createGoToChangeAction(): AnAction = MyGoToChangePopupAction()
    private inner class MyGoToChangePopupAction : PresentableGoToChangePopupAction.Default<PresentableChange>() {

      val viewer get() = model.context.getUserData(COMBINED_DIFF_VIEWER_KEY)

      override fun getChanges(): ListSelection<out PresentableChange> {
        val changes = model.requests.values.filterIsInstance<PresentableChange>()
        val selected = viewer?.getCurrentBlockId() as? CombinedPathBlockId
        val selectedIndex = when {
          selected != null -> changes.indexOfFirst { it.fileStatus == selected.fileStatus && it.filePath == selected.path }
          else -> -1
        }
        return ListSelection.createAt(changes, selectedIndex)
      }

      override fun onSelected(change: PresentableChange) {
        viewer?.selectDiffBlock(CombinedPathBlockId(change.filePath, change.fileStatus), ScrollPolicy.DIFF_BLOCK)
      }
    }
  }

}
