package com.onet.note.form;

import com.onet.common.constant.DirOper;

public class DirForm {
	private DirOper oper;
	private Long cid;
	private Long pid;
	private String cname;
	public DirOper getOper() {
		return oper;
	}
	public void setOper(DirOper oper) {
		this.oper = oper;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
}
