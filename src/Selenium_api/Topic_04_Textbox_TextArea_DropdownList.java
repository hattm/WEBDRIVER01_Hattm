package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_04_Textbox_TextArea_DropdownList {
	WebDriver driver;
	
	
	
	@BeforeClass
	  public void beforeClass() {
		  driver = new FirefoxDriver();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
	  }
	
  @Test
  public void TC_01_DropdowList() {
	  driver.get("https://automationfc.github.io/basic-form/index.html");
	  
	  Select jobRole = new Select(driver.findElement(By.xpath("//select[@id='job1']")));
	  
	  Assert.assertTrue(!jobRole.isMultiple());
	  
	  jobRole.selectByVisibleText("Automation Testing");
	  Assert.assertEquals(jobRole.getFirstSelectedOption(), "Automation Testing");
	  
	  jobRole.selectByValue("Manual Testing");
	  Assert.assertEquals(jobRole.getFirstSelectedOption(), "Manual Testing");
	  
	  jobRole.selectByIndex(3);
	  Assert.assertEquals(jobRole.getFirstSelectedOption(), "Mobile Testing");
	  
	  int jobItems = jobRole.getOptions().size();
	  Assert.assertEquals(jobItems, 5);
	  

  }
  
  @Test
  public void TC_02_Textbox_TextArea() {
	  driver.get("http://demo.guru99.com/v4/");
	  
	  

  }
  

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
