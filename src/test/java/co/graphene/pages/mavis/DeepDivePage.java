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

import java.util.ArrayList;
import java.util.List;

//This is a test comment for checking

public class DeepDivePage {
    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class,'pb-1')]/h1")
    WebElement pageTitle;
    @FindBy(xpath = "//div[contains(@class,'pb-1')]/div/p/span")
    WebElement latestDataUpdateTitle;
    @FindBy(xpath = "//h1[contains(text(),'Positive Themes')]/../..//div[contains(@class,'brand-rating-container')]/div[contains(@class,'container')]//span")
    List<WebElement> uiPositiveThemeNames;
    @FindBy(xpath = "//h1[contains(text(),'Positive Themes')]/../..//div[contains(@class,'brand-rating-container')]/div[contains(@class,'container')]//p")
    List<WebElement> uiPositiveThemeScores;
    @FindBy(xpath = "//h1[contains(text(),'Negative Themes')]/../..//div[contains(@class,'brand-rating-container')]/div[contains(@class,'container')]//span")
    List<WebElement> uiNegativeThemeNames;
    @FindBy(xpath = "//h1[contains(text(),'Negative Themes')]/../..//div[contains(@class,'brand-rating-container')]/div[contains(@class,'container')]//p")
    List<WebElement> uiNegativeThemeScores;

    public DeepDivePage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver, 10);
    }
    //Page Title Validation
    public String verifyDeepDivePageTitle(String expTitle){
        try {
            String actTitle = seleniumLib.getText(pageTitle);
            if(!expTitle.equalsIgnoreCase(actTitle)){
                return "Expected page Title:"+expTitle+",Actual page Title:"+actTitle;
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating verifyDeepDivePageTitle:"+exp;
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
    public String verifyFilterOptions(String filterName,List<String> expectedOptions){
        try {
            //Debugger.println("Filter Name:"+filterName);
           // Debugger.println("DB...:"+expectedOptions);
            String filterNamePath = "//div[contains(@class,'text-gray-500')][text()='"+filterName+"']";
            //Check if Filter name present
            By filter = By.xpath(filterNamePath);
            if(!seleniumLib.isElementPresent(filter)){
                return "Filter "+filterName+" Not Loaded in the page";
            }

            By indicatorArrow = By.xpath(filterNamePath+"/..//div[contains(@class,'indicatorContainer')]");
            seleniumLib.clickOnElement(indicatorArrow);
            seleniumLib.sleepInSeconds(2);
            //Dynamic WebElement based on selection of filters
            By uiOption = By.xpath("//div[contains(@id,'react-select-')]//div/div");
            List<WebElement> uiOptions = driver.findElements(uiOption);
            List<String> actualOptions = new ArrayList<String>();
            for(int i=0; i<uiOptions.size(); i++){
                actualOptions.add(uiOptions.get(i).getText());
            }
            //Debugger.println("UI...:"+actualOptions);
            //Checking the number of options
            if(expectedOptions.size() != actualOptions.size()){
                return "Filter Name:"+filterName+", Expected:"+expectedOptions+",Actual:"+actualOptions;
            }
            //Checking the Contents
            for(int i=0; i<expectedOptions.size(); i++){
                if(!(actualOptions.contains(expectedOptions.get(i)))){
                    return "Filter Name:"+filterName+", Expected:"+expectedOptions+",Actual:"+actualOptions;
                }
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in validating verifyFilterOptions:"+exp;
        }
    }
    public String selectFilterCombination(String filterName,String optionName){
        try {
            String filterNamePath = "//div[contains(@class,'text-gray-500')][text()='"+filterName+"']";
            By indicatorArrow = By.xpath(filterNamePath+"/..//div[contains(@class,'indicatorContainer')]");
            seleniumLib.sleepInSeconds(2);
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
    public String verifyPositiveThemesForAllCombinations(MAVIS_DATAS mavisData){
        String vMessage = "";
        try {
            long startTime = System.currentTimeMillis();

            String time_label = "";
            List<KeyValue> categories = mavisData.getIndustryOptions();
            List<KeyValue> brands = mavisData.getOrganizationOptions();
            List<KeyValue> countries = mavisData.getCountryOptions();
            List<KeyValue> periods = mavisData.getPeriodOptions();
            String dbResult = "";
            String uiResult = "";
            String filterCombination = "";
           // Debugger.println("Categories: "+categories.size()+",Brands: "+brands.size()+",Countries: "+countries.size()+",Periods:"+periods.size());
            for (KeyValue category : categories) {//Each Industry - Category
                selectFilterCombination("Industry", category.getValue());
                for (KeyValue brand : brands) {//Each Organization - Brand
                    if (brand.getValue().equalsIgnoreCase("Benchmark")) {//Bench Mark
                        continue;
                    }
                    selectFilterCombination("Organization", brand.getValue());
                    for (KeyValue country : countries) {//Each Country
                        selectFilterCombination("Country", country.getValue());
                        for (KeyValue period : periods) {//Each Period
                            selectFilterCombination("Period", period.getValue());
                            dbResult = mavisData.readDeepDivePositiveThemeData(category.getValue(), brand.getValue(), country.getValue(), time_label);
                            if (!dbResult.equalsIgnoreCase("Success")) {
                                return "Could not retrieve data from database for filter combination:" + filterCombination;
                            }
                            //Validate against UI
                            Debugger.println("****COMBINATION: " + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue());
                            Debugger.println("****POSITIVE THEMES VALIDATION***");
                            uiResult = verifyPositiveThemesAndScores(mavisData);
                            if (!uiResult.equalsIgnoreCase("Success")) {
                                vMessage += uiResult + ".Combination:" + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue()+"\n";
                                //return uiResult + " For Filter Combination:" + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue();
                            }
                        }//Period
                    }//Country
                }//Brand
            }//Category
            long endTime = System.currentTimeMillis();
            Debugger.println("Total Time Taken (Secs): "+(endTime-startTime)/(1000));
            if(!vMessage.isEmpty()) {
                return vMessage;
            }
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyPositiveThemesPercentage:"+exp;
        }
    }
    public String verifyNegativeThemesForAllCombinations(MAVIS_DATAS mavisData){
        try {
            long startTime = System.currentTimeMillis();
            String time_label = "";
            List<KeyValue> categories = mavisData.getIndustryOptions();
            List<KeyValue> brands = mavisData.getOrganizationOptions();
            List<KeyValue> countries = mavisData.getCountryOptions();
            List<KeyValue> periods = mavisData.getPeriodOptions();
            String dbResult = "";
            String uiResult = "";
            String filterCombination = "";
            // Debugger.println("Categories: "+categories.size()+",Brands: "+brands.size()+",Countries: "+countries.size()+",Periods:"+periods.size());
            for (KeyValue category : categories) {//Each Industry - Category
                selectFilterCombination("Industry", category.getValue());
                for (KeyValue brand : brands) {//Each Organization - Brand
                    if (brand.getValue().equalsIgnoreCase("Benchmark")) {//Bench Mark
                        continue;
                    }
                    selectFilterCombination("Organization", brand.getValue());
                    for (KeyValue country : countries) {//Each Country
                        selectFilterCombination("Country", country.getValue());
                        for (KeyValue period : periods) {//Each Period
                            time_label = period.getValue();
                            selectFilterCombination("Period", period.getValue());
                            dbResult = mavisData.readDeepDiveNegativeThemeData(category.getValue(), brand.getValue(), country.getValue(), time_label);
                            if (!dbResult.equalsIgnoreCase("Success")) {
                                return "Could not retrieve data from database for filter combination:" + filterCombination;
                            }
                            //Validate against UI
                            Debugger.println("****COMBINATION: " + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue());
                            Debugger.println("****NEGATIVE THEMES VALIDATION***");
                            uiResult = verifyNegativeThemesAndScores(mavisData);
                            if (!uiResult.equalsIgnoreCase("Success")) {
                                return uiResult + " For Filter Combination:" + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue();
                            }
                        }//Period
                    }//Country
                }//Brand
            }//Category
            long endTime = System.currentTimeMillis();
            Debugger.println("Total Time Taken (mins): "+(endTime-startTime)/(1000*60));
            return "Success";
        } catch (Exception exp) {
            return "Exception in verifyPositiveThemesPercentage:"+exp;
        }
    }
    public String verifyPositiveThemesAndScores(MAVIS_DATAS mavisDatas){
        try {
          //Check the number of themes loaded.
            if(mavisDatas.noOfPositiveThemes() != uiPositiveThemeNames.size()){
                return "#PositiveThemes Expected:"+mavisDatas.noOfPositiveThemes()+",Actual:"+uiPositiveThemeNames.size();
            }
            String uiThemeName = "";
            String dbThemeScore = "";
            String uiThemeScore = "";
            for(int i=0; i<uiPositiveThemeNames.size(); i++) {
                uiThemeName = seleniumLib.getText(uiPositiveThemeNames.get(i));
                if(uiThemeName.startsWith("Innovative Rewards") ||
                        uiThemeName.startsWith("Retirement")){
                    continue;
                }
                dbThemeScore = mavisDatas.getPositiveThemePercentFor(uiThemeName);
                uiThemeScore = seleniumLib.getText(uiPositiveThemeScores.get(i));
                dbThemeScore += "%";
                if(!dbThemeScore.equalsIgnoreCase(uiThemeScore)){
                    return "Theme:"+uiThemeName+", Expected:"+dbThemeScore+",Actual:"+uiThemeScore;
                    //Debugger.println("\tTheme:"+uiThemeName+" DB Score:"+dbThemeScore+", UI Score:"+uiThemeScore+" - FAILED");
                }
                Debugger.println("\tTheme:"+uiThemeName+" DB Score:"+dbThemeScore+", UI Score:"+uiThemeScore+" - PASS");
            }

            return "Success";
        }catch (Exception exp) {
            return "Exception in verifyPositiveThemesAndScores:"+exp;
        }
    }
    public String verifyNegativeThemesAndScores(MAVIS_DATAS mavisDatas){
        try {
            //Check the number of themes loaded.
            if(mavisDatas.noOfNegativeThemes() != uiNegativeThemeNames.size()){
                return "#Negative themes. Expected:"+mavisDatas.noOfNegativeThemes()+",Actual:"+uiNegativeThemeNames.size();
            }
            String uiThemeName = "";
            String dbThemeScore = "";
            String uiThemeScore = "";
            for(int i=0; i<uiNegativeThemeNames.size(); i++) {
                uiThemeName = seleniumLib.getText(uiNegativeThemeNames.get(i));
                dbThemeScore = mavisDatas.getNegativeThemePercentFor(uiThemeName);
                uiThemeScore = seleniumLib.getText(uiNegativeThemeScores.get(i));
                dbThemeScore += "%";
                if(!dbThemeScore.equalsIgnoreCase(uiThemeScore)){
                    //Debugger.println("\tTheme:"+uiThemeName+" DB Score:"+dbThemeScore+", UI Score:"+uiThemeScore+" - FAILED");
                    return "Theme:"+uiThemeName+", Expected:"+dbThemeScore+",Actual:"+uiThemeScore;
                }
                Debugger.println("\tTheme:"+uiThemeName+" DB Score:"+dbThemeScore+", UI Score:"+uiThemeScore+" - PASS");
            }
            return "Success";
        }catch (Exception exp) {
            return "Exception in verifyNegativeThemesAndScores:"+exp;
        }
    }

    //For Specific Combinations
    public String verifyPositiveThemesForSpecificCombination(String industry,String organization,String country,String period,MAVIS_DATAS mavisData) {
        String vMessage = "";
        try {
            Debugger.println("****COMBINATION: " + industry + "," + organization + "," + country + "," + period);
            selectFilterCombination("Industry", industry);
            selectFilterCombination("Organization", organization);
            seleniumLib.sleepInSeconds(2);//Wait for 5 seconds to ensure data is loaded
            selectFilterCombination("Country", country);
            selectFilterCombination("Period", period);
            seleniumLib.sleepInSeconds(5);//Wait for 5 seconds to ensure data is loaded
            String dbResult = mavisData.readDeepDivePositiveThemeData(industry, organization, country, period);
            if (!dbResult.equalsIgnoreCase("Success")) {
                return "Could not retrieve data from database for filter combination:";
            }
            String uiResult = verifyPositiveThemesAndScores(mavisData);
            if (!uiResult.equalsIgnoreCase("Success")) {
                vMessage += uiResult + ".Combination:" + industry + "," + organization + "," + country + "," + period+"\n";
                //return uiResult + " For Filter Combination:" + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue();
            }
            if(!vMessage.isEmpty()) {
                return vMessage;
            }
            return "Success";

        } catch (Exception exp) {
            return "Exception from verifyPositiveThemesForSpecificCombination:"+exp;
        }
    }
    //For Specific Combinations
    public String verifyNegativeThemesForSpecificCombination(String industry,String organization,String country,String period,MAVIS_DATAS mavisData) {
        String vMessage = "";
        try {
            Debugger.println("****COMBINATION: " + industry + "," + organization + "," + country + "," + period);
            selectFilterCombination("Industry", industry);
            selectFilterCombination("Organization", organization);
            seleniumLib.sleepInSeconds(2);//Wait for 5 seconds to ensure data is loaded
            selectFilterCombination("Country", country);
            selectFilterCombination("Period", period);
            seleniumLib.sleepInSeconds(5);//Wait for 5 seconds to ensure data is loaded
            String dbResult = mavisData.readDeepDiveNegativeThemeData(industry, organization, country, period);
            if (!dbResult.equalsIgnoreCase("Success")) {
                return "Could not retrieve data from database for filter combination:";
            }
            String uiResult = verifyNegativeThemesAndScores(mavisData);
            if (!uiResult.equalsIgnoreCase("Success")) {
                vMessage += uiResult + ".Combination:" + industry + "," + organization + "," + country + "," + period+"\n";
                //return uiResult + " For Filter Combination:" + category.getValue() + "," + brand.getValue() + "," + country.getValue() + "," + period.getValue();
            }
            if(!vMessage.isEmpty()) {
                return vMessage;
            }
            return "Success";

        } catch (Exception exp) {
            return "Exception from verifyNegativeThemesForSpecificCombination:"+exp;
        }
    }

}//end
