package co.com.petstore.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features/crud_usuario.feature",
    glue = "co.com.petstore.stepdefinitions",
    plugin = {"pretty", "summary"},
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CucumberWithSerenityRunner {
}
