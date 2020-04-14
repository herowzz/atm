package com.github.herowzz.atm.driver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.herowzz.atm.model.Config;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

/**
 * windows application驱动
 * @author wangzz
 */
public class WinFormDriver implements ITestDriver<WindowsDriver<WindowsElement>> {

	private WindowsDriver<WindowsElement> driver;
	private DesiredCapabilities appCapabilities = new DesiredCapabilities();

	public WinFormDriver() {

	}

	@Override
	public void run(String runPath) throws Exception{
		appCapabilities.setCapability("app", runPath);
		appCapabilities.setCapability("deviceName", "Windows");
		try {
			driver = new WindowsDriver<WindowsElement>(new URL(Config.DriverUrl), appCapabilities);
		}catch (Exception e) {
			driver = new WindowsDriver<WindowsElement>(new URL(Config.DriverUrl), appCapabilities);
		}
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@Override
	public void close() {
		driver.quit();
	}

	@Override
	public WindowsDriver<WindowsElement> getDriver() {
		return driver;
	}

}
