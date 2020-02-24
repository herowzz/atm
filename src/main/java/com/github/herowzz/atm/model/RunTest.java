package com.github.herowzz.atm.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.util.DateUtils;

/**
 * 执行测试对象
 * @author wangzz
 */
public class RunTest {

	private static Logger log = LoggerFactory.getLogger(RunTest.class);

	/**
	 * 执行名称
	 */
	private String name;

	/**
	 * 测试开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 测试结束时间
	 */
	private LocalDateTime endTime;

	/**
	 * 执行模块
	 */
	private List<RunModule> moduleList = new LinkedList<>();

	/**
	 * 执行结果
	 */
	private List<CaseResult> resultList = new LinkedList<>();

	/**
	 * 初始化测试对象
	 * @param moduleList 模块列表
	 */
	public RunTest(List<RunModule> moduleList) {
		this.moduleList = moduleList;
		this.name = DateUtils.getNowDateTimeStr();
		Config.CaseCode = this.name;
	}

	/**
	 * 执行用例
	 */
	public RunTest runCase() {
		this.startTime = LocalDateTime.now();// 启动
		try {
			for (RunModule module : this.moduleList) {
				log.info("开始执行模块:" + module);
				List<CaseResult> caseList = module.executeMethods();
				resultList.addAll(caseList);
				if (module.isSuspend())
					break;
			}
		} catch (Exception e) {
			log.error("execute case Methods error!", e);
		}
		this.endTime = LocalDateTime.now();// 结束
		return this;
	}

	/**
	 * 获取执行时间
	 * @return 执行时间(单位: 秒)
	 */
	public long getRunTimes() {
		Duration duration = Duration.between(startTime, endTime);
		return duration.toMillis() / 1000;
	}

	/**
	 * 获取所有用例数量
	 * @return 所有用例数量
	 */
	public long getAllCaseCount() {
		int count = 0;
		for (RunModule runModule : moduleList) {
			count += runModule.getRunMethodList().size();
		}
		return count;
	}

	/**
	 * 获取已执行用例数量
	 * @return 已执行用例数量
	 */
	public long getExecutedCaseCount() {
		return resultList.size();
	}

	/**
	 * 获取已执行的成功用例数量
	 * @return 已执行的成功用例数量
	 */
	public long getExecutedCaseSuccessCount() {
		return resultList.stream().filter(r -> r.isSuccess()).count();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public List<RunModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<RunModule> moduleList) {
		this.moduleList = moduleList;
	}

	public List<CaseResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<CaseResult> resultList) {
		this.resultList = resultList;
	}

}
