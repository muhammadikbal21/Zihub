package stepDefinitions.resources.encounter;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.resources.encounter.SuccessDateFilterPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SuccessDateFilterSteps {

    private SuccessDateFilterPage successDateFilterPage;

    public SuccessDateFilterSteps() {
        this.successDateFilterPage = new SuccessDateFilterPage(Hooks.driver);
    }

    @Given("user click from date")
    public void userClickFromDate() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement fromDate = wait.until(ExpectedConditions.visibilityOfElementLocated(successDateFilterPage.getFromDateFilter()));
        wait.until(ExpectedConditions.elementToBeClickable(successDateFilterPage.getFromDateFilter()));

        fromDate.click();

        WebElement datePicker = wait.until(ExpectedConditions.visibilityOfElementLocated(successDateFilterPage.getOpenFromDatePicker()));
        Assert.assertTrue("Date picker should be visible", datePicker.isDisplayed());
    }

    @And("user select from date")
    public void userSelectFromDate() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement monthDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(successDateFilterPage.getSelectFromDate()));

        // JavascriptExecutor adalah interface di Selenium yang memungkinkan kita menjalankan JavaScript di dalam browser.
        JavascriptExecutor jsExecutor = (JavascriptExecutor) Hooks.driver;

        jsExecutor.executeScript(
                "arguments[0].value = '01 Nov 2024';" + // mengubah langsung tanpa menggunakan interaksi keyboard atau klik
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                // Memicu event input, yang biasanya digunakan oleh library frontend untuk mendeteksi perubahan teks pada input.
                // Memicu event change, yang biasanya diperlukan agar aplikasi mengenali perubahan nilai input.
                // Memicu event blur, yang meniru kehilangan fokus dari elemen input. Ini berguna jika aplikasi hanya menyimpan nilai saat pengguna berpindah dari input.
                // { bubbles: true } memastikan event naik ke elemen induknya agar dideteksi oleh event listener.
                monthDropdown
        );

        // Validasi hasil
        String selectedDate = monthDropdown.getAttribute("value");
        Assert.assertEquals("Tanggal yang dipilih tidak sesuai.", "01 Nov 2024", selectedDate);

        System.out.println("Tanggal yang dipilih: " + selectedDate);
    }

    @And("user click to date")
    public void userClickToDate() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement toDate = wait.until(ExpectedConditions.visibilityOfElementLocated(successDateFilterPage.getToDateFilter()));
        wait.until(ExpectedConditions.elementToBeClickable(successDateFilterPage.getToDateFilter()));

        toDate.click();

        WebElement datePicker = wait.until(ExpectedConditions.visibilityOfElementLocated(successDateFilterPage.getOpenToDatePicker()));
        Assert.assertTrue("Date picker should be visible", datePicker.isDisplayed());
    }

    @And("user select to date")
    public void userSelectToDate() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement monthDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(successDateFilterPage.getSelectToDateElement()));

        JavascriptExecutor jsExecutor = (JavascriptExecutor) Hooks.driver;

        jsExecutor.executeScript(
                "arguments[0].value = '03 Nov 2024';" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                monthDropdown
        );

        // Validasi hasil
        String selectedDate = monthDropdown.getAttribute("value");
        Assert.assertEquals("Tanggal yang dipilih tidak sesuai.", "03 Nov 2024", selectedDate);

        System.out.println("Tanggal yang dipilih: " + selectedDate);
    }

    @And("user click search")
    public void userClickSearch() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement searchElement = wait.until(ExpectedConditions.elementToBeClickable(successDateFilterPage.getSearchButton()));

        searchElement.click();

        Assert.assertTrue("Search button should be clickable", searchElement.isEnabled());

    }

    @Then("user sees the list of encounter with date filter")
    public void userSeesTheListOfEncounterWithDateFilter() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successDateFilterPage.getEncounterTable()));

        List<WebElement> rows = successDateFilterPage.getEncounterTableRows(); // membuat array list dengan banyak element rows di element table
        Assert.assertTrue("The Encounter list should not be empty", rows.size() > 0); // disini variable array rows sudah ada element nya di dalamnya

        // looping isi array dari semua element rows yg telah disimpan di variable array rows
        for (WebElement row : rows) {
            String encounterDateText = row.findElement(By.xpath("./td[5]")).getText(); // Sesuaikan indeks kolom
            String dateOnly = encounterDateText.substring(0, 10);
            /*
                Penjelasan substring diatas:
                1. substring(start, end): Metode substring memotong atau mengambil bagian dari string mulai dari indeks start hingga indeks end (tidak termasuk karakter pada end).
                2. integer 0: Menunjukkan indeks awal dari substring, yaitu posisi karakter pertama dalam string.
                3. integer 10: Menunjukkan indeks akhir dari substring, yaitu posisi karakter kesebelas. Karakter pada indeks ke-10 tidak disertakan dalam hasil.
                Jadi, substring(0, 10) akan mengambil 10 karakter pertama dari string. Contohnya, jika encounterDateText berisi "2024-11-01 06:07:39", maka
                Hasilnya akan menjadi "2024-11-01", yang hanya berisi bagian tanggal dan menghilangkan waktu (06:07:39).
             */

            // Konversi tanggal encounter ke objek tanggal
            LocalDate encounterDate = LocalDate.parse(dateOnly, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Tanggal filter awal dan akhir
            LocalDate startDate = LocalDate.of(2024, 11, 1);
            LocalDate endDate = LocalDate.of(2024, 11, 3);

            // Assertion bahwa encounterDate berada dalam rentang startDate dan endDate
            Assert.assertTrue("Encounter date is not within the filtered range",
                    (encounterDate.isEqual(startDate) || encounterDate.isAfter(startDate)) &&
                            (encounterDate.isEqual(endDate) || encounterDate.isBefore(endDate)));
        }
    }
}
