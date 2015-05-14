package com.onet.article.main.service;

import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;


public interface ArticleService {
	public IBaseResponse add(ArticleDTO articleDTO);
	public IBaseResponse edit(ArticleDTO articleDTO);
	/**
	 * 删除一篇文章
	 * @param toTrash 是否移到回收站
	 */
	public IBaseResponse deleteArticle(Long aid, boolean toTrash, Long operatorId);
	/**
	 * 清空回收站
	 */
	public IBaseResponse clearTrash(Long userid);
	
	public IBaseResponse restoreArticle(Long aid, Long operatorId);
	
	//public IBaseResponse starArticle(Long aid, boolean starred, Long operatorId);
	
	public CommRes<ArticleDTO> queryArticle(Long id, Long userid);
	public PageRes<ArticleDTO> queryArticleList(ArticleQueryRequest request); 
	public int queryArticleCount(ArticleQueryRequest request);
}
