package pageObjects.resources.encounter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DropdownStatusFilterPage {

    private WebDriver driver;

    public DropdownStatusFilterPage(WebDriver driver) {
        this.driver = driver;
    }

    By statusFilter = By.id("menu-button");
    By valueStatus = By.xpath("//div[@role='none']/a[@id]\n");
    By rowsTable = By.xpath("//table//tr");

    public By getStatusFilter() {
        return statusFilter;
    }

    public By getValueStatus() {
        return valueStatus;
    }

    public By getRowsTable() {
        return rowsTable;
    }
}
