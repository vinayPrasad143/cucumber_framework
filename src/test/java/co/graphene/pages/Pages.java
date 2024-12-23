package co.graphene.pages;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.mavis.DeepDivePage;
import co.graphene.pages.mavis.DriversOfRatingPage;
import co.graphene.pages.mavis.MavisHomePage;
import co.graphene.pages.mavis.MavisSideNavigationPage;
import co.graphene.pages.takeda.TakedaATUPage;
import co.graphene.pages.takeda.TakedaFilterPanelPage;
import co.graphene.pages.takeda.TakedaHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Pages {

    protected WebDriver driver;

    protected AccessPointHome accessPointHome;
    //We have to initialize all the Pages Created in this class.
    //Takeda
    protected TakedaHomePage takedaHome;
    protected TakedaFilterPanelPage takedaFilterPanel;
    protected TakedaATUPage takedaAtu;
    //Mavis
    protected MavisHomePage mavisHomePage;
    protected DeepDivePage deepDivePage;
    protected MavisSideNavigationPage mavisSideNavigationPage;
    protected DriversOfRatingPage driversOfRatingPage;

    public Pages(SeleniumDriver driver) {
        this.driver = driver;
        PageObjects();
    }

    public void PageObjects() {
        accessPointHome = PageFactory.initElements(driver, AccessPointHome.class);
        //Takeda
        takedaHome          = PageFactory.initElements(driver, TakedaHomePage.class);
        takedaFilterPanel   = PageFactory.initElements(driver, TakedaFilterPanelPage.class);
        takedaAtu           = PageFactory.initElements(driver, TakedaATUPage.class);
        //Mavis
        mavisHomePage       = PageFactory.initElements(driver, MavisHomePage.class);
        deepDivePage        = PageFactory.initElements(driver, DeepDivePage.class);
        mavisSideNavigationPage=PageFactory.initElements(driver, MavisSideNavigationPage.class);
        driversOfRatingPage = PageFactory.initElements(driver, DriversOfRatingPage.class);
    }
}//end class
