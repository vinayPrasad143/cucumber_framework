package co.graphene.pages.takeda;

import co.graphene.lib.SeleniumLib;
import co.graphene.config.AppConfig;
import co.graphene.util.Debugger;
import co.graphene.util.Wait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TakedaHomePage {

    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//a[contains(@class,'navbar-brand')]/img[@alt='takeda-logo']")
    WebElement homePageTakedaLogo;

    @FindBy(xpath = "//div[contains(@class,'footer')]//img[@class='graphene-logo']")
    WebElement grapheneFooterLogo;

    @FindBy(xpath = "//div[contains(@class,'footer')]//p[contains(text(),' Copyright 2021 by Graphene')]")
    WebElement grapheneFooterCopyright;

    @FindBy(xpath = "//ul[@id='step1']/li/a")
    List<WebElement> headerElements;

    @FindBy(xpath = "/html/body/header-component/header/div/nav/div[5]/a[2]")
    WebElement logoutButton;

    @FindBy(xpath = "/html/body/header-component/header/div/nav/div[5]/a[1]")
    WebElement helpIcon;

    public TakedaHomePage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public boolean isTakedaAppAlreadyLoaded(){
        try {
            String currentTitle = driver.getTitle();
            Debugger.println("Current Title: "+currentTitle);
            if(currentTitle != null && currentTitle.contains("Takeda")){
                return true;
            }
            return false;
        } catch (Exception exp) {
            return false;
        }
    }

    public String verifyHeaderElement(String pageName) {

        if (headerElements.size() == 0) {
            return "Header Elements are not loaded";
        }
        boolean isPresent = false;
        for (int i = 0; i < headerElements.size(); i++) {
            if (headerElements.get(i).getText().equalsIgnoreCase(pageName)) {
                isPresent = true;
                seleniumLib.highLightElement(headerElements.get(i));
                break;
            }
        }
        if (!isPresent) {
            return "Header element:" + pageName + " not present. Expected to be present.";
        }
        return "Success";
    }

    public String verifyApplicationFooter() {
        try {
            if (!seleniumLib.isElementPresent(grapheneFooterLogo)) {
                return "Graphene logo not loaded in the footer.";
            }
            seleniumLib.highLightElement(grapheneFooterLogo);
            if (!seleniumLib.isElementPresent(grapheneFooterCopyright)) {
                return "Graphene Copy rights not loaded in the footer.";
            }
            seleniumLib.highLightElement(grapheneFooterCopyright);
        } catch (Exception exp) {
            return "Exception from validating footer: " + exp;
        }
        return "Success";
    }

    public String verifyHelpIconAndLogout() {
        try {
            if (!seleniumLib.isElementPresent(helpIcon)) {
                return "On boarding message Help Icon loaded in the header.";
            }
            seleniumLib.highLightElement(helpIcon);
            if (!seleniumLib.isElementPresent(logoutButton)) {
                return "Logout button loaded in the header.";
            }
            seleniumLib.highLightElement(logoutButton);
        } catch (Exception exp) {
            return "Exception from validating Help Icon and Logout Button: " + exp;
        }
        return "Success";
    }

    public String navigateToPage(String pageName) {
        if (headerElements.size() == 0) {
            return "Header Pages are not loaded";
        }
        boolean isPresent = false;
        for (int i = 0; i < headerElements.size(); i++) {
            if (headerElements.get(i).getText().equalsIgnoreCase(pageName)) {
                isPresent = true;
                headerElements.get(i).click();
                break;
            }
        }
        if (!isPresent) {
            return "Page:" + pageName + " not loaded in Header.";
        }
        return "Success";
    }

}//end
