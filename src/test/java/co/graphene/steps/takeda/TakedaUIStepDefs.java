package co.graphene.steps.takeda;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import co.graphene.util.takeda.TAKEDA_DATAS;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;

public class TakedaUIStepDefs extends Pages {

    TAKEDA_DATAS takeda_datas;

    public TakedaUIStepDefs(SeleniumDriver driver) {

        super(driver);
        takeda_datas = new TAKEDA_DATAS();
    }

    @Then("^the user should see the header loaded with below components$")
    public void userShouldSeeTheHeaderComponents(DataTable headers) {
        List<List<String>> headerPages = headers.raw();
        String vMessage = "";
        for (int i = 1; i < headerPages.size(); i++) {
            vMessage = takedaHome.verifyHeaderElement(headerPages.get(i).get(0));
            if (!vMessage.equalsIgnoreCase("Success")) {
                Assert.fail(vMessage);
            }
        }
    }

    @And("^the user should see the onboarding message icon and logout button on header$")
    public void helpIconAndLogoutButton() {
        String stepResult = takedaHome.verifyHelpIconAndLogout();
        if(!stepResult.equalsIgnoreCase("Success")){
            Assert.fail(stepResult);
        }
    }

    @And("^the application footer loaded successfully$")
    public void applicationFooterLoadedSuccessfully() {
        String stepResult = takedaHome.verifyApplicationFooter();
        if(!stepResult.equalsIgnoreCase("Success")){
            Assert.fail(stepResult);
        }
    }

    @Then("^the user navigates to takeda page (.*)$")
    public void userNavigatesToTakedaPage(String pagename) {
        String vMessage = "";
        vMessage = takedaHome.navigateToPage(pagename);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @Then("^the user should see the filter panels loaded in the specified order$")
    public void userShouldSeeTheFilterValues(DataTable filters) {
        List<List<String>> filterPanels = filters.raw();
        String vMessage = "";
        String panelName = "", expPosition="";
        for (int i = 1; i < filterPanels.size(); i++) {
            panelName = filterPanels.get(i).get(0);
            expPosition = filterPanels.get(i).get(1);
            vMessage = takedaFilterPanel.verifyFilterPanelPosition(panelName, Integer.parseInt(expPosition)-1);
            if (!vMessage.equalsIgnoreCase("Success")) {
                Assert.fail(vMessage);
            }
        }
    }

    @And("^the user should see the information icon and latest db update message (.*)$")
    public void userShouldSeeInfoIconAndDBMessage(String dbMessage) {
        String vMessage = "";
        vMessage = takedaFilterPanel.verifyInfoIconAndLastDBUpdate(dbMessage);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @When("^the user clicks on the information icon panel$")
    public void theUserClicksOnTheInformationIconPanel() {
        String vMessage = "";
        vMessage = takedaFilterPanel.clickOnInfoIcon();
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @Then("^the user should see the (.*) information pops up with details below$")
    public void theUserShouldSeeTheATUInformationPopsUpWithDetailsBelow(String infoTitle, DataTable infoContents) {
        String vMessage = "";
        vMessage = takedaFilterPanel.verifyInfoTitle(infoTitle);
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    /***************************************************** ATU Page *********************************/

    @And("^the top section loaded with title (.*) attached with a help icon$")
    public void theTopSectionLoadedWithTitleCategoryPerformanceAttachedWithAHelpIcon(String titleName) {
    }

    @And("^the Sort By Highest on the right side with below options$")
    public void theSortByHighestOnTheRightSideWithBelowOptions() {
    }

    @And("^the Category Performance section shows three tabs as radio button$")
    public void theCategoryPerformanceSectionHaveThreeTabsAsRadioButton(DataTable inputs) {
    }

    @And("^the bottom section loaded with title (.*) attached with a help icon$")
    public void theBottomSectionLoadedWithTitleATUTrendlineAttachedWithAHelpIcon(String titleName) {
    }

    @And("^the ATU Trendline section shows three tabs as radio button with click disabled$")
    public void theATUTrendlineSectionHaveThreeTabsAsRadioButtonWithClickDisabled(DataTable inputs) {
    }

    @And("^the selected section in Category Performance should be highlighted in ATU Trendline section also$")
    public void theSelectedSectionInCategoryPerformanceShouldBeHighlightedInATUTrendlineSectionAlso() {
    }

    @When("^the user clicks on (.*) button in the brand filter section$")
    public void theUserClicksOnSelectAllButtonInTheBrandFilterSection(String buttonName) {
    }

    @Then("^all the brands loaded should be selected and highlighted$")
    public void allTheBrandsLoadedShouldBeSelectedAndHighlighted() {
    }

    @When("^the user selects a specific brand name from the brand names displayed$")
    public void theUserSelectASpecificBrandName() {
    }

    @Then("^the selected brand should change its status from enable or disable based on the current state$")
    public void theSelectedBrandShouldChangeItsStatusFromEnableOrDisableBasedOnTheCurrentState() {
    }

    @And("^the brand value should be highlighted or grayed out from the chart also$")
    public void theBrandValueShouldBeEnabledOrDisabledFromTheChartAlso() {
    }

    @When("^the user select the option (.*) from Sort By Highest section$")
    public void theUserSelectTheOptionBrandRankFromSortByHighestSection(String optionName) {
    }

    @Then("^the Category performance section chart should be loaded with rank values$")
    public void theCategoryPerformanceSectionChartShouldBeLoadedWithRankValues() {
    }

    @Then("^the Category performance section chart should be loaded with percent values$")
    public void theCategoryPerformanceSectionChartShouldBeLoadedWithPercentValues() {
    }

    @When("^the user clicks on takeda brand button$")
    public void theUserClicksOnTakedaBrandButton() {
    }

    @Then("^takeda brand is highlighted and other brands are greyed out in chart and filter section$")
    public void takedaBrandIsHighlightedAndOtherBrandsAreGreyedOutInChartAndFilterSection() {
    }

    @And("^the data loaded in descending order for each Category Performance section$")
    public void theDataLoadedInDescendingOrderForEachCategoryPerformanceSection() {
    }

}//end
