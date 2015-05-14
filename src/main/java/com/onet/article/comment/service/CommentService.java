package com.onet.article.comment.service;

import java.util.List;

import com.onet.article.comment.dto.CommentDTO;
import com.onet.common.dto.IBaseResponse;

public interface CommentService {
	public IBaseResponse addComment(CommentDTO comment);
	public IBaseResponse deleteComment(Long commentId, Long operatorId);
	
	public List<CommentDTO> queryCommentOfArticle(Long articleId);
}
