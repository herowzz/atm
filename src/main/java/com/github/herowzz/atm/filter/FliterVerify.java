package com.github.herowzz.atm.filter;

import org.apache.commons.lang3.StringUtils;

import com.github.herowzz.atm.exception.TestException;

/**
 *  配置验证
 */
public class FliterVerify {
	
	/**
	  * 验证统一入口
	 */
	public static void verify(String include, String exclude) {
		startVerify(include);
		startVerify(exclude);
	}

	/**
	  * 解析配置并验证
	 * @param config
	 * @return
	 */
	private static void startVerify(String config) {
		if(StringUtils.isNotBlank(config)) {
			String[] includeArray = config.split(",");
			for (String item : includeArray) {
				int type = FilterResolver.checkType(item);
				if(type == 0) {//单个模块，模块下需指定Case
					bracketNumVerify(item);//左括号和右括号是否都只存在一个
					bracketLocationVerify(item);//左右括号位置是否正确
					bracketExternalNumVerify(item);//括号外是否存在数字以外的字符
					getChlidList(FilterResolver.getBracketContent(item));//验证子节点
				}else if(type == 1){//范围
					twoOrMoreLine(item);//是否只有一个“-”
					lineBothEndsNumber(item);//“-”两边是否都存在数字，并且不存在特殊字符
					fristNumLessThanSecondNum(item);//第一个是数字是否小于第二个
				}else if(type == 2){//非范围
					existNotNumber(item);//判断是否存在数字以外的字符
				}else if(type == 4){
					throw new TestException("配置文件错误：类型错误");
				}
			}
		}
	}
	
	/**
	   * 子节点解析
	 * @param str
	 * @return
	 */
	private static boolean getChlidList(String str){
		String[] array = str.split(",");
		for (String item : array) {
			if(FilterResolver.checkType(item) == 1) {//范围
				twoOrMoreLine(item);//是否只有一个“-”
				lineBothEndsNumber(item);//“-”两边是否都存在数字，并且不存在特殊字符
				fristNumLessThanSecondNum(item);//第一个是数字是否小于第二个
			}else if(FilterResolver.checkType(item) == 2) {//非范围
				existNotNumber(item);//判断是否存在数字以外的字符
			}
		}
		return true;
	}
	
	/**
	  *  是否只有一个“-”
	 * @param temp
	 * @return
	 */
	public static void twoOrMoreLine(String temp) {
		if(countString(temp, "-") != 1) {
			throw new TestException("配置文件错误：[" + temp + "]存在多个“-”");
		}
	}
	
	/**
	   * 是否存在数字以外的字符
	 * @param temp
	 * @return
	 */
	public static void existNotNumber(String temp) {
		try {
			Integer.valueOf(temp);
		} catch (Exception e) {
			throw new TestException("配置文件错误：[" + temp + "]存在数字以外的字符");
		}
	}
	
	/**
	 * “-”两边是否都存在数字，并且不存在特殊字符
	 * @param temp
	 * @return
	 */
	public static void lineBothEndsNumber(String temp) {
		try {
			Integer.valueOf(temp.split("-")[0]);
			Integer.valueOf(temp.split("-")[1]);
		} catch (Exception e) {
			throw new TestException("配置文件错误：[" + temp + "]“-”两边不存在数字，或者存在特殊字符");
		}
	}
	
	/**
	   * 第一个数字是否小于第二个数字
	 * @param temp
	 * @return
	 */
	public static void fristNumLessThanSecondNum (String temp) {
		int first = Integer.valueOf(temp.split("-")[0]);
		int second = Integer.valueOf(temp.split("-")[1]);
		if(first >= second) {
			throw new TestException("配置文件错误：[" + temp + "]左侧数字不小于右侧数字");
		}
	}
	
	/**
	 *    左括号和右括号是否都只存在一个
	 * @param temp
	 * @return
	 */
	public static void bracketNumVerify (String temp) {
		if(countString(temp, "(") != 1 || countString(temp, "(") != 1 ) {
			throw new TestException("配置文件错误：[" + temp + "]左括号或右括号存在多个");
		}
	}
	
	/**
	 * 位置验证，左括号是否在右括号的左边
	 * @param temp
	 * @return
	 */
	public static void bracketLocationVerify (String temp) {
		if(temp.indexOf("(") >= temp.indexOf(")")) {
			throw new TestException("配置文件错误：[" + temp + "]左括号在右括号右侧");
		}
	}
	
	/**
	  * 括号外是否只存在数字
	 * @param temp
	 * @return
	 */
	public static void bracketExternalNumVerify (String temp) {
		String num = temp.substring(0, temp.indexOf("("));
		try {
			Integer.valueOf(num);
		} catch (Exception e) {
			throw new TestException("配置文件错误：[" + temp + "]左括号或右括号存在多个");
		}
	}
	
	public static int countString(String str, String s) {
		int count = 0;
		while (str.indexOf(s) != -1) {
			str = str.substring(str.indexOf(s) + 1, str.length());
			count++;
		}
		return count;
	}


}
