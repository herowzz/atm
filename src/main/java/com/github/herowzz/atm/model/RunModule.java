package com.github.herowzz.atm.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.github.herowzz.atm.annotation.TestModule;
import com.github.herowzz.atm.annotation.UseCase;
import com.github.herowzz.atm.exception.TestException;

/**
 * 执行模块
 * @author wangzz
 */
public class RunModule {

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
	private Class<?> classz;

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

	public RunModule(Class<?> classz) {
		TestModule testModule = classz.getAnnotation(TestModule.class);
		this.name = testModule.name();
		this.order = testModule.order();
		this.classz = classz;
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
			this.beforeMethod.invoke(this.classz.newInstance(), new Object[] {});
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
			try {
				Object invokeRes = runMethod.invoke(this.classz.newInstance(), new Object[] {});
				if (invokeRes != null)
					caseResult = (CaseResult) invokeRes;
			} catch (InvocationTargetException e) {
				Throwable targetException = e.getTargetException();
				caseResult = caseResult.error(targetException);
			} catch (TestException e) {
				caseResult = caseResult.error(e);
			} catch (Exception e) {
				throw e;
			}
			caseResult.setModuleName(this.name);
			caseResult.setModuleOrder(this.order);
			UseCase useCase = runMethod.getAnnotation(UseCase.class);
			caseResult.setCaseName(useCase.name());
			caseResult.setCaseOrder(useCase.order());
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
			this.endMethod.invoke(this.classz.newInstance(), new Object[] {});
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

	public Class<?> getClassz() {
		return classz;
	}

	public void setClassz(Class<?> classz) {
		this.classz = classz;
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
