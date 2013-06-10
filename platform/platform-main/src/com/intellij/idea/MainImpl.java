/*
 * Copyright 2000-2013 JetBrains s.r.o.
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
package com.intellij.idea;

import com.intellij.openapi.application.ConfigImportHelper;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.AppUIUtil;
import com.intellij.util.PlatformUtils;
import com.intellij.util.PlatformUtilsCore;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;

@SuppressWarnings({"HardCodedStringLiteral", "UseOfSystemOutOrSystemErr", "UnusedDeclaration"})
public class MainImpl {
  private static final String LOG_CATEGORY = "#com.intellij.idea.Main";

  private MainImpl() { }

  /**
   * Called from PluginManager via reflection.
   */
  protected static void start(final String[] args) {
    System.setProperty(PlatformUtilsCore.PLATFORM_PREFIX_KEY, PlatformUtils.getPlatformPrefix(PlatformUtils.COMMUNITY_PREFIX));

    if (!Main.isHeadless()) {
      AppUIUtil.updateFrameClass();
      AppUIUtil.updateWindowIcon(JOptionPane.getRootFrame());
      AppUIUtil.registerBundledFonts();

      UIUtil.initDefaultLAF();

      final boolean isNewConfigFolder = PathManager.ensureConfigFolderExists(true);
      if (isNewConfigFolder) {
        ConfigImportHelper.importConfigsTo(PathManager.getConfigPath());
      }
    }

    if (!StartupUtil.checkStartupPossible(args)) {   // It uses config folder!
      System.exit(-1);
    }

    Logger.setFactory(LoggerFactory.getInstance());

    final Logger LOG = Logger.getInstance(LOG_CATEGORY);

    StartupUtil.startLogging(LOG);

    _main(args);
  }

  protected static void _main(final String[] args) {
    // http://weblogs.java.net/blog/shan_man/archive/2005/06/improved_drag_g.html
    System.setProperty("sun.swing.enableImprovedDragGesture", "");

    Logger LOG = Logger.getInstance(LOG_CATEGORY);
    StartupUtil.fixProcessEnvironment(LOG);
    StartupUtil.loadSystemLibraries(LOG);

    startApplication(args);
  }

  private static void startApplication(final String[] args) {
    final IdeaApplication app = new IdeaApplication(args);
    //noinspection SSBasedInspection
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        app.run();
      }
    });
  }
}