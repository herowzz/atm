package com.github.herowzz.atm.filter;

import java.util.ArrayList;
import java.util.List;


import com.github.herowzz.atm.model.Config;
import com.github.herowzz.atm.model.IncludeModel;
import com.github.herowzz.atm.model.RunModule;
import com.github.herowzz.atm.model.refrence.FilterTypeEnum;
import com.google.common.collect.Range;

/**
 *  过滤器
 */
public class FilterCase {
	

	public static List<RunModule> filter(List<RunModule> moduleList) throws Exception {
		List<RunModule> runModules = new ArrayList<RunModule>();
		moduleList.forEach(item -> {
			runModules.add(item);
		});
		//验证Config配置
		FliterVerify.verify(Config.Include, Config.Exclude);
		//过滤操作
		//Include
		List<RunModule> includeModules = new ArrayList<RunModule>();
		List<IncludeModel> includeList = getIncludeList();
		//include
		if(includeList.size() > 0) {
			for (RunModule model : runModules) {
				for (IncludeModel includeModel : includeList) {
					RunModule m = includeModel.includeFilter(model);
					if(m != null) {
						includeModules.add(m);
						break;
					}
				}
			}
		}
		
		if(includeModules.isEmpty()) {
			includeModules = runModules;
		}
		
		//Exclude
		List<RunModule> excludeModules = new ArrayList<RunModule>();
		List<IncludeModel> excludeList = getExcludeList();
		if(excludeList.size() > 0) {
			for (RunModule model : includeModules) {
				boolean isExist = true;
				for (IncludeModel includeModel : excludeList) {
					RunModule m = includeModel.excludeFilter(model);
					if(m == null) {
						isExist = false;
					}
				}
				if(isExist) {
					excludeModules.add(model);
				}
			}
			return excludeModules;
		}
		return includeModules;
	}
	
	private static List<IncludeModel> getExcludeList() {
		/**
		 * exclude=30100(1-2),30100-30110 include=30100(30101-30104),110100-110200
		 */
		String exclude = Config.Exclude;
		List<IncludeModel> excludeModels = new ArrayList<IncludeModel>();
		if(exclude.length() == 0) {
			return excludeModels;
		}
		String[] excludeArray = exclude.split(",");

		for (String item : excludeArray) {
			IncludeModel includeModel = new IncludeModel();
			if(FilterResolver.checkType(item) == FilterTypeEnum.NotScopeChild) {//单个模块，模块下需指定Case
				includeModel.type = 0;
				includeModel.order = FilterResolver.getFristTypeOrder(item);
				includeModel.chlidList = getChlidList(FilterResolver.getBracketContent(item));
			}else if(FilterResolver.checkType(item) == FilterTypeEnum.Scope){//范围
				String[] rangeArray = item.split("-");
				includeModel.type = 1;
				includeModel.moduleRange = Range.closed(Integer.valueOf(rangeArray[0]), Integer.valueOf(rangeArray[1]));
			}else if(FilterResolver.checkType(item) == FilterTypeEnum.NotScope){//非范围
				includeModel.type = 0;
				includeModel.order = Integer.valueOf(item);
			}
			excludeModels.add(includeModel);
		}
		return excludeModels;
	}
	
	private static List<IncludeModel> getIncludeList() {
		/**
		 * exclude=30100(1-2),30100-30110 include=30100(30101-30104),110100-110200
		 */
		String include = Config.Include;
		List<IncludeModel> includeModels = new ArrayList<IncludeModel>();
		if(include.length() == 0) {
			return includeModels;
		}
		String[] includeArray = include.split(",");

		for (String item : includeArray) {
			IncludeModel includeModel = new IncludeModel();
			if(FilterResolver.checkType(item) == FilterTypeEnum.NotScopeChild) {//单个模块，模块下需指定Case
				includeModel.type = 0;
				includeModel.order = FilterResolver.getFristTypeOrder(item);
				includeModel.chlidList = getChlidList(FilterResolver.getBracketContent(item));
			}else if(FilterResolver.checkType(item) == FilterTypeEnum.Scope){//范围
				String[] rangeArray = item.split("-");
				includeModel.type = 1;
				includeModel.moduleRange = Range.closed(Integer.valueOf(rangeArray[0]), Integer.valueOf(rangeArray[1]));
			}else if(FilterResolver.checkType(item) == FilterTypeEnum.NotScope){//非范围
				includeModel.type = 0;
				includeModel.order = Integer.valueOf(item);
			}
			includeModels.add(includeModel);
		}
		return includeModels;
	}
	
	private static List<IncludeModel> getChlidList(String str){
		List<IncludeModel> list = new ArrayList<IncludeModel>();
		String[] array = str.split(",");
		for (String item : array) {
			IncludeModel includeModel = new IncludeModel();
			if(FilterResolver.checkType(item) == FilterTypeEnum.Scope) {
				String[] rangeArray = item.split("-");
				includeModel.type = 1;
				includeModel.moduleRange = Range.closed(Integer.valueOf(rangeArray[0]), Integer.valueOf(rangeArray[1]));
			}else if(FilterResolver.checkType(item) == FilterTypeEnum.NotScope) {
				includeModel.type = 0;
				includeModel.order = Integer.valueOf(item);
			}
			list.add(includeModel);
		}
		return list;
	}

}
