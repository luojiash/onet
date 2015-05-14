package com.onet.article.share.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.onet.article.main.dao.ArticleDao;
import com.onet.article.main.po.ArticlePO;
import com.onet.article.share.dao.ShareDAO;
import com.onet.article.share.dto.ShareDTO;
import com.onet.article.share.po.SharePO;
import com.onet.article.share.service.ShareService;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.manager.ObjectConvertManager;

public class ShareServiceImpl implements ShareService {

	@Autowired
	private ShareDAO shareDAO;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse publishArticle(Long articleId, Long operatorId) {
		SharePO sharePO = new SharePO();
		sharePO.setArticleId(articleId);
		sharePO.setAuthorId(operatorId);
		sharePO.setShareDate(new Date());
		shareDAO.insertShare(sharePO);
		ArticlePO articlePO = articleDao.queryArticle(articleId);
		articlePO.setShared(true);
		articleDao.updateArticle(articlePO);
		String link = "http://www.onet.com/article/share/detail/"+sharePO.getId();
		return new BaseResponse(true, link, null);
	}

	/*@Override
	public IBaseResponse cancelShare(Long shareId, Long operatorId) {
		SharePO po = shareDAO.querySharePO(shareId);
		if (!operatorId.equals(po.getAuthorId())) {
			return new BaseResponse(false, "no permission!");
		}
		ArticlePO articlePO = articleDao.queryArticle(po.getArticleId());
		articlePO.setShared(false);
		articleDao.updateArticle(articlePO);
		shareDAO.deleteShare(shareId);
		return new BaseResponse(true);
	}*/
	
	@Override
	public IBaseResponse cancelShare(Long articleId, Long operatorId) {
		SharePO po = shareDAO.querySharePOByArticleId(articleId);
		if (!operatorId.equals(po.getAuthorId())) {
			return new BaseResponse(false, "no permission!");
		}
		
		ArticlePO articlePO = articleDao.queryArticle(articleId);
		articlePO.setShared(false);
		articleDao.updateArticle(articlePO);
		
		shareDAO.deleteShare(po.getId());
		return new BaseResponse(true);
	}
	
	/*@Override
	public IBaseResponse updateShare(ShareDTO shareDTO) {
		shareDAO.updateShare(objectConvertManager.toSharePO(shareDTO));
		return new BaseResponse(true);
	}*/

	@Override
	public CommRes<ShareDTO> queryShareDTO(Long shareId) {
		SharePO po = shareDAO.querySharePO(shareId);
		if (null == po) {
			return new CommRes<ShareDTO>(0, null, "Sharing is not exist!");
		}
		ShareDTO dto = objectConvertManager.toShareDTO(po);
		//查询分享的文章详情
		ArticlePO articlePO = articleDao.queryArticle(po.getArticleId());
		
		// share article not exists or is trashed
		if (null == articlePO || articlePO.isTrashed()) {
			if (null == articlePO) {
				shareDAO.deleteShare(shareId); //delete outdated share
			}
			return new CommRes<ShareDTO>(0, null, "Sharing is not exist!");
		}
		// update article's readCount
		articlePO.setReadCount(articlePO.getReadCount()+1);
		articleDao.updateArticle(articlePO);
		
		dto.setArticle(objectConvertManager.toArticleDTO(articlePO));
		return new CommRes<ShareDTO>(dto);
	}

	@Override
	public List<ShareDTO> queryMyShare(Long userId) {
		List<ShareDTO> dtos = new ArrayList<ShareDTO>();
		List<SharePO> pos = shareDAO.queryShareList(userId);
		for (SharePO po : pos) {
			ShareDTO dto = objectConvertManager.toShareDTO(po);
			ArticlePO articlePO = articleDao.queryArticle(po.getArticleId());
			if (null == articlePO) {
				shareDAO.deleteShare(po.getId());
			}if (null == articlePO || articlePO.isTrashed()) {
				continue;
			}
			dto.setArticle(objectConvertManager.toArticleDTO(articlePO));
			dtos.add(dto);
		}
		return dtos;
	}

}
