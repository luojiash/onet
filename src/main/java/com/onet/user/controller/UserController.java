package com.onet.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onet.auth.AuthNotRequired;
import com.onet.common.constant.CookieKey;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.common.util.MD5Util;
import com.onet.note.service.DirService;
import com.onet.user.constant.Role;
import com.onet.user.dto.UserDTO;
import com.onet.user.form.UserForm;
import com.onet.user.form.UserLoginForm;
import com.onet.user.form.UserRegisterForm;
import com.onet.user.po.SettingPO;
import com.onet.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	private final int WEEK = 3600*24*7;
	@Autowired
	private UserService userService;
	@Autowired
	private DirService dirService;
	
	@RequestMapping("/signin")
	@AuthNotRequired
	public ModelAndView toSign(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/user_sign");
		return mv;
	}
	/**
	 * 用户注册提交
	 */
	@RequestMapping("/register/submit")
	@ResponseBody
	@AuthNotRequired
	public IBaseResponse register(UserRegisterForm userRegisterForm, HttpServletRequest request, HttpServletResponse response) {
		String userName = userRegisterForm.getUserName();
		String password = userRegisterForm.getPassword();
		if (!password.equals(userRegisterForm.getConfirmPassword())) {
			return new BaseResponse(false, "两次输入密码不一致");
		}
		IBaseResponse baseResponse =null;
		try {
			UserDTO userDto = new UserDTO();
			userDto.setUserName(userName);
			userDto.setPassword(MD5Util.encrypt(password));
			userDto.setRole(Role.USER);
			userDto.setActive(true);
			baseResponse = userService.add(userDto);
			dirService.addRootCategory(userDto.getId());// 添加根目录
		} catch (Exception e) {
			logger.error("register error", e);
		}
		return baseResponse;
	}
	
	/**
	 * 用户登录提交
	 */
	@RequestMapping("/login/submit")
	@ResponseBody
	@AuthNotRequired
	public IBaseResponse login(UserLoginForm userLoginForm, HttpServletRequest request, HttpServletResponse response) {
		String userName = userLoginForm.getUserName();
		String password = userLoginForm.getPassword();
		try {
			CommRes<UserDTO> res = userService.queryUser(userName);
			if (res.getRs() == 0) {
				return new BaseResponse(false, res.getMsg());
			}
			if (!MD5Util.encrypt(password).equals(res.getData().getPassword())) {
				return new BaseResponse(false, "密码错误");
			}
			if (!res.getData().isActive()) {
				return new BaseResponse(false, "account is inactive");
			}
			int cookieTime = 0;
			if ("y".equals(userLoginForm.getRememberMe())) {
				cookieTime = WEEK;
			}
			CookieUtil.addCookie(response, CookieKey.USER_NAME.getEnName(), userName, cookieTime);
			CookieUtil.addCookie(response, CookieKey.USER_ID.getEnName(), res.getData().getId().toString(), cookieTime);
			CookieUtil.addCookie(response, CookieKey.USER_ROLE.getEnName(), res.getData().getRole().name(), cookieTime);
		} catch (Exception e) {
			logger.error("login error", e);
		}
		return new BaseResponse(true, "登录成功");
	}
	
	@RequestMapping(value="/logout",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieUtil.removeAllCookie(request, response);	
		} catch (Exception e) {
			logger.error("logout error", e);
		}
		return "登出成功";
	}
	
	@RequestMapping("/profile")
	public ModelAndView toUserCenter(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/user_profile");
		try {
			String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
			CommRes<UserDTO> res = userService.queryUser(Long.valueOf(userId));
			mv.addObject("user", res.getData());
		} catch (Exception e) {
			logger.error("toUserCenter error", e);
		}
		return mv;
	}
	
	@RequestMapping("/profile/edit")
	@ResponseBody
	public IBaseResponse editProfile(UserForm userForm, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
			CommRes<UserDTO> res = userService.queryUser(Long.valueOf(userId));
			UserDTO userDTO = res.getData();
			userDTO.setEmail(userForm.getEmail());
			baseResponse = userService.edit(userDTO);
		} catch (Exception e) {
			logger.error("editProfile error", e);
		}
		return baseResponse;
	}
	
	@RequestMapping("/chpwd/submit")
	@ResponseBody
	public IBaseResponse chpwd(UserForm userForm, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			if (!userForm.getNewPassword().equals(userForm.getNewPassword1())) {
				return new BaseResponse(false, "两次输入密码不一致！");
			} else if ("".equals(userForm.getNewPassword())) {
				return new BaseResponse(false, "密码不能为空！");
			}
			String pwd = MD5Util.encrypt(userForm.getPassword());
			String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
			CommRes<UserDTO> res = userService.queryUser(Long.valueOf(userId));
			UserDTO userDTO = res.getData();
			if (!pwd.equals(userDTO.getPassword())) {
				return new BaseResponse(false, "原密码错误！");
			}
			String newpwd = MD5Util.encrypt(userForm.getNewPassword());
			userDTO.setPassword(newpwd);
			baseResponse = userService.edit(userDTO);
		} catch (Exception e) {
			logger.error("chpw error", e);
		}
		return baseResponse;
	}
	
	@RequestMapping("/setting")
	public ModelAndView toSetting(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("user/sys_setting");
		try {
			String userId = CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName());
			SettingPO po = userService.queryUserSetting(Long.valueOf(userId));
			mv.addObject("setting", po);
		} catch (Exception e) {
			logger.error("toSetting error", e);
		}
		return mv;
	}
	
	@RequestMapping("/setting/submit")
	@ResponseBody
	public IBaseResponse chSetting(SettingPO po, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			po.setUserid(userid);
			baseResponse = userService.updateSetting(po);
		} catch (Exception e) {
			logger.error("chSetting error", e);
		}
		return baseResponse;
	}
	
	@RequestMapping("/setting/get")
	@ResponseBody
	public SettingPO getSetting(HttpServletRequest request, HttpServletResponse response) {
		SettingPO po = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			po = userService.queryUserSetting(userid);
		} catch (Exception e) {
			logger.error("getSetting error", e);
		}
		return po;
	}
}
