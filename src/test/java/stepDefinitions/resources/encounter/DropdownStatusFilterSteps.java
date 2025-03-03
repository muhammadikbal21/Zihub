package stepDefinitions.resources.encounter;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.resources.encounter.DropdownStatusFilterPage;
import pageObjects.resources.encounter.EncounterPage;
import pageObjects.resources.encounter.SuccessDateFilterPage;
import utils.WaitUtils;

import java.util.List;

public class DropdownStatusFilterSteps {

    private DropdownStatusFilterPage dropdownStatusFilterPage;
    private WebDriver driver = Hooks.driver;
    private SuccessDateFilterPage successDateFilterPage = new SuccessDateFilterPage(driver);
    private EncounterPage encounterPage = new EncounterPage(driver);

    public DropdownStatusFilterSteps() {
        this.dropdownStatusFilterPage = new DropdownStatusFilterPage(driver);
    }

    @Given("user click status filter")
    public void userClickStatusFilter() {
        WebElement openStatusDropdownMenu = WaitUtils.waitForElementToBeVisible(dropdownStatusFilterPage.getStatusFilter());
        WaitUtils.waitForElementToBeClickable(dropdownStatusFilterPage.getStatusFilter());

        openStatusDropdownMenu.click();
        Assert.assertTrue("Dropdown filter should be clickable", openStatusDropdownMenu.isEnabled());
    }

    @And("user select status and search")
    public void userSelectStatusAndSearch() {
        WaitUtils.waitForElementToBeVisible(dropdownStatusFilterPage.getValueStatus());

        List<WebElement> statusOptions = driver.findElements(dropdownStatusFilterPage.getValueStatus());

        // Pastikan dropdown tidak kosong
        Assert.assertFalse("Dropdown status is empty!", statusOptions.isEmpty());

        for (int i = 1; i <=3 && i < statusOptions.size(); i++) {
            // Pemanggilan kembali baris ini di dalam loop digunakan untuk memastikan elemen-elemen dropdown diperbarui setiap kali perulangan dijalankan
            statusOptions = driver.findElements(dropdownStatusFilterPage.getValueStatus());

            WebElement option = statusOptions.get(i); // urutan dari value status nya (Semua, Success, Fail, ConditionUpdated)
            String statusText = option.getText().trim(); // Dapatkan teks dari status

            // Klik status yang dipilih
            option.click();
            Assert.assertTrue("Status Value should be selected", option.isEnabled());

            // Klik tombol search
            WebElement searchButton = WaitUtils.waitForElementToBeClickable(successDateFilterPage.getSearchButton());
            searchButton.click();
            Assert.assertTrue("Search button should be clickable", searchButton.isEnabled());

            // Tunggu hasil pencarian muncul
            WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTable());

            // Verifikasi hasil filter
            verifyFilteredStatus(statusText);

            // Klik dropdown lagi untuk memilih opsi lain (hanya jika masih ada opsi yang tersisa)
            // disini hanya bisa dipanggil 2 kali untuk Failed dan ConditionUpdated (untuk yang Success tidak perlu karena sudah di awal)
            if (i < 3 && i < statusOptions.size() - 1) {
                userClickStatusFilter();
            }
        }
    }

    @Then("user sees the list of encounter with status filter")
    public void userSeesTheListOfEncounterWithStatusFilter() {
        // ini tidak perlu bikin script karena sudah dilakukan looping diatas dan hanya formalitas
    }

    private void verifyFilteredStatus(String expectedStatus) {
        WaitUtils.waitForElementToBeVisible(encounterPage.getEncounterTable());

        // mengecek dan memastikan expectedStatus tidak kosong dengan retry
        int retryCount = 0;
        while ((expectedStatus == null || expectedStatus.trim().isEmpty()) && retryCount < 5) {
            System.out.println("Expected status masih kosong, retrying...");
            WaitUtils.waitForElementToHaveText(expectedStatus); // tunggu sampe value nya ketemu
            retryCount++;
        }

        // memastikan expectedStatus tidak kosong
        if (expectedStatus == null || expectedStatus.trim().isEmpty()) {
            throw new AssertionError("Expected status is empty or null after retries!");
        }

        List<WebElement> rows = driver.findElements(dropdownStatusFilterPage.getRowsTable());
        System.out.println("Jumlah rows: " + rows.size());

        for (int i = 1; i < rows.size(); i++) {
            WaitUtils.waitForElementToBeVisible(dropdownStatusFilterPage.getRowsTable());
            WebElement statusCell = rows.get(i).findElement(By.xpath("./td[2]/button"));

            // Tunggu hingga elemen status muncul & memiliki teks
            WaitUtils.waitForElementToHaveText(statusCell);

            String actualStatus = statusCell.getText().trim();
            System.out.println("Expected Status: " + expectedStatus.toUpperCase());
            System.out.println("Actual Status: " + actualStatus);

            Assert.assertEquals("Filter is not match", expectedStatus.toUpperCase(), actualStatus);
        }
    }
}
