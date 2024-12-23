package co.graphene.steps;

import co.graphene.config.SeleniumDriver;
import co.graphene.lib.SeleniumLib;
import co.graphene.pages.Pages;
import co.graphene.util.Debugger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class TestHooks extends Pages {

    SeleniumLib seleniumLib;
    public static String currentTagName = "";
    public static String currentTags = "";
    public static String currentFeature = "";
    public static String temptagname = "";
    public static boolean new_scenario_feature = false;

    public TestHooks(SeleniumDriver driver) {
        super(driver);
        seleniumLib = new SeleniumLib(driver);
    }

    @Before
    public void beginingOfTest(Scenario scenario) {
        Debugger.println("Begining of Test...........");
        currentTagName = scenario.getSourceTagNames().toString();
        currentTags = scenario.getSourceTagNames().toString();
        currentFeature = scenario.getId().split(";")[0];
        if (temptagname.isEmpty() || !(temptagname.equalsIgnoreCase(currentTagName))) {
            Debugger.println("\n" + scenario.getSourceTagNames() + " STARTED");
            temptagname = currentTagName;
            new_scenario_feature = true;
            Debugger.println("FEATURE: " + currentFeature.replaceAll("-", " "));
            Debugger.println("SCENARIO: " + scenario.getName());
        } else {
            new_scenario_feature = false;
            Debugger.println("SCENARIO: " + scenario.getName());
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        Debugger.println("Tear down Scenario..........");
        String scenarioStatus = scenario.getStatus().toString();
        if (!scenarioStatus.equalsIgnoreCase("PASSED")) {
            scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
        }
        Debugger.println("STATUS: " + scenarioStatus.toUpperCase());
    }

    @After(order = 1)
    public void afterScenario() {
        Debugger.println("After scenario..........");
    }
}//end class
