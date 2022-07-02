package utils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class GlobalHooks {

    public static String browserType;
    protected WebDriver driver;

    @BeforeSuite (alwaysRun = true)
    public void beforeSuite(ITestContext context) {

        // Read the data from Current XML file or Consul and store it in System class variable
        System.setProperty("appUrl", context.getCurrentXmlTest().getParameter("appUrl"));
        System.setProperty("browserName", context.getCurrentXmlTest().getParameter("browserName"));
        System.setProperty("headLessBrowser", context.getCurrentXmlTest().getParameter("headLessBrowser"));
        System.setProperty("remoteRun", context.getCurrentXmlTest().getParameter("remoteRun"));
        System.setProperty("hubAddress", context.getCurrentXmlTest().getParameter("hubAddress"));
    }

    @BeforeTest (alwaysRun = true)
    public void beforeTest(ITestContext context) {
        browserType = context.getCurrentXmlTest().getParameter("browserName");
        Log.info("Execution will start on Web Browser Name: " + browserType.toUpperCase());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext context) {
        this.closeDriver();
    }

    private void closeDriver(){
        if(this.driver!=null) {
            this.driver.quit();
        }
    }



}
