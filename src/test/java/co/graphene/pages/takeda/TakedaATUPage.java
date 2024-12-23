package co.graphene.pages.takeda;

import co.graphene.lib.SeleniumLib;
import co.graphene.util.Debugger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TakedaATUPage {

    private SeleniumLib seleniumLib;
    WebDriver driver;
    WebDriverWait wait;

    public TakedaATUPage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
        wait = new WebDriverWait(driver,10);
    }

    public String validateBrandsDisplay(String bu,String benchmark,String indication,List<String> brands){
        Debugger.println("BU: "+bu);
        Debugger.println("\tBenchMark: "+benchmark);
        Debugger.println("\t\tIndication: "+indication);
        for(int i=0;i<brands.size(); i++){
            Debugger.println("\t\t\tBrand: "+brands.get(i));
        }
        //Write code to get the Brand name elements and validate the presence
        //Select the BU
        //Select the bench mark
        //Select the indication
        // Validate the brand list
        return "Success";
    }
    public String validateCountriesDisplay(String bu,String benchmark,List<String> countries){
        Debugger.println("BU: "+bu);
        Debugger.println("\tBenchMark: "+benchmark);
        for(int i=0;i<countries.size(); i++){
            Debugger.println("\t\tCountry: "+countries.get(i));
        }
        //Write code to get the Brand name elements and validate the presence
        //Select the BU
        //Select the bench mark
        // Validate the Country list
        return "Success";
    }
}//end
