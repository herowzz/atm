package com.github.herowzz.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.annotation.Configuration;
import com.github.herowzz.atm.model.DriverType;

public class TestApplication {

	private static Logger log = LoggerFactory.getLogger(TestEngine.class);

	public static void run(Class<?> classz) throws Exception {
		Configuration configuration = classz.getAnnotation(Configuration.class);
		String packages = configuration.packages();
		DriverType driverType = configuration.driverType();
		log.info("扫描用例包: " + packages + ",  驱动: " + driverType);
		TestEngine TestEngine = new TestEngine(packages, driverType);
		TestEngine.start();
	}

}
