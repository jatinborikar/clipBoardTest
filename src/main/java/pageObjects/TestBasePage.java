package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class TestBasePage {

	private final WebDriver driver;

	public TestBasePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}

