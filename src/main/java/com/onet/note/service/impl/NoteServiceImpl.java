package com.onet.note.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.onet.common.constant.TagType;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.note.dao.NoteDao;
import com.onet.note.dto.NoteDTO;
import com.onet.note.dto.NoteQueryRequest;
import com.onet.note.po.NotePO;
import com.onet.note.service.NoteService;
import com.onet.tag.helper.TagHelper;

public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteDao noteDao;
	@Autowired
	private TagHelper tagHelper;
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse add(NoteDTO noteDto) {
		Long userid = noteDto.getUserid();
		Assert.notNull(userid, "NoteDTO:userid must not be null");
		
		NotePO notePo = objectConvertManager.toNotePO(noteDto);
		notePo.setCreateTime(new Date());
		notePo.setModifyTime(new Date());
		notePo.setModifier(notePo.getUsername());
		noteDao.insertNote(notePo);
		Long noteId = notePo.getId();
		noteDto.setId(noteId);
		
		tagHelper.handleNewRefer(noteDto.getTagList(), TagType.NOTE, noteId, userid);
		return new BaseResponse(true, noteId.toString(), null);
	}

	@Override
	public IBaseResponse edit(NoteDTO noteDto) {
		Long noteId = noteDto.getId();
		Long userid = noteDto.getUserid();
		Assert.notNull(userid, "NoteDTO:userid must not be null");
		
		NotePO notePo = objectConvertManager.toNotePO(noteDto);
		notePo.setModifyTime(new Date());
		noteDao.updateNote(notePo);// 更新笔记
		
		tagHelper.handleNewRefer(noteDto.getTagList(), TagType.NOTE, noteId, userid);
		return new BaseResponse(true, noteId.toString(), null);
	}

	@Override
	public IBaseResponse deleteNote(Long id, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		tagHelper.removeReferMap(id, TagType.NOTE);// 删除旧的note, tag 映射
		noteDao.deleteNote(id, userid);// delete note
		return new BaseResponse(true);
	}

	/*@Override
	public IBaseResponse clearTrash(Long userid) {
		noteDao.deleteTrashedMap(userid); // 先删除映射关系
		noteDao.clearTrash(userid);		  // 在删除note
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse restoreNote(Long noteId, Long userid) {
		noteDao.updateNoteTrash(userid, noteId, false);
		return new BaseResponse(true);
	}*/
	@Override
	public IBaseResponse starNote(Long noteId, boolean starred, Long operatorId) {
		NotePO notePO = noteDao.queryNotePoById(noteId, operatorId);
		if (null == notePO) {
			return new BaseResponse(false, "权限不足！");
		}
		notePO.setStarred(starred);
		noteDao.updateNote(notePO);
		return new BaseResponse(true);
	}
	@Override
	public CommRes<NoteDTO> queryNote(Long id, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		NotePO notePO = noteDao.queryNotePoById(id, userid);
		NoteDTO noteDTO = objectConvertManager.toNoteDTO(notePO);
		CommRes<NoteDTO> res = new CommRes<NoteDTO>(noteDTO);
		if (null == notePO) {
			res.setRs(0);
			res.setMsg("笔记不存在，id:"+id+", userid:"+userid);
		}
		return res;
	}

	@Override
	public PageRes<NoteDTO> queryNoteList(NoteQueryRequest request) {
		List<NoteDTO> noteDTOs =new ArrayList<NoteDTO>();
		List<NotePO> notePOs = noteDao.queryNoteListByRequest(request);
		for (NotePO notePO : notePOs) {
			NoteDTO noteDTO = objectConvertManager.toNoteDTO(notePO);
			noteDTOs.add(noteDTO);
		}
		int total = noteDao.queryNoteCountByRequest(request);
		PageRes<NoteDTO> res = new PageRes<NoteDTO>();
		res.setList(noteDTOs);
		res.setPageNo(request.getPageNo());
		res.setPageSize(request.getPageSize());
		res.setTotal(total);
		return res;
	}

	@Override
	public int queryNoteCount(NoteQueryRequest request) {
		int total = noteDao.queryNoteCountByRequest(request);
		return total;
	}
}
