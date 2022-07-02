import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import pageObjects.AmazonPage;
import utils.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class TestAmazon extends GlobalHooks {

    @Test(description = "Test Amazon Page", priority = 1)
    public void T_00001(Method method) throws IOException, ParseException {
        // Reading the Test data from JSON file
        Map<String, Object> testData = DataReadUtils.readData("dataAmazon", "uniqueId", method.getName());

        driver = WebDriverFactory.get(System.getProperty("browserName"));
        AmazonPage amazonPage = new AmazonPage(driver);

        // Step - 1: Open https://www.amazon.in/
        TestUtils.navigateTo(driver, System.getProperty("appUrl"), 5);

        // Step - 2: Click on the hamburger menu in the top left corner.
        TestUtils.clickElement(driver, amazonPage.getHamburgerMenu(), "Hamburger Link", false);

        // Step - 3: Scroll own and then Click on the TV, Appliances and Electronics link under Shop by Department section.
        TestUtils.clickElement(driver, amazonPage.getTvAppliancesElectronicsLink(), "TV, Appliances and Electronics Link", true);

        // Step - 4: Then click on Televisions under Tv, Audio & Cameras sub section.
        TestUtils.clickElement(driver, amazonPage.getTelevisionLink(), "Television Link", false);

        // Step - 5: Scroll down and filter the results by Brand ‘Samsung’.
        amazonPage.filterResults(testData.get("filterType").toString(), testData.get("filterValues").toString());

        // Step - 6: Sort the Samsung results with price High to Low.
        amazonPage.sortResults(testData.get("sortValue").toString());

        // Step - 7: Click on the second highest priced item (whatever that maybe at the time of automating).
        amazonPage.selectProductByOrder(2);

        // Step - 8: Switch the Window
        TestUtils.switchToWindow(driver, 2);

        // Step - 9: Assert that “About this item” section is present and log this section text to console/report.
        boolean result = amazonPage.validateAboutThisItemSection();
        Log.assertThatSoft(result, "'About this item' section is present on Webpage",
                "'About this item' section is NOT present on Webpage", driver);

        Log.testExecutionResult();
    }


}
