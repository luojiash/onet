package com.onet.tag.service;

import java.util.List;

import com.onet.common.constant.TagType;
import com.onet.common.dto.IBaseResponse;
import com.onet.tag.dto.TagDTO;


public interface TagService {
	public IBaseResponse add(TagDTO tagDTO);
	public IBaseResponse edit(TagDTO tagDTO);
	public IBaseResponse deleteTag(Long tagId, Long userid);
	
	public List<TagDTO> queryTagList(Long userid, TagType tagType); 
}
