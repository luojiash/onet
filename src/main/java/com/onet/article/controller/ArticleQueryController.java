package com.onet.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.service.ArticleService;
import com.onet.article.main.service.CategoryService;
import com.onet.common.constant.CookieKey;
import com.onet.common.constant.TagType;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.PageRes;
import com.onet.common.util.CookieUtil;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/article")
public class ArticleQueryController {
	private static Logger logger = Logger.getLogger(ArticleQueryController.class);
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TagService tagService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list/**")
	public ModelAndView toArticleList(Long cid, Long tid, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/article_list");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));	
			ArticleQueryRequest queryRequest = new ArticleQueryRequest(userid);
			String page = request.getParameter("page");
			String pageSizeStr = request.getParameter("pageSize");
			int pageNo = null == page ? 1:Integer.valueOf(page);
			int pageSize = null == pageSizeStr ? 10:Integer.valueOf(pageSizeStr);
			queryRequest.setPageNo(pageNo);
			queryRequest.setPageSize(pageSize);
			String param = request.getRequestURI().substring("/onet/article/list".length());
			if (!"".equals(param)) {
				String p [] = param.substring(1).split("/");
				if (p.length == 2) {
					switch (p[0]) {
					case "category":
						queryRequest.setCategoryId(Long.valueOf(p[1]));
						view.addObject("cid", Long.valueOf(p[1]));
						break;
					case "tag":
						queryRequest.setTagId(Long.valueOf(p[1]));
						view.addObject("tid", Long.valueOf(p[1]));
						break;
					default:
						break;
					}
				}
			}
			PageRes<ArticleDTO> res = articleService.queryArticleList(queryRequest);
			
			view.addObject("articleRes", res);
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toArticleList error", e);
		}
		return view;
	}
	@RequestMapping("/trash")
	public ModelAndView toTrash(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/article_list");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));	
			ArticleQueryRequest queryRequest = new ArticleQueryRequest(userid);
			queryRequest.setTrashed(true);
			queryRequest.setPageSize(Integer.MAX_VALUE);
			PageRes<ArticleDTO> res = articleService.queryArticleList(queryRequest);

			view.addObject("articleRes", res);
			view.addObject("trash", true);
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toTrash error", e);
		}
		return view;
	}
	private void addCommonObject(ModelAndView view, Long userid) {
		List<CategoryDTO> categoryDTOs = categoryService.queryCategories(userid);
		List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.ARTICLE);
		
		view.addObject("categories", categoryDTOs);
		view.addObject("tags", tagDTOs);
	}
	@RequestMapping("/detail/{articleId:\\d+}")
	public ModelAndView toArticleDetail(@PathVariable("articleId") Long articleId, HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("article/article_detail");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			CommRes<ArticleDTO> res = articleService.queryArticle(articleId, userid);
			if (res.getRs() == 0) {
				view.setViewName("common/404");
				return view;
			}
			view.addObject("article", res.getData());
		} catch (Exception e) {
			logger.error("toArticleDetail error", e);
		}
		return view;
	}
}
