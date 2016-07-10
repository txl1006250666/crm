package com.atguigu.crm.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Lang;

public class PropertyFilter {
	
	private String propertyName;
	private Object propertyVal;
	
	private Class propertyType; //目标属性的类型
	private MatchType matchType; //比较的方式
	
	public enum MatchType{
		EQ,GT,GE,LT,LE,LIKE;
	}
	
	public PropertyFilter(String propertyName,Object propertyVal){
		String str = StringUtils.substringBefore(propertyName, "_"); //获得LIKES
		String matchTypeCode = StringUtils.substring(str, 0, str.length() - 1); //LIKE
		String propertyTypeCode = StringUtils.substring(str, str.length()-1); //S
		
		this.matchType = Enum.valueOf(MatchType.class, matchTypeCode); //LIKE对应的枚举类
		
		//单例模式，有构造器，有get方法，所以先获取到一个单例对象（这是一个Enume对象），再用get方法获取该对象（这才是Class对象）
		PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
		this.propertyType = propertyType.getPropertyType();
		
		this.propertyName = StringUtils.substringAfterLast(propertyName, "_");
		
		this.propertyVal = propertyVal;
		
	}
	
	public enum PropertyType{
		//定义枚举类
		I(Integer.class), F(Float.class), S(String.class), D(Date.class), O(Object.class), L(Lang.class);
		
		//定义属性
		private Class propertyType;
		
		//私有化构造器
		private PropertyType(Class propertyType){
			this.propertyType = propertyType;
		}
		
		public Class getPropertyType() {
			return propertyType;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}
	
	
}
