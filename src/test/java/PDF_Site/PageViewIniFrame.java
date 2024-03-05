package PDF_Site;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.time.Duration;
import java.util.List;

public class PageViewIniFrame {
	private WebDriver driver;
	private WebDriverWait wait;
	ExtentTest test;
	ExtentReports reports;

	public PageViewIniFrame(WebDriver driver, ExtentTest test, ExtentReports reports) {
		this.driver = driver;
		this.test = test;
		this.reports = reports;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// Page Elements
	By iframeLocator = By.id("viewerPro");
	By bookDetails = By.xpath("//h1[contains(text(),'The Hobbit')]");
	By previewBtnLocator = By.xpath("//button[contains(text(),'Preview')]");
	By pageCountLocator = By.xpath("/html/body/div[1]/div[3]/div[3]/div[1]/div[2]/input");
	By lastHeadingLocator = By.xpath("//p[contains(text(), \"about this guide’s authors\")]");
	By siblingLocator = By.xpath("following-sibling::p");
	By remoteBtnLocator = By.xpath("//a[@id='goToFileButtonMemberModal']");
	By downloadBtnLocator = By.xpath("//*[@id=\"alternatives\"]/div[1]/a");
	By lolaLocator = By.xpath("//*[contains(text(), 'Lola Hatcher')");

	// Page Methods
	public void getBookTitle() {
		WebElement bookLink = wait.until(ExpectedConditions.visibilityOfElementLocated(bookDetails));
		Assert.assertEquals(bookLink.getText(), "The Hobbit");
	}

	public void clickPreviewBtn() {
		test = reports.createTest("Preview Button Display Verification");
		try {
			WebElement previewBtn = driver.findElement(previewBtnLocator);
			Assert.assertTrue(previewBtn.isDisplayed());
			test.log(Status.PASS, "Preview button is displayed successfully");
			previewBtn.click();
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Preview Button is not displayed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "click preview button is failed\n" + e.getMessage());
		}
	}

	public void authorDisplayTest() {
		test = reports.createTest("Author Name Display Verification");
		try {
			WebElement authorname = driver.findElement(lolaLocator);
			Assert.assertTrue(authorname.isDisplayed());
			test.log(Status.PASS, "Author Name is displayed successfully");
			authorname.click();
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Lola Hatcher is not displayed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "authorDisplayTest is failed\n" + e.getMessage());
		}
	}

	public void switchToViewerFrame() {

		WebElement iframe = driver.findElement(iframeLocator);

		driver.switchTo().frame(iframe);

	}

	public void enterPageNumber(int pageNumber) {
		test = reports.createTest("All 24 Pages Loading Verification");
		try {
			WebElement pageNo = wait.until(ExpectedConditions.visibilityOfElementLocated(pageCountLocator));
			// pageNo.clear();
			// pageNo.sendKeys(String.valueOf(pageNumber));
			// pageNo.submit();
			pageNo.sendKeys(Keys.BACK_SPACE);
			pageNo.sendKeys(String.valueOf(pageNumber) + Keys.ENTER);
			WebElement lastHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(lastHeadingLocator));
			String headingText = lastHeading.getText();
			Assert.assertEquals(headingText, "about this guide’s authors");
			test.log(Status.PASS, "All 24 Pages Loaded Verification Passed\n");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "All 24 Pages Loaded Verification Failed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "Enter Page Number method is failed\n" + e.getMessage());
		}

	}

	public void getAuthorsInformation() {
		String verifyText = "This guide was written in 1981 by Robert Foster. It has been updated and revised by Amy\n"
				+ "Jurskis to now include the Common Core State Standards.\n"
				+ "Robert Foster is the author of The Complete Guide to Middle-Earth. Foster has taught\n"
				+ "Tolkien, science fiction, and fantasy at Rutgers University in New Brunswick, New Jersey.\n"
				+ "Foster holds a BA in Linguistics from Columbia University and an MA and a PhD in English\n"
				+ "and Medieval Literature from the University of Pennsylvania.\n"
				+ "Amy Jurskis is the author of several teaching guides. A former department chair for\n"
				+ "language arts in a title-one public school in Atlanta, she currently teaches English at\n"
				+ "Oxbridge Academy of the Palm Beaches in West Palm Beach, Florida.\n" + "RANDOM HOUSE, INC.";
		test = reports.createTest("Text under Author Heading Verification");
		try {
			WebElement lastHeading = driver.findElement(lastHeadingLocator);
			List<WebElement> siblingElements = lastHeading.findElements(siblingLocator);
			StringBuilder textUnderHeading = new StringBuilder();
			for (WebElement siblingElement : siblingElements) {
				String text = siblingElement.getText();
				textUnderHeading.append(text).append("\n");
			}
			String actualText = textUnderHeading.toString().trim();
			Assert.assertEquals(actualText, verifyText.trim());
			test.log(Status.PASS, "Text Verification under Author's Heading Passed\n");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Text Verification under Author's Heading Failed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "Get Author Information method is failed\n" + e.getMessage());
		}
	}

	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	public void clickRemoteBtn() {
		driver.findElement(remoteBtnLocator).click();
	}

	public void downloadBtn() {
		test = reports.createTest("Download Button Display Verification");
		try {
			WebElement downloadBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(downloadBtnLocator));
			Assert.assertTrue(downloadBtn.isDisplayed());
			test.log(Status.PASS, "Download button is displayed successfully");
		} catch (AssertionError e) {
			test.log(Status.FAIL, "Download Button is not displayed\n" + e.getMessage());
		} catch (Exception e) {
			test.log(Status.FAIL, "Download button Display method is failed\n" + e.getMessage());
		}
	}

}
