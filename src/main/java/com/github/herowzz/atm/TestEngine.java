package com.github.herowzz.atm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.annotation.DriverInject;
import com.github.herowzz.atm.annotation.ModuleBefore;
import com.github.herowzz.atm.annotation.ModuleEnd;
import com.github.herowzz.atm.annotation.TestModule;
import com.github.herowzz.atm.annotation.UseCase;
import com.github.herowzz.atm.driver.DriverFactory;
import com.github.herowzz.atm.driver.ITestDriver;
import com.github.herowzz.atm.generator.PropertiesBuilder;
import com.github.herowzz.atm.generator.TestResultGenerator;
import com.github.herowzz.atm.model.CaseResult;
import com.github.herowzz.atm.model.Config;
import com.github.herowzz.atm.model.DriverType;
import com.github.herowzz.atm.model.RunModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

/**
 * 测试引擎
 * @author wangzz
 */
public class TestEngine {

	private static Logger log = LoggerFactory.getLogger(TestEngine.class);

	private String modulePackage;
	private List<RunModule> moduleList = new LinkedList<>();

	private TestResultGenerator testResultGenerator = new TestResultGenerator();
	private ITestDriver driver;

	public TestEngine(String modulePackage, DriverType driverType) {
		this.modulePackage = modulePackage;
		this.driver = DriverFactory.getDriver(driverType);
		PropertiesBuilder.buildConfig();
	}

	public void start() throws Exception {
		driver.run(Config.Url);

		loadModule();

		List<CaseResult> resultList = runCase();
		buildView(resultList);

		driver.close();
	}

	/**
	 * 装载模块
	 * @throws Exception
	 */
	private List<Class<?>> loadModule() throws Exception {
		List<Class<?>> moduleClassList = new LinkedList<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ImmutableSet<ClassPath.ClassInfo> topLevelClasses = ClassPath.from(classLoader).getTopLevelClassesRecursive(modulePackage);
		for (ClassPath.ClassInfo classInfo : topLevelClasses) {
			Class<?> entityClass = classInfo.load();
			if (entityClass.getAnnotation(TestModule.class) != null) {
				moduleClassList.add(entityClass);
			}
		}

		moduleClassList.sort(Comparator.comparing(c -> c.getAnnotation(TestModule.class).order()));
		for (Class<?> moduleClass : moduleClassList) {
			Object instantObj = moduleClass.newInstance();
			RunModule module = new RunModule(instantObj);
			Field[] fields = moduleClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(DriverInject.class) != null) {
					field.setAccessible(true);
					field.set(instantObj, driver.getDriver());
				}
			}
			Method[] methodList = moduleClass.getMethods();
			for (Method method : methodList) {
				if (method.getAnnotation(ModuleBefore.class) != null) {
					module.setBeforeMethod(method);
				}
				if (method.getAnnotation(ModuleEnd.class) != null) {
					module.setEndMethod(method);
				}
				if (method.getAnnotation(UseCase.class) != null) {
					module.addRunMethod(method);
				}
			}
			module.sortRunMethod();
			moduleList.add(module);
		}
		return moduleClassList;
	}

	/**
	 * 执行用例
	 * @return 执行结果列表
	 */
	private List<CaseResult> runCase() {
		List<CaseResult> resultList = new LinkedList<>();
		try {
			for (RunModule module : moduleList) {
				log.info("开始执行模块:" + module);
				List<CaseResult> caseList = module.executeMethods();
				resultList.addAll(caseList);
				if (module.isSuspend())
					break;
			}
		} catch (Exception e) {
			log.error("execute case Methods error!", e);
		}
		return resultList;
	}

	/**
	 * 渲染结果页面
	 * @param resultList 传入结果List
	 */
	private void buildView(List<CaseResult> resultList) {
		testResultGenerator.export(Config.OutputPath, resultList);
	}

}
