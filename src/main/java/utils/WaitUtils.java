package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static WebDriver driver;
    private static WebDriverWait wait;

    // Set driver saat pertama kali dijalankan
    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Method untuk mendapatkan WebDriverWait
    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        return wait;
    }

    // Method untuk menunggu hingga elemen bisa diklik
    public static WebElement waitForElementToBeClickable(By element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    // Method untuk menunggu hingga elemen terlihat
    public static WebElement waitForElementToBeVisible(By element) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static void waitForTitle(String title) {
        getWait().until(ExpectedConditions.titleIs(title));
    }

    // Method menunggu sampai elemen muncul meskipun mungkin belum terlihat
    public static WebElement waitForElementToPresence(By element) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public static void waitForAlert() {
        getWait().until(ExpectedConditions.alertIsPresent());
    }

}
