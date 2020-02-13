package com.github.herowzz.atm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * @author wangzz
 */
public abstract class DateUtils {

	public static final String DATATIMEF_IN_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 生成当前时间的紧凑格式
	 */
	public static String getNowDateTimeStr() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATATIMEF_IN_FORMAT));
	}

}
