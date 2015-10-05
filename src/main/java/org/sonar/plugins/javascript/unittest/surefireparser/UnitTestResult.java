/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.javascript.unittest.surefireparser;

import org.apache.commons.lang.StringEscapeUtils;

public class UnitTestResult {

  public static final String STATUS_OK = "ok";
  public static final String STATUS_ERROR = "error";
  public static final String STATUS_FAILURE = "failure";
  public static final String STATUS_SKIPPED = "skipped";

  private String name, status, stackTrace, message;
  private long durationMilliseconds = 0L;

  public String getName() {
    return name;
  }

  public UnitTestResult setName(String name) {
    this.name = name;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public UnitTestResult setStatus(String status) {
    this.status = status;
    return this;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public UnitTestResult setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public UnitTestResult setMessage(String message) {
    this.message = message;
    return this;
  }

  public long getDurationMilliseconds() {
    return durationMilliseconds;
  }

  public UnitTestResult setDurationMilliseconds(long l) {
    this.durationMilliseconds = l;
    return this;
  }

  public boolean isErrorOrFailure() {
    return STATUS_ERROR.equals(status) || STATUS_FAILURE.equals(status);
  }

  public boolean isError() {
    return STATUS_ERROR.equals(status);
  }

  public String toXml() {
    StringBuilder sb = new StringBuilder();
    return appendXml(sb).toString();
  }

  public StringBuilder appendXml(StringBuilder sb) {
    sb
      .append("<testcase status=\"")
      .append(status)
      .append("\" time=\"")
      .append(durationMilliseconds)
      .append("\" name=\"")
      .append(StringEscapeUtils.escapeXml(name))
      .append("\"");

    if (isErrorOrFailure()) {
      sb
        .append(">")
        .append(isError() ? "<error message=\"" : "<failure message=\"")
        .append(StringEscapeUtils.escapeXml(message))
        .append("\">")
        .append("<![CDATA[")
        .append(StringEscapeUtils.escapeXml(stackTrace))
        .append("]]>")
        .append(isError() ? "</error>" : "</failure>")
        .append("</testcase>");
    } else {
      sb.append("/>");
    }
    return sb;
  }

}
