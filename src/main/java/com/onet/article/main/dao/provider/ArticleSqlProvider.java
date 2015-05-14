package com.onet.article.main.dao.provider;

import org.apache.log4j.Logger;

import com.onet.article.main.dao.ArticleDao;
import com.onet.article.main.dto.ArticleQueryRequest;
import com.onet.common.dto.Ordering;
import com.onet.common.util.StringUtil;
import com.onet.tag.dao.TagMapDao;

public class ArticleSqlProvider {
	private static Logger logger = Logger.getLogger(ArticleSqlProvider.class);
	
	public String queryByRequest(ArticleQueryRequest request) {
		StringBuilder sb = new StringBuilder("select * from "+ArticleDao.TABLE_SCHEMA+" where userid=#{userid}");
		if (null != request) {
			sb.append(getConditionSql(request));
			Ordering ordering = request.getOrdering();
			if (null != ordering.getField() && null != ordering.getSequence()) {
				sb.append(" ORDER BY " + ordering.getField() + " " + ordering.getSequence().getSql());
			}
			
			if(request.getPageSize() > 0){
				sb.append(" LIMIT #{indexFrom},#{pageSize}");
			}
		}
		logger.debug(sb);
		return sb.toString();
	}
	public String queryCountByRequest(ArticleQueryRequest request) {
		StringBuilder sb = new StringBuilder("select count(*) from "+ArticleDao.TABLE_SCHEMA+" where userid=#{userid}");
		if (null != request) {
			sb.append(getConditionSql(request));
		}
		logger.debug(sb);
		return sb.toString();
	}
	private StringBuilder getConditionSql(ArticleQueryRequest request) {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotEmpty(request.getKeyword())) {
			sb.append(" and (");
			sb.append("title like \"%\"#{keyword}\"%\"");
			sb.append(" or content like \"%\"#{keyword}\"%\"");
			sb.append(")");
		}
		if (null != request.getCategoryId()) {
			sb.append(" and categoryId = #{categoryId}");
		}if (null != request.getTrashed()) {
			sb.append(" and trashed=#{trashed}");
		}if (null != request.getStarred()) {
			sb.append(" and starred = #{starred}");
		}
		if (null !=request.getTagId()) {
			sb.append(" AND id IN (");
			sb.append("SELECT aid FROM "+TagMapDao.ARTICLE_TAG+" WHERE tid = #{tagId}");
			sb.append(")");
		}

		return sb;
	}	
}
