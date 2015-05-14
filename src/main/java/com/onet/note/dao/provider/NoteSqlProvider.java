package com.onet.note.dao.provider;

import org.apache.log4j.Logger;

import com.onet.common.dto.Ordering;
import com.onet.note.dao.NoteDao;
import com.onet.note.dto.NoteQueryRequest;
import com.onet.tag.dao.TagMapDao;

public class NoteSqlProvider {
	private static Logger logger = Logger.getLogger(NoteSqlProvider.class);
	
	public String queryByRequest(NoteQueryRequest request) {
		StringBuilder sb = new StringBuilder("select * from "+NoteDao.TABLE_SCHEMA+" where userid=#{userid}");
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
	public String queryCountByRequest(NoteQueryRequest request) {
		StringBuilder sb = new StringBuilder("select count(*) from "+NoteDao.TABLE_SCHEMA+" where userid=#{userid}");
		if (null != request) {
			sb.append(getConditionSql(request));
		}
		logger.debug(sb);
		return sb.toString();
	}
	private StringBuilder getConditionSql(NoteQueryRequest request) {
		StringBuilder sb = new StringBuilder();
		if (null != request.getSearchWord()) {
			sb.append(" and (");
			sb.append("title like \"%\"#{searchWord}\"%\"");
			sb.append(" or content like \"%\"#{searchWord}\"%\"");
			sb.append(")");
		}
		if (null != request.getDirId()) {
			sb.append(" and dirId = #{dirId}");
		}if (null != request.getTrashed()) {
			sb.append(" and trashed=#{trashed}");
		}if (null != request.getStarred()) {
			sb.append(" and starred = #{starred}");
		}
		if (null !=request.getTagIdList()&&request.getTagIdList().size()>0 ) {
			for (Long tagId : request.getTagIdList()) {
				sb.append(" AND id IN (");
				sb.append("SELECT nid FROM "+TagMapDao.NOTE_TAG+" WHERE tid ="+tagId);
				sb.append(")");
			}
		}

		return sb;
	}	
}
