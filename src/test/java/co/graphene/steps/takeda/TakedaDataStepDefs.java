package co.graphene.steps.takeda;

import co.graphene.config.SeleniumDriver;
import co.graphene.pages.Pages;
import co.graphene.util.takeda.TAKEDA_DATAS;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import org.junit.Assert;

import java.util.List;

public class TakedaDataStepDefs extends Pages {

    TAKEDA_DATAS takeda_datas;

    public TakedaDataStepDefs(SeleniumDriver driver) {

        super(driver);
        takeda_datas = new TAKEDA_DATAS();
    }
    //Data Validations
    @Given("^the user is able to read the (.*) page sp outputs from the database$")
    public void userIsReadingATUPageSPOutFromDatabase(String pageName) {
        String vMessage = "";
        if(pageName.equalsIgnoreCase("ATU")){
            vMessage = takeda_datas.readATUPageData("usp_FT_CP_ACT");
        }else if(pageName.equalsIgnoreCase("MCE Touchpoints")){
            //vMessage = takeda_datas.readATUPageData("MCESP");
        }
        if (!vMessage.equalsIgnoreCase("Success")) {
            Assert.fail(vMessage);
        }
    }

    @And("^Verify that brands loaded correctly for each bu,benchmark and indication combination$")
    public void theUserShouldSeeAllTheBrandsLoadedFromDatabaseAreDisplayedInTheAtuPage() {
        String vMessage = "";
        List<String> bus = takeda_datas.getAllBusinessUnits();
        for(int i=0; i<bus.size(); i++) {
            List<String> benchmarks = takeda_datas.getAllBenchMarks(bus.get(i));
            for (int j = 0; j < benchmarks.size(); j++) {
                List<String> indications = takeda_datas.getAllIndications(bus.get(i), benchmarks.get(j));
                for (int k = 0; k < indications.size(); k++) {
                    List<String> brands = takeda_datas.getAllBrands(bus.get(i), benchmarks.get(j), indications.get(k));
                    vMessage = takedaAtu.validateBrandsDisplay(bus.get(i), benchmarks.get(j), indications.get(k), brands);
                    if (!vMessage.equalsIgnoreCase("Success")) {
                        Assert.fail(vMessage);
                    }
                }
            }
        }
    }
    @And("^Verify that brands loaded correctly for each bu,benchmark and indication combination in the Brand Selection$")
    public void theBrandsUnderBrandSelectionForMCETouchPoints() {
        String vMessage = "";
        List<String> bus = takeda_datas.getAllBusinessUnits();
        for(int i=0; i<bus.size(); i++) {
            List<String> benchmarks = takeda_datas.getAllBenchMarks(bus.get(i));
            for (int j = 0; j < benchmarks.size(); j++) {
                List<String> indications = takeda_datas.getAllIndications(bus.get(i), benchmarks.get(j));
                for (int k = 0; k < indications.size(); k++) {
                    List<String> brands = takeda_datas.getAllBrands(bus.get(i), benchmarks.get(j), indications.get(k));
                    vMessage = takedaAtu.validateBrandsDisplay(bus.get(i), benchmarks.get(j), indications.get(k), brands);
                    if (!vMessage.equalsIgnoreCase("Success")) {
                        Assert.fail(vMessage);
                    }
                }
            }
        }
    }

    @And("^Verify that countries loaded correctly for each bu,benchmark combination$")
    public void theUserShouldSeeTheCountriesAreLoadedProperlyFromDatabaseBasedOnBuAndBenchmark() {
        String vMessage = "";
        List<String> bus = takeda_datas.getAllBusinessUnits();
        for(int i=0; i<bus.size(); i++) {
            List<String> benchmarks = takeda_datas.getAllBenchMarks(bus.get(i));
            for (int j = 0; j < benchmarks.size(); j++) {
                List<String> countries = takeda_datas.getAllCountries(bus.get(i), benchmarks.get(j));
                vMessage = takedaAtu.validateCountriesDisplay(bus.get(i), benchmarks.get(j),countries);
                if (!vMessage.equalsIgnoreCase("Success")) {
                    Assert.fail(vMessage);
                }
            }
        }
    }

    @And("^Verify that (.*) data loaded correctly in (.*) for each brands for all filter combinations$")
    public void verifyDataInSectionForSpecifiedPage(String section,String pageName) {
    }

    //MCE Touchpoints
    @And("^Verify that the Touchpoints are loaded properly under Multi Channel Engagement Touchpoints$")
    public void verifyThatTheTouchpointsAreLoadedProperlyUnderMultiChannelEngagementTouchpoints() {
    }

    @And("^Verify that the Brand Touchpoint Score for each Touchpoint is loaded properly$")
    public void verifyThatTheBrandTouchpointScoreForEachTouchpointIsLoadedProperly() {
    }

    @And("^Verify that the Sub Ratings for each Touchpoint is loaded properly$")
    public void verifyThatTheSubRatingsForEachTouchpointIsLoadedProperly() {
    }

    // Message Monitor
    @And("^Verify that Monthly Average across selected Period Multi Brand - Brand Actions loaded properly$")
    public void verifyThatMonthlyAverageAcrossSelectedPeriodMultiBrandBrandActionsLoadedProperly() {
    }

    @And("^Verify that HCP - (.*) Reactions to the Brand loaded properly$")
    public void verifyThatHCPPositiveReactionsToTheBrandLoadedProperly(String reactionType) {
    }

}//end
