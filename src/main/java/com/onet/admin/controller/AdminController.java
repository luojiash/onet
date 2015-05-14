package com.onet.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.user.constant.Role;
import com.onet.user.dto.UserDTO;
import com.onet.user.dto.UserQueryRequest;
import com.onet.user.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	/**
	 * ?u=lo
	 */
	@RequestMapping("/user/list")
	public ModelAndView toUserList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("admin/user_list");
		try {
			String searchWord = request.getParameter("u");
			UserQueryRequest queryRequest = new UserQueryRequest();
			queryRequest.setSearchWord(searchWord);
			PageRes<UserDTO> res = userService.queryUserList(queryRequest);
			view.addObject("userRes", res);
			view.addObject("u", searchWord);
		} catch (Exception e) {
			logger.error("toUserList error", e);
		}
		return view;
	}
	/**
	 * ajax 分页请求
	 */
	@RequestMapping("/user/pagination")
	public ModelAndView userPagination(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("admin/_user_list_body");
		try {
			String searchWord = request.getParameter("u");
			int pageNo = Integer.valueOf(request.getParameter("page"));
			UserQueryRequest queryRequest = new UserQueryRequest();
			queryRequest.setPageNo(pageNo);
			queryRequest.setSearchWord(searchWord);
			PageRes<UserDTO> res = userService.queryUserList(queryRequest);
			view.addObject("userRes", res);
		} catch (Exception e) {
			logger.error("userPagination error", e);
		}
		return view;
	}
	
	@RequestMapping("/user/activation")
	@ResponseBody
	public IBaseResponse activation(Long userId, Boolean active, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			baseResponse = userService.editUserMode(userId, active);
		} catch (Exception e) {
			logger.error("activation error", e);
		}
		return baseResponse;
	}
	@RequestMapping("/user/role")
	@ResponseBody
	public IBaseResponse changeRole(Long userId, Role role, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			baseResponse = userService.editUserRole(userId, role);
		} catch (Exception e) {
			logger.error("activation error", e);
		}
		return baseResponse;
	}
}
