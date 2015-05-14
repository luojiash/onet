package com.onet.common.constant;

public enum CookieKey {
	USER_ID("user_id", "用户id"),
	USER_NAME("user_name", "用户名"),
	USER_ROLE("user_role", "用户角色");
	private String enName;
	private String mean;

	public String getMean() {
		return mean;
	}

	public String getEnName() {
		return enName;
	}

	private CookieKey(String enName, String mean) {
		this.enName = enName;
		this.mean = mean;
	}

	
	
}
