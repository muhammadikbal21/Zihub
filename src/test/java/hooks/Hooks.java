package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver;
    public static String baseUrl = "https://zihub-iris-dev.zicare.id/auth/login";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.setHeadless(false);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WaitUtils.setDriver(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try {
                // Tunggu hingga browser benar-benar siap sebelum quit agar tidak terjadinya warning 'connection reset' di logger nya
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

                driver.quit();
            } catch (Exception e) {
                System.out.println("Exception during driver quit: " + e.getMessage());
            }
        }
    }
}
