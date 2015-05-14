package com.onet.note.controller;

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

import com.onet.common.constant.CookieKey;
import com.onet.common.constant.TagType;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.util.CookieUtil;
import com.onet.note.dto.DirDTO;
import com.onet.note.dto.NoteDTO;
import com.onet.note.service.DirService;
import com.onet.note.service.NoteService;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/note")
public class NoteOperateController {
	private static Logger logger = Logger.getLogger(NoteOperateController.class);
	@Autowired
	private NoteService noteService;
	@Autowired
	private TagService tagService;
	@Autowired
	private DirService dirService;
	
	@RequestMapping("/edit")
	public ModelAndView toNewNote(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("note/note_edit");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toNewNote error", e);
		}
		return view;
	}
	private void addCommonObject(ModelAndView view, Long userid) {
		List<DirDTO> dirDTOs = dirService.queryCategories(userid);
		List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.NOTE);
		
		view.addObject("categories", dirDTOs);
		view.addObject("tags", tagDTOs);
	}
	@RequestMapping("/edit/{noteId}")
	public ModelAndView toEditNote(@PathVariable("noteId")Long noteId, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("note/note_edit");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			CommRes<NoteDTO> res = noteService.queryNote(noteId, userid);
			view.addObject("note", res.getData());
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toEditNote error", e);
		}
		return view;
	}
	
	/**
	 * 新建笔记
	 */
	@RequestMapping("/add/submit")
	@ResponseBody
	public IBaseResponse addNote(@RequestBody NoteDTO noteDTO, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null; 
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String username = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			noteDTO.setUserid(userid);
			noteDTO.setUsername(username);
			baseResponse = noteService.add(noteDTO);
		} catch (Exception e) {
			logger.error("addNote error", e);
		}
		return baseResponse;
	}
	/**
	 * 更新笔记
	 */
	@RequestMapping("/edit/submit")
	@ResponseBody
	public IBaseResponse editNote(@RequestBody NoteDTO noteDTO, HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String username = CookieUtil.getCookie(request, CookieKey.USER_NAME.getEnName());
			noteDTO.setUserid(userid);
			noteDTO.setModifier(username);
			baseResponse = noteService.edit(noteDTO);
		} catch (Exception e) {
			logger.error("editNote error", e);
		}
		return baseResponse;
	}
	/**
	 * 删除笔记
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public IBaseResponse deleteNote(HttpServletRequest request, HttpServletResponse response) {
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			String param = request.getParameter("id");
			String [] noteIds = param.split(",");
			for(String v:noteIds){
				Long noteId = Long.parseLong(v);
				baseResponse = noteService.deleteNote(noteId, userid);
			}
		} catch (Exception e) {
			logger.error("deleteNote error", e);
		}
		return baseResponse;
	}
	
	/**
	 * 清空回收站
	 */
	/*@RequestMapping("/trash/clear")
	@ResponseBody
	public IBaseResponse clearTrash(HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = noteService.clearTrash(userid);
		} catch (Exception e) {
			logger.error("clearTrash error", e);
		}
		return baseResponse;
	}
	@RequestMapping("/trash/restore")
	@ResponseBody
	public IBaseResponse restoreTrash(Long id, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = noteService.restoreNote(id, userid);
		} catch (Exception e) {
			logger.error("clearTrash error", e);
		}
		return baseResponse;
	}*/
	
	@RequestMapping("/star")
	@ResponseBody
	public IBaseResponse starNote(Long id, Boolean starred, HttpServletRequest request, HttpServletResponse response){
		IBaseResponse baseResponse = null;
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			baseResponse = noteService.starNote(id, starred, userid);
		} catch (Exception e) {
			logger.error("starNote error", e);
		}
		return baseResponse;
	}
}
