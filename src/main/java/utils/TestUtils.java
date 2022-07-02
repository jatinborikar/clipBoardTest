package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestUtils extends GlobalHooks {

	public static WebDriver switchToWindow(WebDriver driver, int windowNumber) {
		ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
		Log.logEvent("Switching to the window number: " + windowNumber);
		return driver.switchTo().window(windows.get(windowNumber-1));
	}

	public static void navigateTo(WebDriver driver, String url, int waitTime) {
		WaitUtils.nap(WaitUtils.STATIC_TIME_OUT);
		Log.logEvent("Navigating to the URL: " + url);
		driver.get(url);
		WaitUtils.nap(waitTime); //Hard Wait is given as loading of view page might take sometime
	}

	public static boolean isDisplayed(WebDriver driver, WebElement webElement) {
		try {
			ScrollManager.scrollintoView(driver, webElement);
			return webElement.isDisplayed();
		} catch (Exception e) {
			Log.logError("Exception Occurred: " + e.getMessage());
			return false;
		}
	}

	public static void clickElementByJS(WebDriver driver, WebElement element, String elementName, boolean scrollIntoView) {
		WaitUtils.waitUntilElementVisible(driver, element, WaitUtils.MAX_ELEMENT_WAIT);
		if(scrollIntoView) {
			ScrollManager.scrollintoView(driver, element);
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", new Object[] {element});
		Log.logEvent("Clicking on the Element: " + elementName);
		WaitUtils.nap(WaitUtils.STATIC_TIME_OUT);
	}

	public static void clickElement(WebDriver driver, WebElement element, String elementName, boolean scrollIntoView) {
		WaitUtils.waitUntilElementVisible(driver, element, WaitUtils.MAX_ELEMENT_WAIT);
		WaitUtils.nap(WaitUtils.STATIC_TIME_OUT);
		if(scrollIntoView) {
			ScrollManager.scrollintoView(driver, element);
		}
		element.click();
		Log.logEvent("Clicking on the Element: " + elementName);
		WaitUtils.nap(WaitUtils.STATIC_TIME_OUT);

	}

	public static WebElement find(WebDriver driver, By locator){
    	return driver.findElement(locator);
	}

	public static void takeScreenshot(WebDriver driver, String screenShotName){
    	TakesScreenshot screenshot = ((TakesScreenshot)driver);
		File fileScr = screenshot.getScreenshotAs(OutputType.FILE);
		String folderName = System.getProperty("user.dir") + File.separator+"Screenshots" + File.separator+ "GeneralScreenshots"
				+ File.separator+Reporter.getCurrentTestResult().getName()+"_"+Reporter.getCurrentTestResult().getMethod().getDescription();
		new File(folderName).mkdirs();
		File fileDestination = new File(folderName+File.separator+screenShotName+".png");

		try {
			FileUtils.copyFile(fileScr, fileDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
