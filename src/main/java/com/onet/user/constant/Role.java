package com.onet.user.constant;

public enum Role {
	ADMIN("后台管理员"),
	USER("普通用户");
	private String mean;

	private Role(String mean) {
		this.mean = mean;
	}

	public String getMean() {
		return mean;
	}
	
}
