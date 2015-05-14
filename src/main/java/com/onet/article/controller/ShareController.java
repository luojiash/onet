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

import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.service.CategoryService;
import com.onet.article.share.dto.ShareDTO;
import com.onet.article.share.service.ShareService;
import com.onet.common.constant.CookieKey;
import com.onet.common.constant.TagType;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/article/share")
public class ShareController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ShareService shareService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TagService tagService;
	
	@RequestMapping("")
	public ModelAndView toMyShare(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/share/share_list");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<ShareDTO> dtos = shareService.queryMyShare(userid);
			view.addObject("shares", dtos);
			view.addObject("sharePage", true);
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toMyShare error", e);
		}
		return view;
	}
	private void addCommonObject(ModelAndView view, Long userid) {
		List<CategoryDTO> categoryDTOs = categoryService.queryCategories(userid);
		List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.ARTICLE);
		
		view.addObject("categories", categoryDTOs);
		view.addObject("tags", tagDTOs);
	}
	@RequestMapping("/detail/{shareId}")
	public ModelAndView toShareDetail(@PathVariable Long shareId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/share/share_detail");
		try {
			CommRes<ShareDTO> res = shareService.queryShareDTO(shareId);
			if (res.getRs() == 0) {
				view.setViewName("common/404");
				return view;
			}
			ShareDTO shareDTO = res.getData();
			view.addObject("share", shareDTO);
			view.addObject("sharePage", true);
		} catch (Exception e) {
			logger.error("toMyShare error", e);
		}
		return view;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public IBaseResponse shareArticle(Long articleId, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = shareService.publishArticle(articleId, userid);
		} catch (Exception e) {
			logger.error("shareArticle error", e);
		}
		return baseResponse;
	}
	
	@RequestMapping("/cancel")
	@ResponseBody
	public IBaseResponse cancelShare(Long articleId, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = shareService.cancelShare(articleId, userid);
		} catch (Exception e) {
			logger.error("cancelShare error", e);
		}
		return baseResponse;
	}
}
