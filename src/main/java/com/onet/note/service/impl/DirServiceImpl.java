package com.onet.note.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.onet.common.constant.CommConst;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.note.dao.DirDao;
import com.onet.note.dao.NoteDao;
import com.onet.note.dto.DirDTO;
import com.onet.note.dto.NoteQueryRequest;
import com.onet.note.po.DirPO;
import com.onet.note.service.DirService;

public class DirServiceImpl implements DirService {

	@Autowired
	private ObjectConvertManager objectConvertManager;
	@Autowired
	private DirDao dirDao;
	@Autowired
	private NoteDao noteDao;
	
	@Override
	public IBaseResponse addRootCategory(Long userid){
		DirPO po = new DirPO();
		po.setUserId(userid);
		po.setDepth(0);
		po.setLeaf(true);
		po.setName("root");
		po.setPath("/");
		po.setPid(CommConst.ROOT_DIR_PID);
		dirDao.insertCategory(po);
		return new BaseResponse(true);
	}
	
	@Override
	public IBaseResponse add(String cateName, Long pid, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		DirPO cPO = dirDao.querySiblingByName(cateName, pid, userid);
		if (null != cPO) {
			return new BaseResponse(false, "该目录已存在！");
		}
		DirPO parent = dirDao.queryCategoryById(pid, userid);
		if (null == parent) {
			return new BaseResponse(false, "失败！父目录不存在。");
		}
		// 创建新目录
		DirPO po = new DirPO();
		po.setName(cateName);
		po.setPid(pid);
		po.setUserId(userid);
		po.setDepth(parent.getDepth() + 1);
		po.setLeaf(true);
		po.setPath(parent.getPath() + "/" + parent.getName());
		dirDao.insertCategory(po);
		// 更新父目录信息
		if (parent.isLeaf()) { 
			parent.setLeaf(false);
			dirDao.updateCategory(parent);
		}
		return new BaseResponse(true, po.getId().toString(), null);
	}

	@Override
	public IBaseResponse renameCate(Long cid, Long userid, String newName) {
		Assert.notNull(userid, "userid must not be null");
		
		DirPO categoryPO = dirDao.queryCategoryById(cid, userid);
		if (null == categoryPO) {
			return new BaseResponse(false, "目录不存在：cid: "+cid);
		}
		categoryPO.setName(newName);
		dirDao.updateCategory(categoryPO);
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse deleteCategory(Long categoryId, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		DirPO po = dirDao.queryCategoryById(categoryId, userid);
		if (po.getDepth() == 0 || !po.isLeaf()) {
			return new BaseResponse(false, "非叶子目录不能删除");
		}
		Long pid = po.getPid();
		// 将该目录里的笔记移到父目录
		noteDao.updateNoteCategory(userid, categoryId, pid);
		dirDao.deleteCategoryById(categoryId, userid);
		// 更新父节点信息
		List<DirPO> pos = dirDao.querySubCate(pid, userid);
		if (pos.size() == 0) {
			DirPO parent = dirDao.queryCategoryById(pid, userid);
			parent.setLeaf(true);
			dirDao.updateCategory(parent);
		}
		return new BaseResponse(true);
	}

	@Override
	public DirDTO queryDir(Long dirId, Long userid) {
		DirPO po = dirDao.queryCategoryById(dirId, userid);
		return objectConvertManager.toDirDTO(po);
	}

	private List<DirDTO> queryCates(Long pid, Long userid, boolean recuesive) {
		List<DirDTO> dtos = new ArrayList<DirDTO>();
		List<DirPO> pos = dirDao.querySubCate(pid, userid);
		for (DirPO categoryPO : pos) {
			DirDTO dto = objectConvertManager.toDirDTO(categoryPO);
			NoteQueryRequest request = new NoteQueryRequest(userid);
			request.setDirId(dto.getId());
			int noteCount = noteDao.queryNoteCountByRequest(request);
			dto.setNoteCount(noteCount);
			// 递归查询子目录
			if (recuesive && !dto.isLeaf()) {
				dto.setChildren(queryCates(dto.getId(), userid, recuesive));
			}
			dtos.add(dto);
		}
		return dtos;
	}
	
	@Override
	public List<DirDTO> queryCategories(Long userid) {
		Assert.notNull(userid, "userid must not be null");

		return queryCates(CommConst.ROOT_DIR_PID, userid, true);
	}
	@Override
	public List<DirDTO> querySubCates(Long pid, Long userid) {
		return queryCates(pid, userid, false);
	}
}
