package com.onet.common.constant;

public enum TagType {
	NOTE("note", "文章"),
	ARTICLE("article", "笔记");
	private String enName;
	private String mean;

	public String getMean() {
		return mean;
	}

	public String getEnName() {
		return enName;
	}

	private TagType(String enName, String mean) {
		this.enName = enName;
		this.mean = mean;
	}

	
	
}
