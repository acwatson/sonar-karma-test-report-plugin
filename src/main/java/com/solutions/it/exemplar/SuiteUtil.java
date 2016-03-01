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
package com.solutions.it.exemplar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for getting the test suite name.
 */
public class SuiteUtil {

  private static final String SUITE_REGEX = "^(describe\\((\\'|\\\")(.*)(\\'|\\\").*$)";
  private static final Pattern SUITE_PATTERN = Pattern.compile(SUITE_REGEX);
  private static final Logger LOG = LoggerFactory.getLogger(SuiteUtil.class);

  /**
   * Returns the suite name, if it can be found in the spec file.
   * @param file the spec file
   * @return the suite name, if it can be found in the spec file or null
   */
  public String getSuiteName(final File file) {

    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e) {
      LOG.warn("An error was thrown trying to read \"{}\"", file.getAbsolutePath(), e);
    }

    if (br == null) {
      return null;
    }

    try {

      String line = br.readLine();

      while(line != null){

        String suiteName = getSuiteName(line);
        if (suiteName != null) {
          return suiteName;
        }
        line = br.readLine();
      }

    } catch (IOException e) {
      LOG.warn("An error was thrown trying to read " + file.getAbsolutePath(), e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          LOG.warn("An error was thrown trying to close the reader for " + file.getAbsolutePath(), e);
        }
      }
    }

    return null;
  }

  /**
   * Returns suite name based on spec file line or null if it cannot be found in the line supplied.
   * @param line the spec file line
   * @return suite name based on spec file line or null if it cannot be found in the line supplied
   */
  public String getSuiteName(final String line) {

    String suiteName = null;
    String trimmedLine = line;

    if (trimmedLine != null) {
      trimmedLine = trimmedLine.trim();
    }

    if (trimmedLine != null && trimmedLine.length() > 0) {

      LOG.debug("Trying to get suite name from \"" + trimmedLine + "\"");

      Matcher m = SUITE_PATTERN.matcher(trimmedLine);

      if (!m.matches()) {
        LOG.debug("Regex could not find suite name: " + SUITE_REGEX);
      } else if (m.groupCount() != 4) {
        LOG.debug("Regex did not have the expected 4 groups. It only had " + m.groupCount() + "groups. Regex: " + SUITE_REGEX);
      } else {
        suiteName = m.group(3);
      }

    }

    return suiteName;
  }
}
