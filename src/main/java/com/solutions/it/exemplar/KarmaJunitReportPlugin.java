package com.solutions.it.exemplar;

/*
 * SonarQube Karma Test Reporting Plugin
 * Copyright (C) 2015 Exemplar IT Solutions LLC and Anthony Watson
 *
 * This file is part of SonarQube Karma Test Reporting Plugin.
 * SonarQube Karma Test Reporting Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SonarQube Karma Test Reporting Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SonarQube Karma Test Reporting Plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

/**
 * Main plugin type that registers all of the plugin's extensions with SonarQube.
 */
public class KarmaJunitReportPlugin extends SonarPlugin {

  private static final String TEST_AND_COVERAGE = "Tests and Coverage";

  public static final String PROPERTY_PREFIX = "sonar.javascript";
  public static final String REPORTS_PATH = PROPERTY_PREFIX + ".karmajstestdriver.reportsPath";
  public static final String REPORTS_PATH_DEFAULT_VALUE = "";

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List getExtensions() {
      List extensionList = new ArrayList();
      extensionList.add(KarmaJunitReporterJsTestDriverSensor.class);

      //Add support for parameters
      extensionList.add(PropertyDefinition.builder(REPORTS_PATH)
            .defaultValue(REPORTS_PATH_DEFAULT_VALUE)
            .name("JSTestDriver output folder")
            .description("Folder where JsTestDriver unit test reports are located.")
            .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
            .subCategory(TEST_AND_COVERAGE)
            .build());

      return extensionList;
  }
}
