package com.onet.article.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.onet.article.comment.po.CommentPO;

@Repository
public interface CommentDAO {
	public static String TABLE_SCHEMA = "t_article_comment";
	public static String  TABLE_FIELD = "id,pid,articleId,content,commentDate,userid,username,up,replyCount";
	
	@Insert("INSERT INTO " + TABLE_SCHEMA + " (" + TABLE_FIELD + ") VALUES("
			+ "#{id},#{pid},#{articleId},#{content},#{commentDate},#{userid},#{username},#{up},#{replyCount}"
			+ ")")
	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
	public int insert(CommentPO comment);
	
	@Update("UPDATE " + TABLE_SCHEMA + " SET "
			+ ""
			+ "WHERE id = #{id}")
	public int update(CommentPO comment);
	
	@Update("UPDATE " + TABLE_SCHEMA + " SET replyCount = replyCount + #{n} WHERE id = #{id}")
	public int increaseReplyCountByOne(@Param("id")Long id, @Param("n")int n);
	
	@Delete("DELETE FROM " + TABLE_SCHEMA + " WHERE id=#{commentId}")
	public int deleteById(Long commentId);
	
	@Select("SELECT * FROM " + TABLE_SCHEMA + " WHERE id = #{id}")
	public CommentPO queryById(Long id);
	
	@Select("SELECT * FROM " + TABLE_SCHEMA + " WHERE articleId = #{articleId} and pid = #{pid} ORDER BY commentDate DESC")
	public List<CommentPO> queryByArticleId(@Param("articleId")Long articleId, @Param("pid")Long pid);
}
