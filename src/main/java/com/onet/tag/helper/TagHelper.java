package com.onet.tag.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onet.common.constant.TagType;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.tag.dao.TagDao;
import com.onet.tag.dao.TagMapDao;
import com.onet.tag.po.TagPO;

@Service
public class TagHelper {
	@Autowired
	private TagDao tagDao;
	@Autowired
	private TagMapDao tagMapDao;
	
	public IBaseResponse handleNewRefer(List<String> tagList, TagType type,	Long referId, Long userid) {
		switch (type) {
		case NOTE:
			tagMapDao.deleteNoteTagByNoteId(referId);
			break;
		case ARTICLE:
			// delete outdated tag map
			tagMapDao.deleteArticleTagByArticleId(referId);
			break;	
		default:
			break;
		}
		
		
		if (null != tagList) {
			for (String tag : tagList) {
				TagPO tagPO = tagDao.queryTagByName(tag, userid);
				if (null == tagPO) {// tag 不存在，插入新的tag
					tagPO = new TagPO(userid, tag, type);
					tagDao.insertTag(tagPO);
				}
				switch (type) {
				case NOTE:
					tagMapDao.insertNoteTag(referId, tagPO.getId());
					break;
				case ARTICLE:
					tagMapDao.insertArticleTag(referId, tagPO.getId());
					break;
				default:
					break;
				}
			}
		}
		return new BaseResponse(true);
	}
	
	public IBaseResponse removeReferMap(Long referId, TagType type) {
		switch (type) {
		case NOTE:
			tagMapDao.deleteNoteTagByNoteId(referId);
			break;
		case ARTICLE:
			tagMapDao.deleteArticleTagByArticleId(referId);
			break;
		default:
			break;
		}
		return new BaseResponse(true);
	}
	
	public IBaseResponse clearTrash(Long userid) {
		tagMapDao.deleteArticleTrashTag(userid);
		return new BaseResponse(true);
	}
}
