package support.EventListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import support.ExtentReport.ExtentManager;
import utils.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;


public class TestListener implements ITestListener  {

    // Extent Report Declarations
    public static ExtentReports extent = ExtentManager.createInstance();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static ExtentTest extentTest;


    @Override
    public synchronized void onStart(ITestContext context) {
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        Log.info("//////////////////// Automation Script Execution Completed! /////////////////////////");
        extent.flush();
    }
 
    @Override
    public synchronized void onTestStart(ITestResult result) {
        extentTest = extent.createTest(result.getMethod().getMethodName() + ": " + result.getMethod().getDescription());
        test.set(extentTest);
    }
 
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
        Log.info("\n"+"####################### Test Case: "+result.getMethod().getMethodName() + ": "+ result.getMethod().getDescription() + " is passed!####################################"+"\n");
    }
 
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " "+ result.getMethod().getDescription() + " failed!"));

        test.get().fail(result.getThrowable());
        Log.info("\n"+"############### Test Case: "+result.getMethod().getMethodName() + ": "+ result.getMethod().getDescription() + " is Failed!#################"+"\n");

        String testCaseName = result.getMethod().getMethodName() + "_" + result.getMethod().getDescription();

        // Capturing Screenshots on Failure
        WebDriver driver = null;
        try {
            // Getting the driver instance from Test Class
            Field field = result.getTestClass().getRealClass().getSuperclass().getDeclaredField("driver");

            // Setting the access for driver as its a protected variable
            field.setAccessible(true);

            // Assigning the driver
            driver = (WebDriver) field.get(result.getInstance());

        } catch (Exception e) {
            e.printStackTrace();
        }
            TakesScreenshot screenshot = ((TakesScreenshot) driver);
            File fileScr = screenshot.getScreenshotAs(OutputType.FILE);
            File fileDestination = new File(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + "FailedTestScreenshots" + File.separator + testCaseName + "_failed.png");

            try {
                FileUtils.copyFile(fileScr, fileDestination);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName() + "_" + result.getMethod().getDescription();

        Log.info(testCaseName + " skipped!");
        test.get().skip(result.getThrowable());

        // Capturing Screenshots on Skipped
        WebDriver driver = null;
        try {
            // Getting the driver instance from Test Class
            Field field = result.getTestClass().getRealClass().getSuperclass().getDeclaredField("driver");

            // Setting the access for driver as its a protected variable
            field.setAccessible(true);

            // Assigning the driver
            driver = (WebDriver) field.get(result.getInstance());

        } catch (Exception e) {
            e.printStackTrace();
        }
            TakesScreenshot screenshot = ((TakesScreenshot) driver);
            File fileScr = screenshot.getScreenshotAs(OutputType.FILE);
            File fileDestination = new File(System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + "SkippedTestScreenshots" + File.separator + testCaseName + "_skipped.png");

            try {
                FileUtils.copyFile(fileScr, fileDestination);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}


}
