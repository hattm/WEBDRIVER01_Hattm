package Selenium_api;

import org.testng.annotations.Test;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_07_UserInteractions {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	@Test
	public void TC_01_HoverMouse() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		WebElement hoverText = driver.findElement(By.xpath("//a[text()='Hover over me']"));
		Actions action = new Actions(driver);

		// Hover Mouse
		action.moveToElement(hoverText).perform();
		Thread.sleep(5000);

		// Verify tooltip displayed
		Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Hover over me']")).getText(), "Hover over me");

	}

	public void TC_02_ClickAndHold() throws InterruptedException {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> selectable = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee']"));

		// Actions action = new Actions(driver);
		// action.clickAndHold(selectable.get(0)).moveToElement(selectable.get(3)).release().perform();
		// List<WebElement>selectableSelected =
		// driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee']"));
		// Assert.assertEquals(selectableSelected.size(),8);

		Actions action = new Actions(driver);

		// Gia lap nhan phim Ctrl xuong
		action.keyDown(Keys.CONTROL).build().perform();
		selectable.get(0).click();
		selectable.get(2).click();
		selectable.get(4).click();
		selectable.get(6).click();

		// Gia lap nha phim Ctrl ra
		action.keyUp(Keys.CONTROL).build().perform();

		Thread.sleep(5000);
		List<WebElement> selectableSelected = driver
				.findElements(By.xpath("//li[@class='ui-state-default ui-selectee']"));
		Assert.assertEquals(selectableSelected.size(), 8);
	}


	public void TC_04_RighClick() throws InterruptedException {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement righClickbutton = driver.findElement(By.xpath("//span[text()='right click me']"));
		Actions action = new Actions(driver);

		// Righ click
		action.contextClick(righClickbutton).perform();

		WebElement quitBefore = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));

		// Hover to quit
		action.moveToElement(quitBefore).perform();

		// Verify hover Success
		Assert.assertTrue(driver.findElement(By.xpath(
				"//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]"))
				.isDisplayed());

		// action Click
		action.click(quitBefore).perform();
		Thread.sleep(5000);
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "clicked: quit");
		alert.accept();

	}

	
	public void TC_05_DragAndDrop() throws InterruptedException {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");
		
		WebElement sourceElement = driver.findElement(By.xpath("//div[@id='droptarget']"));
		WebElement targetElement = driver.findElement(By.xpath("//div[@id='draggable']"));
		
		Assert.assertEquals(targetElement.getText(), "Drag the small circle here");
		
		Actions action = new Actions(driver);
		action.dragAndDrop(sourceElement, targetElement).release().perform();
		
		Thread.sleep(4000);
		Assert.assertEquals(targetElement.getText(), "You did great!");
		
		
	}
	

	public void TC_06_DragAndDrop_HTML() throws InterruptedException {
		driver.get("https://html5demos.com/drag/#");
		WebElement sourceElement = driver.findElement(By.xpath("//a[@id='one']"));
		WebElement targetElement = driver.findElement(By.xpath("//div[@id='bin']"));
		
		Actions action = new Actions(driver);
		action.click(sourceElement).moveToElement(targetElement).release(targetElement).build().perform();
		
		Thread.sleep(4000);
		
		Assert.assertFalse(sourceElement.isDisplayed());
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
