package com.github.herowzz.atm.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.annotation.TestModule;
import com.github.herowzz.atm.annotation.UseCase;
import com.github.herowzz.atm.exception.TestException;

/**
 * 执行模块
 * @author wangzz
 */
public class RunModule {

	private static Logger log = LoggerFactory.getLogger(RunModule.class);

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 顺序号
	 */
	private int order;

	/**
	 * 执行对象
	 */
	private Object instantObj;

	/**
	 * 前置方法
	 */
	private Method beforeMethod;

	/**
	 * 执行用例方法列表
	 */
	private List<Method> runMethodList = new LinkedList<>();

	/**
	 * 后置方法
	 */
	private Method endMethod;

	/**
	 * 是否中断
	 */
	private boolean suspend = false;

	public RunModule() {
	}

	public RunModule(Object instantObj) throws Exception {
		TestModule testModule = instantObj.getClass().getAnnotation(TestModule.class);
		this.name = testModule.name();
		this.order = testModule.order();
		this.instantObj = instantObj;
	}

	public void addRunMethod(Method method) {
		this.runMethodList.add(method);
	}

	/**
	 * 对执行用例进行排序
	 */
	public void sortRunMethod() {
		runMethodList.sort(Comparator.comparing(c -> c.getAnnotation(UseCase.class).order()));
	}

	/**
	 * 执行该模块中的用例
	 * @throws Exception
	 */
	public List<CaseResult> executeMethods() throws Exception {
		this.executeBeforeMethod();
		List<CaseResult> resultList = this.executeRunMethodList();
		if (!this.isSuspend())
			this.executeEndMethod();
		return resultList;
	}

	/**
	 * 执行前置方法
	 * @throws Exception
	 */
	public void executeBeforeMethod() throws Exception {
		if (this.beforeMethod != null) {
			this.beforeMethod.invoke(instantObj, new Object[] {});
		}
	}

	/**
	 * 执行用例方法列表
	 * @throws Exception
	 */
	public List<CaseResult> executeRunMethodList() throws Exception {
		List<CaseResult> resultList = new ArrayList<>();
		for (Method runMethod : runMethodList) {
			CaseResult caseResult = new CaseResult();
			UseCase useCase = runMethod.getAnnotation(UseCase.class);
			caseResult.setModuleName(this.name);
			caseResult.setModuleOrder(this.order);
			caseResult.setCaseName(useCase.name());
			caseResult.setCaseOrder(useCase.order());
			try {
				log.info(caseResult.getFullName() + " 开始测试.");
				Object invokeRes = runMethod.invoke(instantObj, new Object[] {});
				if (invokeRes != null) {
					caseResult = caseResult.copy((CaseResult) invokeRes);
					if (caseResult.isResult() == true) {
						log.info(caseResult.getFullName() + " 测试结果: 成功!");
					} else {
						log.warn(caseResult.getFullName() + " 测试结果: 失败!\n" + caseResult.getErrorInfo());
					}
				}
			} catch (InvocationTargetException e) {
				Throwable targetException = e.getTargetException();
				caseResult = caseResult.error(targetException);
				log.error(caseResult.getFullName() + " 测试结果失败, 主流程中断!\n" + caseResult.getErrorInfo());
			} catch (TestException e) {
				caseResult = caseResult.error(e);
				log.error(caseResult.getFullName() + " 测试结果失败, 主流程中断!\n" + caseResult.getErrorInfo());
			} catch (Exception e) {
				throw e;
			}
			resultList.add(caseResult);
			if (caseResult.isSuspend()) {
				this.suspend = caseResult.isSuspend();
				break;
			}
		}
		return resultList;
	}

	/**
	 * 执行后置方法
	 * @throws Exception
	 */
	public void executeEndMethod() throws Exception {
		if (this.endMethod != null) {
			this.endMethod.invoke(instantObj, new Object[] {});
		}
	}

	public Method getBeforeMethod() {
		return beforeMethod;
	}

	public void setBeforeMethod(Method beforeMethod) {
		this.beforeMethod = beforeMethod;
	}

	public Method getEndMethod() {
		return endMethod;
	}

	public void setEndMethod(Method endMethod) {
		this.endMethod = endMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return order + "." + name;
	}

	public List<Method> getRunMethodList() {
		return runMethodList;
	}

	public void setRunMethodList(List<Method> runMethodList) {
		this.runMethodList = runMethodList;
	}

	public boolean isSuspend() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

}
