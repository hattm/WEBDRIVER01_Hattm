package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_Xpath_Css_Locator {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_CheckNavigate() {
		driver.get("http://live.demoguru99.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Home page");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.navigate().back();
		Assert.assertTrue(driver.findElement(By.xpath("//form[@id='login-form']")).isDisplayed());
		String loginUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginUrl, "http://live.demoguru99.com/index.php/customer/account/login/");
		driver.navigate().forward();
		Assert.assertTrue(driver.findElement(By.xpath("//form[@id='form-validate']")).isDisplayed());
		String registerUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerUrl, "http://live.demoguru99.com/index.php/customer/account/create/");

	}

	@Test
	public void TC_02_LoginWithUserPassEmpty() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		String usernameEmptyMessage = driver.findElement(By.xpath("//div[@id='advice-required-entry-email']"))
				.getText();
		Assert.assertEquals(usernameEmptyMessage, "This is a required field.");
		String passwordEmptyMessage = driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).getText();
		Assert.assertEquals(passwordEmptyMessage, "This is a required field.");
	}

	@Test
	public void TC_03_LoginWithEmailInvalid() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("1243@13424.1324");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		String usernameInvalidMessage = driver.findElement(By.xpath("//div[@id='advice-validate-email-email']"))
				.getText();
		Assert.assertEquals(usernameInvalidMessage,
				"Please enter a valid email address. For example johndoe@domain.com.");

	}

	@Test
	public void TC_04_LoginWithPasswordLessThan6Characters() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.cssSelector("#pass")).sendKeys("1234");
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		String passwordIncorrectMessage = driver.findElement(By.xpath("//div[@id='advice-validate-password-pass']"))
				.getText();
		Assert.assertEquals(passwordIncorrectMessage,
				"Please enter 6 or more characters without leading or trailing spaces.");

	}

	@Test
	public void TC_05_CreateAnAcount() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("automation" + randomEmail() + "@gmail.com");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomEmail() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return number;

	}

}
