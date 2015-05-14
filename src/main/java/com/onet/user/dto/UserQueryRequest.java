package com.onet.user.dto;

import com.onet.common.dto.Ordering;
import com.onet.common.dto.PageRequest;

public class UserQueryRequest extends PageRequest {
	
	private static final long serialVersionUID = 1L;
	
	private String searchWord;
	private Ordering ordering = new Ordering();
	
	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}
	
}
