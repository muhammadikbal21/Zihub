package stepDefinitions.resources.encounter;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.resources.encounter.AddEncounterByUploadPage;
import pageObjects.resources.encounter.EncounterPage;
import pageObjects.resources.encounter.SuccessDateFilterPage;
import utils.WaitUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddEncounterByUploadSteps {

    private AddEncounterByUploadPage addEncounterByUploadPage;

    private WebDriver driver = Hooks.driver;

    public AddEncounterByUploadSteps() {
        this.addEncounterByUploadPage = new AddEncounterByUploadPage(driver);
    }

    @Given("user click add data button")
    public void userClickAddDataButton() {
        WebElement addDataButtonElement = WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getAddDataButton());

        addDataButtonElement.click();

        WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getAddDataEncounterModal());
    }

    @And("user click import csv tab")
    public void userClickImportCsvTab() {
        WebElement importCsvTabElement = WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getImportCsvTab());

        importCsvTabElement.click();

        WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getUploadBoxButton());
    }

    @And("user click upload file")
    public void userClickUploadFile() {
        WebElement uploadCsvButtonElement = WaitUtils.waitForElementToPresence(addEncounterByUploadPage.getUploadCsvButton());
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].classList.remove('hidden');", uploadCsvButtonElement);
        /*
        Input file sering disembunyikan dengan class="hidden". Karena Selenium tidak bisa langsung mengklik elemen tersembunyi, JavaScript digunakan untuk:
        Mengakses properti elemen DOM (arguments[0] adalah elemen yang diteruskan dari Selenium).
        Menghapus kelas CSS hidden dari elemen.
         */

        // ini di macbook
//        String filePath = "/Users/muhammadikbal/Documents/Ikbal/Project/Selenium/Zihub/src/test/resources/testfiles/EncounterData.csv";

        // ini di pc
        String filePath = "D:\\Project\\Zihub\\src\\test\\resources\\testfiles\\EncounterData.csv";
        uploadCsvButtonElement.sendKeys(filePath);

        WebElement fileNameElement = WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getFileName());
        WebElement successUploadWordingElement = WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getSuccessUploadWording());
        String actualFileName = fileNameElement.getText();
        String actualSuccessUploadWording = " " + successUploadWordingElement.getText();
        String expectedFileName = "EncounterData.csv";
        String expectedSuccessUploadWording = " sudah siap diupload. Klik tombol \"Save Data\" untuk mulai mengimpor data";

        Assert.assertEquals("Pesan upload tidak sesuai", expectedFileName + expectedSuccessUploadWording, actualFileName + actualSuccessUploadWording);
    }

    @And("user see pop up success upload and saved file")
    public void userSeePopUpSuccessUploadAndSavedFile() {
        WebElement saveDataButtonElement = WaitUtils.waitForElementToBeVisible(addEncounterByUploadPage.getSaveDataButton());

        saveDataButtonElement.click();

        Alert alert = driver.switchTo().alert();
        String confirmAlert = alert.getText();
        String expectedConfirmAlert = "Apakah file yang diupload sudah sesuai?";
        Assert.assertEquals(expectedConfirmAlert, confirmAlert);
        alert.accept();

        WaitUtils.waitForAlert();
        Alert secondAlert = driver.switchTo().alert();
        String succeededUploadAlert = secondAlert.getText();
        String expectedSucceededUploadAlert = "File uploaded and saved successfully";
        Assert.assertEquals(expectedSucceededUploadAlert, succeededUploadAlert);
        secondAlert.accept();
    }

    @And("user get auto reload page")
    public void userGetAutoReloadPage() {
        driver.navigate().refresh();

        EncounterPage encounterPage = new EncounterPage(driver);
        WebElement elementAfterReload = WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTable());

        Assert.assertTrue("Element should be visible after reload", elementAfterReload.isDisplayed());
    }

    @Then("user will see new encounter in the table")
    public void userWillSeeNewEncounterInTheTable() {
        // jika ingin tambah data baru menggunakan template file, ganti encounter id
        EncounterPage encounterPage = new EncounterPage(driver);
        WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTable());

        // verif status satusehat dan tgl update nya terkini
        String encounterDateElement = "/html/body/div[1]/div[1]/div[2]/div/div[3]/table/tbody/tr[1]";
        String encounterDateText = driver.findElement(By.xpath(encounterDateElement + "/td[5]")).getText();
        String satuSehatStatus = driver.findElement(By.xpath(encounterDateElement + "/td[2]")).getText();
        String dateOnly = encounterDateText.substring(0, 10);

        LocalDate encounterDate = LocalDate.parse(dateOnly, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate createDateEncounter = LocalDate.now();

        Assert.assertEquals(encounterDate, createDateEncounter);
        Assert.assertEquals("SUCCESS", satuSehatStatus);
    }

}
