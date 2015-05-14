package com.onet.common.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onet.article.comment.dto.CommentDTO;
import com.onet.article.comment.po.CommentPO;
import com.onet.article.main.dao.CategoryDao;
import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.po.ArticlePO;
import com.onet.article.main.po.CategoryPO;
import com.onet.article.share.dto.ShareDTO;
import com.onet.article.share.po.SharePO;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.note.dao.DirDao;
import com.onet.note.dto.DirDTO;
import com.onet.note.dto.NoteDTO;
import com.onet.note.po.DirPO;
import com.onet.note.po.NotePO;
import com.onet.tag.dao.TagMapDao;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.po.TagPO;
import com.onet.user.dto.UserDTO;
import com.onet.user.po.UserPO;

@Service("objectConvertManager")
public class ObjectConvertManagerImpl implements ObjectConvertManager {
	@Autowired
	private DirDao dirDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private TagMapDao tagMapDao;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public UserDTO toUserDTO(UserPO po) {
		UserDTO dto = new UserDTO();
		if (null != po) {
			BeanUtils.copyProperties(po, dto);
			if (null != po.getRegisterDate()) {
				dto.setRegisterDate(dateFormat.format(po.getRegisterDate()));
			}
		}
		return dto;
	}

	@Override
	public UserPO toUserPO(UserDTO dto) {
		UserPO po = new UserPO();
		if (null != dto) {
			BeanUtils.copyProperties(dto, po);
		}
		return po;
	}

	@Override
	public NoteDTO toNoteDTO(NotePO notePo) {
		NoteDTO noteDto = new NoteDTO();
		if (null != notePo) {
			BeanUtils.copyProperties(notePo, noteDto);
			// 格式化时间
			if (null != notePo.getCreateTime()) {
				noteDto.setCreateTime(dateFormat.format(notePo.getCreateTime()));
			}
			if (null != notePo.getModifyTime()) {
				noteDto.setModifyTime(dateFormat.format(notePo.getModifyTime()));
			}
			// 查询tag
			List<TagDTO> tags = new ArrayList<TagDTO>();
			List<TagPO> tagPOs = tagMapDao.queryTagListByNoteId(noteDto.getId());
			for (TagPO tagPO : tagPOs) {
				TagDTO tagDTO = toTagDTO(tagPO);
				tags.add(tagDTO);
			}
			noteDto.setTags(tags);
			// 查询category
			DirPO categoryPO = dirDao.queryCategoryById(notePo.getDirId(), notePo.getUserid());
			DirDTO categoryDTO = toDirDTO(categoryPO);
			noteDto.setCategory(categoryDTO);
		}
		return noteDto;
	}

	@Override
	public NotePO toNotePO(NoteDTO noteDto) {
		NotePO notePo = new NotePO();
		if (null != noteDto) {
			BeanUtils.copyProperties(noteDto, notePo);
		}
		return notePo;
	}

	@Override
	public DirDTO toDirDTO(DirPO categoryPO) {
		if (null == categoryPO) {
			return null;
		}
		DirDTO categoryDTO = new DirDTO();
		BeanUtils.copyProperties(categoryPO, categoryDTO);
		return categoryDTO;
	}

	@Override
	public DirPO toDirPO(DirDTO category) {
		DirPO categoryPO = new DirPO();
		if (null == category) {
			BeanUtils.copyProperties(category, categoryPO);
		}
		return categoryPO;
	}

	@Override
	public TagDTO toTagDTO(TagPO tagPO) {
		if (null == tagPO) {
			return null;
		}
		TagDTO tagDTO = new TagDTO();
		BeanUtils.copyProperties(tagPO, tagDTO);
		return tagDTO;
	}

	@Override
	public TagPO toTagPO(TagDTO tagDTO) {
		if (null == tagDTO) {
			return null;
		}
		TagPO tagPO = new TagPO();
		BeanUtils.copyProperties(tagDTO, tagPO);
		return tagPO;
	}

	@Override
	public ShareDTO toShareDTO(SharePO sharePO) {
		if (null == sharePO) {
			return null;
		}
		ShareDTO dto = new ShareDTO();
		BeanUtils.copyProperties(sharePO, dto);
		return dto;
	}

	@Override
	public SharePO toSharePO(ShareDTO shareDTO) {
		if (null == shareDTO) {
			return null;	
		}
		SharePO po = new SharePO();
		BeanUtils.copyProperties(shareDTO, po);
		return po;
	}

	@Override
	public CategoryDTO toCategoryDTO(CategoryPO categoryPO) {
		if (null == categoryPO) {
			return null;
		}
		CategoryDTO dto = new CategoryDTO();
		BeanUtils.copyProperties(categoryPO, dto);
		return dto;
	}

	@Override
	public ArticlePO toArticlePO(ArticleDTO articleDTO) {
		if (null == articleDTO) {
			return null;
		}
		ArticlePO po = new ArticlePO();
		BeanUtils.copyProperties(articleDTO, po);
		if (null != articleDTO.getCategory()) {
			po.setCategoryId(articleDTO.getCategory().getId());	
		}
		return po;
	}

	@Override
	public ArticleDTO toArticleDTO(ArticlePO articlePO) {
		if (null == articlePO) {
			return null;
		}
		ArticleDTO dto = new ArticleDTO();
		BeanUtils.copyProperties(articlePO, dto);
		// query category
		CategoryPO categoryPO = categoryDao.queryCategoryById(articlePO.getCategoryId());
		dto.setCategory(toCategoryDTO(categoryPO));
		// query tags
		List<TagDTO> tags = new ArrayList<TagDTO>();
		List<TagPO> tagPOs = tagMapDao.queryTagListByArticleId(dto.getId());
		for (TagPO tagPO : tagPOs) {
			TagDTO tagDTO = toTagDTO(tagPO);
			tags.add(tagDTO);
		}
		dto.setTags(tags);
		return dto;
	}

	@Override
	public CommentPO toCommentPO(CommentDTO dto) {
		if (null == dto) {
			return null;
		}
		CommentPO po = new CommentPO();
		BeanUtils.copyProperties(dto, po);
		return po;
	}

	@Override
	public CommentDTO toCommentDTO(CommentPO po) {
		if (null == po) {
			return null;
		}
		CommentDTO dto = new CommentDTO();
		BeanUtils.copyProperties(po, dto);
		return dto;
	}
}
