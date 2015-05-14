package com.onet.article.share.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.onet.article.share.po.SharePO;

@Repository
public interface ShareDAO {
	public static String TABLE_SCHEMA = "t_article_share";
	public static String TABLE_FIELD = "id,code,articleId,authorId,shareDate,scope,operations";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{code},#{articleId},#{authorId},#{shareDate},#{scope},#{operations}"+")")
	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
	public int insertShare(SharePO sharePO);
	
	/*@Update("UPDATE "+TABLE_SCHEMA+" SET "
			+ "visitCount=#{visitCount}"
			+ " WHERE id=#{id}")
	public int updateShare(SharePO sharePO);*/
	
	@Delete("DELETE FROM "+TABLE_SCHEMA+" WHERE id=#{id}")
	public int deleteShare(Long id);
	
	@Select("SELECT * FROM "+TABLE_SCHEMA+" WHERE id=#{shareId}")
	public SharePO querySharePO(Long shareId);
	
	@Select("SELECT * FROM "+TABLE_SCHEMA+" WHERE articleId=#{articleId}")
	public SharePO querySharePOByArticleId(Long articleId);
	
	@Select("SELECT * FROM "+TABLE_SCHEMA+" WHERE authorId=#{authorId}")
	public List<SharePO> queryShareList(Long authorId);
}
