package com.onet.note.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.onet.note.dao.provider.NoteSqlProvider;
import com.onet.note.dto.NoteQueryRequest;
import com.onet.note.po.NotePO;

@Repository
public interface NoteDao { 
	public static final String TABLE_SCHEMA = "t_note";
	public static final String TABLE_FIELD = "id,userid,username,title,content,dirId,createTime,modifier,modifyTime,status,starred,trashed,shared";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{userid},#{username},#{title},#{content},#{dirId},#{createTime},#{modifier},#{modifyTime},#{status},#{starred},#{trashed},#{shared}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertNote(NotePO notePo);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "title=#{title},content=#{content},dirId=#{dirId},modifier=#{modifier},modifyTime=#{modifyTime},status=#{status},starred=#{starred},trashed=#{trashed},shared=#{shared}"
			+ " where id=#{id} and userid=#{userid}")
	public int updateNote(NotePO notePo);
	
	/*@Update("update " + TABLE_SCHEMA + " set trashed=#{trashed} where id=#{id} and userid=#{userid}")
	public int updateNoteTrash(@Param("userid")Long userid, @Param("id")Long id, @Param("trashed")boolean trashed);*/
	
	/**
	 * 更新note 的category
	 */
	@Update("update " + TABLE_SCHEMA + " set dirId=#{newCategoryId} where userid=#{userid} and dirId=#{dirId}")
	public int updateNoteCategory(@Param("userid")Long userid, @Param("dirId")Long dirId, @Param("newCategoryId")Long newCategoryId);
	
	@Delete("delete from "+TABLE_SCHEMA+" where id=#{id} and userid=#{userid}")
	public int deleteNote(@Param("id")Long id, @Param("userid")Long userid);
	
	@Delete("delete from "+TABLE_SCHEMA+" where userid=#{userid} and trashed=true")
	public int clearTrash(Long userid);
	
	/**
	 * 通过id查询笔记
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where userid=#{userid} and id=#{id}")
	public NotePO queryNotePoById(@Param("id")Long id, @Param("userid")Long userid);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where id in (${ids}) and userid=#{userid}")
	public List<NotePO> queryNoteList(@Param("userid")Long userid, @Param("ids")String ids);
	/*@Select("select count(*) from "+TABLE_SCHEMA+" where userid=#{userid} and categoryName=#{categoryName}")
	public int queryNoteCountByCategory(@Param("userid")Long userid, @Param("categoryName")String categoryName);*/
	
	@SelectProvider(type=NoteSqlProvider.class, method="queryByRequest")
	public List<NotePO> queryNoteListByRequest(NoteQueryRequest request); 
	
	@SelectProvider(type=NoteSqlProvider.class, method="queryCountByRequest")
	public int queryNoteCountByRequest(NoteQueryRequest request);
	
	// --------------------note, tag关系表操作------------------------------
	/*public static final String NOTE_TAG_MAP = "t_note_tag_map";
	
	@Insert("INSERT INTO "+NOTE_TAG_MAP+" (nid, tid) "+"VALUES(#{nid},#{tid})")
	public int insertNoteTagMap(@Param("nid")Long noteId, @Param("tid")Long tagId);
	@Delete("delete from "+NOTE_TAG_MAP+" where nid=#{nid}")
	public int deleteNoteTagByNoteId(@Param("nid")Long noteId);
	@Delete("delete from "+NOTE_TAG_MAP+" where tid=#{tid}")
	public int deleteNoteTagByTagId(@Param("tid")Long tagId);
	
	*//**
	 * 清空回收站时删除note, tag 映射
	 *//*
	@Delete("delete from "+NOTE_TAG_MAP+" where nid in (SELECT id FROM "+TABLE_SCHEMA+" WHERE userid=#{userid} and trashed=true)")
	public int deleteTrashedMap(Long userid);
	
	@Select("SELECT * FROM "+TagDao.TABLE_SCHEMA+" WHERE id IN (SELECT tid FROM "+NOTE_TAG_MAP+" WHERE nid=#{nid})")
	public List<TagPO> queryTagListByNid(Long nid);*/
}
