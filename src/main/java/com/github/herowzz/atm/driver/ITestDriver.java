package com.github.herowzz.atm.driver;

import org.openqa.selenium.WebDriver;

/**
 * 测试驱动
 * @author wangzz
 */
public interface ITestDriver<T extends WebDriver> {

	/**
	 * 启动
	 * @param runPath 启动路径
	 */
	void run(String runPath) throws Exception;

	/**
	 * 关闭
	 */
	void close();

	/**
	 * 获取驱动
	 * @return WebDriver及其子类驱动
	 */
	T getDriver();
}
