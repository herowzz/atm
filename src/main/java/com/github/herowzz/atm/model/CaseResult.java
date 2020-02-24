package com.github.herowzz.atm.model;

import com.github.herowzz.atm.event.IEventObject;

/**
 * 用例执行结果
 * @author wangzz
 */
public class CaseResult implements IEventObject {

	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 模块序号
	 */
	private int moduleOrder;

	/**
	 * 用例名称
	 */
	private String caseName;

	/**
	 * 用例序号
	 */
	private int caseOrder;

	/**
	 * 执行结果
	 */
	private boolean success;

	/**
	 * 错误信息
	 */
	private String errorInfo;

	/**
	 * 错误截图
	 */
	private String errorPic;

	/**
	 * 是否中断
	 */
	private boolean suspend = false;

	public CaseResult ok() {
		this.success = true;
		return this;
	}

	public CaseResult error(String errorInfo) {
		this.success = false;
		this.errorInfo = errorInfo;
		return this;
	}

	public CaseResult error(Exception exception) {
		this.success = false;
		this.errorInfo = exception.getMessage();
		this.suspend = true;
		return this;
	}

	public CaseResult error(Throwable throwable) {
		this.success = false;
		this.errorInfo = throwable.getMessage();
		this.suspend = true;
		return this;
	}

	public CaseResult copy(CaseResult caseResult) {
		this.success = caseResult.isSuccess();
		this.errorInfo = caseResult.getErrorInfo();
		this.suspend = caseResult.isSuspend();
		return this;
	}

	public String getFullName() {
		return this.moduleOrder + "." + this.caseOrder + "-" + this.caseName;
	}

	public String getFullNameBar() {
		return this.moduleOrder + "-" + this.caseOrder + "-" + this.caseName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public int getModuleOrder() {
		return moduleOrder;
	}

	public void setModuleOrder(int moduleOrder) {
		this.moduleOrder = moduleOrder;
	}

	public int getCaseOrder() {
		return caseOrder;
	}

	public void setCaseOrder(int caseOrder) {
		this.caseOrder = caseOrder;
	}

	public boolean isSuspend() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	public String getErrorPic() {
		return errorPic;
	}

	public void setErrorPic(String errorPic) {
		this.errorPic = errorPic;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
