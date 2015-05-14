package com.onet.tag.dto;

import com.onet.common.dto.Ordering;
import com.onet.common.dto.PageRequest;

public class TagQueryRequest extends PageRequest {
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String searchWord;
	
	private Ordering ordering = new Ordering();
	
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Ordering getOrdering() {
		return ordering;
	}
	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}
}
