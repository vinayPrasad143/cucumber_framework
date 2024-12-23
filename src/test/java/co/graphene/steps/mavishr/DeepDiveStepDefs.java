package co.graphene.steps.mavishr;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import co.graphene.util.Debugger;
import co.graphene.util.mavishr.MAVIS_DATAS;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class DeepDiveStepDefs extends Pages {

    MAVIS_DATAS mavisDatas;

    public DeepDiveStepDefs(SeleniumDriver driver) {
        super(driver);
        mavisDatas = new MAVIS_DATAS();
    }

    @When("^the user navigates to Mavis HR Deep Dive Page$")
    public void theUserNavigatesToMavisHRDeepDivePage() {
        String stepMsg = mavisHomePage.navigateToDeepDive();
        if (!stepMsg.equalsIgnoreCase("Success")) {
            Assert.fail(stepMsg);
        }
    }

    @Then("^the user should see the deep dive page title as (.*)$")
    public void theUserShouldSeeTheDeepDivePageTitle(String ddPageTitle) {
        String vStep = deepDivePage.verifyDeepDivePageTitle(ddPageTitle);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }

    @And("^the latest data uploaded for deep dive should match with latest data uploaded in the database$")
    public void theLatestDataUploadedForDeepDive() {
        //Read Latest restate info from Database
        String latestDateFromDb = mavisDatas.readLatestDataTransformDateForDeepDive();
        //Validate with UI
        String vStep = deepDivePage.verifyLatestDataUploadTitle(latestDateFromDb);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }

    @And("^the options in deep dive page filter (.*) should be populated from (.*) master$")
    public void theFilterOptionsInDeepDivePageAgainstDB(String filterName,String masterName) {
        //Read expected Options from Database
        List<String> expectedOptions = mavisDatas.readFilterOptionsFromMaster(masterName);
        //Validate with UI
        String vStep = deepDivePage.verifyFilterOptions(filterName,expectedOptions);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }else {
            Assert.assertTrue(filterName +" drop down values verified",true);
        }
    }

    @And("^the Positive Themes for each filter combinations loaded properly$")
    public void thePositiveThemesForEachFilterCombinationsLoadedProperly() {
        String vMessage = deepDivePage.verifyPositiveThemesForAllCombinations(mavisDatas);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }
    @And("^the Negative Themes for each filter combinations loaded properly$")
    public void theNegativeThemesForEachFilterCombinationsLoadedProperly() {
        String vMessage = deepDivePage.verifyNegativeThemesForAllCombinations(mavisDatas);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }
    @And("^the Positive Themes for specific combinations loaded properly$")
    public void thePositiveThemesForSpecificCombinationsLoadedProperly(DataTable filters) {
        Debugger.println("****POSITIVE THEMES VALIDATION - SPECIFIC COMBINATION***");
        String vMessage = "";
        List<List<String>> filterOptions = filters.raw();
        for (int i = 1; i < filterOptions.size(); i++) {
            vMessage = deepDivePage.verifyPositiveThemesForSpecificCombination(filterOptions.get(i).get(0),filterOptions.get(i).get(1),
                    filterOptions.get(i).get(2),filterOptions.get(i).get(3),mavisDatas);
            if (!vMessage.equalsIgnoreCase("Success")) {
                Assert.fail(vMessage);
            }
        }
    }

    @And("^the Negative Themes for specific combinations loaded properly$")
    public void theNegativeThemesForSpecificCombinationsLoadedProperly(DataTable filters) {
        Debugger.println("****NEGATIVE THEMES VALIDATION - SPECIFIC COMBINATION***");
        String vMessage = "";
        List<List<String>> filterOptions = filters.raw();
        for (int i = 1; i < filterOptions.size(); i++) {
            vMessage = deepDivePage.verifyNegativeThemesForSpecificCombination(filterOptions.get(i).get(0),filterOptions.get(i).get(1),
                    filterOptions.get(i).get(2),filterOptions.get(i).get(3),mavisDatas);
            if (!vMessage.equalsIgnoreCase("Success")) {
                Assert.fail(vMessage);
            }
        }
    }
}//end
