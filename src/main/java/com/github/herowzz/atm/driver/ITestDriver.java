package com.github.herowzz.atm.driver;

import org.openqa.selenium.WebDriver;

/**
 * 测试驱动
 * @author wangzz
 */
public interface ITestDriver {

	/**
	 * 启动
	 * @param url 地址
	 */
	void run(String url);

	/**
	 * 关闭
	 */
	void close();

	/**
	 * 获取驱动
	 * @return WebDriver及其子类驱动
	 */
	WebDriver getDriver();
}
