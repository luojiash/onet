package com.onet.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlProviderUtil {
	public static String getInsertFields(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(",#{"+field+"}");
		}
		return sb.substring(1);
	}
	
	public static String getTableField(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(","+field);
		}
		return sb.substring(1);
	}
	
	public static String getUpdateSql(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(","+field + "=#{" + field + "}");
		}
		return sb.substring(1);
	}
	
	private static List<String> getFields(Class<?> clazz, String [] ignoreProperties) {
		Field fields[] = clazz.getDeclaredFields();//反射获取字段名和值，不获取父类的字段
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		List<String> resultFields = new ArrayList<String>();//字段名结果集
		for (Field field : fields) {
			String fieldName = field.getName();
			if (ignoreProperties != null && ignoreList.contains(fieldName)) {
				continue;
			}
			resultFields.add(fieldName);
		}
		return resultFields;
	}
}
