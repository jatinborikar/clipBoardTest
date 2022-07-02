package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WaitUtils {

    public static final long PAGE_LOAD_TIMEOUT = 150;
    public static final long IMPLICIT_WAIT = 60;
    public static final int MAX_ELEMENT_WAIT = 45;
    public static final int MIN_ELEMENT_WAIT = 10;
    public static final int MAX_PAGE_LOAD_WAIT = 120;
    public static final int STATIC_TIME_OUT = 2;


    public static void nap(int seconds) {
        try {
            Thread.sleep((long) seconds * 1000L);
        } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waitUntilElementVisible(WebDriver driver, WebElement element, int timeOutLimit) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutLimit);
        wait.until(ExpectedConditions.visibilityOf(element));
    }


}


