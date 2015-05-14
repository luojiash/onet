package com.onet.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.service.CategoryService;
import com.onet.common.constant.CookieKey;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.note.form.DirForm;

@Controller
@RequestMapping("/article/category")
public class CategoryController {
	private static Logger logger = Logger.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/manage")
	public ModelAndView toManageCategory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("article/category/category_manage");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<CategoryDTO> categoryDTOs = categoryService.queryCategories(userid);
			view.addObject("categories", categoryDTOs);
		} catch (Exception e) {
			logger.error("toManageCategory error", e);
		}
		return view;
	}
	
	@RequestMapping("/oper")
	@ResponseBody
	public IBaseResponse categoryOperation(DirForm form, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null; 
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			switch (form.getOper()) {
			case cre:
				baseResponse = categoryService.add(form.getCname(), userid);
				break;
			case del:
				baseResponse = categoryService.deleteCate(form.getCid(), userid);
				break;
			case ren:
				baseResponse = categoryService.renameCate(form.getCid(), userid, form.getCname());
				break;			
			default:
				return new BaseResponse(false, "unknown operation!");
			}
			
		} catch (Exception e) {
			logger.error("categoryOperation error", e);
		}
		return baseResponse;
	}
}
