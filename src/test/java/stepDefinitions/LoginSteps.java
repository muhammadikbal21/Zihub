package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.LoginPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginSteps {

    private LoginPage loginPage;

    // Inisialisasi loginPage dengan WebDriver dari Hooks
    public LoginSteps() {
        this.loginPage = new LoginPage(Hooks.driver);
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {
        String expectedTitle = "Zi.Hub | Login";
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        WebElement image = Hooks.driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/img"));
        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo.svg";
        String actualSrc = image.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);
    }

    @When("input username")
    public void inputUsername() {
        loginPage.enterUsername("superAdmin");
    }

    @And("input password")
    public void inputPassword() {
        loginPage.enterPassword("admin");
    }

    @And("click login button")
    public void clickLoginButton() {
        loginPage.clickLogin();

        // Validasi bahwa login berhasil dengan menunggu elemen di halaman dashboard
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[1]/div/span")));
    }

    @Then("user is navigated to the home page")
    public void userIsNavigatedToTheHomePage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        // ini berfungsi agar value getTitle() tidak mengembalikan judul yang sebelumnya yaitu "Zi.Hub | Login"
        wait.until(ExpectedConditions.titleIs("Dashboard | Zi.Hub"));

        String expectedTitle = "Dashboard | Zi.Hub";
        String actualTitle = Hooks.driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        WebElement image = Hooks.driver.findElement(By.xpath("/html/body/div[1]/header/nav/div[1]/a/img"));
        String expectedSrc = "https://zihub-iris-dev.zicare.id/images/zihub-logo-small.svg";
        String actualSrc = image.getAttribute("src");
        Assert.assertEquals(actualSrc, expectedSrc);

        Thread.sleep(2000);
    }
}
