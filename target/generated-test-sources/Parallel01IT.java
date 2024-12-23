import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"D:/CUCUMBER/src/test/resources/features/mavishr/deepdive/O3_mavishr_company_deepdive.feature"},
        plugin = {"json:D:/CUCUMBER/target/cucumber-parallel/1.json"},
        monochrome = false,
        tags = {"@deepdive_negative_themes_selected_combination"},
        glue = {"co.graphene.steps"})
public class Parallel01IT {
}
