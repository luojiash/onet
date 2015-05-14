package com.onet.tag.po;

import com.onet.common.constant.TagType;

public class TagPO {
	private Long id;
	private Long userid;
	private String name;
	private TagType type;
	
	
	public TagPO() {
	}
	public TagPO(Long userid, String name, TagType type) {
		this.userid = userid;
		this.name = name;
		this.type = type;
	}
	
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
}
