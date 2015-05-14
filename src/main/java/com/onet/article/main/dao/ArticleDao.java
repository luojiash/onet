package com.onet.article.main.dao;

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

import com.onet.article.main.dao.provider.ArticleSqlProvider;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.article.main.po.ArticlePO;

@Repository
public interface ArticleDao { 
	public static final String TABLE_SCHEMA = "t_article";
	public static final String TABLE_FIELD = "id,userid,username,title,content,categoryId,createTime,modifier,modifyTime,status,starred,trashed,shared,readCount";
	
	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") VALUES("+
			"#{id},#{userid},#{username},#{title},#{content},#{categoryId},#{createTime},#{modifier},#{modifyTime},#{status},#{starred},#{trashed},#{shared},#{readCount}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertArticle(ArticlePO articlePO);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "title=#{title},content=#{content},categoryId=#{categoryId},modifier=#{modifier},modifyTime=#{modifyTime},status=#{status},starred=#{starred},trashed=#{trashed},shared=#{shared},readCount=#{readCount}"
			+ " where id=#{id} and userid=#{userid}")
	public int updateArticle(ArticlePO articlePO);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "categoryId=#{newCid}"
			+ " where categoryId=#{cid} and userid=#{userid}")
	public int updateArticleCate(@Param("cid")Long cid, @Param("newCid")Long newCid, @Param("userid")Long userid);
	
	@Delete("delete from "+TABLE_SCHEMA+" where id=#{id}")
	public int deleteArticle(Long id);
	
	@Delete("delete from "+TABLE_SCHEMA+" where userid=#{userid} and trashed=true")
	public int clearTrash(Long userid);
	
	/**
	 * 通过id查询笔记
	 */
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where id=#{id}")
	public ArticlePO queryArticle(Long id);
	
	@SelectProvider(type=ArticleSqlProvider.class, method="queryByRequest")
	public List<ArticlePO> queryArticleListByRequest(ArticleQueryRequest request); 
	
	@SelectProvider(type=ArticleSqlProvider.class, method="queryCountByRequest")
	public int queryArticleCountByRequest(ArticleQueryRequest request);
	
	/*// --------------------article, tag关系表操作------------------------------
	public static final String TAG_MAP = "t_article_tag_map";
	
	@Insert("INSERT INTO "+TAG_MAP+" (aid, tid) "+"VALUES(#{aid},#{tid})")
	public int insertTagMap(@Param("aid")Long articleId, @Param("tid")Long tagId);
	@Delete("delete from "+TAG_MAP+" where aid=#{aid}")
	public int deleteTagMapByArticleId(@Param("aid")Long articleId);
	@Delete("delete from "+TAG_MAP+" where tid=#{tid}")
	public int deleteTagMapByTagId(@Param("tid")Long tagId);
	
	*//**
	 * 清空回收站时删除article, tag 映射
	 *//*
	@Delete("delete from "+TAG_MAP+" where aid in (SELECT id FROM "+TABLE_SCHEMA+" WHERE userid=#{userid} and trashed=true)")
	public int deleteTrashedMap(Long userid);
	
	@Select("SELECT * FROM "+TagDao.TABLE_SCHEMA+" WHERE id IN (SELECT tid FROM "+TAG_MAP+" WHERE aid=#{aid})")
	public List<TagPO> queryTagListByAid(Long aid);*/
}
