package com.github.herowzz.atm.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.github.herowzz.atm.annotation.UseCase;
import com.google.common.collect.Range;

/**
 * Include封装
 */
public class IncludeModel {

	/**
	 * exclude=30100(1-2),30100-30110 include=30100(30101-30104, 20102,
	 * 20010),110100-110200,100210
	 */

	/**
	 * 序号
	 */
	public int order;

	/**
	 * 范围
	 */
	public Range<Integer> moduleRange;

	/**
	 * 0 非范围，1 范围
	 */
	public int type;

	/**
	 * Include子节点
	 */
	public List<IncludeModel> chlidList = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.println(Range.closed(1, 3).contains(2));
	}

	public RunModule includeFilter(RunModule module) {
		RunModule newModule = module;//克隆
		if (type == 0) {
			if (module.getOrder() != order) 
				return null;
			if (!chlidList.isEmpty()) {
				List<Method> runMethodList = new ArrayList<Method>();
				for (Method method : newModule.getRunMethodList()) {
					int methodOrder = method.getAnnotation(UseCase.class).order();
					for (IncludeModel childModel : chlidList) {
						if (childModel.contains(methodOrder)) {
							runMethodList.add(method);
							break;
						}
					}
				}
				newModule.setRunMethodList(runMethodList);
			}
			return newModule;
		} else {
			if(moduleRange.contains(module.getOrder())) 
				return newModule;
		}
		return null;
	}

	public RunModule excludeFilter(RunModule module) {
		RunModule newModule = module;
		if (type == 0) {
			if (module.getOrder() == order) {
				if (!chlidList.isEmpty()) {
					//存在chlidList，进行case过滤
					List<Method> runMethodList = new ArrayList<Method>();
					for (Method method : newModule.getRunMethodList()) {
						int methodOrder = method.getAnnotation(UseCase.class).order();
						for (IncludeModel childModel : chlidList) {
							if (!childModel.contains(methodOrder)) {
								//复制 childModel
								runMethodList.add(method);
							}
						}
					}
					newModule.setRunMethodList(runMethodList);
					return newModule;
				}
				return null;
			}
		} else {
			if(moduleRange.contains(module.getOrder())) {
				return null;
			}
		}
		return module;
	}
	
	public boolean contains(int order) {
		if (type == 1) {
			return this.moduleRange.contains(order);
		} else {
			return this.order == order;
		}
	}

}
