package com.onet.note.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.onet.common.dto.IBaseResponse;
import com.onet.note.dto.DirDTO;


public interface DirService {
	/**
	 * 添加根目录，其pid 为-1L
	 */
	public IBaseResponse addRootCategory(Long userId);
	public IBaseResponse add(String cateName, Long pid, Long userId);
	
	/**
	 * 叶子目录才能重命名
	 */
	public IBaseResponse renameCate(Long cid, Long userId, String newName);
	
	/**
	 * 叶子目录才能删除
	 */
	@Transactional
	public IBaseResponse deleteCategory(Long categoryId, Long userId);
	
	public DirDTO queryDir(Long dirId, Long userid);
	
	/**
	 * 查询用户的目录结构
	 */
	public List<DirDTO> queryCategories(Long userId);
	public List<DirDTO> querySubCates(Long pid, Long userId);
}
