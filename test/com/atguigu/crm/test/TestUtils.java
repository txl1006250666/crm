package com.atguigu.crm.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.atguigu.crm.utils.DataProcessUtils;

public class TestUtils {

	@Test
	public void testUtils() {
		
		Map<String, String[]> map = new HashMap<>();
		
		String[] s1 = new String[]{"hao"};
		
		map.put("search_LIKE_custName", s1);
		
		Map<String, String[]> obscureParamMap = DataProcessUtils.getObscureParamMap(map, "search_LIKE_");
		
		//Set<Entry<String, String[]>> entrySet = obscureParamMap.entrySet();
		
		
		System.out.println(obscureParamMap.get("custName").toString());
	}

}
