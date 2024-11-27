package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.LoginPage;

import java.time.Duration;

public class LoginSteps {

    private LoginPage loginPage;

    // Inisialisasi loginPage dengan WebDriver dari Hooks
    public LoginSteps() {
        this.loginPage = new LoginPage(Hooks.driver);
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement imageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginImage()));

        String expectedTitle = "Zi.Hub | Login";
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo.svg";
        String actualSrc = imageElement.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);
    }

    @When("input username {string}")
    public void inputUsername(String username) {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getUsernameField()));

        usernameElement.sendKeys(username);

        // ini berfungsi untuk mengambil nilai dari field username agar dapat diperiksa
        String expectedUsernameInput = usernameElement.getAttribute("value");
        Assert.assertEquals("Username input mismatch", expectedUsernameInput, username);
    }

    @And("input password {string}")
    public void inputPassword(String password) {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getPasswordField()));

        passwordElement.sendKeys(password);

        // ini mengecek apakah field password bertipe "password" (untuk memastikan teks disembunyikan)
        boolean isPasswordType = "password".equals(passwordElement.getAttribute("type"));
        // Asersi untuk memastikan password terisi
        Assert.assertTrue("Password input mismatch", isPasswordType);
    }

    @And("click login button")
    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement loginButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginButton()));

        loginButtonElement.click();

        // Validasi bahwa login berhasil dengan menunggu elemen di halaman dashboard
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getDashboardTitlePage()));
    }

    @Then("user is navigated to the home page")
    public void userIsNavigatedToTheHomePage() {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        WebElement zihubImageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage.getZihubImage()));
        // ini berfungsi agar value getTitle() tidak mengembalikan judul yang sebelumnya yaitu "Zi.Hub | Login"
        wait.until(ExpectedConditions.titleIs("Dashboard | Zi.Hub"));

        String expectedTitle = "Dashboard | Zi.Hub";
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo-small.svg";
        String actualSrc = zihubImageElement.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);
    }
}
