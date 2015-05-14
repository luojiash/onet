package com.onet.article.main.service;

import java.util.List;

import com.onet.article.main.dto.CategoryDTO;
import com.onet.common.dto.IBaseResponse;


public interface CategoryService {
	public IBaseResponse add(String cateName, Long userid);
	public IBaseResponse renameCate(Long cid, Long userid, String newName);
	public IBaseResponse deleteCate(Long cid, Long userid);
	
	public List<CategoryDTO> queryCategories(Long userid);
}
