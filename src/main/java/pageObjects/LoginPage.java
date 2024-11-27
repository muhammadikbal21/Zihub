package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    By loginImage = By.xpath("/html/body/div[1]/div[1]/div/img");
    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By loginButton = By.xpath("/html/body/div[1]/div[1]/div/div[1]/form/button");
    By dashboardTitlePage = By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[1]/div/span");
    By zihubImage = By.xpath("/html/body/div[1]/header/nav/div[1]/a/img");

    public By getLoginImage() {
        return loginImage;
    }

    public By getUsernameField() {
        return usernameField;
    }

    public By getPasswordField() {
        return passwordField;
    }

    public By getLoginButton() {
        return loginButton;
    }

    public By getDashboardTitlePage() {
        return dashboardTitlePage;
    }

    public By getZihubImage() {
        return zihubImage;
    }
}
