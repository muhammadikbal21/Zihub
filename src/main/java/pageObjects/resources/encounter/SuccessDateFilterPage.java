package pageObjects.resources.encounter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SuccessDateFilterPage {

    private WebDriver driver;

    public SuccessDateFilterPage(WebDriver driver) {
        this.driver = driver;
    }

    By fromDateFilter = By.xpath("//*[@id=\"startDateEl\"]");
    By openFromDatePicker = By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]"); // tidak bisa menambahkan contains style di xpath
    By selectFromDate = By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[contains(@class, 'flatpickr-day') and contains(@aria-label, 'November 1, 2024')]");
    By toDateFilter = By.xpath("//*[@id=\"endDateEl\"]");
    By openToDatePicker = By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]");
    By selectToDate = By.xpath("//div[contains(@class, 'flatpickr-calendar') and contains(@class, 'open')]//span[contains(@class, 'flatpickr-day') and contains(@aria-label, 'November 3, 2024')]");
    By searchButton = By.xpath("//div/button[contains(@class, 'border') and contains(@type, 'submit')]");
    By encounterTable = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[3]/table");
    By encounterTableRows = By.xpath("//table[@class='min-w-full svelte-1vzd8ce']/tbody/tr"); // xpath ini mendapatkan semua row pada element yg sama (di element table)

    /*
        Penjelasan untuk membuat xpath manual:
        1. //div -> Memilih semua elemen <div> di seluruh dokumen.
        2. //div/button -> Memilih elemen <button> yang parent nya berada di elemen <div>
        3. contains() -> memungkinkan pencocokan meskipun ada kelas lain yang juga ada dalam atribut class.
        4. [contains(@class, 'flatpickr-calendar') and contains(@class, 'open')] -> Memfilter elemen <div> yang mengandung dua kelas yaitu 'flatpickr-calendar' dan 'open'.
        5. /tbody: Memilih elemen <tbody> dalam tabel tersebut.
        6. /tr[1]: Memilih elemen baris pertama <tr> di dalam <tbody>.
     */

    public By getFromDateFilter() {
        return fromDateFilter;
    }

    public By getOpenFromDatePicker() {
        return openFromDatePicker;
    }

    public By getSelectFromDate() {
        return selectFromDate;
    }

    public By getToDateFilter() {
        return toDateFilter;
    }

    public By getOpenToDatePicker() {
        return  openToDatePicker;
    }

    public By getSelectToDateElement() {
        return selectToDate;
    }

    public By getSearchButton() {
        return searchButton;
    }

    public By getEncounterTable() {
        return encounterTable;
    }

    public List<WebElement> getEncounterTableRows() {
        return driver.findElements(encounterTableRows);
    }

}
