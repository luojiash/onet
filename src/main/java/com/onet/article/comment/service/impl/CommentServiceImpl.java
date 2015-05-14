package com.onet.article.comment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.onet.article.comment.dao.CommentDAO;
import com.onet.article.comment.dto.CommentDTO;
import com.onet.article.comment.po.CommentPO;
import com.onet.article.comment.service.CommentService;
import com.onet.article.main.dao.ArticleDao;
import com.onet.article.main.po.ArticlePO;
import com.onet.common.constant.CommConst;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.manager.ObjectConvertManager;

public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse addComment(CommentDTO comment) {
		ArticlePO articlePO = articleDao.queryArticle(comment.getArticleId());
		if (!articlePO.isShared() && !articlePO.getUserid().equals(comment.getUserid())) {
			return new BaseResponse(false, "no permission!");
		}
		if (!CommConst.ROOT_COMMENT_PID.equals(comment.getPid())) {
			int row = commentDAO.increaseReplyCountByOne(comment.getPid(), 1);
			if (row == 0) {
				return new BaseResponse(false, "parent comment not exists!");
			}
		}
		CommentPO po = objectConvertManager.toCommentPO(comment);
		po.setCommentDate(new Date());
		commentDAO.insert(po);
		return new BaseResponse(true, po.getId().toString(),null);
	}

	@Override
	public IBaseResponse deleteComment(Long commentId, Long operatorId) {
		IBaseResponse response = new BaseResponse(false);
		CommentPO po = commentDAO.queryById(commentId);
		if (!operatorId.equals(po.getUserid())) { // check permission
			response.setReturnMessage("no permission!");
			return response;
		}
		if (!CommConst.ROOT_COMMENT_PID.equals(po.getPid())) { // check parent comment
			int row = commentDAO.increaseReplyCountByOne(po.getPid(), -1);
			if (row == 0) {
				response.setReturnMessage("parent comment not exists!");
				return response;
			}
		}
		int rows = commentDAO.deleteById(commentId);
		if (rows == 0) {
			response.setReturnMessage("delete comment fail!");
		} else {
			response.setResult(true);
		}
		return response;
	}

	private List<CommentDTO> queryComment(Long articleId, Long pid) {
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();
		List<CommentPO> pos = commentDAO.queryByArticleId(articleId, pid);
		for (CommentPO po : pos) {
			CommentDTO dto = objectConvertManager.toCommentDTO(po);
			if (dto.getReplyCount() > 0) {
				dto.setReplies(queryComment(articleId, dto.getId()));
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public List<CommentDTO> queryCommentOfArticle(Long articleId) {		
		return queryComment(articleId, CommConst.ROOT_COMMENT_PID);
	}

}
