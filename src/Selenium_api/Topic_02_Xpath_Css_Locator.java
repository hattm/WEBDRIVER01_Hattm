package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_Xpath_Css_Locator {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	/* Verify URL and title
	 * Step 01 - Truy vào trang: http://live.demoguru99.com/index.php
	 * Step 02 - Kiểm tra title của page là: Home page
	 * Step 03 - Click vào link "My Account" để tới trang đăng nhập
	 * Step 04 - Click CREATE AN ACCOUNT button để tới trang đăng ký khoản
	 * Step 05 - Back lại trang đăng nhập
	 * Step 06 - Kiểm tra url của page tạo tài khoản
	 * Step 07 - forward tới trang tạo tài khoản
	 * Step 08 - Kiểm tra url của page tạo tài khoản là http://live.demoguru99.com/index.php/customer/account/create
	 * */

	public void TC_01_CheckNavigate() {
		driver.get("http://live.demoguru99.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Home page");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		
		driver.navigate().back();
		// Về lại page login thành công 
		Assert.assertTrue(driver.findElement(By.xpath("//form[@id='login-form']")).isDisplayed());
		
		String loginUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginUrl, "http://live.demoguru99.com/index.php/customer/account/login/");
		
		driver.navigate().forward();
		
		// Ve lai page register thanh cong
		Assert.assertTrue(driver.findElement(By.xpath("//form[@id='form-validate']")).isDisplayed());
		
		String registerUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerUrl, "http://live.demoguru99.com/index.php/customer/account/create/");

	}

	/* Test Script Login empty
	 * Step 01- Truy vào trang: http://live.demoguru99.com/index.php
	 * Step 02: Click vào link "My Account" để tới trang đăng nhập
	 * Step 03: Để trống Usertname/ Password
	 * Step 04: Click Login button
	 * Step  05: Verify error message xuất hiện tại 2 field: This is a require field
	 * */
	
	public void TC_02_LoginWithUserPassEmpty() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		
		String usernameEmptyMessage = driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).getText();
		Assert.assertEquals(usernameEmptyMessage, "This is a required field.");
		
		String passwordEmptyMessage = driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).getText();
		Assert.assertEquals(passwordEmptyMessage, "This is a required field.");
	}

	/* Test Script  Email invalid
	 * Step 01- Truy vào trang: http://live.demoguru99.com/index.php
	 * Step 02: Click vào link "My Account" để tới trang đăng nhập
	 * Step 03: Nhập email invalid: 1242342@13134242
	 * Step 04: Click Login button
	 * Step  05: Verify error message xuất hiện: Please enter a valid email address. For example johndoe@domain.com
	 * */

	public void TC_03_LoginWithEmailInvalid() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("1243@13424.1324");
		
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		String usernameInvalidMessage = driver.findElement(By.xpath("//div[@id='advice-validate-email-email']")).getText();
		
		Assert.assertEquals(usernameInvalidMessage,
				"Please enter a valid email address. For example johndoe@domain.com.");

	}

	public void TC_04_LoginWithPasswordLessThan6Characters() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("1234");
		
		driver.findElement(By.xpath("//button[@id='send2']")).click();
		String passwordIncorrectMessage = driver.findElement(By.xpath("//div[@id='advice-validate-password-pass']")).getText();
		
		Assert.assertEquals(passwordIncorrectMessage,
				"Please enter 6 or more characters without leading or trailing spaces.");

	}
	
	
	/* Test Script  Create an account
	 * Step 01- Truy vào trang: http://live.demoguru99.com/index.php
	 * Step 02: Click vào link "My Account" để tới trang đăng nhập
	 * Step 03: Click CREATE AN ACCOUNT button để tới trang đăng ký tài khoản
	 * Step 04: Nhập thông tin hợp lệ vào tất cả các field: First Name/ Last Name/ Email Address/ Password/ Confirm Password
	 * Lưu ý: Tạo random cho dữ liệu tại field Email Address
	 * Step 05: Click REGISTER button
	 * Step 06: Verify message xuất hiện khi đăng ký thành công: Thank you for registering with Main Website Store
	 * Step 06: Logout khỏi hệ thống
	 * Step 07: Kiểm tra hệ thống navigate về Home page sau khi logout thành công
	 * */
	@Test
	public void TC_05_CreateAnAcount() throws InterruptedException {
		driver.get("http://live.demoguru99.com/index.php");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@class='button']")).click();
		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Tran");
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("Ha");
		driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys("automation" + randomEmail() + "@gmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
		driver.findElement(By.xpath("//input[@id='confirmation']")).sendKeys("123456");
		driver.findElement(By.xpath("//button[@class='button']")).click();
		String messagesuccess=  driver.findElement(By.xpath("//span[contains(text(),'Thank you for registering with Main Website Store.')]")).getText();
		Assert.assertEquals(messagesuccess, "Thank you for registering with Main Website Store.");
		
		Thread.sleep(5000);
		WebElement righClickbutton =  driver.findElement(By.xpath("//a[@class='skip-link skip-account']"));
		 Actions action = new Actions(driver);
		  // Righ click
		  action.contextClick(righClickbutton).perform();
		  WebElement quitBefore = driver.findElement(By.xpath("//a[contains(text(),'Log Out')]"));
		  // Hover to quit
		  action.moveToElement(quitBefore).perform();
		  
	
		
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
