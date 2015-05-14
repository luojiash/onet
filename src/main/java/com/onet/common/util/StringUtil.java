package com.onet.common.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 * @author chenjiajie
 *
 */
public class StringUtil {
    private static Pattern p = Pattern.compile("(\\w+)");
	/**
	 * 判空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s);
	}


	/**
	 * 过滤url，防止xss
	 * @return
	 */
	public static String escapeHtml(String value){
		if(isEmpty(value)){return value;}
		return StringEscapeUtils.escapeHtml(value);//添加过滤xss
	}


	
	
	public static  boolean isNotEmpty(String s){
		return !isEmpty(s);
	}
	/**
	 * 用sign来join数组
	 * @param arr
	 * @param sign
	 * @return
	 */
	public static String join(String[] arr,String sign) {
		if(arr==null||arr.length==0){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(String s : arr){
			sb.append(s+sign);
		}
		return sb.substring(0,sb.lastIndexOf(sign));
	}
	/**
	 * 获取对象的值(一层结构),一般用于日志,如果有异常不处理
	 */
	public static String javaBeanToString(Object o) {
		try {
			Class<?> cla = o.getClass();
			// 获得该类下面所有的字段集合
			Field[] filed = cla.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			for (Field fd : filed) {
				String filedName = fd.getName();
				if (filedName.equalsIgnoreCase("serialVersionUID")) {
					continue;
				}
				String firstLetter = filedName.substring(0, 1).toUpperCase(); // 获得字段第一个字母大写
				String getMethodName = "get" + firstLetter + filedName.substring(1); // 转换成字段的get方法
				Method getMethod = cla.getMethod(getMethodName);
				Object value = getMethod.invoke(o); // 这个对象字段get方法的值
				sb.append(filedName).append(":").append(value).append(",");
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 去掉str最后的字符串，比如deleteLastChar("23,45,",",") 变为"23,45"
	 * @param str
	 * @param lastSymbol(length可以大于1)
	 */
	public static String deleteLastChar(String str, String lastSymbol){
		if(lastSymbol==null || lastSymbol.length() == 0){return str;}
		if(str==null || str.length() <= lastSymbol.length()){return str;}
		if(str.substring(str.length() - lastSymbol.length(), str.length()).equals(lastSymbol)){
			return str.substring(0,str.length() - lastSymbol.length());
		}
		return str;
	}
	
	/**
	 * 把一个String转为String[]，比如"ABC" 变为["A","B","C"]
	 */
	public static String[] getStringArray(String s){
		if(StringUtil.isEmpty(s)){
			return new String[0];
		}
		String[] stArray = new String[s.length()];
        for(int i = 0;i<s.length();i++){
        	stArray[i] = String.valueOf(s.charAt(i));
        }
        return stArray;
	}

	/**
	 * 把流转化为String（只适合英文，中文utf8可能乱码）
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromInputStream(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] bt = new byte[4096];
		for (int n; (n = in.read(bt)) != -1;) {
			out.append(new String(bt, 0, n));
		}
		return out.toString();
	}
	/**
	 * inputstream to string
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String ins2String(InputStream is) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        try {
        	reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
	}

	// 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 左位补值
     * @param s 待补位值
     * @param n 补位后长度
     * @param replace 用来补位的值
     * @return
     */
    public static String lpad(String s, int n, String replace){
    	while(s.length()<n){
    		s = replace + s;
    	}
    	return s;
    }


    public static String toInsert(String s){
        Matcher m =p.matcher(s);
        if(m.find()){
            return m.replaceAll("#{$1}") + ",creator=#{creator},createTime=now()";
        }
        return "";
    }

    public static String toUpdate(String s){
        Matcher m =p.matcher(s);
        if(m.find()){
            return m.replaceAll("$1=#{$1}") + ",modifier=#{modifier},modifyTime=now()";
        }
        return "";
    }

    public static String toLogString(Object object,String... ignoreFields) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.getSerializationConfig().addMixInAnnotations(
                Object.class, PropertyFilterMixIn.class);
        FilterProvider filters = new SimpleFilterProvider().addFilter("filter properties by name",
                SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields));
        ObjectWriter writer = mapper.writer(filters);
        String result = "";
        try {
            result =  writer.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getString(Object obj){
    	if(obj!=null){
    		return obj.toString().trim();
    	}else{
    		return null;
    	}
    }
    


    /**
     * 过滤html的标签 "<span>a</span>" 变为 a
     */
    public static String removeHtmlElement(String htmlString) {
		if (htmlString == null) {
			return null;
		}
		String str = htmlString.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replace("&nbsp", "");
		return str;
	}
    /** 
     * 去除字符串首尾出现的某个字符
     * @param source 源字符串
     * @param ch 需要去除的字符
     * @return String
     */  
    public static String trim(String source,char ch){  
    	if(source == null || source == "") return source;
        boolean beginIndexFlag = true;  
        boolean endIndexFlag = true;  
        do{
            int beginIndex = source.indexOf(ch) == 0 ? 1 : 0; 
            int endIndex = source.lastIndexOf(ch) + 1 == source.length() ? source.lastIndexOf(ch) : source.length();
            source = source.substring(beginIndex, endIndex);
            beginIndexFlag = (source.indexOf(ch) == 0);
            endIndexFlag = (source.lastIndexOf(ch) + 1 == source.length());
        } while (beginIndexFlag || endIndexFlag);  
        return source;
    }
    /**
     * 验证字符串是否纯整数
     * @param str
     * @return
     */
    public static boolean validateIsInt(String str){
    	if(null != str){
    		return str.matches("\\d+");
    	}
    	return false;
    }
    
    /**
	 * 替换掉 无法识别的字符
	 * @param s
	 * @return
	 */
	public static String filterUnidentifiedChar(String s) {
		if (!isEmpty(s)) {
			return s.replace(" ", "");
		}
		return s;
	}
    
    @JsonFilter("filter properties by name")
    private class PropertyFilterMixIn{}

    public static void main(String[] args) throws IOException {
        //System.out.println(validateIsInt("12515646161D61647"));
        String a = "ABC ";
        System.out.println(filterUnidentifiedChar(a));
    }

	
}
