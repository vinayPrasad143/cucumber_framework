package co.graphene.pages;

import co.graphene.config.AppConfig;
import co.graphene.lib.SeleniumLib;
import co.graphene.util.Debugger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AccessPointHome {
    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//h1[@Class='header-title']")
    WebElement loginTitle;

    @FindBy(xpath = "//p[@class='login-name'][contains(text(),'Graphene')]/../a")
    WebElement grapheneLogin;

    @FindBy(xpath = "//div[@class='dashBoardHeader']/span")
    WebElement accessPointTitle;

    @FindBy(xpath = "//ul/li[@role='presentation']/a[contains(text(),'Application')]")
    WebElement accessPointApplicationsTab;

    @FindBy(xpath = "//div[@id='application-tiles']//input")
    WebElement accessPointSearch;

    @FindBy(xpath = "//div[@id='application-tiles']//div[@class='BrandName']")
    List<WebElement> accessPointBrandTiles;

    @FindBy(xpath = "//span[contains(text(),'Login As External User')]/../../a")
    WebElement externalLogin;

    @FindBy(xpath = "//input[@type='email']")
    WebElement loginEmailTB;

    @FindBy(xpath = "//input[@type='password']")
    WebElement loginPasswordTB;

    @FindBy(xpath = "//input[@type='submit']")
    WebElement next_signInBut;

    @FindBy(xpath = "//input[@type='button']")
    WebElement staySignedInNoBut;

    public AccessPointHome(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver, 10);
    }
    public String searchAndNavigateToApplication(String appName) {
        try {
            //First time accessing, Login to the application via Access Point
            //1. Load Graphene Access point
            String stepResult = loadGrapheneAccessPoint();
            if(!stepResult.equalsIgnoreCase("Success")){
                return stepResult;
            }
            //2. Login to the Access point
            stepResult = logInToGrapheneAccessPoint();
            if(!stepResult.equalsIgnoreCase("Success")){
                return stepResult;
            }
            wait.until(ExpectedConditions.visibilityOf(accessPointTitle));
            if (!accessPointTitle.getText().equalsIgnoreCase("Access Point")) {
                return "Access point home page not loaded properly";
            }
            stepResult = navigateToSpecifiedApplication(appName);

            return stepResult;
        } catch (Exception exp) {
            return "Failed to Access Point Page" + exp;
        }
    }
    public String directlyNavigateToApplication(String appURL) {
        try {
            driver.get(appURL);
            wait.until(ExpectedConditions.visibilityOf(loginTitle));
            String actTitleName = loginTitle.getText();
            if (actTitleName == null || !actTitleName.contains("LOGIN USING")) {
                return "Login Page for Access point not loaded.";
            }
            return logInToGrapheneAccessPoint();
        } catch (Exception exp) {
            return "Failed to Access Point Page" + exp;
        }
    }

    private String loadGrapheneAccessPoint() {
        //The URL will be reading from the Config File.
        Debugger.println("Accessing Graphene Access Point.......................");
        String accessPointUrl = AppConfig.getPropertyValue("GRAPHENE_ACCESS_POINT_URL");
        Debugger.println("Graphene Access Point URL: "+accessPointUrl);
        if (accessPointUrl == null || accessPointUrl.isEmpty()) {
            return "Graphene Access point URL is empty. Please provide URL in configuration file  graphene_accesspoint.properties";
        }
        driver.get(accessPointUrl);
        wait.until(ExpectedConditions.visibilityOf(loginTitle));
        String actTitleName = loginTitle.getText();
        if (actTitleName == null || !actTitleName.contains("LOGIN USING")) {
            return "Login Page for Access point not loaded.";
        }
        return "Success";
    }

    private String loadDirectApplication() {
        //The URL will be reading from the Config File.
        Debugger.println("Accessing Graphene Access Point.......................");
        String accessPointUrl = AppConfig.getPropertyValue("GRAPHENE_ACCESS_POINT_URL");
        Debugger.println("Graphene Access Point URL: "+accessPointUrl);
        if (accessPointUrl == null || accessPointUrl.isEmpty()) {
            return "Graphene Access point URL is empty. Please provide URL in configuration file  graphene_accesspoint.properties";
        }
        driver.get(accessPointUrl);
        wait.until(ExpectedConditions.visibilityOf(loginTitle));
        String actTitleName = loginTitle.getText();
        if (actTitleName == null || !actTitleName.contains("LOGIN USING")) {
            return "Login Page for Access point not loaded.";
        }
        return "Success";
    }

    private String logInToGrapheneAccessPoint() {
        String userName = AppConfig.getPropertyValue("USER_NAME");
        String password = AppConfig.getPropertyValue("PASSWORD");
        String userType = AppConfig.getPropertyValue("USER_TYPE");
        if (userType == null || userType.isEmpty()) {
            userType = "Graphene";//Considering default user type as Graphene
        }
        if (userType.equalsIgnoreCase("Graphene")) {
            grapheneLogin.click();
        } else {
            externalLogin.click();//To be tested this flow
        }
        if (userName == null || userName.isEmpty()) {
            return "userName is empty. Please provide userName in properties file";
        }
        if (password == null || password.isEmpty()) {
            return "password is empty. Please provide userName in properties file";
        }
        seleniumLib.sleepInSeconds(2);
        loginEmailTB.sendKeys(userName);
        seleniumLib.sleepInSeconds(2);
        next_signInBut.click();
        seleniumLib.sleepInSeconds(2);
        loginPasswordTB.sendKeys(password);
        seleniumLib.sleepInSeconds(2);
        next_signInBut.click();
        seleniumLib.sleepInSeconds(5);
        staySignedInNoBut.click();
        seleniumLib.sleepInSeconds(2);
        return "Success";
    }
    public String navigateToSpecifiedApplication(String appName){
        try{
            //Click on Applications Tab
            seleniumLib.clickOnWebElement(accessPointApplicationsTab);

            seleniumLib.sendValue(accessPointSearch,appName);
            seleniumLib.sleepInSeconds(3);
            //Click on the Tile
            int noOfTiles = accessPointBrandTiles.size();
            if(noOfTiles == 0){
                return "No Tiles for application "+appName+" present in Access Point";
            }
            if(noOfTiles > 0){
                //return "Multiple tiles present with the given app Name. Please specify unique tile name.";
                Debugger.println("Multiple tiles present with the given app Name. Considering first one and going forward.");
            }
            accessPointBrandTiles.get(0).click();
            seleniumLib.sleepInSeconds(3);
            seleniumLib.ChangeWindow();
            Debugger.println("Title After Switch: "+driver.getTitle());
            return "Success";
        }catch(Exception exp){
            return "Could not navigate to specified application "+exp;
        }
    }
}//end

