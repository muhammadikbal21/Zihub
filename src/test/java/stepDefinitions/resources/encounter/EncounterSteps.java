package stepDefinitions.resources.encounter;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.resources.encounter.EncounterPage;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class EncounterSteps {

    private EncounterPage encounterPage;

    private WebDriver driver = Hooks.driver;

    public EncounterSteps() {
        this.encounterPage = new EncounterPage(driver);
    }

    @Given("user click resources menu")
    public void userClickResourcesMenu() {
        WebElement resourceButtonElement = WaitUtils.waitForElementToBeVisible(encounterPage.getResourcesButton());

        resourceButtonElement.click();

        WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTitlePage());
    }

    @And("user is on encounter sub menu")
    public void userIsOnEncounterSubMenu() {
        WebElement encounterSubMenuElement = WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterSubMenu());

        encounterSubMenuElement.click();

        WaitUtils.waitForTitle("Resources - Encounter | Zi.Hub");

        String expectedTitle = "Resources - Encounter | Zi.Hub";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Then("user sees the list of encounter")
    public void userSeesTheListOfEncounter() {
        WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTable());

        List<WebElement> rows = encounterPage.getEncounterTableRows(); // Mengambil salah satu daftar baris dalam tabel dan menginisialisasi ke variable rows
        Assert.assertTrue("The Encounter list should not be empty", rows.size() > 0); // Mengecek apakah jumlah elemen di dalam rows lebih dari 0, yang berarti tabel Encounter berisi setidaknya satu baris
        int expectedNumberEncounterInt = 1; // inisialisasi untuk compare assert expected dan actual

        // looping array dari element <tr>
        for (WebElement row : rows) {
            String encounterNumber = row.findElement(By.xpath("./td[1]")).getText(); // mengambil element <td> kolom pertama
            Assert.assertEquals(String.valueOf(expectedNumberEncounterInt), encounterNumber); // assert element nya
            expectedNumberEncounterInt++; // mengupdate value dari variable tersebut dengan menambahkan 1 setiap loopingnya
        }
    }

}