package com.github.herowzz.atm.driver;

import com.github.herowzz.atm.model.DriverType;

public class DriverFactory {

	public static ITestDriver getDriver(DriverType driverType) {
		switch (driverType) {
		case Web:
			return new WebChromeDriver();
		default:
			return null;
		}
	}

}
