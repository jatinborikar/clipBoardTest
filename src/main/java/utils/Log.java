package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import support.ExtentReport.ExtentTestManager;

import java.util.concurrent.atomic.AtomicInteger;


public class Log {

    private static final AtomicInteger screenShotIndex = new AtomicInteger(0);

    // Initialize Log4j instance
    public static Logger logger () {
        return Logger.getLogger(Thread.currentThread().getName());
    }

    // Info Level Logs
	public static void info (String message) { logger().info(message); }
 
    // Error Level Logs
    public static void error (String message) {
        logger().error(message);
    }

    // Method for SoftAssertion
    public static void assertThatSoft(boolean status, String passMessage, String failMessage) {
        if (!status){
            failSoft(failMessage);
        } else {
            pass(passMessage);
        }
    }

    // Method for SoftAssertion with screenshots
    public static void assertThatSoft(boolean status, String passMessage, String failMessage, WebDriver driver) {
        if (!status){
            failSoft(failMessage);
            String inputFileName = Reporter.getCurrentTestResult().getName() + "_" + screenShotIndex.incrementAndGet();
            TestUtils.takeScreenshot(driver, inputFileName);
        } else {
            pass(passMessage);
            String inputFileName = Reporter.getCurrentTestResult().getName() + "_" + screenShotIndex.incrementAndGet();
            TestUtils.takeScreenshot(driver, inputFileName);
        }
    }

    public static void failSoft(String message) {
        Reporter.log("---FAILSOFT---");
        Reporter.log(message);
        error(message);
        ExtentTestManager.fail(message);
    }

    public static void pass(String message) {
        Reporter.log(message);
        info(message);
        ExtentTestManager.pass(message);
    }

    public static void testExecutionResult() {
        String reporterOutput = Reporter.getOutput(Reporter.getCurrentTestResult()).toString();
        if(reporterOutput.contains("FAILSOFT")) {
            Assert.fail("Test Execution Failed. Check Steps");
        } else if(reporterOutput.contains("FAIL")) {
            Assert.fail("Test Execution Failed. Check Steps");
        } else if(reporterOutput.contains("UnhandledException")) {
            Assert.fail("Test Execution Failed. Check Steps");
        } else {
            pass("Test Execution Completed");
        }
    }

    public static void logEvent(String message) {
        Reporter.log(message);
        info(message);
        ExtentTestManager.info(message);
    }

    public static void logError(String message) {
        Reporter.log(message);
        Reporter.log("FAIL");
        error(message);
        ExtentTestManager.error(message);
    }

    public static void message(String message) {
        Reporter.log(message);
        info(message);
        ExtentTestManager.info(message);
    }

}
