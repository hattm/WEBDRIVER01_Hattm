package Selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_08_Iframe_Windows {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		// Implicit wait/ Global wait findElement/ findElements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public void TC_01() {

	}

	public void TC_02_WindowsGoogle() {
		driver.get("https://automationfc.github.io/basic-form/");
		/* Case 01 - 2 windows/ 2 tabs: switch via GUID */
		// Get GUID of current page (parent page)
		String parentGUID = driver.getWindowHandle();

		System.out.println("Title before = " + driver.getTitle());

		// Click to new windows
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();

		// Switch to New tab or new windows
		switchToChildWindowByGUID(parentGUID);

		// Verify switch success
		String googleTitle = driver.getTitle();
		System.out.println("Title after =  " + googleTitle);
		Assert.assertEquals(googleTitle, "Google");

		closeAllWiththoutParentWindows(parentGUID);

		// Verify switch to parnet success
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		/* Case 02- >= 2 windows/ 2 tabs: switch via page title */
	}

	public void TC_03_WindowsFACEBOOK() {
		driver.get("https://automationfc.github.io/basic-form/");

		String parentGUID = driver.getWindowHandle();

		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();

		switchToChildWindowByGUID(parentGUID);

		String facebookTitle = driver.getTitle();
		Assert.assertEquals(facebookTitle, "Facebook - Đăng nhập hoặc đăng ký");

		closeAllWiththoutParentWindows(parentGUID);

		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}
	@Test
	public void TC_04_WindowsTIKI() {
		driver.get("https://automationfc.github.io/basic-form/");

		String parentGUID = driver.getWindowHandle();
		System.out.println("Parent ID = " + parentGUID);
		System.out.println("Title before= " + driver.getTitle());

		driver.findElement(By.xpath("//a[text()='TIKI']")).click();

		switchToChildWindowByGUID(parentGUID);

		String tikititle = driver.getTitle();
		Assert.assertEquals(tikititle, "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		System.out.println("Title after =  " + tikititle);
		closeAllWiththoutParentWindows(parentGUID);
		

		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}

	
	public void TC_05_WindowsLazada() {
		driver.get("https://automationfc.github.io/basic-form/");

		String parentGUID = driver.getWindowHandle();

		driver.findElement(By.xpath("//a[text()='LAZADA']")).click();

		switchToChildWindowByGUID(parentGUID);
		String lazadaTitle = driver.getTitle();
		Assert.assertEquals(lazadaTitle, "Shopping online - Buy online on Lazada.vn");
		closeAllWiththoutParentWindows(parentGUID);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}

	
	public void TC_06_HdfcWindows() {
		driver.get("https://www.hdfcbank.com/");
		String parentGUID = driver.getWindowHandle();
		driver.findElement(
				By.xpath("//ul[@class='nav navbar-nav lp-navbar-advanced-root normal-main-header' ]//a[text()='Agri']"))
				.click();
		switchToWindowByTitle("Agri");

	}

	@AfterClass
	public void afterClass() {
		 driver.quit();
	}

	public void switchToChildWindowByGUID(String parentID) {
		// Get all current windows/ tabs
		Set<String> allWindows = driver.getWindowHandles();

		// Duyệt qua tất cả các windows/ tabs
		for (String runwindow : allWindows) {
			// Nếu window/ tab ID nào mà khác với parentID thì switch qua
			if (!runwindow.equals(parentID)) {
				driver.switchTo().window(runwindow);

				// driver.switchTo().alert();
				break;
			}
		}
	}

	public void switchToWindowByTitle(String expectedTittle) {
		// Get all current windows/ tabs
		Set<String> allWindows = driver.getWindowHandles();

		// Lặp trừng window/ tab
		for (String runWindows : allWindows) {
			System.out.println("Window ID = " + runWindows);
			// Switch vào từng windows trước
			driver.switchTo().window(runWindows);

			// Get ra title của window/tab mà mình đã switch qua
			String actualTitle = driver.getTitle();

			// Kiểm tra nếu title đã ghet bằng với expected title mình truyền vào
			if (actualTitle.equals(expectedTittle)) {
				// thoát khoi vòng lặp
				break;
			}
		}

	}

	// Đóng tất cả các windows/ tabs ngoại trừ parent window
	public boolean closeAllWiththoutParentWindows(String parentGUID) {
		// Get all current windows/ tabs
		Set<String> allWindows = driver.getWindowHandles();

		// Duyệt qu từng window/tab
		for (String runWindows : allWindows) {
			System.out.println("Window ID = " + runWindows);
			// Nếu windows/ tab guid khác vs parent id
			if (!runWindows.equals(parentGUID)) {
				// Switch qua window/ tab đó
				driver.switchTo().window(runWindows);

				// Close window/ tab đó
				driver.close();
			}
		}

		// Switch về parent windows
		driver.switchTo().window(parentGUID);

		// Kiểm tra xem có đúng 1 window hay kg
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
}
