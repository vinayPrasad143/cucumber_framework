package co.graphene.steps.mavishr;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import co.graphene.util.mavishr.MAVIS_DATAS;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class DriversOfRatingStepDefs extends Pages {

    MAVIS_DATAS mavisDatas;

    public DriversOfRatingStepDefs(SeleniumDriver driver) {
        super(driver);
        mavisDatas = new MAVIS_DATAS();
    }

    @When("^the user navigates to Mavis HR Drivers of Rating Page$")
    public void theUserNavigatesToMavisHRDriversOfRatingPage() {
        String stepMsg = mavisHomePage.navigateToDriversOfRating();
        if (!stepMsg.equalsIgnoreCase("Success")) {
            Assert.fail(stepMsg);
        }
    }

    @Then("^the user should see the drivers of rating page title as (.*)$")
    public void theUserShouldSeeTheDriversOfRatingPageTitle(String ddPageTitle) {
        String vStep = driversOfRatingPage.verifyDriversOfRatingPageTitle(ddPageTitle);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }

    @And("^the latest data uploaded for drivers of rating should match with latest data uploaded in the database$")
    public void theLatestDataUploadedForDriversOfRating() {
        //Read Latest restate info from Database
        String latestDateFromDb = mavisDatas.readLatestDataTransformDateForDriversOfRating();
        //Validate with UI
        String vStep = driversOfRatingPage.verifyLatestDataUploadTitle(latestDateFromDb);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }

    @And("^the options in drivers of rating page filter (.*) should be populated from (.*) master$")
    public void theFilterOptionsInDriversOfRatingAgainstDB(String filterName,String masterName) {
        //Read expected Options from Database
        List<String> expectedOptions = mavisDatas.readFilterOptionsFromMaster(masterName);
        //Validate with UI
        String vStep = deepDivePage.verifyFilterOptions(filterName,expectedOptions);//Same as in Deep Dive Page
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }

    @Then("^under Summary View the Employee score and Positive Drivers score for each filter combinations loaded properly$")
    public void underSummaryViewTheEmployeeScoreAndPositiveDriversScoreForEachFilterCombinationsLoadedProperly() {
        String vMessage = driversOfRatingPage.verifyBrandScoreAndPositiveDriverRatingsSummaryView(mavisDatas);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @And("^under Summary View the Employee score and Negative Drivers score for each filter combinations loaded properly$")
    public void underSummaryViewTheEmployeeScoreAndNegativeDriversScoreForEachFilterCombinationsLoadedProperly() {
        String vMessage = driversOfRatingPage.verifyNegativeDriverRatingsSummaryView(mavisDatas);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }
}//end
