package com.github.herowzz.atm.util;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.model.CaseResult;
import com.github.herowzz.atm.model.Config;
import com.google.common.io.Files;

public abstract class DriverUtils {

	private static Logger log = LoggerFactory.getLogger(DriverUtils.class);

	public static void screenshot(WebDriver driver, CaseResult caseResult) {
		String outputPath = Config.OutputPath + "\\" + Config.Product + "_测试结果_" + Config.CaseCode;
		try {
			File saveFilePath = new File(outputPath);
			if (!saveFilePath.exists()) {
				saveFilePath.mkdirs();
			}
			String saveFileName = outputPath + "\\" + caseResult.getFullNameBar() + ".jpg";
			File saveFile = new File(saveFileName);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, saveFile);
			caseResult.setErrorPic(saveFileName);
		} catch (Exception e) {
			log.error("screenshot error!", e);
		}
	}

}
