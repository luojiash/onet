package com.onet.tag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.onet.article.main.dao.ArticleDao;
import com.onet.tag.dao.TagDao;
import com.onet.tag.po.TagPO;

@Repository
public interface TagMapDao { 	
	// --------------------note, tag关系表操作------------------------------
	public static final String NOTE_TAG = "t_note_tag_map";
	
	@Insert("INSERT INTO "+NOTE_TAG+" (nid, tid) "+"VALUES(#{nid},#{tid})")
	public int insertNoteTag(@Param("nid")Long noteId, @Param("tid")Long tagId);
	
	@Delete("delete from "+NOTE_TAG+" where nid=#{nid}")
	public int deleteNoteTagByNoteId(@Param("nid")Long noteId);
	@Delete("delete from "+NOTE_TAG+" where tid=#{tid}")
	public int deleteNoteTagByTagId(@Param("tid")Long tagId);
	
	@Select("SELECT * FROM "+TagDao.TABLE_SCHEMA+" WHERE id IN (SELECT tid FROM "+NOTE_TAG+" WHERE nid=#{nid})")
	public List<TagPO> queryTagListByNoteId(Long nid);
	
	@Select("SELECT COUNT(*) FROM "+NOTE_TAG+" WHERE tid=#{tid}")
	public int queryNoteReferCount(Long tid);
	
	// --------------------article, tag关系表操作------------------------------
	public static final String ARTICLE_TAG = "t_article_tag_map";
	
	@Insert("INSERT INTO "+ARTICLE_TAG+" (aid, tid) "+"VALUES(#{aid},#{tid})")
	public int insertArticleTag(@Param("aid")Long articleId, @Param("tid")Long tagId);
	
	@Delete("delete from "+ARTICLE_TAG+" where aid=#{aid}")
	public int deleteArticleTagByArticleId(@Param("aid")Long articleId);
	@Delete("delete from "+ARTICLE_TAG+" where tid=#{tid}")
	public int deleteArticleTagByTagId(@Param("tid")Long tagId);
	
	/**
	 * 清空回收站时删除article, tag 映射
	 */
	@Delete("delete from "+ARTICLE_TAG+" where aid in (SELECT id FROM "+ArticleDao.TABLE_SCHEMA+" WHERE userid=#{userid} and trashed=true)")
	public int deleteArticleTrashTag(Long userid);
	
	@Select("SELECT * FROM "+TagDao.TABLE_SCHEMA+" WHERE id IN (SELECT tid FROM "+ARTICLE_TAG+" WHERE aid=#{aid})")
	public List<TagPO> queryTagListByArticleId(Long aid);
	
	@Select("SELECT COUNT(*) FROM "+ARTICLE_TAG+" WHERE tid=#{tid}")
	public int queryArticleReferCount(Long tid);
}
