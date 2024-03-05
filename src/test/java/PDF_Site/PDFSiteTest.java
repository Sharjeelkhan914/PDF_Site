package PDF_Site;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PDFSiteTest {
	
	/* Note: I recommend to run this class directly from here
	 * Note: We can run this from testng.xml but it will take few seconds to initiate driver
	 */
	
	private WebDriver driver;
	private HomePage homePage;
	private ExtentSparkReporter reporter;
	private ExtentReports reports;
	private ExtentTest test;
	private SearchResultsPage searchResultsPage;
	private PageViewIniFrame pageView;

	String fileSuffix = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now());
	String fileName = "ExtentReport_" + fileSuffix + ".html";

	@BeforeClass
	public void setUp() {
		reporter = new ExtentSparkReporter(fileName);
		reports = new ExtentReports();
		reports.attachReporter(reporter);
		reports.setSystemInfo("user", "Muhammad Sharjeel Khan SSQAE");
		reporter.config().setDocumentTitle("ExtentReport_" + fileName);
		reporter.config().setReportName("PDF_Site_Test_Project");
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setTimeStampFormat(fileSuffix);
		test = reports.createTest("Opening Browser");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		test.log(Status.INFO, "Browser opened and maximized.");
		homePage = new HomePage(driver, test, reports);
		searchResultsPage = new SearchResultsPage(driver, test, reports);
		pageView = new PageViewIniFrame(driver, test, reports);
	}

	@Test
	public void verifyPDFDownload() {
		homePage.openHomePage("https://www.pdfdrive.com"); // Opening PDF Site Test
		homePage.searchForBook("The Hobbit");
		homePage.clickSearch();
		searchResultsPage.clickCheckbox();
		searchResultsPage.clickPageCount();
		searchResultsPage.clickYear();
		searchResultsPage.clickLanguage(); /*
											 * Author Name Display Verification (Lola Hatcher is not displayed in book
											 * The Hobbit so it is supposed to fail.)
											 */

		searchResultsPage.clickBook(); // Weblink Verification
		pageView.getBookTitle();
		pageView.authorDisplayTest();
		pageView.clickPreviewBtn(); // Preview Button Display Verification
		pageView.switchToViewerFrame();
		pageView.enterPageNumber(24); /* All 24 Pages Loading Verification Maybe you will have to rerun this suite
									 	    multiple times to pass all assertions as most of time pages in iframe do not load. */
										 
		pageView.getAuthorsInformation(); // Text under Author Heading Verification
		pageView.switchToDefaultFrame();
		pageView.clickRemoteBtn();
		pageView.downloadBtn(); // Download Button Display Verification

		/*
		 * Note: See ExtentReport_date_time.html in project directory above pom.xml file for detailed html reports.
		 * Note: You will need to refresh project so that it render latest report in project directory.
		 */

	}

	@AfterClass
	public void tearDown() {
		reports.flush();
		driver.quit();
	}
}
