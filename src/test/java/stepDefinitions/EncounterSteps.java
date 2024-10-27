package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.EncounterPage;

import java.time.Duration;
import java.util.List;

public class EncounterSteps {

    private EncounterPage encounterPage;

    public EncounterSteps() {
        this.encounterPage = new EncounterPage(Hooks.driver);
    }

    @Given("user click resources menu")
    public void userClickResourcesMenu() {
        encounterPage.clickResources();

        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(encounterPage.getEncounterTitle()));
    }

    @And("user is on encounter sub menu")
    public void userIsOnEncounterSubMenu() {
        encounterPage.clickEncounterSubMenu();

        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Resources - Encounter | Zi.Hub"));

        String expectedTitle = "Resources - Encounter | Zi.Hub";
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Then("user sees the list of encounter")
    public void userSeesTheListOfEncounter() throws InterruptedException {
        List<WebElement> rows = encounterPage.getEncounterTableRows(); // Mengambil salah satu daftar baris dalam tabel dan menginisialisasi ke variable rows
        Assert.assertTrue("The Encounter list should not be empty", rows.size() > 0); // Mengecek apakah jumlah elemen di dalam rows lebih dari 0, yang berarti tabel Encounter berisi setidaknya satu baris

        String encounterId = encounterPage.getEncounterIdFromFirstRow();
        Assert.assertEquals("230917.093", encounterId);

        Thread.sleep(5000);
    }

}