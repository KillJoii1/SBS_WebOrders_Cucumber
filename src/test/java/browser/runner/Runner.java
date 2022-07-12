package browser.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "browser/steps",
        tags = "@AllTest",
        dryRun = false,
        monochrome = false,
        plugin = {
                "html:target/cucumber-reports",
                "json:target/cucumber-reports/Cucumber.json",
                "pretty"
        }
)
public class Runner {
}
