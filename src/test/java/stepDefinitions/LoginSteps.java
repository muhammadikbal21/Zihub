package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.LoginPage;
import utils.WaitUtils;

import java.time.Duration;

public class LoginSteps {

    private LoginPage loginPage;

    private WebDriver driver = Hooks.driver;

    // Inisialisasi loginPage dengan WebDriver dari Hooks
    public LoginSteps() {
        this.loginPage = new LoginPage(driver);
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {
        WebElement imageElement = WaitUtils.waitForElementToBeVisible(loginPage.getLoginImage());

        String expectedTitle = "Zi.Hub | Login";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo.svg";
        String actualSrc = imageElement.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);
    }

    @When("input username {string}")
    public void inputUsername(String username) {
        WebElement usernameElement = WaitUtils.waitForElementToBeVisible(loginPage.getUsernameField());

        usernameElement.sendKeys(username);

        // ini berfungsi untuk mengambil nilai dari field username agar dapat diperiksa
        String expectedUsernameInput = usernameElement.getAttribute("value");
        Assert.assertEquals("Username input mismatch", expectedUsernameInput, username);
    }

    @And("input password {string}")
    public void inputPassword(String password) {
        WebElement passwordElement = WaitUtils.waitForElementToBeVisible(loginPage.getPasswordField());

        passwordElement.sendKeys(password);

        // ini mengecek apakah field password bertipe "password" (untuk memastikan teks disembunyikan)
        boolean isPasswordType = "password".equals(passwordElement.getAttribute("type"));
        // Asersi untuk memastikan password terisi
        Assert.assertTrue("Password input mismatch", isPasswordType);
    }

    @And("click login button")
    public void clickLoginButton() {
        WebElement loginButtonElement = WaitUtils.waitForElementToBeVisible(loginPage.getLoginButton());

        loginButtonElement.click();

        // Validasi bahwa login berhasil dengan menunggu elemen di halaman dashboard
        WaitUtils.waitForElementToBeVisible(loginPage.getDashboardTitlePage());
    }

    @Then("user is navigated to the home page")
    public void userIsNavigatedToTheHomePage() {
        WebElement zihubImageElement = WaitUtils.waitForElementToBeVisible(loginPage.getZihubImage());

        // ini berfungsi agar value getTitle() tidak mengembalikan judul yang sebelumnya yaitu "Zi.Hub | Login"
        WaitUtils.waitForTitle("Dashboard | Zi.Hub");

        String expectedTitle = "Dashboard | Zi.Hub";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo-small.svg";
        String actualSrc = zihubImageElement.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);
    }
}
