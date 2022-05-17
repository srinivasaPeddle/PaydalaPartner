package com.pack.executor.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.pack.executor.stepdefs.StepDefs;
import com.pack.executor.utils.Reports;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The Class Runner.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features", glue = "com/pack/executor/stepdefs", format = { "pretty",
        "html:target/featuretest/test-output", "json:target/featuretest/json_output/cucumber.json",
        "junit:target/featuretest/junit_xml/cucumber.xml" }, monochrome = true, strict = true, dryRun = false)

public class Runner {

    @BeforeClass
    public static void setup() throws Exception {
        StepDefs.reportName = "report-summary";
    }

    @AfterClass
    public static void teardown() throws Exception {
        Reports.finishTest();
    }

}
