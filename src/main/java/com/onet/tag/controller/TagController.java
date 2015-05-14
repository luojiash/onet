package com.onet.tag.controller;

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
import com.onet.common.constant.TagType;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/tag")
public class TagController {
	private static Logger logger = Logger.getLogger(TagController.class);
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping("/manage")
	public ModelAndView toManageTag(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("tag/tag_manage");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			List<TagDTO> atags = tagService.queryTagList(userid, TagType.ARTICLE);
			List<TagDTO> ntags = tagService.queryTagList(userid, TagType.NOTE);
			view.addObject("atags", atags);
			view.addObject("ntags", ntags);
		} catch (Exception e) {
			logger.error("toManageTag error", e);
		}
		return view;
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public IBaseResponse deleteTag(Long tid, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = tagService.deleteTag(tid, userid);
		} catch (Exception e) {
			logger.error("deleteTag error:tagId="+tid, e);
		}
		return baseResponse;
	}
	
	@RequestMapping("/rename")
	@ResponseBody
	public IBaseResponse renameTag(TagDTO tagDTO, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			tagDTO.setUserid(userid);
			baseResponse = tagService.edit(tagDTO);
		} catch (Exception e) {
			logger.error("renameTag error", e);
		}
		return baseResponse;
	}
}
