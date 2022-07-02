package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;
import utils.TestUtils;

public class AmazonPage extends TestBasePage {

    private final WebDriver driver;

    public AmazonPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//*[@id='nav-main']/div[@class='nav-left']")
    private WebElement hamburgerMenu;

    @FindBy(xpath = "//a/div[text()='TV, Appliances, Electronics']")
    private WebElement tvAppliancesElectronicsLink;

    @FindBy(xpath = "//a[text()='Televisions']")
    private WebElement televisionLink;

    @FindBy(xpath = "//*[@class='a-button-text a-declarative']")
    private WebElement sortDropDown;

    @FindBy(xpath = "//*[normalize-space(text())='About this item']/following-sibling::ul")
    private WebElement aboutThisItemSection;

    public WebElement getHamburgerMenu() {
        return hamburgerMenu;
    }

    public WebElement getTvAppliancesElectronicsLink() {
        return tvAppliancesElectronicsLink;
    }

    public WebElement getTelevisionLink() {
        return televisionLink;
    }

    public void filterResults(String filterType, String filterValues) {
        try {
            String filterValue[] = filterValues.split(";");
            for (String value : filterValue) {
                String xpath = "//*[text()='" + filterType + "']/../following-sibling::ul[1]//span[text()='" + value + "']";
                WebElement webElement = TestUtils.find(driver, By.xpath(xpath));
                TestUtils.clickElement(driver, webElement, filterType, true);
                Log.message("Filter applied on the results with " + filterType + ": " + filterValues);
            }
        } catch (Exception e) {
            Log.logError("Unable to select the filters" + e.getMessage());
        }
    }

    public void sortResults(String sortByValue) {
        try {
            TestUtils.clickElement(driver, sortDropDown, "Sort Drop Down", false);
            String xpath = "//div[@id='a-popover-2']//a[text()='" + sortByValue + "']";
            WebElement webElement = TestUtils.find(driver, By.xpath(xpath));
            TestUtils.clickElement(driver, webElement, sortByValue, false);
            Log.message("Results sorted by: " + sortByValue);
        } catch (Exception e) {
            Log.logError("Unable to sort the results" + e.getMessage());
        }
    }

    public void selectProductByOrder(int itemNumber) {
        try {
            String xpath = "//span[@data-component-type='s-search-results']/div[2]/div[" + (itemNumber + 1) + "]//a";
            WebElement webElement = TestUtils.find(driver, By.xpath(xpath));
            TestUtils.clickElement(driver, webElement, "Product", false);
            Log.message("Product placed at the position '" + itemNumber + "' has been selected");
        } catch (Exception e) {
            Log.logError("Unable to select the product" + e.getMessage());
        }
    }

    public boolean validateAboutThisItemSection() {
        return TestUtils.isDisplayed(driver, aboutThisItemSection);
    }

}
