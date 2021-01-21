package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_03_WebElement_Browser {
	WebDriver driver;
	

  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
	  driver = new ChromeDriver();
	  
	  System.setProperty("webdriver.ie.driver", ".\\driver\\IEDriverServer.exe");
	  driver = new InternetExplorerDriver();
	  
	  driver = new FirefoxDriver();
	  driver.manage().window().maximize();
	  driver.get("https://automationfc.github.io/basic-form/");
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

  }
  
  
  @Test
  public void TC_01_IsDisplay() {
	  WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='mail']"));
	  WebElement ageRadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
	  WebElement educationTextArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
	  
	  Assert.assertTrue(isControlDisplayed(emailTextbox));
	  Assert.assertTrue(isControlDisplayed(ageRadioButton));
	  Assert.assertTrue(isControlDisplayed(educationTextArea));
	  
	  if(isControlDisplayed(emailTextbox) && isControlDisplayed(educationTextArea)) {
		  emailTextbox.sendKeys("Automation Testing");
		  educationTextArea.sendKeys("Education Testing");
	  }

	  
  }
  
  @Test
  public void TC_02_IsDisplay() {
	  WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='mail']"));
	  WebElement ageRadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
	  WebElement passwordTextbox = driver.findElement(By.xpath("//input[@name='user_pass']"));
	  WebElement educationTextArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
	  isEnable(emailTextbox);
	  isEnable(ageRadioButton);
	  isEnable(passwordTextbox);
	  isEnable(educationTextArea);
	  
  }
  
  @Test
  public void TC_03_IsSelected() {
	  
	  WebElement ageRadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
	  WebElement developmentCheckbox = driver.findElement(By.xpath("//input[@id='development']"));
	  isControlSelected(ageRadioButton);
	  isControlSelected(developmentCheckbox);
  }
  
  public boolean isControlDisplayed(WebElement element) {
	  return element.isDisplayed();
	  
  }
  
  
  
  public void isEnable(WebElement element) {
	  if(element.isEnabled()) {
		  System.out.println("Element is enabled");
	  }else {
		  System.out.println("Element is disable");
	  }
  }
  
  public void isControlSelected(WebElement element) {
	  if(!element.isSelected()){
		  element.click();
	  }
  }
  
  public boolean isControlSelected(String locator) {
	  WebElement element = driver.findElement(By.xpath(locator));
	  return element.isSelected();
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
