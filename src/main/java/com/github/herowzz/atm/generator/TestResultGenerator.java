package com.github.herowzz.atm.generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.model.CaseResult;
import com.github.herowzz.atm.model.Config;
import com.github.herowzz.atm.util.DateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 测试结果生成引擎
 * @author wangzz
 */
public class TestResultGenerator {

	private static Logger logger = LoggerFactory.getLogger(TestResultGenerator.class);

	private Configuration cfg = new Configuration(Configuration.getVersion());
	private static Template tempHtml;

	public TestResultGenerator() {
		init();
	}

	private void init() {
		try {
			cfg.setClassForTemplateLoading(this.getClass(), "/template/");
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			tempHtml = cfg.getTemplate("html.ftl");
			logger.debug("加载模板完毕");
		} catch (Exception e) {
			logger.error("TestResultGenerator初始化模板异常!", e);
			System.exit(1);
		}
	}

	public void export(String outputPath, List<CaseResult> resultList) {
		try {
			logger.info("开始输出测试结果...");
			outputPath = outputPath + "\\" + Config.Product + "-测试结果-" + DateUtils.getNowDateTimeStr() + ".html";
			Map<String, Object> root = new HashMap<>();
			root.put("rsList", resultList);
			try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"))) {
				tempHtml.process(root, out);
				out.flush();
			}
			logger.info("输出测试结果完毕,输出文件:" + outputPath);
		} catch (Exception e) {
			logger.error("TestResultGenerator导出结果发生未知异常!", e);
		}
	}

}
