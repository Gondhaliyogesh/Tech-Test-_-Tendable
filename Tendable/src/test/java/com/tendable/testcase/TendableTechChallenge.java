package com.tendable.testcase;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TendableTechChallenge {

	private WebDriver driver;
	private String baseUrl = "https://www.tendable.com";

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	/*
	 * Confirm accessibility of the top-level menus: Our Story, Our Solution, and
	 * Why Tendable.
	 */
	@Test(priority = 1)
	public void testTopLevelMenusAccessibility() {
		driver.get(baseUrl);

		List<WebElement> topLevelMenus = driver.findElements(By.xpath("//nav[@id='main-navigation-new']/ul/li"));

		topLevelMenus.forEach(menu -> {
			assertTrue(menu.isDisplayed(), menu.getText() + " menu is not accessible.");
			System.out.println("Menu is accessible: " + menu.getText());
		});
	}

	/*
	 * Verify that the "Request a Demo" button is present and active on each of the
	 * aforementioned top-level menu pages.
	 */
	@Test(priority = 2)
	public void testRequestDemoButtonPresence() {
		driver.get(baseUrl);

		String[] menuItems = { "Our Story", "Our Solution", "Why Tendable?" };

		for (String menuItem : menuItems) {
			clickMenuItem(menuItem);
			assertRequestDemoButtonPresence();
			driver.navigate().back();
		}
	}

	private void clickMenuItem(String menuItem) {
		List<WebElement> menu = driver.findElements(By.xpath("//nav[@id='main-navigation-new']/ul/li"));
		for (WebElement item : menu) {
			if (item.getText().equals(menuItem)) {
				item.click();
				return;
			}
		}
		throw new NoSuchElementException("Menu item not found: " + menuItem);
	}

	private void assertRequestDemoButtonPresence() {
		WebElement requestDemoButton = driver.findElement(By.linkText("Request a Demo"));
		Assert.assertTrue(requestDemoButton.isDisplayed() && requestDemoButton.isEnabled(),
				"Request a Demo button is not present or active.");
	}

	/*
	 * Navigate to the "Contact Us" section, choose "Marketing", and complete the
	 * form— excluding the "Message" field. On submission, an error should arise.
	 * Ensure your script confirms the error message's appearance. If the error is
	 * displayed, mark the test as PASS. If absent, it's a FAIL.
	 */
	@Test(priority = 3)
	public void testContactUsFormError() throws InterruptedException {
		driver.get(baseUrl);

		WebElement contactUsLink = driver.findElement(By.partialLinkText("Contact Us"));
		contactUsLink.click();

		WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout

		WebElement marketingLink = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.cssSelector("body > div:nth-child(7) > div:nth-child(1) > div:nth-child(1) "
						+ "> div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > button:nth-child(1)")));
		marketingLink.click();

		// Fill out the form excluding the "Message" field
		driver.findElement(By.id("form-input-fullName")).sendKeys("Yogesh");
		driver.findElement(By.id("form-input-organisationName")).sendKeys("Gondhali");
		driver.findElement(By.id("form-input-cellPhone")).sendKeys("8007913281");
		driver.findElement(By.id("form-input-email")).sendKeys("yogeshgondhali03@gmail.com");

		// Select Job Role
		WebElement jobRole = driver.findElement(By.id("form-input-jobRole"));

		Select sel = new Select(jobRole);
		sel.selectByVisibleText("Management");

		Thread.sleep(5000);
		// I agree
		WebElement iAgree = driver.findElement(
				By.xpath("//form[@id='contactMarketingPipedrive-163701']//input[@id='form-input-consentAgreed-0']"));

		iAgree.click();

		Thread.sleep(3000);
		// Submit the form
		driver.findElement(By.name("form_page_submit")).submit();

		WebElement errorMessage = driver.findElement(By.cssSelector(".ff-errors"));
		Assert.assertNotNull(errorMessage, "Error message not displayed.");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
