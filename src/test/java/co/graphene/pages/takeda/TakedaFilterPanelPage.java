package co.graphene.pages.takeda;

import co.graphene.lib.SeleniumLib;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TakedaFilterPanelPage {

    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@id='filterIntro']//a[@id='dropdownMenuBrand']/span")
    List<WebElement> filterPanelNames;

    @FindBy(xpath = "//div[@id='filterIntro']//a[@id='dropdownMenuBrand']/div")
    List<WebElement> filterPanelDefaults;

    @FindBy(xpath = "//div[@id='sectionInfo']//a[@class='info-icon']")
    WebElement infoIcon;

    @FindBy(xpath = "//div[@id='filter-navbar']//span[@class='latestdate']/span")
    WebElement latestDbUpdate;

    @FindBy(xpath = "//div[@id='modal-popup']//div[@class='modal-content']/div[contains(@class,'modal-header')]")
    WebElement infoTitle;

    public TakedaFilterPanelPage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver,10);
    }

    public String verifyFilterPanelPosition(String panelName,int expPosition) {
        SeleniumLib.sleepInSeconds(1);
        if(filterPanelNames.size() == 0){
            return "Filter Panels are not loaded.";
        }
        boolean isPresent = false;
        String actPanel="",actDefault="";
        actPanel = filterPanelNames.get(expPosition).getText();
        if(actPanel.equalsIgnoreCase(panelName)) {
            seleniumLib.highLightElement(filterPanelNames.get(expPosition));
            isPresent = true;
        }
        if(!isPresent){
            return "Filter panel "+panelName+" not loaded in the expected position: "+expPosition;
        }
        return "Success";
    }
    public String verifyInfoIconAndLastDBUpdate(String lastDbUpdate) {
        if (!seleniumLib.isElementPresent(infoIcon)) {
            return "Info Icon not loaded.";
        }
        String actDbMessage = latestDbUpdate.getText();
        if(!actDbMessage.trim().equalsIgnoreCase(lastDbUpdate)){
            return "Latest DBUpdate message mismatch: actual:"+actDbMessage+", Expected:"+lastDbUpdate;
        }
        return "Success";
    }

    public String clickOnInfoIcon(){
        try{
            infoIcon.click();
            return "Success";
        }catch(Exception exp){
            return "Exception in clicking Info Icon"+exp;
        }
    }

    public String verifyInfoTitle(String title){
        try{
            if(!infoTitle.getText().equalsIgnoreCase(title)){
                return "Expected info title is:"+title+", but actual:"+infoTitle.getText();
            }
            return "Success";
        }catch(Exception exp){
            return "Exception in Verifying the title of Info message:"+exp;
        }
    }

}//end
