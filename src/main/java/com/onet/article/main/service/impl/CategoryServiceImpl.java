package com.onet.article.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.onet.article.main.dao.ArticleDao;
import com.onet.article.main.dao.CategoryDao;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.po.CategoryPO;
import com.onet.article.main.service.CategoryService;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.manager.ObjectConvertManager;

public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse add(String cateName, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		CategoryPO po = categoryDao.queryCategoryByName(cateName);
		if (null != po) {
			return new BaseResponse(false, cateName + " already exists!");
		}
		// 创建新分类
		CategoryPO po2 = new CategoryPO();
		po2.setName(cateName);
		po2.setUserid(userid);
		categoryDao.insertCategory(po2);
		return new BaseResponse(true, po2.getId().toString(), null);
	}

	@Override
	public IBaseResponse renameCate(Long cid, Long userid, String newName) {
		Assert.notNull(userid, "userid must not be null");
		
		CategoryPO po = categoryDao.queryCategoryById(cid);
		if (null == po) {
			return new BaseResponse(false, "category not exists. category id: "+cid);
		} if (!userid.equals(po.getUserid())) {
			return new BaseResponse(false, "no permission! category id: "+cid+", userid: "+userid);
		}
		po.setName(newName);
		categoryDao.updateCategory(po);
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse deleteCate(Long cid, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		articleDao.updateArticleCate(cid, null, userid);
		categoryDao.deleteCategory(cid);
		return new BaseResponse(true);
	}

	@Override
	public List<CategoryDTO> queryCategories(Long userid) {
		List<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
		List<CategoryPO> pos = categoryDao.queryCategoryList(userid);
		for (CategoryPO po : pos) {
			CategoryDTO dto = objectConvertManager.toCategoryDTO(po);
			ArticleQueryRequest request = new ArticleQueryRequest(userid);
			request.setCategoryId(dto.getId());
			int count = articleDao.queryArticleCountByRequest(request);
			dto.setArticleCount(count);
			dtos.add(dto);
		}
		return dtos;
	}
}
