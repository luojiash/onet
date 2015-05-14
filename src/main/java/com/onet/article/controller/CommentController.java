package com.onet.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.article.comment.dto.CommentDTO;
import com.onet.article.comment.service.CommentService;
import com.onet.common.constant.CookieKey;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;

@Controller
@RequestMapping("/article/comment")
public class CommentController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/add")
	@ResponseBody
	public IBaseResponse addComment(CommentDTO dto, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String username = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			dto.setUserid(userid);
			dto.setUsername(username);
			baseResponse = commentService.addComment(dto);
		} catch (Exception e) {
			logger.error("addComment error", e);
		}
		
		return baseResponse;
	}
	
	@RequestMapping("/{articleId:\\d+}")
	public ModelAndView queryComment(@PathVariable Long articleId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/comment/_article_comment");
		try {
			List<CommentDTO> comments = commentService.queryCommentOfArticle(articleId);
			view.addObject("comments", comments);
		} catch (Exception e) {
			logger.error("queryComment error", e);
		}
		return view;
	}
}
