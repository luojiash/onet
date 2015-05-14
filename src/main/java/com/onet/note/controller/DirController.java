package com.onet.note.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.common.constant.CookieKey;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.note.dto.DirDTO;
import com.onet.note.form.DirForm;
import com.onet.note.service.DirService;

@Controller
@RequestMapping("/note/category")
public class DirController {
	private static Logger logger = Logger.getLogger(DirController.class);
	
	@Autowired
	private DirService categoryService;
	
	@RequestMapping("/oper")
	@ResponseBody
	public IBaseResponse categoryOperation(DirForm form, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null; 
		try {
			Long userId = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			switch (form.getOper()) {
			case del:
				baseResponse = categoryService.deleteCategory(form.getCid(), userId);
				break;
			case ren:
				baseResponse = categoryService.renameCate(form.getCid(), userId, form.getCname());
				break;
			case cre:
				baseResponse = categoryService.add(form.getCname(), form.getPid(), userId);
				break;
			default:
				return new BaseResponse(false, "unknown operation!");
			}
			
		} catch (Exception e) {
			logger.error("categoryOperation error", e);
		}
		return baseResponse;
	}
	
	/**
	 * ?pid=2
	 */
	@RequestMapping("/getsub")
	public ModelAndView getSubCates(Long pid, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		try {
			Long userId = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<DirDTO> categoryDTOs = categoryService.querySubCates(pid, userId);
			view.addObject("categories", categoryDTOs);
		} catch (Exception e) {
			logger.error("getSubCates error", e);
		}
		return view;
	}
}
