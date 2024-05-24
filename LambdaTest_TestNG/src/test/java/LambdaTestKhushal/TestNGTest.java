package LambdaTestKhushal;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestNGTest {
	SoftAssert softAssert;
	public String username = "khushal_chandak";
	public String accesskey = "L9YDn8vr0IEmLUR0FxCXAZOnnBINU6HKhmgQSCzfDId5mBomeE";
	public static RemoteWebDriver driver = null;
	public String gridURL = "hub.lambdatest.com/wd/hub";
	WebDriverWait wait;
	@BeforeMethod
	@Parameters({"BrowserName","URL","Version","Platform"})
	public void setup(String browsername,String url,String version, String platform) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browsername);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform);
        capabilities.setCapability("build", "LambdaTestTestNG");
        capabilities.setCapability("name", "LambdaTestTestNG");
        capabilities.setCapability("network", "true");
        capabilities.setCapability("visual", "true");
        capabilities.setCapability("video", "true");
        capabilities.setCapability("console", "true");
	    try {
	        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + "@" + gridURL), capabilities);
	    } catch (MalformedURLException e) {
	        System.out.println("Invalid grid URL");
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	    wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		softAssert = new SoftAssert();
		driver.get(url);
	}
	
	@Test(timeOut=20000)
	public void testScenario_1() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver)
						.executeScript("return document.readyState")
						.equals("complete");
			}
		});
		softAssert.assertEquals(driver.getTitle(), "LambdaTest");
		softAssert.assertAll();
	}
	
	@Test(timeOut=20000)
	public void testScenario_2() {
		driver.findElement(By.linkText("Checkbox Demo")).click();
		WebElement checkbox = driver.findElement(By.id("isAgeSelected"));
		checkbox.click();
		softAssert.assertTrue(checkbox.isSelected());
		checkbox.click();
		softAssert.assertFalse(checkbox.isSelected());
		softAssert.assertAll();
	}
	
	@Test(timeOut=20000)
	public void testScenario_3() {
		driver.findElement(By.linkText("Javascript Alerts")).click();
		driver.findElement(By.xpath("//p[text()='JavaScript Alerts']//button[text()='Click Me']")).click();
		String alertText = driver.switchTo().alert().getText();
		softAssert.assertEquals(alertText, "I am an alert box!");
		driver.switchTo().alert().accept();
		softAssert.assertAll();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}