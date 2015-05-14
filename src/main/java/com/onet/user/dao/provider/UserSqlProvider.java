package com.onet.user.dao.provider;

import org.apache.log4j.Logger;

import com.onet.common.dto.Ordering;
import com.onet.user.dao.UserDao;
import com.onet.user.dto.UserQueryRequest;

public class UserSqlProvider {
	private static Logger logger = Logger.getLogger(UserSqlProvider.class);
	
	public String queryByRequest(UserQueryRequest request) {
		StringBuilder sb = new StringBuilder("select * from "+UserDao.TABLE_SCHEMA+" where 1=1");
		if (null != request) {
			sb.append(getConditionSql(request));
			Ordering ordering = request.getOrdering();
			if (null != ordering.getField() && null != ordering.getSequence()) {
				sb.append(" ORDER BY " + ordering.getField() + " " + ordering.getSequence().getSql());
				//sb.append(" ORDER BY #{ordering.field} #{ordering.sequence.sql}");//排序字段会加上引号，用$, 但会注入
			}
			
			if(request.getPageSize() > 0){
				sb.append(" LIMIT #{indexFrom},#{pageSize}");
			}
		}
		logger.info(sb);
		return sb.toString();
	}
	public String queryCountByRequest(UserQueryRequest request) {
		StringBuilder sb = new StringBuilder("select count(*) from "+UserDao.TABLE_SCHEMA+" where 1=1");
		if (null != request) {
			sb.append(getConditionSql(request));
		}
		logger.info(sb);
		return sb.toString();
	}
	private StringBuilder getConditionSql(UserQueryRequest request) {
		StringBuilder sb = new StringBuilder();
		if (null != request.getSearchWord()) {
			sb.append(" and (");
			sb.append("email like \"%\"#{searchWord}\"%\"");
			sb.append(" or userName like \"%\"#{searchWord}\"%\"");
			sb.append(" or nickName like \"%\"#{searchWord}\"%\"");
			sb.append(")");
		}
		return sb;
	}	
}
