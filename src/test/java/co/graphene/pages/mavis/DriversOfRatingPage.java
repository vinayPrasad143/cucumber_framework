package co.graphene.pages.mavis;

import co.graphene.lib.SeleniumLib;
import co.graphene.util.Debugger;
import co.graphene.util.mavishr.KeyValue;
import co.graphene.util.mavishr.MAVIS_DATAS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DriversOfRatingPage {
    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class,'pb-1')]/h1")
    WebElement pageTitle;
    @FindBy(xpath = "//div[contains(@class,'pb-1')]/div/p/span")
    WebElement latestDataUpdateTitle;
    @FindBy(xpath = "//h1[contains(text(),'Employer Score Across All Sites')]/../../..//div[contains(@class,'brand-rating-container')]//div[contains(@class,'w-full')]/span")
    List<WebElement> uiEmployerNames;
    @FindBy(xpath = "//h1[contains(text(),'Employer Score Across All Sites')]/../../..//div[contains(@class,'brand-rating-container')]//div[contains(@class,'widthMin')]")
    List<WebElement> uiEmployerScores;
    @FindBy(xpath = "//h1[contains(text(),'Performance Against the Market')]/../../..//div[contains(@class,'progress')]//div[contains(@class,'w-full')]/div[contains(@class,'font-medium')]")
    List<WebElement> uiDriverNames;
    @FindBy(xpath = "//h1[contains(text(),'Performance Against the Market')]/../../..//div[contains(@class,'progress')]//div[contains(@class,'widthMin')]")
    List<WebElement> uiDriverScores;
    @FindBy(xpath = "//div[contains(@id,'headless')]//p[text()='Negative']")
    WebElement negativeDriver;


    public DriversOfRatingPage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver, 10);
    }
    //Page Title Validation
    public String verifyDriversOfRatingPageTitle(String expTitle){
        try {
            String actTitle = seleniumLib.getText(pageTitle);
            if(!expTitle.equalsIgnoreCase(actTitle)){
                return "Expected page Title:"+expTitle+",Actual page Title:"+actTitle;
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating verifyDriversOfRatingPageTitle:"+exp;
        }
    }
    //Latest Data uploaded information
    public String verifyLatestDataUploadTitle(String latestFromDb){
        try {
            //Check Page title
            String actualLatestDataInfo = seleniumLib.getText(latestDataUpdateTitle);
            Debugger.println("Latest Data Uploaded: "+actualLatestDataInfo);
            if(!(actualLatestDataInfo.startsWith("Latest data update shows upto") &&
                    actualLatestDataInfo.endsWith(latestFromDb))){
                return "Expected Latest Data update Info:"+latestFromDb+", Actual:"+actualLatestDataInfo;
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating verifyLatestDataUploadTitle:"+exp;
        }
    }
    public String verifyBrandScoreAndPositiveDriverRatingsSummaryView(MAVIS_DATAS mavisData){
        try {
            long startTime = System.currentTimeMillis();
            int category_key = 0;
            int country_key = 0;
            String time_label = "";
            List<KeyValue> categories = mavisData.getIndustryOptions();
            List<KeyValue> countries = mavisData.getCountryOptions();
            List<KeyValue> periods = mavisData.getPeriodOptions();
            String dbResult = "";
            String uiResult = "";
            String filterCombination = "";
            Debugger.println("Categories: "+categories.size()+",Countries: "+countries.size()+",Periods:"+periods.size());
            for (KeyValue category : categories) {//Each Industry - Category
                category_key = category.getKey();
                selectFilterCombination("Industry", category.getValue());
                for (KeyValue country : countries) {//Each Country
                    country_key = country.getKey();
                    selectFilterCombination("Country", country.getValue());
                    for (KeyValue period : periods) {//Each Period
                        time_label = period.getValue();
                        selectFilterCombination("Period", period.getValue());
                        dbResult = mavisData.getEmployerDriverSummaryViewPositiveScore(category_key, country_key, time_label);
                        if (!dbResult.equalsIgnoreCase("Success")) {
                            return "Could not retrieve data from database for filter combination:" + filterCombination;
                        }
                        //Validate against UI
                        Debugger.println("****COMBINATION: " + category.getValue() + "," + country.getValue() + "," + period.getValue());
                        Debugger.println("****EMPLOYER SCORE VALIDATION***");
                        uiResult = verifyEmployerScore(mavisData);
                        if (!uiResult.equalsIgnoreCase("Success")) {
                            Debugger.println("FAILED "+uiResult);
                            return uiResult + " For Filter Combination:" + filterCombination;
                        }
                        Debugger.println("****DRIVER SCORE VALIDATION***");
                        uiResult = verifyDriverScore(mavisData);
                        if (!uiResult.equalsIgnoreCase("Success")) {
                            Debugger.println("FAILED "+uiResult);
                            return uiResult + " For Filter Combination:" + filterCombination;
                        }
                    }//Period
                }//Country

            }//Category
            long endTime = System.currentTimeMillis();
            Debugger.println("Total Time Taken (Secs): "+(endTime-startTime)/(1000));
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyPositiveThemesPercentage:"+exp;
        }
    }
    public String verifyNegativeDriverRatingsSummaryView(MAVIS_DATAS mavisData){
        try {
            long startTime = System.currentTimeMillis();
            int category_key = 0;
            int country_key = 0;
            String time_label = "";
            List<KeyValue> categories = mavisData.getIndustryOptions();
            List<KeyValue> countries = mavisData.getCountryOptions();
            List<KeyValue> periods = mavisData.getPeriodOptions();
            String dbResult = "";
            String uiResult = "";
            String filterCombination = "";
            seleniumLib.clickOnWebElement(negativeDriver);
            Debugger.println("Categories: "+categories.size()+",Countries: "+countries.size()+",Periods:"+periods.size());
            for (KeyValue category : categories) {//Each Industry - Category
                category_key = category.getKey();
                selectFilterCombination("Industry", category.getValue());
                for (KeyValue country : countries) {//Each Country
                    country_key = country.getKey();
                    selectFilterCombination("Country", country.getValue());
                    for (KeyValue period : periods) {//Each Period
                        time_label = period.getValue();
                        selectFilterCombination("Period", period.getValue());
                        dbResult = mavisData.getEmployerDriverSummaryViewNegativeScore(category_key, country_key, time_label);
                        if (!dbResult.equalsIgnoreCase("Success")) {
                            return "Could not retrieve data from database for filter combination:" + filterCombination;
                        }
                        //Validate against UI
                        Debugger.println("****COMBINATION: " + category.getValue() + "," + country.getValue() + "," + period.getValue());
                        Debugger.println("****DRIVER SCORE VALIDATION***");
                        uiResult = verifyDriverScore(mavisData);
                        if (!uiResult.equalsIgnoreCase("Success")) {
                            Debugger.println("FAILED "+uiResult);
                            return uiResult + " For Filter Combination:" + filterCombination;
                        }
                    }//Period
                }//Country

            }//Category
            long endTime = System.currentTimeMillis();
            Debugger.println("Total Time Taken (Secs): "+(endTime-startTime)/(1000));
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyPositiveThemesPercentage:"+exp;
        }
    }
    public String selectFilterCombination(String filterName,String optionName){
        try {
            String filterNamePath = "//div[contains(@class,'text-gray-500')][text()='"+filterName+"']";
            By indicatorArrow = By.xpath(filterNamePath+"/..//div[contains(@class,'indicatorContainer')]");
            seleniumLib.clickOnElement(indicatorArrow);
            seleniumLib.sleepInSeconds(2);
            //Dynamic WebElement based on selection of filters
            By uiOption = By.xpath("//div[contains(@id,'react-select-')]//div/div");
            List<WebElement> uiOptions = driver.findElements(uiOption);
            for (WebElement option : uiOptions) {
                if (option.getText().equalsIgnoreCase(optionName)) {
                    option.click();
                    break;
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in selectFilterCombination:"+exp;
        }
    }

    public String verifyEmployerScore(MAVIS_DATAS mavisDatas){
        try {
            String uiEmployerName = "";
            String dbEmployerScore = "";
            String uiEmployerScore = "";
            for(int i=0; i<uiEmployerNames.size(); i++) {
                uiEmployerName = seleniumLib.getText(uiEmployerNames.get(i));
                dbEmployerScore = mavisDatas.getEmployerScoreFor(uiEmployerName);
                uiEmployerScore = seleniumLib.getText(uiEmployerScores.get(i));

                if(!dbEmployerScore.equalsIgnoreCase(uiEmployerScore)){
                    Debugger.println("\tEmployer:"+uiEmployerName+" Employer DB Score:"+dbEmployerScore+", UI Score:"+uiEmployerScore+" - FAILED");
                    return "Employer Score mismatch. Theme:"+uiEmployerName+", Expected:"+dbEmployerScore+",Actual:"+uiEmployerScore;
                }
                Debugger.println("\tEmployer:"+uiEmployerName+" DB Score:"+dbEmployerScore+", UI Score:"+uiEmployerScore+" - PASS");
            }
            return "Success";
        }catch (Exception exp) {
            return "Exception in verifyEmployerScore:"+exp;
        }
    }
    public String verifyDriverScore(MAVIS_DATAS mavisDatas){
        try {
            String uiEmployerName = "";
            String uiDriverName = "";
            String uiDriverScore = "";
            String dbDriverScore = "";
            for(int i=0; i<uiEmployerNames.size(); i++) {//For each Employer
                uiEmployerName = seleniumLib.getText(uiEmployerNames.get(i));
                seleniumLib.clickOnWebElement(uiEmployerNames.get(i));
                for(int j=0; j<uiDriverNames.size(); j++) {//For each Driver from UI
                    uiDriverName = seleniumLib.getText(uiDriverNames.get(j));
                    dbDriverScore = mavisDatas.getPositiveDriverScoreFor(uiEmployerName, uiDriverName);
                    uiDriverScore = seleniumLib.getText(uiDriverScores.get(j));
                    if (!dbDriverScore.equalsIgnoreCase(uiDriverScore)) {
                        Debugger.println("\tEmployer:" + uiEmployerName + ",Driver: " + uiDriverName + " DB Score:" + dbDriverScore + ", UI Score:" + uiDriverScore + " - FAILED");
                        return "Driver Score mismatch. Driver:" + uiDriverName + ", Expected:" + dbDriverScore + ",Actual:" + uiDriverScore;
                    }
                    Debugger.println("\t\tEmployer:"+uiEmployerName+",Driver:"+uiDriverName+" DB Score:"+dbDriverScore+", UI Score:"+uiDriverScore+" - PASS");
                }
            }
            return "Success";
        }catch (Exception exp) {
            return "Exception in verifyDriverScore:"+exp;
        }
    }

}//end
