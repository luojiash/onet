package com.onet.tag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.onet.common.constant.TagType;
import com.onet.tag.po.TagPO;

@Repository
public interface TagDao {
	
	public static final String TABLE_SCHEMA = "t_tag";
	public static final String TABLE_FIELD = "id,userid,name,type";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{userid},#{name},#{type}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertTag(TagPO tagPO);
	
	@Update("UPDATE "+TABLE_SCHEMA+" SET name=#{name} WHERE id=#{id} and userid=#{userid}")
	public int updateTag(TagPO tagPO);
	
	/**
	 * 由tagId 删除tag
	 */
	@Delete("DELETE FROM "+TABLE_SCHEMA+" WHERE id=#{tagId}")
	public int deleteTag(Long tagId);
	
	/**
	 * 由tagId 查询tag
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where id=#{tagId} and userid=#{userid}")
	public TagPO queryTagById(@Param("tagId")Long tagId, @Param("userid")Long userid);
	
	/**
	 * 由tag name 查询tag
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where userid=#{userid} and name=#{tagName}")
	public TagPO queryTagByName(@Param("tagName")String tagName, @Param("userid")Long userid);

	/**
	 * 查询一个User 的tag
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where userid=#{userid} and type=#{type}")
	public List<TagPO> queryTagList(@Param("userid")Long userid, @Param("type")TagType type);
}
