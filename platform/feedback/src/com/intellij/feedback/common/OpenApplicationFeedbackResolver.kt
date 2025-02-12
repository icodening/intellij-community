// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.feedback.common

import com.intellij.feedback.common.bundle.CommonFeedbackBundle
import com.intellij.feedback.common.notification.RequestFeedbackNotification
import com.intellij.feedback.kotlinRejecters.bundle.KotlinRejectersFeedbackBundle
import com.intellij.feedback.kotlinRejecters.dialog.KotlinRejectersFeedbackDialog
import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.feedback.kotlinRejecters.state.KotlinRejectersInfoService
import com.intellij.notification.NotificationAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ex.ProjectManagerEx
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

class OpenApplicationFeedbackShower : AppLifecycleListener {

  companion object {
    val LAST_DATE_COLLECT_FEEDBACK = LocalDate(2022, 7, 19)
    const val MAX_NUMBER_NOTIFICATION_SHOWED = 3

    fun showNotification(project: Project?, forTest: Boolean) {
      val notification = RequestFeedbackNotification(
        KotlinRejectersFeedbackBundle.message("notification.kotlin.feedback.request.feedback.title"),
        KotlinRejectersFeedbackBundle.message("notification.kotlin.feedback.request.feedback.content")
      )
      notification.addAction(
        NotificationAction.createSimpleExpiring(CommonFeedbackBundle.message("notification.request.feedback.action.respond.text")) {
          val dialog = KotlinRejectersFeedbackDialog(project, forTest)
          dialog.show()
        }
        )
        notification.notify(project)
        notification.addAction(
          NotificationAction.createSimpleExpiring(CommonFeedbackBundle.message("notification.request.feedback.action.dont.show.text")) {
            if (!forTest) {
              IdleFeedbackTypeResolver.isFeedbackNotificationDisabled = true
            }
          }
        )
    }
  }

  override fun appStarted() {
    //Try to show only one possible feedback form - Kotlin Rejecters form
    val kotlinRejectersInfoState = KotlinRejectersInfoService.getInstance().state
    if (!kotlinRejectersInfoState.feedbackSent && kotlinRejectersInfoState.showNotificationAfterRestart &&
        LAST_DATE_COLLECT_FEEDBACK >= Clock.System.todayAt(TimeZone.currentSystemDefault()) &&
        !IdleFeedbackTypeResolver.isFeedbackNotificationDisabled &&
        kotlinRejectersInfoState.numberNotificationShowed <= MAX_NUMBER_NOTIFICATION_SHOWED) {
      val project = ProjectManagerEx.getInstance().openProjects.firstOrNull()
      kotlinRejectersInfoState.numberNotificationShowed += 1
      showNotification(project, false)
    }
  }
}