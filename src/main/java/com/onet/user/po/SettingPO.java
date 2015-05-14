package com.onet.user.po;

public class SettingPO {
	private Long id;
	private Long userid;
	private boolean mdEnabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public boolean isMdEnabled() {
		return mdEnabled;
	}
	public void setMdEnabled(boolean mdEnabled) {
		this.mdEnabled = mdEnabled;
	}
}
