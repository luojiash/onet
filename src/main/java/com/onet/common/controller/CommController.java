package com.onet.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common")
public class CommController {
	@RequestMapping(value="/denied",produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String accessDenied(HttpServletRequest request, HttpServletResponse response) {
		return "禁止访问";
	}
}
