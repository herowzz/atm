package com.github.herowzz.atm.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Chrome驱动
 * @author wangzz
 */
public class WebChromeDriver implements ITestDriver<AtmWebDriver> {

	private AtmWebDriver driver;

	public WebChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		driver = new AtmWebDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);// 设置页面加载超时
		driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
	}

	@Override
	public void run(String runPath) {
		driver.get(runPath);
	}

	@Override
	public void close() {
		if (driver != null)
			driver.quit();
	}

	@Override
	public AtmWebDriver getDriver() {
		return driver;
	}

}
