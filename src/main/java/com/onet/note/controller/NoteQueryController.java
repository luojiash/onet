package com.onet.note.controller;

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

import com.onet.common.constant.CookieKey;
import com.onet.common.constant.TagType;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.PageRes;
import com.onet.common.util.CookieUtil;
import com.onet.common.util.StringUtil;
import com.onet.note.dto.DirDTO;
import com.onet.note.dto.NoteDTO;
import com.onet.note.dto.NoteQueryRequest;
import com.onet.note.service.DirService;
import com.onet.note.service.NoteService;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.service.TagService;

@Controller
@RequestMapping("/note")
public class NoteQueryController {
	private static Logger logger = Logger.getLogger(NoteQueryController.class);
	@Autowired
	private NoteService noteService;
	@Autowired
	private DirService dirService;
	@Autowired
	private TagService tagService;
	
	/**
	 * 笔记列表
	 * ?page=2&pageSize=9&cid=2&tids=1,2
	 */
	@RequestMapping("/list/**")
	public ModelAndView toNoteList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("note/note_list_full");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			putNote(view, request);
			addCommonObject(view, userid);
		} catch (Exception e) {
			logger.error("toNoteList error", e);
		}
		return view;
	}
	
	private void addCommonObject(ModelAndView view, Long userid) {
		List<DirDTO> dirDTOs = dirService.queryCategories(userid);
		List<TagDTO> tagDTOs = tagService.queryTagList(userid, TagType.NOTE);
		
		view.addObject("categories", dirDTOs);
		view.addObject("tags", tagDTOs);
	}
	private void putNote(ModelAndView view, HttpServletRequest request) {
		Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
		NoteQueryRequest noteReq = new NoteQueryRequest(userid);
		String filter = request.getRequestURI().substring("/onet/note/list".length());
		switch (filter) {
		case "/star":
			noteReq.setStarred(true);
			view.addObject("star", true);
			break;
		case "/more":
			view.setViewName("note/_note_list");
			break;
		default:
			break;
		}
		String cid = request.getParameter("cid");
		if (StringUtil.isNotEmpty(cid)) {
			noteReq.setDirId(Long.valueOf(cid));
		}
		String page = request.getParameter("page");
		String pageSizeStr = request.getParameter("pageSize");
		String tids = request.getParameter("tids");
		int pageNo = null == page ? 1:Integer.valueOf(page);
		int pageSize = null == pageSizeStr ? 10:Integer.valueOf(pageSizeStr);
		noteReq.setPageNo(pageNo);
		noteReq.setPageSize(pageSize);
		List<Long> tagIdList = noteReq.getTagIdList();
		if (StringUtil.isNotEmpty(tids)) {
			for (String tid : tids.split(",")) {
				tagIdList.add(Long.valueOf(tid));
			}	
		}
		PageRes<NoteDTO> res = noteService.queryNoteList(noteReq);
		view.addObject("noteRes", res);
	}
	
	@RequestMapping("/view/{noteId}")
	public ModelAndView toSingleNote(@PathVariable("noteId") Long noteId, HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("note/note_item");
		try {
			Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
			CommRes<NoteDTO> res = noteService.queryNote(noteId, userid);
			view.addObject("noteRes", res);
		} catch (Exception e) {
			logger.error("toSingleNote error", e);
		}
		return view;
	}
	
	/**
	 * ?starred=true
	 */
	@RequestMapping("/count")
	@ResponseBody
	public int queryNoteCount(Boolean starred, HttpServletRequest request, HttpServletResponse response){
		Long userid = Long.valueOf(CookieUtil.getCookie(request, CookieKey.USER_ID.getEnName()));
		NoteQueryRequest noteQueryRequest = new NoteQueryRequest(userid);
		noteQueryRequest.setStarred(starred);
		int total = noteService.queryNoteCount(noteQueryRequest);
		return total;
	}
	/*public static void main(String[] args) {
		System.out.println("2".substring(2));
	}*/
}
