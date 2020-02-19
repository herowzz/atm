package com.github.herowzz.atm.driver;

import com.github.herowzz.atm.model.DriverType;

/**
 * 驱动工厂
 * @author wangzz
 */
public class DriverFactory {

	/**
	 * 获取驱动
	 * @param driverType 驱动类型
	 * @return 驱动对象, 若不存在的类型则返回null
	 */
	public static ITestDriver<?> getDriver(DriverType driverType) {
		switch (driverType) {
		case Web:
			return new WebChromeDriver();
		case WinForm:
			return new WinFormDriver();
		default:
			return null;
		}
	}

}
