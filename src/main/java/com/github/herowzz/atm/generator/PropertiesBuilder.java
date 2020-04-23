package com.github.herowzz.atm.generator;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.model.Config;

/**
 * Properties构造器
 * @author wangzz
 */
public class PropertiesBuilder {

	private static Logger logger = LoggerFactory.getLogger(PropertiesBuilder.class);

	/**
	 * 加载config.properties配置文件
	 */
	public static void buildConfig() {
		Properties properties = new Properties();
		InputStream inputStream = PropertiesBuilder.class.getResourceAsStream("/config.properties");
		try {
			properties.load(inputStream);
			Config.Product = properties.get("product").toString();
			Config.RunPath = properties.get("runPath").toString();
			Config.DriverUrl = properties.get("driverUrl").toString();
			Config.Version = properties.get("version").toString();
			Config.OutputPath = properties.get("outputPath").toString();
			Config.Exclude = properties.get("exclude") != null ? properties.get("exclude").toString() : "";
			Config.Include = properties.get("include") != null ? properties.get("include").toString() : "";
			logger.error("加载配置:" + Config.show());
		} catch (Exception e) {
			logger.error("加载config.properties异常!", e);
			System.exit(1);
		}
	}

}
