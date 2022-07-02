//**********************************************************************************************************
//Description: ExtentReports related operations are done by this class. I added extra functionality such as
//"getCurrentPlatform". In this way, framework can create a report folder and file based on OS.
//Reference: http://extentreports.com/docs/versions/3/java/
//**********************************************************************************************************

package support.ExtentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Platform;

import java.io.File;

public class ExtentManager {
	
  private static ExtentReports extent;
  private static Platform platform;
  private static final String reportFileName = "ExtentReport.html" ;
  private static final String dirPath = System.getProperty("user.dir") + File.separator + "ExtentReports";
  private static final String reportFileLoc = dirPath + File.separator + reportFileName;


  //Create an extent report instance
  public static ExtentReports createInstance() {
     

	  platform = getCurrentPlatform();
      String fileName = getReportFileLocation(platform);
      ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
      htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
      htmlReporter.config().setChartVisibilityOnOpen(true);
      htmlReporter.config().setTheme(Theme.STANDARD);
      htmlReporter.config().setDocumentTitle("EFE Salesforce Test Automation Results");
      htmlReporter.config().setEncoding("utf-8");
      htmlReporter.config().setReportName("EFE Salesforce Test Automation Results");
      htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

      extent = new ExtentReports();
      extent.attachReporter(htmlReporter);
      
      extent.setSystemInfo("OS", platform.toString());
      extent.setSystemInfo("OS Version", System.getProperty("os.version"));
      extent.setSystemInfo("Java Version", System.getProperty("java.runtime.version"));
      extent.setSystemInfo("Username", System.getProperty("user.name"));

      return extent;
  }

  //Select the extent report file location based on platform
  private static String getReportFileLocation (Platform platform) {
      String reportFileLocation = null;
     
      switch (platform) {
          case MAC:
              reportFileLocation = reportFileLoc;
              createReportPath(dirPath);
              System.out.println("ExtentReport Path for MAC: " + dirPath + "\n");
              break;

          case WINDOWS:
              reportFileLocation = reportFileLoc;
              createReportPath(dirPath);
              System.out.println("ExtentReport Path for WINDOWS: " + dirPath + "\n");
              break;

          case LINUX:
              reportFileLocation = reportFileLoc;
              createReportPath(dirPath);
              System.out.println("ExtentReport Path for LINUX: " + dirPath + "\n");
              break;

          default:
              System.out.println("ExtentReport path has not been set! There is a problem!\n");
              break;
      }
      return reportFileLocation;
  }


  
  
  
  //Create the report path if it does not exist
  private static void createReportPath (String path) {
      File testDirectory = new File(path);
      if (!testDirectory.exists()) {
          if (testDirectory.mkdir()) {
              System.out.println("Directory: " + path + " is created!" );
          } else {
              System.out.println("Failed to create directory: " + path);
          }
      } else {
          System.out.println("Directory already exists: " + path);
      }
  }

  //Get current platform
  private static Platform getCurrentPlatform () {
      if (platform == null) {
          String operSys = System.getProperty("os.name").toLowerCase();
          System.out.println("Operation System: " + operSys);
          if (operSys.contains("win")) {
              platform = Platform.WINDOWS;
          } else if (operSys.contains("nix") || operSys.contains("nux")
                  || operSys.contains("aix")) {
              platform = Platform.LINUX;
          } else if (operSys.contains("mac")) {
              platform = Platform.MAC;
          }
      }
      return platform;
  }
}



