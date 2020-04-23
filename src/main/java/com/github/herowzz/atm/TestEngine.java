package com.github.herowzz.atm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.github.herowzz.atm.annotation.DriverInject;
import com.github.herowzz.atm.annotation.ModuleBefore;
import com.github.herowzz.atm.annotation.ModuleEnd;
import com.github.herowzz.atm.annotation.TestModule;
import com.github.herowzz.atm.annotation.UseCase;
import com.github.herowzz.atm.driver.DriverFactory;
import com.github.herowzz.atm.driver.ITestDriver;
import com.github.herowzz.atm.event.DriverEvent;
import com.github.herowzz.atm.event.RunCaseListener;
import com.github.herowzz.atm.filter.FilterCase;
import com.github.herowzz.atm.generator.PropertiesBuilder;
import com.github.herowzz.atm.generator.TestResultGenerator;
import com.github.herowzz.atm.model.Config;
import com.github.herowzz.atm.model.DriverType;
import com.github.herowzz.atm.model.RunModule;
import com.github.herowzz.atm.model.RunTest;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

/**
 * 测试引擎
 * @author wangzz
 */
public class TestEngine {

	private String modulePackage;
	private RunTest runTest;

	private TestResultGenerator testResultGenerator = new TestResultGenerator();
	private ITestDriver<?> driver;

	/**
	 * 构造函数
	 * @param modulePackage 用例所属包
	 * @param driverType    驱动类型
	 */
	public TestEngine(String modulePackage, DriverType driverType) throws Exception {
		this.modulePackage = modulePackage;
		this.driver = DriverFactory.getDriver(driverType);
		PropertiesBuilder.buildConfig();
		List<RunModule> moduleList = loadModule();
		List<RunModule> runModuleList = FilterCase.filter(moduleList);
		this.runTest = new RunTest(runModuleList);
		DriverEvent.register(new RunCaseListener(driver));
	}

	/**
	 * 启动引擎<br>
	 * 通过加载的用例包,执行用例
	 * @throws Exception
	 */
	public void start() throws Exception {
		driver.run(Config.RunPath);

		runCase();

		buildResult();

		driver.close();
	}

	/**
	 * 装载模块
	 * @throws Exception
	 */
	private List<RunModule> loadModule() throws Exception {
		List<RunModule> moduleList = new LinkedList<>();
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
			moduleClass.getAnnotation(TestModule.class).order();
			module.sortRunMethod();
			moduleList.add(module);
		}
		return moduleList;
	}

	/**
	 * 执行用例
	 */
	public void runCase() {
		this.runTest.runCase();
	}

	/**
	 * 渲染结果页面
	 * @param resultList 传入结果List
	 */
	private void buildResult() {
		testResultGenerator.export(runTest);
	}

	/**
	 * 获取本次测试执行
	 */
	public RunTest getRunTest() {
		return runTest;
	}
	
}
