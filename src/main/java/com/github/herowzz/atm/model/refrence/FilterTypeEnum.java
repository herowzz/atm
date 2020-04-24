package com.github.herowzz.atm.model.refrence;

/**
 * 过滤类型
 */
public enum FilterTypeEnum {

	Scope("范围"),
	
	NotScope("非范围"),

	NotScopeChild("非范围包含子节点");

	private String title;

	private FilterTypeEnum(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public String toString() {
		return this.getTitle();
	}

	public static FilterTypeEnum get(int index) {
		return FilterTypeEnum.values()[index];
	}

	public String getName() {
		return this.name();
	}

	public int getValue() {
		return this.ordinal();
	}

}
