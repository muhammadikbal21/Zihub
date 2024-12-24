package pageObjects.resources.encounter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddEncounterByUploadPage {

    private WebDriver driver;

    public AddEncounterByUploadPage(WebDriver driver) {
        this.driver = driver;
    }

    By addDataButton = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div[2]/button");
    By importCsvTab = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/button[2]");
    By uploadBoxButton = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div[2]/div/div/div[2]/div/button");
    By uploadCsvButton = By.xpath("//*[@id=\"file-input\"]");
    By saveDataButton = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div[2]/div/div/div[3]/button[1]");
    By addDataEncounterModal = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]");
    By fileName = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div[2]/div/div/div[2]/div/div[2]/div[1]/span[1]");
    By successUploadWording = By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div[2]/div/div/div[2]/div/div[2]/div[1]/span[2]");

    public By getAddDataButton() {
        return addDataButton;
    }

    public By getImportCsvTab() {
        return importCsvTab;
    }

    public By getUploadBoxButton() {
        return uploadBoxButton;
    }

    public By getUploadCsvButton() {
        return uploadCsvButton;
    }

    public By getSaveDataButton() {
        return saveDataButton;
    }

    public By getAddDataEncounterModal() {
        return addDataEncounterModal;
    }

    public By getFileName() {
        return fileName;
    }

    public By getSuccessUploadWording() {
        return successUploadWording;
    }

}
