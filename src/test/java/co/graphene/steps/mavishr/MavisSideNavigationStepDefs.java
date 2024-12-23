package co.graphene.steps.mavishr;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import co.graphene.util.Debugger;
import co.graphene.util.mavishr.MAVIS_DATAS;
import co.graphene.util.mavishr.MavisHeaderFooter;
import co.graphene.util.mavishr.MavisMenu;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;

public class MavisSideNavigationStepDefs extends Pages{

        MAVIS_DATAS mavisDatas;

        public MavisSideNavigationStepDefs(SeleniumDriver driver) {
            super(driver);
            mavisDatas = new MAVIS_DATAS();
        }
//        @And("^click on page name (.*) to Navigate from side bar$")
//        public void clickOnPageNamePagenameToNavigateFromSideBar(String pagename) {
//            mavisSideNavigationPage.clickOnPageName(pagename.trim());
//        }
//        @Then("^User should be on (.*) page$")
//        public void user_should_be_on_Company_Deep_Dive_page(String pageTitle)  {
//            String expectedPageTitle=pageTitle.trim();
//            String actualPageTitle=mavisSideNavigationPage.isActualpageloaded(pageTitle);
//            if (!actualPageTitle.equalsIgnoreCase("Success")) {
//                Assert.fail(actualPageTitle);
//            }
//
//        }
        @Then("^the logo (.*) should be displayed$")
        public void the_client_logo_should_be_displayed(String logo)  {
            String islogoDisplayed=null;
            islogoDisplayed=mavisSideNavigationPage.isElementDisplayed(logo);
            if (!islogoDisplayed.equalsIgnoreCase("Success")) {
                Assert.fail(islogoDisplayed);
            }
        }


        @Then("^the page names should show in shortended format in the right order$")
        public void theLatestDataUploadedForDeepDive() {
            List<MavisMenu> menus = mavisDatas.getMavisMenus();
            if(menus.size() == 0){
                String readMenuInfo = mavisDatas.readSidebarMenuPagesConfiguration();
                if (!readMenuInfo.equalsIgnoreCase("Success")) {
                    Assert.fail(readMenuInfo);
                }
            }
            menus = mavisDatas.getMavisMenus();
            if(menus.size() == 0){
                Assert.fail("No Menu Details present in the Database");
            }
            String vStep = "";
            //Validate with UI
            for (MavisMenu menu : menus) {
                vStep = mavisSideNavigationPage.verifyMenuOrderAndShortNames(menu.getShort_name(),menu.getPage_order());
                if (!vStep.equalsIgnoreCase("Success")) {
                    Assert.fail(vStep);
                }
            }
        }
        @When("^click on the hamburger menu icon$")
        public void click_on_hamburger_menu_icon() {
            String vStep = mavisSideNavigationPage.clickOnHamburgerMenuIcon();
            if (!vStep.equalsIgnoreCase("Success")) {
                Assert.fail(vStep);
            }
        }
        @When("^click on hamburger close button$")
        public void clickOnHamburgerCloseButton() {
            String vStep = mavisSideNavigationPage.clickOnCloseButton();
            if (!vStep.equalsIgnoreCase("Success")) {
                Assert.fail(vStep);
            }
        }

        @Then("^the page names should show in expanded format in the right order$")
        public void thePagenamesShouldShowInExpandedFormat() {
            String vStep = "";
            List<MavisMenu> menus = mavisDatas.getMavisMenus();
            if(menus.size() == 0){
                String readMenuInfo = mavisDatas.readSidebarMenuPagesConfiguration();
                if (!readMenuInfo.equalsIgnoreCase("Success")) {
                    Assert.fail(readMenuInfo);
                }
            }
            menus = mavisDatas.getMavisMenus();
            if(menus.size() == 0){
                Assert.fail("No Menu Details present in the Database");
            }
            //Validate with UI
            for (MavisMenu menu : menus) {
                vStep = mavisSideNavigationPage.verifyMenuOrderAndFullNames(menu.getPage_name(),menu.getPage_order());
                if (!vStep.equalsIgnoreCase("Success")) {
                    Assert.fail(vStep);
                }
            }

        }

    @And("^the browser title, header logo and footer information should be loaded properly$")
    public void theBrowserTitleHeaderLogoAndFooterInformationShouldBeLoadedProperly() {
        String headerFooter = mavisDatas.readHeaderFooterAndTitle();
        if (!headerFooter.equalsIgnoreCase("Success")) {
            Assert.fail(headerFooter);
        }
        MavisHeaderFooter mavisHeaderFooter = mavisDatas.getMavisHeaderFooter();
        String vStep = mavisSideNavigationPage.verifyTitleHeaderAndFooter(mavisHeaderFooter);
        if (!vStep.equalsIgnoreCase("Success")) {
            Assert.fail(vStep);
        }
    }
}//end

