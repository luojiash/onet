package com.onet.common.manager;

import com.onet.article.comment.dto.CommentDTO;
import com.onet.article.comment.po.CommentPO;
import com.onet.article.main.dto.ArticleDTO;
import com.onet.article.main.dto.CategoryDTO;
import com.onet.article.main.po.ArticlePO;
import com.onet.article.main.po.CategoryPO;
import com.onet.article.share.dto.ShareDTO;
import com.onet.article.share.po.SharePO;
import com.onet.note.dto.DirDTO;
import com.onet.note.dto.NoteDTO;
import com.onet.note.po.DirPO;
import com.onet.note.po.NotePO;
import com.onet.tag.dto.TagDTO;
import com.onet.tag.po.TagPO;
import com.onet.user.dto.UserDTO;
import com.onet.user.po.UserPO;

public interface ObjectConvertManager {
	public UserDTO toUserDTO(UserPO userPO);
	public UserPO toUserPO(UserDTO userDTO);
	
	
	public NoteDTO toNoteDTO(NotePO notePO);
	public NotePO toNotePO(NoteDTO noteDTO);
	
	public DirDTO toDirDTO(DirPO dirPO);
	public DirPO toDirPO(DirDTO dirDTO);
	
	
	public TagDTO toTagDTO(TagPO tagPO);
	public TagPO toTagPO(TagDTO tagDTO);
	
	
	public SharePO toSharePO(ShareDTO shareDTO);
	public ShareDTO toShareDTO(SharePO sharePO);
	
	public CategoryDTO toCategoryDTO(CategoryPO categoryPO);
	
	public ArticlePO toArticlePO(ArticleDTO articleDTO);
	public ArticleDTO toArticleDTO(ArticlePO articlePO);
	public CommentPO toCommentPO(CommentDTO dto);
	public CommentDTO toCommentDTO(CommentPO po);
}
