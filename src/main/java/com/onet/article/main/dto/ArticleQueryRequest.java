package com.onet.article.main.dto;

import com.onet.common.constant.Sequence;
import com.onet.common.dto.Ordering;
import com.onet.common.dto.PageRequest;

public class ArticleQueryRequest extends PageRequest {
	
	private static final long serialVersionUID = 1L;
	
	private Long userid;
	private String keyword;
	private Long categoryId;
	private Long tagId;
	private Boolean trashed = false;
	private Boolean starred;
	
	private Ordering ordering = new Ordering("createTime", Sequence.DESC);
	
	public ArticleQueryRequest(Long userid) {
		this.userid = userid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public Boolean getTrashed() {
		return trashed;
	}

	public void setTrashed(Boolean trashed) {
		this.trashed = trashed;
	}

	public Boolean getStarred() {
		return starred;
	}

	public void setStarred(Boolean starred) {
		this.starred = starred;
	}

	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}
}
