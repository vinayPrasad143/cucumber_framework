package co.graphene.steps.mavishr;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;

public class MavisHomeStepDefs  extends Pages {

    public MavisHomeStepDefs(SeleniumDriver driver) {

        super(driver);    }


    @When("^the mavis home page displays with the title (.*)$")
    public void userIsReadingATUPageSPOutFromDatabase(String pageTitle) {
        String stepMsg = mavisHomePage.verifyMavisHomePageLoading(pageTitle);
        if (!stepMsg.equalsIgnoreCase("Success")) {
            Assert.fail(stepMsg);
        }
    }

    @And("^the header should display below dropdown filters$")
    public void theHeaderShouldShowDisplayBelowDropdownFilters(DataTable filterLabels) {
        List<List<String>> filters = filterLabels.raw();
        String vMessage = mavisHomePage.verifyFilterElement(filters);
        if (!vMessage.equalsIgnoreCase("Success")) {
               Assert.fail(vMessage);
        }
    }

    @And("^the user icon on the right side of the header$")
    public void theUserIconOnTheRightSideOfTheHeader() {
        String vMessage = mavisHomePage.verifyPresenceOfUserIcon();
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @And("^the page should display below tab sections$")
    public void thePageShouldDisplayBelowTabSections(DataTable mainPageTabNames) {
        List<List<String>> tabs = mainPageTabNames.raw();
        String vMessage = mavisHomePage.verifyTabElement(tabs);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @When("^the user selects the tab (.*) under employer of rating$")
    public void theUserSelectsTheTabSummaryView(String tabName) {
        String vMessage = mavisHomePage.selectTabUnderDriversOfEmployerRating(tabName);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @Then("^the page should display below title sections$")
    public void thePageShouldDisplayBelowTitleSections(DataTable sectionTitles) {
        List<List<String>> titles = sectionTitles.raw();
        String vMessage = mavisHomePage.verifySectionTitles(titles);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @And("^Employer and Employer Score should show under first section$")
    public void employerAndEmployerScoreShouldShowUnderFirstSection() {
    }

    @And("^Driver and Company Equity Score under second section$")
    public void driverAndCompanyEquityScoreUnderSecondSection() {
    }

    @And("^Positive and Negative links next to the second section$")
    public void positiveAndNegativeLinksNextToTheSecondSection() {
    }

    @When("^the user selects the tab Detailed View$")
    public void theUserSelectsTheTabDetailedView() {
    }

    @And("^the employer score loaded across all sites should be proper$")
    public void theEmployerScoreLoadedAcrossAllSitesShouldBeProper() {
        String vMessage = mavisHomePage.verifyEmployerRatingScoreAcrossSites();
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }
}//class
