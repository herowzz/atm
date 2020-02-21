package com.github.herowzz.atm.model;

public abstract class Config {

	/**
	 * 产品名称
	 */
	public static String Product = "";

	/**
	 * 项目版本号
	 */
	public static String Version = "";

	/**
	 * 项目启动路径
	 */
	public static String RunPath = "";

	/**
	 * 驱动路径
	 */
	public static String DriverUrl = "";

	/**
	 * 结果输出目录
	 */
	public static String OutputPath = "";

	/**
	 * 执行测试时生成的编号
	 */
	public static String CaseCode = "";

	public static String show() {
		return "Config [Product=" + Product + ", Version=" + Version + ", RunPath=" + RunPath + ", OutputPath=" + OutputPath + ", DriverUrl=" + DriverUrl + "]";
	}

}
