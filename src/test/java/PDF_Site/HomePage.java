package PDF_Site;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class HomePage {
	private WebDriver driver;
	private WebDriverWait wait;
	ExtentTest test;
	ExtentReports reports;

	public HomePage(WebDriver driver, ExtentTest test, ExtentReports reports) {
		this.driver = driver;
		this.test = test;
		this.reports = reports;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// Page Elements
	By searchBoxLocator = By.id("q");
	By enterLocator = By.xpath("//*[@id='search-form']/button");

	// Page Methods
	public void openHomePage(String url) {
		test = reports.createTest("Opening PDF Site");
		driver.get(url);
		test.log(Status.INFO, "Opened Successfully");
	}

	public void searchForBook(String bookName) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator)).sendKeys(bookName);
	}

	public void clickSearch() {
		driver.findElement(enterLocator).click();
	}
}
