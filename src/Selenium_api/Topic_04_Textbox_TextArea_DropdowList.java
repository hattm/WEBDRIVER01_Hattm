package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_04_Textbox_TextArea_DropdowList {
	WebDriver driver;
	String name, dob, address, city, pin, phone, email, password, state, customerID = null, newAddress, newCity;
	// BY ELEMENT
	By nameTextbox = By.xpath("//input[@name='name']");
	By genderTextbox = By.xpath("//input[@name='gender']");
	By dobTextbox = By.xpath("//input[@name='dob']");
	By addressTextArea = By.xpath("//textarea[@name='addr']");
	By cityTextbox = By.xpath("//input[@name='city']");
	By stateTextbox = By.xpath("//input[@name='state']");
	By pinTextbox = By.xpath("//input[@name='pinno']");
	By phoneTextbox = By.xpath("//input[@name='telephoneno']");
	By emailTextbox = By.xpath("//input[@name='emailid']");
	By passwordTextbox = By.xpath("//input[@name='password']");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// DATA TEST INPUT VARIABLE
		name = "Automation Test";
		dob = "1995-01-02";
		address = "123 Nguyen van Linh";
		city = "Ha Noi";
		state = "Cam Le";
		pin = "653298";
		phone = "0123456789";
		email = "auto" + randomUniqueNumber() + "@gmail.com";
		password = "123123";
		newAddress = "234 Le Duan";
		newCity = "Bac Giang";

	}
	
	public void TC_01_DropDowList() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		Select jobRole = new Select(driver.findElement(By.xpath("//select[@id='job1']")));

		Assert.assertTrue(!jobRole.isMultiple());

		jobRole.selectByVisibleText("Automation Testing");
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Automation Testing");
		Thread.sleep(5000);

		jobRole.selectByValue("manual");
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Manual Testing");

		jobRole.selectByIndex(3);
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Mobile Testing");

		int jobItems = jobRole.getOptions().size();
		Assert.assertEquals(jobItems, 10);

	}

	@Test
	public void TC_02_Textbox_TextArea() {
		driver.get("http://demo.guru99.com/v4");

		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys("mngr313080");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("uzapetY");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]"))
				.isDisplayed());

		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		// INPUT DATA TO NEW CUSTOMER FORM
		driver.findElement(nameTextbox).sendKeys(name);
		driver.findElement(dobTextbox).sendKeys(dob);
		driver.findElement(addressTextArea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextbox).sendKeys(pin);
		driver.findElement(phoneTextbox).sendKeys(phone);
		driver.findElement(emailTextbox).sendKeys(email);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='sub']")).click();

		// GET DYNAMIC CUSTOMER ID
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println("DYNAMIC CUSTOMER ID=" + customerID);

		// VERIFY CREATE NEW CUSTOMER CUSSESS
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dob);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				email);

		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(customerID);
		driver.findElement(By.xpath("//input[@name='AccSubmit']")).click();

		// CHECK 3 FIELDs(NAME/GENDERT/DATE OF BIRTH) are disable
		Assert.assertFalse(driver.findElement(nameTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(genderTextbox).isEnabled());
		Assert.assertFalse(driver.findElement(dobTextbox).isEnabled());

		Assert.assertTrue(driver.findElement(cityTextbox).isEnabled());

		// VERIFY CUSTOMER NAME/ ADDRESS EQUAL INPUT DATA
		Assert.assertEquals(driver.findElement(nameTextbox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(addressTextArea).getText(), address);

		// EDIT DATA FOR ADDRESS/ CITY

		driver.findElement(addressTextArea).clear();
		driver.findElement(addressTextArea).sendKeys(newAddress);
		driver.findElement(cityTextbox).clear();
		driver.findElement(cityTextbox).sendKeys(newCity);
		driver.findElement(By.xpath("//input[@name='sub']")).click();

		// VERIFY EDIT CUSTOMER SUCCESS
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),newAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),newCity);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomUniqueNumber() {
		Random rand = new Random();
		int number = rand.nextInt(999999) + 1;
		return number;

	}
}