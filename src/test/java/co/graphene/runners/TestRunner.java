package co.graphene.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        glue = {"co.graphene.steps"},
        features = {"src/test/resources/features/mavishr"},
        tags = {"@ciplahr"}
)

public class TestRunner {

}//end