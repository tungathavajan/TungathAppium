package framework;

import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.ElementOption;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

import java.io.IOException;

public class Demo extends Capability {

	AndroidDriver<AndroidElement> driver;

	@BeforeTest
	public void beforeMethod() throws IOException, InterruptedException {
		/*
		 * driver = capablities(appPackage, appActivity, deviceName, platformName,
		 * chromeExecutable); driver.manage().timeouts().implicitlyWait(30,
		 * TimeUnit.SECONDS);
		 */
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
	}

	@Test(enabled = false)
	public void Testcase1() {
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		WebElement name = driver.findElementByClassName("android.widget.EditText");
		name.sendKeys("Tulasi");
		String enteredName = name.getAttribute("text");
		System.out.println(enteredName);
		driver.findElementByClassName("android.widget.Spinner").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Austria\"))")
				.click();
		// driver.findElementByClassName("android.widget.Button").click();
	}

	@Test(enabled = false)
	public void Testcase2() {
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		// WebElement name = driver.findElementByClassName("android.widget.EditText");
		// name.sendKeys("Tulasi");
		driver.findElementByClassName("android.widget.Spinner").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"India\"))")
				.click();
		driver.findElementByClassName("android.widget.Button").click();
		String ermsg = driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		String exp = "Please enter your name";
		Assert.assertEquals(ermsg, exp);
	}

	@Test(enabled = false)
	public void Testcase3() throws InterruptedException {
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		WebElement name = driver.findElementByClassName("android.widget.EditText");
		name.sendKeys("Tulasi");
		driver.findElementByClassName("android.widget.Spinner").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Austria\"))")
				.click();
		driver.findElementByClassName("android.widget.Button").click();
//		driver.findElementByAndroidUIAutomator(
//				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Air Jordan 9 Retro\"))");
//		int Length = driver.findElementsById("com.androidsample.generalstore:id/productName").size();
//		System.out.println(Length);
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Air Jordan 9 Retro\").instance(0))");
		int Length = driver.findElementsById("com.androidsample.generalstore:id/productName").size();
		for (int i = 0; i < Length; i++) {
			String productName = driver.findElementsById("com.androidsample.generalstore:id/productName").get(i)
					.getText();
			if (productName.equalsIgnoreCase("Air Jordan 9 Retro")) {
				driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(i).click();
				break;
			}
		}
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(3000);
		String ActualPN = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
		String expectedPN = "Air Jordan 9 Retro";
		Assert.assertEquals(expectedPN, ActualPN);
	}

	@Test(enabled = true)
	public void Testcase4() throws InterruptedException, IOException {
		service = startServer();
		driver = capablities(appPackage, appActivity, deviceName, platformName, chromeExecutable);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@text='Female']")).click();
		WebElement name = driver.findElementByClassName("android.widget.EditText");
		name.sendKeys("Tulasi");
		driver.findElementByClassName("android.widget.Spinner").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Austria\"))")
				.click();
		driver.findElementByClassName("android.widget.Button").click();
		// to add the first product
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		// to add the second product (again index is zero because first one becomes
		// added to cart)
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(3000);
		String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText();
		amount1 = amount1.substring(1);
		double amount1value = Double.parseDouble(amount1);
		String amount2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText();
		amount2 = amount2.substring(1);
		double amount2value = Double.parseDouble(amount2);
		double totalvalue = amount1value + amount2value;
		String TotalAmount = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
		TotalAmount = TotalAmount.substring(1);
		double TotalAmountValue = Double.parseDouble(TotalAmount);
		Assert.assertEquals(TotalAmountValue, totalvalue);
		WebElement tapCheckbox = driver.findElement(By.className("android.widget.CheckBox"));
		TouchAction touchAction = new TouchAction(driver);
		touchAction.tap(tapOptions().withElement(element(tapCheckbox))).perform();
		WebElement termsCondition = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
		touchAction.longPress(longPressOptions().withElement(element(termsCondition)).withDuration(ofSeconds(3)))
				.release().perform();
		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(10000);
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys("IBM");
		driver.findElement(By.xpath("//*[@name='q']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context("NATIVE_APP");

		service.stop();
	}

}
