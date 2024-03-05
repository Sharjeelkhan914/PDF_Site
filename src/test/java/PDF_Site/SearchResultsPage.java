package PDF_Site;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class SearchResultsPage {
	private WebDriver driver;
	private WebDriverWait wait;
	ExtentReports reports;
	ExtentTest test;

	public SearchResultsPage(WebDriver driver, ExtentTest test, ExtentReports reports) {
		this.driver = driver;
		this.test = test;
		this.reports = reports;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	}

	// Page Elements
	By checkBoxLocator = By.xpath("//*[@id=\"select-pagecount\"]/option[2]");
	By pageCountLocator = By.xpath("//*[@id=\"select-pagecount\"]/option[2]");
	By yearLocator = By.xpath("//*[@id=\"select-pubyear\"]/option[3]");
	By languageLocator = By.xpath("//*[@id=\"select-searchin\"]/option[12]");
	By bookLocator = By.xpath("/html/body/div[3]/div[1]/div[1]/div[4]/ul/li[1]/div/div/div[2]/a");

	// Page Methods
	public void clickCheckbox() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ftcb"))).click();
	}

	public void clickPageCount() {
		driver.findElement(pageCountLocator).click();
	}

	public void clickYear() {
		driver.findElement(yearLocator).click();
	}

	public void clickLanguage() {
		driver.findElement(languageLocator).click();
	}

	public void clickBook() {
		test = reports.createTest("Weblink Verification Test");
		try {
			WebElement webLink = wait.until(ExpectedConditions.elementToBeClickable(bookLocator));
			String bookTitle = webLink.getText();
			webLink.click();
			Assert.assertEquals(bookTitle, "The Hobbit");
			test.log(Status.PASS, "WebLink Assertion Passed\n");

		} catch (AssertionError e) {
			test.log(Status.FAIL, "WebLink Assertion Failed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "ClickBook Method Failed\n" + e.getMessage());
		}

	}
}
