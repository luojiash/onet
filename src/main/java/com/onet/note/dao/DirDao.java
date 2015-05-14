package com.onet.note.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.onet.note.po.DirPO;

@Repository
public interface DirDao {
	
	public static final String TABLE_SCHEMA = "t_note_category";
	public static final String TABLE_FIELD = "id,userId,name,pid,path,depth,leaf";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{userId},#{name},#{pid},#{path},#{depth},#{leaf}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertCategory(DirPO categoryPO);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "name=#{name},leaf=#{leaf}"
			+ " where id=#{id} and userId=#{userId}")
	public int updateCategory(DirPO categoryPO);
	
	/**
	 * 由categoryId 删除category
	 */
	@Delete("DELETE FROM "+TABLE_SCHEMA+" WHERE id=#{dirId} and userId=#{userId}")
	public int deleteCategoryById(@Param("dirId")Long dirId, @Param("userId")Long userId);
	
	/*@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE userId=#{userId}")
	public List<CategoryPO> queryCategoryList(Long userId);*/
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE id=#{id} and userId=#{userId}")
	public DirPO queryCategoryById(@Param("id")Long id, @Param("userId")Long userId);
	/*@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE name=#{name} and userId=#{userId}")
	public CategoryPO queryCategoryByName(@Param("name")String categoryName, @Param("userId")Long userId);*/
	/**
	 * 查询同级目录
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE name=#{cateName} and pid=#{pid} and userId=#{userId}")
	public DirPO querySiblingByName(@Param("cateName")String cateName, @Param("pid")Long pid, @Param("userId")Long userId);
	
	/**
	 * 查询根目录
	 */
	/*@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE pid IS NULL and userId=#{userId}")
	public CategoryPO queryRoot(Long userId);*/
	
	/**
	 * 查询子目录
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE pid=#{pid} and userId=#{userId}")
	public List<DirPO> querySubCate(@Param("pid")Long pid, @Param("userId")Long userId);
}
