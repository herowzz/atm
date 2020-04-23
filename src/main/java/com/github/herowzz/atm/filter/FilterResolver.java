package com.github.herowzz.atm.filter;


/**
 *  配置解析
 */
public class FilterResolver {

	/**
	  *  获取括号左边数字
	 * @param temp 配置内容
	 * @return
	 */
	public static int getFristTypeOrder(String temp) {
		return Integer.valueOf(temp.substring(0, temp.indexOf("(")));
	}
	
	/**
	   * 获取括号内内容
	 * @param temp 配置内容
	 * @return
	 */
	public static String getBracketContent(String temp) {
		return temp.substring(temp.indexOf("(") + 1, temp.indexOf(")"));
	}
	
	/**
	  *  获取配置项类型
	 * @param item 配置项
	 * @return
	 */
	public static int checkType(String item) {
		if(item.indexOf("(") > 0 && item.indexOf(")") > 0 && item.indexOf("(") < item.indexOf(")")) {//单个模块，模块下需指定Case
			return 0;
		}
		if(item.indexOf("-") > 0 && item.indexOf("(") < 0 && item.indexOf(")") < 0) {//范围，并且不存在括号
			return 1;
		}
		if(item.indexOf("-") < 0 && item.indexOf("(") < 0 && item.indexOf(")") < 0) {//非范围，并且不存在括号
			return 2;
		}
		return 4;
	}
}
