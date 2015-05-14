package com.onet.article.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.onet.article.main.dao.ArticleDao;
import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.article.main.po.ArticlePO;
import com.onet.article.main.service.ArticleService;
import com.onet.common.constant.TagType;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.tag.helper.TagHelper;

public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private TagHelper tagHelper;
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse add(ArticleDTO articleDTO) {
		Long userid = articleDTO.getUserid();
		Assert.notNull(userid, "ArticleDTO:userid must not be null");
		
		ArticlePO po = objectConvertManager.toArticlePO(articleDTO);
		po.setCreateTime(new Date());
		po.setModifyTime(new Date());
		articleDao.insertArticle(po);

		// set id to dto after insert
		Long aid = po.getId();
		articleDTO.setId(aid);
		
		tagHelper.handleNewRefer(articleDTO.getTagList(), TagType.ARTICLE, aid, userid);
		return new BaseResponse(true, aid.toString(), null);
	}

	@Override
	public IBaseResponse edit(ArticleDTO articleDTO) {
		Long aid = articleDTO.getId();
		Long userid = articleDTO.getUserid();
		Assert.notNull(userid, "ArticleDTO:userid must not be null");
		
		ArticlePO po = objectConvertManager.toArticlePO(articleDTO);
		po.setModifyTime(new Date());
		articleDao.updateArticle(po);
		
		tagHelper.handleNewRefer(articleDTO.getTagList(), TagType.ARTICLE, aid, userid);
		return new BaseResponse(true, aid.toString(), null);
	}

	@Override
	public IBaseResponse deleteArticle(Long aid, boolean toTrash, Long operatorId) {
		Assert.notNull(operatorId, "operatorId must not be null");

		ArticlePO articlePO = articleDao.queryArticle(aid);
		if (!operatorId.equals(articlePO.getUserid())) {
			return new BaseResponse(false, "fail, no permission!");
		}
		if (toTrash) {
			// update trashed property
			articlePO.setTrashed(true);
			articleDao.updateArticle(articlePO);
		}else {
			tagHelper.removeReferMap(aid, TagType.ARTICLE);
			articleDao.deleteArticle(aid);
		}
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse clearTrash(Long userid) {
		tagHelper.clearTrash(userid);
		articleDao.clearTrash(userid);
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse restoreArticle(Long aid, Long operatorId) {
		ArticlePO articlePO = articleDao.queryArticle(aid);
		if (!operatorId.equals(articlePO.getUserid())) {
			return new BaseResponse(false, "fail, no permission!");
		}
		articlePO.setTrashed(false);
		articleDao.updateArticle(articlePO);
		return new BaseResponse(true);
	}

	/*@Override
	public IBaseResponse starArticle(Long aid, boolean starred,	Long operatorId) {
		ArticlePO po = articleDao.queryArticle(aid);
		if (!operatorId.equals(po.getUserid())) {
			return new BaseResponse(false, "fail, no permission!");
		}
		po.setStarred(starred);
		articleDao.updateArticle(po);
		return new BaseResponse(true);
	}*/
	
	@Override
	public CommRes<ArticleDTO> queryArticle(Long id, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		ArticlePO po = articleDao.queryArticle(id);
		if (null == po) {
			return new CommRes<ArticleDTO>(0, null, "笔记不存在，id:"+id+", userid:"+userid);
		} if (!userid.equals(po.getUserid())) {
			return new CommRes<ArticleDTO>(0, null, "fail, no permission!");
		}
		ArticleDTO dto = objectConvertManager.toArticleDTO(po);
		CommRes<ArticleDTO> res = new CommRes<ArticleDTO>(dto);
		return res;
	}

	@Override
	public PageRes<ArticleDTO> queryArticleList(ArticleQueryRequest request) {
		List<ArticleDTO> dtos =new ArrayList<ArticleDTO>();
		List<ArticlePO> pos = articleDao.queryArticleListByRequest(request);
		for (ArticlePO po : pos) {
			ArticleDTO dto = objectConvertManager.toArticleDTO(po);
			dtos.add(dto);
		}
		int total = articleDao.queryArticleCountByRequest(request);
		PageRes<ArticleDTO> res = new PageRes<ArticleDTO>();
		res.setList(dtos);
		res.setPageNo(request.getPageNo());
		res.setPageSize(request.getPageSize());
		res.setTotal(total);
		return res;
	}

	@Override
	public int queryArticleCount(ArticleQueryRequest request) {
		int total = articleDao.queryArticleCountByRequest(request);
		return total;
	}
}
