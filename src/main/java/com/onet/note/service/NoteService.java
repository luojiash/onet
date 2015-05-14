package com.onet.note.service;

import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.note.dto.NoteDTO;
import com.onet.note.dto.NoteQueryRequest;


public interface NoteService {
	public IBaseResponse add(NoteDTO noteDto);
	public IBaseResponse edit(NoteDTO noteDto);
	/**
	 * 删除一篇笔记
	 * @param toTrash 是否移到回收站
	 */
	public IBaseResponse deleteNote(Long id, Long userId);
	/**
	 * 清空回收站
	 */
	/*public IBaseResponse clearTrash(Long userId);
	public IBaseResponse restoreNote(Long noteId, Long userId);*/
	public IBaseResponse starNote(Long noteId, boolean starred, Long operatorId);
	
	public CommRes<NoteDTO> queryNote(Long id, Long userId);
	public PageRes<NoteDTO> queryNoteList(NoteQueryRequest request); 
	public int queryNoteCount(NoteQueryRequest request);
}
