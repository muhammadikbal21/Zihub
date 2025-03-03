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

    public static void waitForElementToHaveText(WebElement element) {
        getWait().until(driver -> !element.getText().trim().isEmpty());
    }
    // driver -> = ini adalah bentuk lambda function yang mana driver adalah parameter yang mewakili instance WebDriver.
    // trim() = Hilangkan spasi di awal/akhir teks.
    // isEmpty() = Mengecek apakah teks kosong, jika kosong berarti nilai nya true
    // !element.getText().trim().isEmpty() = kondisi apakah element tersebut kosong, tetapi kita bikin negasi yang isEmpty awalnya true menjadi false dan sebaliknya
    // !isEmpty() → Kalo kosong (false, karena ada negasi) dia tetap menunggu, tapi jika tidak kosong (true) dia akan berhenti

    public static void waitForElementToHaveText(String text) {
        getWait().until(driver -> text != null && !text.trim().isEmpty());
    }
    // text != null → Pastikan teks tidak null (tidak ada isinya sama sekali).
    // && → kedua kondisi harus benar benar true agar dapat lanjut ke berikutnya
    /*
        !text.trim().isEmpty() →
            .trim() → Menghapus spasi di awal dan akhir teks.
            .isEmpty() → Mengecek apakah teks kosong ("").
            ! (negasi) → Berarti kondisi ini harus FALSE, alias teks tidak boleh kosong.
     */
}
