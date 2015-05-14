package com.onet.tag.dto;

import com.onet.common.constant.TagType;

public class TagDTO {
	private Long id;
	private Long userid;
	private String name;
	private TagType type;
	private int referCount;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TagType getType() {
		return type;
	}
	public void setType(TagType type) {
		this.type = type;
	}
	public int getReferCount() {
		return referCount;
	}
	public void setReferCount(int referCount) {
		this.referCount = referCount;
	}
}
