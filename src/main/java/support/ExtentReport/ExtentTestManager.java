package support.ExtentReport;

import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import support.EventListener.TestListener;

import java.util.HashMap;
import java.util.Map;


public class ExtentTestManager {

	
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	static ExtentReports extent = ReportLocation.getReporter();
	

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));

    }
 
    public static synchronized void endTest() {
        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }
 
    
	public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static void pass(String message){
        TestListener.test.get().log(Status.PASS, "<font colour=\"green\">" + message + "</font>");
    }

    public static void fail(String message){
        TestListener.test.get().log(Status.FAIL, "<font colour=\"red\">" + message + "</font>");
    }

    public static void fatal(String message){
        TestListener.test.get().log(Status.FATAL, message);
    }

    public static void skip(String message){
        TestListener.test.get().log(Status.SKIP, "<font colour=\"orange\">" + message + "</font>");
    }

    public static void debug(String message){
        TestListener.test.get().log(Status.DEBUG, message);
    }

    public static void error(String message){
        TestListener.test.get().log(Status.ERROR, "<font colour=\"red\">" + message + "</font>");
    }

    public static void info(String message){
        TestListener.test.get().log(Status.INFO, message);
    }






}
