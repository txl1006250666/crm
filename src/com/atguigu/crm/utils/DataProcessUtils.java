package com.atguigu.crm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.atguigu.crm.utils.PropertyFilter.MatchType;

public class DataProcessUtils {

	public static Map<String, String[]> getObscureParamMap(Map<String, String[]> paramMap, String prefix) {
		// 如果为空，直接返回空
		if (paramMap == null || paramMap.size() == 0) {
			return null;
		}

		// 创建结果集
		Map<String, String[]> result = new HashMap<>();

		Set<String> keySet = paramMap.keySet();
		Iterator<String> iterator = keySet.iterator();

		// 如果找到符合条件的，将该key跟原集合中的值放入结果集中
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.startsWith(prefix)) {
				String[] strings1 = paramMap.get(key);
				result.put(key, strings1);
			}
		}

		return result;
	}

	public static String transformMapToString(Map<String, Object> params) {

		StringBuilder sBuilder = new StringBuilder();

		// 遍历map的entry的Set集合
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();

			if (val == null || val.toString().equals("")) {
				continue;
			}

			sBuilder.append("&").append("search_").append(key).append("=").append(val);
		}

		return sBuilder.toString();
	}

	public static List<PropertyFilter> transformHandlerParamsToPropertyFilters(Map<String, Object> params) {
		
		List<PropertyFilter> filters = new ArrayList<>();
		
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			PropertyFilter propertyFilter = new PropertyFilter(key,value);
			filters.add(propertyFilter);
		}
		
		return filters;
	}
	
	static{
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"});
		ConvertUtils.register(dateConverter, Date.class);
	}

	public static Map<String, Object> transformPropertyFiltersToHandlerParams(List<PropertyFilter> filters) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		for (PropertyFilter filter : filters) {
			String propertyName = filter.getPropertyName();
			Object propertyVal = filter.getPropertyVal();
			
			Class propertyType = filter.getPropertyType();
			
			//System.out.println(propertyVal.toString().length() != 0);
			//将值进行转换
			if (propertyVal != null) {
				if (propertyVal.toString().length() != 0) {
					propertyVal = ConvertUtils.convert(propertyVal, propertyType);
				}
			}
			
			//propertyVal = ConvertUtils.convert(propertyVal, propertyType);
			
			MatchType matchType = filter.getMatchType();
			if (matchType == MatchType.LIKE) {
				/*if (propertyVal != "") {
					propertyVal = null;
				}*/
				if (propertyVal != "") {
					propertyVal = "%" + propertyVal + "%";
				}
			}
			
			params.put(propertyName, propertyVal);
		}
		
		return params;
	}

}
