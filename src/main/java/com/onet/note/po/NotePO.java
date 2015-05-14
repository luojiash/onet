package com.onet.note.po;

import java.util.Date;

public class NotePO {
	private Long id;
	private Long userid;
	private String username;
	private String title;
	private String content;
	private Long dirId;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private String status;
	private boolean starred;
	private boolean trashed;
	private boolean shared;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getDirId() {
		return dirId;
	}
	public void setDirId(Long dirId) {
		this.dirId = dirId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isStarred() {
		return starred;
	}
	public void setStarred(boolean starred) {
		this.starred = starred;
	}
	public boolean isTrashed() {
		return trashed;
	}
	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
}
