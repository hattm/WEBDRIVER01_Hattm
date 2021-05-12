package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_Button_Radio_Checkbox_Alert {
	WebDriver driver;
	
	
	
	@BeforeClass
	  public void beforeClass() {
		  driver = new FirefoxDriver();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
	  }
	

  public void TC_01() {
	  driver.get("http://live.demoguru99.com/");
	  
	  // Build - in click Selenium
	  driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
	  Assert.assertTrue(driver.findElement(By.xpath("//form[@id='login-form']")).isDisplayed());
	  Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/login/");
	  
	  // Javascript click: element visible/ invisible/clickable 
	  clickElementByJavascript("//a[@title='Create an Account']");
	  Assert.assertTrue(driver.findElement(By.xpath("//form[@id='form-validate']")).isDisplayed());
	  Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/create/");
	  
	 

  }
  

  public void TC_02_dualZoneCheckbox()  {
	  driver.get("https://demos.telerik.com/kendo-ui/styling/checkboxes");
	  // Case 01- Lable: đang visible - nó có thể click được 
	  //WebElement dualZoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']"));
	  // dualZoneCheckbox.click();
	  // Khi Assert thì failed: vì mình không check isSelected cho 1 cái lable
	  //Assert.assertTrue(dualZoneCheckbox_.isSelected());
	
	  
	  // Case 02- Input: invisible - không thể click
	  // WebElement dualZoneCheckbox_ = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']//preceding-sibling::input"));
	  // duaZoneCheckbox_.click
	  //Assert.assertTrue(dualZoneCheckbox_.isSelected());
	  
	  //Case 03: Mix cả lable + input - phải khai báo cho 2 elements
	  
	  // Case- 04: Chỉ dùng 1 element (vừa click dc + vừa verify được)
	  String dualZoneCheckbox = "//label[text()='Dual-zone air conditioning']//preceding-sibling::input";
	  clickElementByJavascript(dualZoneCheckbox);
	  Assert.assertTrue(isElementSelected(dualZoneCheckbox));
	  
	  /* Sau khi checkbox đã được chọn - deselect nó và kiểm tra nó chưa được chọn */
	  unCheckTheCheckbox(dualZoneCheckbox);
	  Assert.assertFalse(isElementSelected(dualZoneCheckbox));
	  
	  

  }
  public void TC_03_radiocheckbox()  {
	  driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
	  String twoPetrol ="//label[text()='2.0 Petrol, 147kW']//preceding-sibling::input";
	  clickElementByJavascript(twoPetrol);
	  Assert.assertTrue(isElementSelected(twoPetrol));  
  }
  

  public void TC_04_alertAlert()  {
	  driver.get("https://automationfc.github.io/basic-form/");
	  //JS Alert
	  driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
	  Alert alert = driver.switchTo().alert();
	  String alertJSMessage = alert.getText();
	  Assert.assertTrue(alertJSMessage.equals("I am a JS Alert"));
	  alert.accept();
	  Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(),"You clicked an alert successfully");
	    
  }

  public void TC_05_alertConfirm()  {
	  driver.get("https://automationfc.github.io/basic-form/");
	  driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
	  Alert alert = driver.switchTo().alert();
	  String alertJSMessage = alert.getText();
	  Assert.assertTrue(alertJSMessage.equals("I am a JS Confirm"));
	  alert.dismiss();
	  Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(),"You clicked: Cancel");
  }
  @Test
  public void TC_06_alertSendky() {
	  driver.get("https://automationfc.github.io/basic-form/");
	  driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
	  Alert alert = driver.switchTo().alert();
	  String alertJSMessage = alert.getText();
	  Assert.assertTrue(alertJSMessage.equals("I am a JS prompt"));
	  alert.sendKeys("tranthimongha");
	  alert.accept();
	  Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(),"You entered: tranthimongha");
  }
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
  
  public void clickElementByJavascript(String locator) {
	  WebElement element = driver.findElement(By.xpath(locator));
	  JavascriptExecutor je = (JavascriptExecutor) driver;
	  je.executeScript("arguments[0].click();", element);
  }
  
  public boolean isElementSelected(String locator) {
	  WebElement element = driver.findElement(By.xpath(locator));
	  return element.isSelected();
  }
  
  public void unCheckTheCheckbox(String locator) {
	  if(isElementSelected(locator)) {
		  clickElementByJavascript(locator);
	  }
  }

}
