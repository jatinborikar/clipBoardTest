package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    public static WebDriver get(String browserName) throws MalformedURLException {
        WebDriver driver;
        DesiredCapabilities cap;

        if(System.getProperty("remoteRun").equalsIgnoreCase("true")) {
           cap = new DesiredCapabilities();
           cap.setBrowserName(browserName);
           driver = new RemoteWebDriver(new URL(System.getProperty("hubAddress")), cap);
        }
        else {

            Map<String, Object> prefs = new HashMap<>();

            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 2);

            ChromeOptions chromeOptions = new ChromeOptions();
            if(System.getProperty("headLessBrowser").equalsIgnoreCase("true")) {
                chromeOptions.addArguments(new String[]{"--headless"});
                chromeOptions.addArguments(new String[]{"--window-size=1920,1080"});
            }

            chromeOptions.addArguments(new String[]{"–-allow-running-insecure-content"});
            chromeOptions.addArguments(new String[]{"--disable-popup-blocking"});
            chromeOptions.addArguments(new String[]{"--disable-extensions"});
            chromeOptions.addArguments(new String[]{"--disable-notifications"});
            chromeOptions.addArguments(new String[]{"--disable-infobars"});
            chromeOptions.addArguments(new String[]{"–-disable-web-security"});
            chromeOptions.addArguments(new String[]{"--disable-gpu"});
            chromeOptions.addArguments(new String[]{"--no-sandbox"});

            driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();

            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(WaitUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(WaitUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        }
        return driver;
    }
}
