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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class SuiteUtilTest {

  private SuiteUtil suiteUtil;

  @Before
  public void setUp() throws Exception {
    suiteUtil = new SuiteUtil();
  }

  @Test
  public void getSuiteName_should_return_null_when_input_is_null() {

    String suiteLine = null;

    assertNull(suiteUtil.getSuiteName(suiteLine));
  }

  @Test
  public void getSuiteName_should_work_for_simple_name() {

    String suiteLine = "describe('pageContentResolverProvider', function() {";

    assertEquals("pageContentResolverProvider", suiteUtil.getSuiteName(suiteLine));
  }

  @Test
  public void getSuiteName_should_work_for_name_with_multiple_words() {

    String suiteLine = "describe('head directive', function() {";

    assertEquals("head directive", suiteUtil.getSuiteName(suiteLine));
  }

  @Test
  public void getSuiteName_should_work_for_name_with_leading_white_space() {

    String suiteLine = "  describe('directive navbar', function() {";

    assertEquals("directive navbar", suiteUtil.getSuiteName(suiteLine));
  }
}
