package com.onet.article.share.service;

import java.util.List;

import com.onet.article.share.dto.ShareDTO;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;

public interface ShareService {
	public IBaseResponse publishArticle(Long articleId, Long operatorId);
	/*public IBaseResponse cancelShare(Long shareId, Long operatorId);*/
	public IBaseResponse cancelShare(Long articleId, Long operatorId);
	
	/*public IBaseResponse updateShare(ShareDTO shareDTO);*/
	
	public CommRes<ShareDTO> queryShareDTO(Long shareId);
	public List<ShareDTO> queryMyShare(Long userId);
}
