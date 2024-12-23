package co.graphene.pages.mavis;

import co.graphene.lib.SeleniumLib;
import co.graphene.util.Debugger;
import co.graphene.util.mavishr.MavisHeaderFooter;
import co.graphene.util.mavishr.MavisMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MavisSideNavigationPage {

        @FindBy(xpath="//button[@class='mx-auto']")
        WebElement btn_hamburgerMenu;
        @FindBy(xpath="//button[@class='ml-auto']")
        WebElement btn_hamburgerclose;
        @FindBy(xpath="//img[@alt='logo']")
        WebElement img_ciplalogo;
        @FindBy(xpath="//img[@alt='footerLogo']")
        WebElement img_footerlogo;
        @FindBy(xpath = "//ul/a//div/span[contains(@class,'font-medium')]")
        List<WebElement> menuShortNames;
        @FindBy(xpath = "//ul/a//span[contains(@class,'font-semibold')]")
        List<WebElement> menuFullNames;
        @FindBy(xpath = "//div[@id='footer']//p")
        List<WebElement> footerInfos;

        private SeleniumLib seleniumLib;
        WebDriver driver;
        WebDriverWait wait;

        public MavisSideNavigationPage(WebDriver driver) {
            this.driver = driver;
            seleniumLib = new SeleniumLib(driver);
            wait = new WebDriverWait(driver, 10);
        }

//        public void clickOnPageName(String pagename){
//            String partialpath="//span[contains(text(),'";
//            String fullpath=partialpath+pagename+"')]";
//            By pagelink = By.xpath(fullpath);
//            Debugger.println("full xpath link::"+pagelink.toString());
//            seleniumLib.clickOnElement(pagelink);
//        }

//        public String isActualpageloaded(String pagename) {
//            String partialpath="//h1[contains(text(),'";
//            String fullpath=partialpath+pagename+"')]";
//            By pagetitle = By.xpath(fullpath);
//            Debugger.println("full xpath link for pagename::"+pagetitle.toString());
//
//            if(!seleniumLib.isElementPresent(pagetitle)){
//                return "Expected page Title "+pagetitle+" not loaded";
//            }
//            return "success";
//        }
        public String isElementDisplayed(String webelement) {
            WebElement web=null;

            if (webelement=="cipla logo"){
                web= img_ciplalogo;
            }
            else if(webelement=="graphene logo"){
                web= img_footerlogo;
            }

            if(!seleniumLib.isElementPresent(web)){
                return "Expected element: "+webelement+" not displayed";
            }
            return "success";
        }

        public String clickOnCloseButton() {
            try {
                seleniumLib.clickOnWebElement(btn_hamburgerclose);
                return "Success";
            }catch(Exception exp){
                return "Could not click on HamburgerMenu:"+exp;
            }
        }

        //Click on Hamburger Menu to Expand the Menu
        public String clickOnHamburgerMenuIcon(){
            try {
                seleniumLib.clickOnWebElement(btn_hamburgerMenu);
                return "Success";
            }catch(Exception exp){
                return "Could not click on HamburgerMenu:"+exp;
            }
        }

        //Verify Shortname menus are loaded correctly in the expected order
        public String verifyMenuOrderAndShortNames(String expectedShortName, int order){
            if(menuShortNames.size() == 0){
                return "Could not Read Menu Short names from the UI";
            }
            if(menuShortNames.size() < order){
                return "Looks like all the menus are not loaded in the UI: "+expectedShortName;
            }
            String uiName = menuShortNames.get(order-1).getText();
            if(!expectedShortName.equalsIgnoreCase(uiName)){
                Debugger.println("Expected: "+expectedShortName+", Actual:"+uiName);
                return "Expected Menu "+expectedShortName+" not loaded in the Right order "+order;
            }
            Debugger.println("Expected Menu Shortname:"+expectedShortName+",Actual:"+uiName+" at position: "+order);
            return "Success";
        }
        //Verify Shortname menus are loaded correctly in the expected order
        public String verifyMenuOrderAndFullNames(String expectedFullName, int order){
            if(menuFullNames.size() == 0){
                return "Could not Read Menu Full names from the UI";
            }
            if(menuFullNames.size() < order){
                return "Looks like all the menus are not loaded in the UI: "+expectedFullName;
            }
            String uiName = menuFullNames.get(order-1).getText();
            if(!expectedFullName.equalsIgnoreCase(uiName)){
                Debugger.println("Expected: "+expectedFullName+", Actual:"+uiName);
                return "Expected Menu "+expectedFullName+" not loaded in the Right order "+order;
            }
            Debugger.println("Expected Menu Full name:"+expectedFullName+",Actual:"+uiName+" at position: "+order);
            return "Success";
        }

        //Verify Shortname menus are loaded correctly in the expected order
        public String verifyTitleHeaderAndFooter(MavisHeaderFooter mavisHeaderFooter){
            //Check for Header Logo
            if(!seleniumLib.isElementPresent(img_ciplalogo)){
                return "Cipla Header logo could not locate";
            }
            String actHeadLogo = seleniumLib.getAttributeValue(img_ciplalogo, "src");
            if(!actHeadLogo.equalsIgnoreCase(mavisHeaderFooter.getHeaderLogo())){
                return "Header logo location loaded is not as expected. Actual:"+actHeadLogo+", Expected:"+mavisHeaderFooter.getHeaderLogo();
            }
            //Check for Footer Logo
            if(!seleniumLib.isElementPresent(img_footerlogo)){
                return "Cipla Footer logo could not locate";
            }
            String actFooterLogo = seleniumLib.getAttributeValue(img_footerlogo, "src");
            if(!actFooterLogo.equalsIgnoreCase(mavisHeaderFooter.getFooterLogo())){
                return "Footer logo location loaded is not as expected. Actual:"+actFooterLogo+", Expected:"+mavisHeaderFooter.getFooterLogo();
            }
            //check for Copy Right
            String footerText = "";
            for(int i=1; i<footerInfos.size(); i++){//Starts with 1 as first element is for logo, which is covered separately
                footerText = footerInfos.get(i).getText();
                if(i == 1){
                    if(!footerText.contains(mavisHeaderFooter.getCopyRight()+" GrapheneAI. All Rights Reserved")){
                        return "Footer does not contain Graphene Copy Rights info: "+footerText;
                    }
                }else if( i == 2){
                    if(!footerText.contains("Client version : 1.0.0")){
                        return "Footer does not contain Client Version: "+footerText;
                    }
                }else if( i == 3){
                    if(!footerText.contains("MAVIS")){
                        return "Footer does not contain MAVIS: "+footerText;
                    }
                }
                Debugger.println("Footer: "+footerText);
            }
            return "Success";
        }

    }//end


