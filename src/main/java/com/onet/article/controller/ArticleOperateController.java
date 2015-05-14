package com.onet.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.service.ArticleService;
import com.onet.article.main.service.CategoryService;
import com.onet.common.constant.CookieKey;
import com.onet.common.constant.TagType;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/article")
public class ArticleOperateController {
	private static Logger logger = Logger.getLogger(ArticleOperateController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private TagService tagService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/edit")
	public ModelAndView toNewArticle(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/article_edit");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<CategoryDTO> categoryDTOs = categoryService.queryCategories(userid);
			List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.ARTICLE);
			
			view.addObject("categories", categoryDTOs);
			view.addObject("tags", tagDTOs);
		} catch (Exception e) {
			logger.error("toNewArticle error", e);
		}
		return view;
	}
	
	@RequestMapping("/edit/{articleId}")
	public ModelAndView toEditArticle(@PathVariable("articleId")Long articleId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/article_edit");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<CategoryDTO> categoryDTOs = categoryService.queryCategories(userid);
			List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.ARTICLE);
			CommRes<ArticleDTO> res = articleService.queryArticle(articleId, userid);
			// article not exists or is trashed
			if (res.getRs() == 0 || res.getData().isTrashed()) {
				view.setViewName("common/404");
				return view;
			}
			
			view.addObject("categories", categoryDTOs);
			view.addObject("tags", tagDTOs);
			view.addObject("article", res.getData());
		} catch (Exception e) {
			logger.error("toEditArticle error", e);
		}
		return view;
	}
	
	/**
	 * 新建文章
	 */
	@RequestMapping("/add")
	@ResponseBody
	public IBaseResponse addArticle(@RequestBody ArticleDTO articleDTO, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null; 
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String username = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			articleDTO.setUserid(userid);
			articleDTO.setUsername(username);
			baseResponse = articleService.add(articleDTO);
		} catch (Exception e) {
			logger.error("addArticle error", e);
		}
		return baseResponse;
	}
	/**
	 * 更新文章
	 */
	@RequestMapping("/update")
	@ResponseBody
	public IBaseResponse editArticle(@RequestBody ArticleDTO articleDTO, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String username = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			articleDTO.setUserid(userid);
			articleDTO.setUsername(username);
			baseResponse = articleService.edit(articleDTO);
		} catch (Exception e) {
			logger.error("editArticle error", e);
		}
		return baseResponse;
	}
	/**
	 * 删除文章
	 * toTrash = false 永久删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public IBaseResponse deleteArticle(Long aid, Boolean toTrash, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = articleService.deleteArticle(aid, toTrash, userid);
		} catch (Exception e) {
			logger.error("deleteArticle error", e);
		}
		return baseResponse;
	}
	
	/**
	 * 清空回收站
	 */
	@RequestMapping("/trash/clear")
	@ResponseBody
	public IBaseResponse clearTrash(HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = articleService.clearTrash(userid);
		} catch (Exception e) {
			logger.error("clearTrash error", e);
		}
		return baseResponse;
	}
	@RequestMapping("/trash/restore")
	@ResponseBody
	public IBaseResponse restoreTrash(Long aid, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = articleService.restoreArticle(aid, userid);
		} catch (Exception e) {
			logger.error("restoreTrash error", e);
		}
		return baseResponse;
	}
}
