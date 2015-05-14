package com.onet.tag.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.onet.common.constant.TagType;
import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.tag.dao.TagDao;
import com.onet.tag.dao.TagMapDao;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.po.TagPO;
import com.onet.tag.service.TagService;

public class TagServiceImpl implements TagService {

	@Autowired
	private TagDao tagDao;
	@Autowired
	private TagMapDao tagMapDao; 
	@Autowired
	private ObjectConvertManager objectConvertManager;
	
	@Override
	public IBaseResponse add(TagDTO tagDTO) {
		Assert.notNull(tagDTO.getUserid(), "TagDTO:userid must not be null");
		TagPO tagPO = objectConvertManager.toTagPO(tagDTO);
		tagDao.insertTag(tagPO);
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse edit(TagDTO tagDTO) {
		Assert.notNull(tagDTO.getUserid(), "TagDTO:userid must not be null");
		
		TagPO tagPO = objectConvertManager.toTagPO(tagDTO);
		tagDao.updateTag(tagPO);
		return new BaseResponse(true);
	}

	@Override
	public IBaseResponse deleteTag(Long tagId, Long userid) {
		Assert.notNull(userid, "userid must not be null");
		
		TagPO po = tagDao.queryTagById(tagId, userid);
		if (null == po) {
			return new BaseResponse(false, "tag not exists. tagId: "+tagId);
		} if (!userid.equals(po.getUserid())) {
			return new BaseResponse(false, "no permission!");
		}
		switch (po.getType()) {
		case NOTE:
			tagMapDao.deleteNoteTagByTagId(tagId);// 删除tag 和note 的关联
			break;
		case ARTICLE:
			tagMapDao.deleteArticleTagByTagId(tagId);
			break;
		default:
			break;
		}
		
		tagDao.deleteTag(tagId); // 删除tag
		return new BaseResponse(true);
	}

	@Override
	public List<TagDTO> queryTagList(Long userid, TagType tagType) {
		Assert.notNull(userid, "userid must not be null");
		
		List<TagDTO> tagDTOs = new ArrayList<TagDTO>();
		List<TagPO> tagPOs = tagDao.queryTagList(userid, tagType);
		for (TagPO tagPO : tagPOs) {
			TagDTO tagDTO = objectConvertManager.toTagDTO(tagPO);
			switch (tagType) {
			case NOTE:
				tagDTO.setReferCount(tagMapDao.queryNoteReferCount(tagPO.getId()));
				break;
			case ARTICLE:
				tagDTO.setReferCount(tagMapDao.queryArticleReferCount(tagPO.getId()));
				break;
			default:
				break;
			}
			tagDTOs.add(tagDTO);
		}
		Collections.sort(tagDTOs, new Comparator<TagDTO>() {
			@Override
			public int compare(TagDTO o1, TagDTO o2) {
				return Integer.compare(o2.getReferCount(), o1.getReferCount());
			}
		});
		return tagDTOs;
	}
}
