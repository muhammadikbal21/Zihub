package pageObjects.resources.encounter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EncounterPage {

    private WebDriver driver;

    public EncounterPage(WebDriver driver) {
        this.driver = driver;
    }

    By resourcesButton = By.xpath("/html/body/div[1]/div[1]/div[1]/aside/div/div[1]/div[3]/button");
    By encounterSubMenu = By.xpath("/html/body/div[1]/div[1]/div[1]/aside/div/div[3]/div[2]");
    By encounterTitle = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[1]/span"); // title halaman encounter
    By encounterTableRows = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[3]/table/tbody/tr"); // salah satu row pada tabel yg akan kita gunakan


    public void clickResources() {
        driver.findElement(resourcesButton).click();
    }

    public void clickEncounterSubMenu() {
        driver.findElement(encounterSubMenu).click();
    }

    public By getEncounterTitle() {
        return encounterTitle;
    }

    public List<WebElement> getEncounterTableRows() {
        return driver.findElements(encounterTableRows); // Me-return daftar semua baris dalam tabel encounter
        // disini menggunakan List<WebElement> karena di dalam baris memiliki beberapa <td> yang mempunyai tiap tiap value nya
    }

    public String getEncounterIdFromFirstRow() {
        // Mendapatkan teks Encounter ID dari sel tertentu di baris pertama tabel encounter
        return driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[3]/table/tbody/tr[1]/td[4]")).getText();
    }

}
