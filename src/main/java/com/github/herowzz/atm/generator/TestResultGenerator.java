package com.github.herowzz.atm.generator;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.model.Config;
import com.github.herowzz.atm.model.RunTest;

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

	/**
	 * 初始化
	 */
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

	/**
	 * 输出执行结果
	 */
	public void export(RunTest runTest) {
		try {
			logger.info("开始输出测试结果...");
			String outputPath = Config.OutputPath + "\\" + Config.Product + "-测试结果-" + runTest.getName() + ".html";
			Map<String, Object> root = new HashMap<>();
			root.put("rsList", runTest.getResultList());
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
