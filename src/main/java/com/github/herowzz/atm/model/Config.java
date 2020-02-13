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
	 * 项目访问路径
	 */
	public static String Url = "";

	/**
	 * 结果输出目录
	 */
	public static String OutputPath = "";

	public static String show() {
		return "Config [Product=" + Product + ", Version=" + Version + ", Url=" + Url + ", OutputPath=" + OutputPath + "]";
	}

}
