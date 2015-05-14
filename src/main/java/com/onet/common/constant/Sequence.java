package com.onet.common.constant;

public enum Sequence {
	ASC("ASC", "升序"),
	DESC("DESC", "降序");
	
	private String sql;
	private String mean;
	private Sequence(String sql, String mean) {
		this.sql = sql;
		this.mean = mean;
	}
	
	public String getSql() {
		return sql;
	}
	public String getMean() {
		return mean;
	}
}
