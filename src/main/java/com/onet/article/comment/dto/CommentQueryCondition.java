package com.onet.article.comment.dto;

import com.onet.common.constant.Sequence;
import com.onet.common.dto.Ordering;
import com.onet.common.dto.PageRequest;

public class CommentQueryCondition extends PageRequest {

	private static final long serialVersionUID = 1L;
	
	private Long articleId;
	private Long userid;
	
	private Ordering ordering = new Ordering("commentDate", Sequence.DESC);

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Ordering getOrdering() {
		return ordering;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}
}
