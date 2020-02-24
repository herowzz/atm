package com.github.herowzz.atm.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.herowzz.atm.driver.ITestDriver;
import com.github.herowzz.atm.model.CaseResult;
import com.github.herowzz.atm.util.DriverUtils;
import com.google.common.eventbus.Subscribe;

/**
 * 执行case监听
 * @author wangzz
 */
public class RunCaseListener implements IDriverListener<CaseResult> {

	private static Logger log = LoggerFactory.getLogger(RunCaseListener.class);

	private ITestDriver<?> driver;

	public RunCaseListener(ITestDriver<?> driver) {
		this.driver = driver;
	}

	@Subscribe
	@Override
	public void listen(CaseResult caseResult) {
		if (caseResult != null) {
			if (caseResult.isSuccess()) {
				log.info(caseResult.getFullName() + " 测试结果: 成功!");
			} else {
				String error = caseResult.getFullName() + " 测试结果: 失败!";
				if (caseResult.isSuspend())
					error += " - 主流程中断!";
				error += "\n" + caseResult.getErrorInfo();
				log.warn(error);

				DriverUtils.screenshot(driver.getDriver(), caseResult);
			}
		}
	}

}
