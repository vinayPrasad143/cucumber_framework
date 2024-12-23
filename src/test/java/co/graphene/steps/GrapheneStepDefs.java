package co.graphene.steps;

import co.graphene.config.AppConfig;
import co.graphene.config.SeleniumDriver;
import co.graphene.lib.SeleniumLib;
import co.graphene.pages.Pages;
import co.graphene.util.DBConnector;
import co.graphene.util.Debugger;
import cucumber.api.java.en.Given;
import org.junit.Assert;

import java.sql.Connection;

public class GrapheneStepDefs extends Pages {

    public GrapheneStepDefs(SeleniumDriver driver) {
        super(driver);
    }

    @Given("^the user is logged into the Application Under Test via Graphene Access Point$")
    public void userIsInApplicationHome() {
        //Check Whether already logged into the Mavis app
        if(mavisHomePage.verifyLatestDataUploadTitlePresence()){
            Debugger.println("Already Logged in to Mavis HR...");
            return;//Already logged in, proceed with next step
        }
        Debugger.println("Logging into Mavis HR....");
        String stepResult = "";
        //Search for the Application Tile on Specific Environment
        String appName = AppConfig.getPropertyValue("APPLICATION_UNDER_TEST");
        if(appName == null || appName.isEmpty()){
            Assert.fail("Please specify the Application Under Test in the file graphene_accesspoint.properties in config directory.");
        }
        stepResult = accessPointHome.searchAndNavigateToApplication(appName);
        if(!stepResult.equalsIgnoreCase("Success")){
            Assert.fail(stepResult);
        }
        SeleniumLib.sleepInSeconds(5);
    }
    @Given("^the user is logged into the Application Under Test Directly$")
    public void userIsInApplicationHomeDirectly() {

        String stepResult = "";
        //Search for the Application Tile on Specific Environment
        String appName = AppConfig.getPropertyValue("APPLICATION_DIRECT_URL");
        if(appName == null || appName.isEmpty()){
            Assert.fail("Please specify the APPLICATION_DIRECT_URL in the file graphene_accesspoint.properties in config directory.");
        }
        //Checking whether the application is already loaded
        if(appName.toLowerCase().contains("takeda")) {
            if(takedaHome.isTakedaAppAlreadyLoaded()){
                return;//No need to proceed as the application is already loaded.
            }
        }
        stepResult = accessPointHome.directlyNavigateToApplication(appName);
        if(!stepResult.equalsIgnoreCase("Success")){
            Assert.fail(stepResult);
        }
        SeleniumLib.sleepInSeconds(5);
    }

}//end