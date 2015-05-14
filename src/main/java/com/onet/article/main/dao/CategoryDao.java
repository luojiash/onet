package com.onet.article.main.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.onet.article.main.po.CategoryPO;

@Repository
public interface CategoryDao {
	
	public static final String TABLE_SCHEMA = "t_article_category";
	public static final String TABLE_FIELD = "id,userid,name";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{userid},#{name}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertCategory(CategoryPO categoryPO);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "name=#{name}"
			+ " where id=#{id}")
	public int updateCategory(CategoryPO categoryPO);
	
	/**
	 * 由categoryId 删除category
	 */
	@Delete("DELETE FROM "+TABLE_SCHEMA+" WHERE id=#{categoryId}")
	public int deleteCategory(Long categoryId);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE id=#{id}")
	public CategoryPO queryCategoryById(Long id);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE name=#{name}")
	public CategoryPO queryCategoryByName(String name);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA +" WHERE userid=#{userid}")
	public List<CategoryPO> queryCategoryList(Long userid);
}
