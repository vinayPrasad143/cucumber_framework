package co.graphene.pages.mavis;

import co.graphene.lib.SeleniumLib;
import co.graphene.util.Debugger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MavisHomePage {
    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    //Page elements
    @FindBy(xpath = "//div[contains(@class,'pb-1')]/div/p/span")
    WebElement latestDataUpdateTitle;
    @FindBy(xpath = "//div[contains(@class,'w-auto')]//div[contains(@class,'mx-auto w-full')]/div[contains(@class,'text-left')]")
    List<WebElement> filterLabels;
    @FindBy(xpath = "//button[@id='headlessui-menu-button-1']")
    WebElement userIcon;
    @FindBy(xpath = "//div[@id='headlessui-radiogroup-4']//p")
    List<WebElement> tabNames;
    @FindBy(xpath = "//h1[contains(@class,'flex items-center')]")
    List<WebElement> sectionTitles;
    @FindBy(xpath = "//div[contains(@class,'brand-rating-container')]/div//span[contains(@data-tip,'true')]")
    List<WebElement> brandRatingNames;
    @FindBy(xpath = "//div[contains(@class,'brand-rating-container')]//div[contains(@class,'flex items-center w-36  font-medium')]")
    List<WebElement> brandRatingScores;
    @FindBy(xpath = "//div[@class='mx-auto w-full']//h1[@class='flex items-center']")
    WebElement brandTitleForDriverRating;
    @FindBy(xpath = "//a[@href='/branddeepdive']")
    WebElement deepDiveLink;
    @FindBy(xpath = "//a[@href='/driversofrating']")
    WebElement driversOfRestingLink;


    public MavisHomePage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public String navigateToDeepDive(){
        try {
            //Check Page title
            if(!seleniumLib.isElementPresent(deepDiveLink)){
                return "Could not locate DeepDive Link";
            }
            seleniumLib.clickOnWebElement(deepDiveLink);
            return "Success";
        } catch (Exception exp) {
            return "Exception in navigateToDeepDive:"+exp;
        }
    }
    public String navigateToDriversOfRating(){
        try {
            //Check Page title
            if(!seleniumLib.isElementPresent(driversOfRestingLink)){
                return "Could not locate Drivers of Rating Link";
            }
            seleniumLib.clickOnWebElement(driversOfRestingLink);
            return "Success";
        } catch (Exception exp) {
            return "Exception in navigateToDriversOfRating:"+exp;
        }
    }

    public String verifyMavisHomePageLoading(String pTitle){
        try {
            //Check browser Title
            String browserTitle = driver.getTitle();
            Debugger.println("Browser Title: "+browserTitle);
            if(browserTitle == null || !browserTitle.contains("Mavis HR")){
                return "Mavis Home Page is not loaded";
            }
            //Check Page title
            By pageTitle = By.xpath("//h1[contains(text(),'"+pTitle+"')]");
            if(!seleniumLib.isElementPresent(pageTitle)){
                return "Expected page Title "+pTitle+" not loaded on Mavis Home Page";
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating Mavis Home Page:"+exp;
        }
    }
    public boolean verifyLatestDataUploadTitlePresence(){
        try {
            if(driver.getCurrentUrl().contains("mavis-hr")) {
                return true;
            }
            return false;
        } catch (Exception exp) {
            return false;
        }
    }
    public String verifyFilterElement(List<List<String>> filters){
        try {
            boolean isPresent = false;
            for (int i = 0; i < filters.size(); i++) {
                isPresent = false;
                for (int j = 0; j < filterLabels.size(); j++) {
                    if (seleniumLib.getText(filterLabels.get(j)).equals(filters.get(i).get(0))) {
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    return "Expected Filter " + filters.get(i).get(0) + " not present";
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyFilterElement:"+exp;
        }
    }
    public String verifyPresenceOfUserIcon(){
        try {
            //Check Page title
            if(!seleniumLib.isElementPresent(userIcon)){
                return "User Icon not present in the Mavis Header Page";
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating verifyPresenceOfUserIcon:"+exp;
        }
    }
    public String verifyTabElement(List<List<String>> tabs){
        try {
            boolean isPresent = false;
            for (int i = 0; i < tabs.size(); i++) {
                isPresent = false;
                for (int j = 0; j < tabNames.size(); j++) {
                    if (seleniumLib.getText(tabNames.get(j)).equals(tabs.get(i).get(0))) {
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    return "Expected Tab " + tabs.get(i).get(0) + " not present";
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyTabElement:"+exp;
        }
    }
    public String selectTabUnderDriversOfEmployerRating(String tabName){
        try {
            boolean isPresent = false;
            for (int j = 0; j < tabNames.size(); j++) {
                if (seleniumLib.getText(tabNames.get(j)).equals(tabName)) {
                    tabNames.get(j).click();
                    break;
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in selectTabUnderDriversOfEmployerRating:"+exp;
        }
    }
    public String verifySectionTitles(List<List<String>> titles){
        try {
            boolean isPresent = false;
            for (int i = 0; i < titles.size(); i++) {
                isPresent = false;
                for (int j = 0; j < sectionTitles.size(); j++) {
                    if (seleniumLib.getText(sectionTitles.get(j)).contains(titles.get(i).get(0))) {
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    return "Expected Tab " + titles.get(i).get(0) + " not present";
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifySectionTitles:"+exp;
        }
    }
    public String verifyEmployerRatingScoreAcrossSites(){
        try {
            //Should be getting the List of Brands from Database for complete validation
            String brandName = "";
            String brandScore = "";
            String driverRatingTitle = "";
            if(brandRatingNames.size() == 0){
                return "Drivers of Employer Rating Details not loaded.";
            }
            Debugger.println("Drivers of Rating Brand and Scores");
            for (int j = 0; j < brandRatingNames.size(); j++) {

                brandName = seleniumLib.getText(brandRatingNames.get(j));
                brandScore = seleniumLib.getText(brandRatingScores.get(j));
                Debugger.println("Brand:"+brandName+", Score:"+brandScore);
                seleniumLib.sleepInSeconds(1);
                try {
                    seleniumLib.clickOnWebElement(brandRatingNames.get(j));
                }catch(Exception exp){
                    seleniumLib.scrollToElement(brandRatingNames.get(j));
                    seleniumLib.sleepInSeconds(1);
                    seleniumLib.clickOnWebElement(brandRatingNames.get(j));
                }
                seleniumLib.sleepInSeconds(1);
                driverRatingTitle = seleniumLib.getText(brandTitleForDriverRating);
                if(!driverRatingTitle.contains(brandName)){
                    return "Driver Rating Title not loaded for the Brand "+brandName;
                }
                seleniumLib.sleepInSeconds(2);
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyBrandRatingNamesScore:"+exp;
        }
    }
}//end
