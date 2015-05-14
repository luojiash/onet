package com.onet.common.constant;

public enum DirOper {
	del("删除"),
	ren("重命名"),
	cre("新建文件夹");
	
	private String mean;

	private DirOper(String mean) {
		this.mean = mean;
	}

	public String getMean() {
		return mean;
	}
	 
}
