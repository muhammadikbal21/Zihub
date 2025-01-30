package stepDefinitions.resources.encounter;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.resources.encounter.AddEncounterByUploadPage;
import pageObjects.resources.encounter.EncounterPage;
import pageObjects.resources.encounter.SuccessDateFilterPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddEncounterByUploadSteps {

    private AddEncounterByUploadPage addEncounterByUploadPage;

    public AddEncounterByUploadSteps() {
        this.addEncounterByUploadPage = new AddEncounterByUploadPage(Hooks.driver);
    }

    @Given("user click add data button")
    public void userClickAddDataButton() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement addDataButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getAddDataButton()));

        addDataButtonElement.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getAddDataEncounterModal()));
    }

    @And("user click import csv tab")
    public void userClickImportCsvTab() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement importCsvTabElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getImportCsvTab()));

        importCsvTabElement.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getUploadBoxButton()));
    }

    @And("user click upload file")
    public void userClickUploadFile() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement uploadCsvButtonElement = wait.until(ExpectedConditions.presenceOfElementLocated(addEncounterByUploadPage.getUploadCsvButton())); // menunggu sampai elemen muncul meskipun mungkin belum terlihat
        JavascriptExecutor js = (JavascriptExecutor) Hooks.driver;

        js.executeScript("arguments[0].classList.remove('hidden');", uploadCsvButtonElement);
        /*
        Input file sering disembunyikan dengan class="hidden". Karena Selenium tidak bisa langsung mengklik elemen tersembunyi, JavaScript digunakan untuk:
        Mengakses properti elemen DOM (arguments[0] adalah elemen yang diteruskan dari Selenium).
        Menghapus kelas CSS hidden dari elemen.
         */

        String filePath = "/Users/muhammadikbal/Documents/Ikbal/Project/Selenium/Zihub/src/test/resources/testfiles/EncounterData.csv";
        uploadCsvButtonElement.sendKeys(filePath);

        WebElement fileNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getFileName()));
        WebElement successUploadWordingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getSuccessUploadWording()));
        String actualFileName = fileNameElement.getText();
        String actualSuccessUploadWording = " " + successUploadWordingElement.getText();
        String expectedFileName = "EncounterData.csv";
        String expectedSuccessUploadWording = " sudah siap diupload. Klik tombol \"Save Data\" untuk mulai mengimpor data";

        Assert.assertEquals("Pesan upload tidak sesuai", expectedFileName + expectedSuccessUploadWording, actualFileName + actualSuccessUploadWording);
    }

    @And("user see pop up success upload and saved file")
    public void userSeePopUpSuccessUploadAndSavedFile() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement saveDataButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(addEncounterByUploadPage.getSaveDataButton()));

        saveDataButtonElement.click();

        Alert alert = Hooks.driver.switchTo().alert();
        String confirmAlert = alert.getText();
        String expectedConfirmAlert = "Apakah file yang diupload sudah sesuai?";
        Assert.assertEquals(expectedConfirmAlert, confirmAlert);
        alert.accept();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert secondAlert = Hooks.driver.switchTo().alert();
        String succeededUploadAlert = secondAlert.getText();
        String expectedSucceededUploadAlert = "File uploaded and saved successfully";
        Assert.assertEquals(expectedSucceededUploadAlert, succeededUploadAlert);
        secondAlert.accept();
    }

    @And("user get auto reload page")
    public void userGetAutoReloadPage() {
        Hooks.driver.navigate().refresh();

        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        EncounterPage encounterPage = new EncounterPage(Hooks.driver);
        WebElement elementAfterReload = wait.until(ExpectedConditions.visibilityOfElementLocated(encounterPage.getEncounterTable()));

        Assert.assertTrue("Element should be visible after reload", elementAfterReload.isDisplayed());
    }

    @Then("user will see new encounter in the table")
    public void userWillSeeNewEncounterInTheTable() {
        // jika ingin tambah data baru menggunakan template file, ganti encounter id
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        EncounterPage encounterPage = new EncounterPage(Hooks.driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(encounterPage.getEncounterTable()));

        // verif status satusehat dan tgl update nya terkini
        String encounterDateElement = "/html/body/div[1]/div[1]/div[2]/div/div[3]/table/tbody/tr[1]";
        String encounterDateText = Hooks.driver.findElement(By.xpath(encounterDateElement + "/td[5]")).getText();
        String satuSehatStatus = Hooks.driver.findElement(By.xpath(encounterDateElement + "/td[2]")).getText();
        String dateOnly = encounterDateText.substring(0, 10);

        LocalDate encounterDate = LocalDate.parse(dateOnly, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate createDateEncounter = LocalDate.now();

        Assert.assertEquals(encounterDate, createDateEncounter);
        Assert.assertEquals("SUCCESS", satuSehatStatus);
    }

}
