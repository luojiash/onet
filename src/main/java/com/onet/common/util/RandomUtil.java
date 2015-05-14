package com.onet.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class RandomUtil {

	public static Random random = new Random();
	private static Random rd = new Random();
	private static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

	public static String getRandomCode() {
		return fmt.format(new Date()) + getTimeRandom() + getProcessID();
	}

	//获取进程号
	private static String getProcessID() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		String processID = processName.substring(0, processName.indexOf('@'));
		if (processID.length() == 1) {
			processID = "0" + processID;
		} else if (processID.length() > 2) {
			processID = processID.substring(0, 2);
		}
		return processID;
	}

	/**
	 * 获取13位时间，去掉前第5位，分为2个4位数，中间加一个2位随机数
	 *
	 * @return
	 */
	private static String getTimeRandom() {
		Long l = System.currentTimeMillis();
		String str = String.valueOf(l);
		String strStart = str.substring(5, 9);
		String strMid = rd.nextInt(99) + "";
		if (strMid.length() == 1) {
			strMid = "0" + strMid;
		}
		String strEnd = str.substring(9);
		return strStart + strMid + strEnd;
	}

	/**
	 * 从from到to中获取num个不会重复的数
	 */
	public static int[] getRandoms(int from ,int to,int num){
		if(from > to){return null;}
		if(to - from < num) {
			return null;
		}
		int[] array = new int[num];
		for(int i = 0; i<num; i++){
			array[i] = random.nextInt(to - from) + from;
			for(int j = 0;j < i;j++){  //检查重复
			    if(array[i] == array[j]){
			    	i = i-1 ;//重复重新从该位开始
			    	break;
			    }
			}
		}

		return array;
	}
}
