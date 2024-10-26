package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // direktori file" feature
        glue = {"stepDefinitions", "hooks"}, // inisialisasi memanggil file" stepDefinitions dan hooks yang berada didalam folder java
        plugin = {"html:target/HTML_report.html"} // membuat report berbentuk HTML
)
public class Run {
}
