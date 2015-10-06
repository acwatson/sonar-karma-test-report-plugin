SonarQube Karma JUnit Reporting
================

## Purpose

This is the SonarQube Karma JUnit Reporting plugin. It was created to facilitate publishing unit
test reports created using the karma-junit-reporter to SonarQube.

## Download

The latest release of this plugin can be downloaded from https://oss.sonatype.org/content/groups/public/com/github/acwatson/sonar-karma-test-report-plugin/1.0.0.2/sonar-karma-test-report-plugin-1.0.0.2.jar

## Why Was This Plugin Created?

When using the karma-junit-reporter for a JavaScript project, you end up with a unit test report
named something like "TESTS-PhantomJS_1.9.8_(Mac_OS_X_0.0.0).xml".

Looking inside that file, you will see content like:

    <?xml version="1.0"?>
    <testsuite name="PhantomJS 1.9.8 (Mac OS X 0.0.0)" package="" timestamp="2015-10-05T04:56:44" id="0" hostname="myComputer" tests="58" errors="0" failures="0" time="0.475">
        <properties>
            <property name="browser.fullName" value="Mozilla/5.0 (Macintosh; Intel Mac OS X) AppleWebKit/534.34 (KHTML, like Gecko) PhantomJS/1.9.8 Safari/534.34"/>
        </properties>
        <testcase name="should do something cool" time="0.028" classname="PhantomJS_1_9_8_(Mac_OS_X_0_0_0).service.myservice"/>
        ...
        <system-out>
        <![CDATA[
        ]]>
        </system-out>
        <system-err/>
    </testsuite>

### So, What's The Problem? 

The problem lies in the classname that the karma-junit-reporter plugin sets for your 
testcase. Notice above that the plugin has set the classname to `PhantomJS_1_9_8_(Mac_OS_X_0_0_0).service.myservice`. 
The PhantomJS part of the classname is the browser that ran the tests and the rest comes from the name of your test 
suite. In this example, imagine that we have a unit test file located at `service/myservice.spec.js` and that it defines
the top-level test suite with `describe('service.myservice', function() {`. The karma-junit-reporter is basically 
concatenating the browser name and the test suite name to come up with the classname. Unfortunately, SonarQube cannot 
use this classname. SonarQube needs that name of the actual unit test file.

Instead of:
    
    <testcase name="should do something cool" time="0.028" classname="PhantomJS_1_9_8_(Mac_OS_X_0_0_0).service.myservice"/>
    
SonarQube needs something like:

    <testcase name="should do something cool" time="0.028" classname="src/app/service/myservice.spec.js"/>

Basically, we need a way to map the test suite name to the actual unit test file name so that we can report
the unit test file report to SonarQube. That is what this plugin does.

## How Does This Plugin Work?

The SonarQube Karma JUnit Reporting plugin reads the xml report that was created by the karma-junit-reporter
and determines what the real unit test file names are (based off of the testcase classname attribute) and reports 
them to SonarQube.

## Log Output

To troubleshoot or better understand what the SonarQube Karma JUnit Reporting plugin is doing, run Sonar with the debug
flag turned on. You will see some debug logging done by the `KarmaJunitReporterJsTestDriverSensor`.

## What Parameters Can Be Set For This Plugin?

* `sonar.javascript.karmajstestdriver.reportsPath` must be set to a valid value in order for this plugin to take effect.
    * set this configuration instead of `sonar.javascript.jstestdriver.reportsPath`  
