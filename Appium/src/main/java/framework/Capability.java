package framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Capability {

	protected static String appPackage;
	protected static String appActivity;
	protected static String deviceName;
	protected static String platformName;
	protected static String chromeExecutable;
	public AppiumDriverLocalService service;

	public AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunning(4723);
		if (!flag) {
			// service = AppiumDriverLocalService.buildDefaultService();
			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					// This is the path of Nodesjs
					.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
					// This is the path of appium
					.withAppiumJS(new File(
							"C:\\Users\\TUNGATHAVAJANTungath\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
					.withIPAddress("127.0.0.1").usingPort(4723));
			service.start();
		}
		return service;
	}

	public static boolean checkIfServerIsRunning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\emulator.bat");
		Thread.sleep(8000);
	}

	public static AndroidDriver<AndroidElement> capablities(String appPackage, String appActivity, String deviceName,
			String platformName, String chromeExecutable) throws IOException, InterruptedException {

		FileReader fis = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\global.properties");
		Properties pro = new Properties();
		pro.load(fis);
		appPackage = pro.getProperty("appPackage");
		appActivity = pro.getProperty("appActivity");
		deviceName = pro.getProperty("deviceName");
		platformName = pro.getProperty("platformName");
		chromeExecutable = pro.getProperty("chromeExecutable");
		if (deviceName.contains("najav")) {
			startEmulator();
		}
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		// cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,
		// AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeExecutable);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		return driver;
	}
}
