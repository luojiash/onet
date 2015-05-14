package com.onet.note.dto;

import java.util.ArrayList;
import java.util.List;

import com.onet.common.constant.Sequence;
import com.onet.common.dto.Ordering;
import com.onet.common.dto.PageRequest;

public class NoteQueryRequest extends PageRequest {
	
	private static final long serialVersionUID = 1L;
	
	private Long userid;
	private String searchWord;
	private Long dirId;
	private List<Long> tagIdList = new ArrayList<Long>();
	private Boolean trashed = false;
	private Boolean starred;
	
	private Ordering ordering = new Ordering("modifyTime", Sequence.DESC);
	
	public NoteQueryRequest(Long userid) {
		this.userid = userid;
	}

	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public Long getDirId() {
		return dirId;
	}

	public void setDirId(Long dirId) {
		this.dirId = dirId;
	}

	public List<Long> getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(List<Long> tagIdList) {
		this.tagIdList = tagIdList;
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
