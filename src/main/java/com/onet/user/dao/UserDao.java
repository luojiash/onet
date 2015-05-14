package com.onet.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import com.onet.user.constant.Role;
import com.onet.user.dao.provider.UserSqlProvider;
import com.onet.user.dto.UserQueryRequest;
import com.onet.user.po.UserPO;

@Repository
public interface UserDao {
	
	public static final String TABLE_SCHEMA = "t_user";
	public static final String TABLE_FIELD = "id,email,userName,nickName,password,role,registerDate,active";

	@Insert("INSERT INTO "+TABLE_SCHEMA+" ("+TABLE_FIELD+") "+"VALUES("+
			"#{id},#{email},#{userName},#{nickName},#{password},#{role},#{registerDate},#{active}"+")")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.PREPARED, statement = "SELECT LAST_INSERT_ID() AS id")
	public int insertUser(UserPO userPO);
	
	@Update("update " + TABLE_SCHEMA + " set "
			+ "email=#{email},nickName=#{nickName},password=#{password},role=#{role},active=#{active}"
			+ " where id=#{id}")
	public int updateUser(UserPO userPO);	
	/**
	 * 更新用户角色
	 */
	@Update("UPDATE "+TABLE_SCHEMA+" SET role=#{role} WHERE id=#{id}")
	public int updateUserRole(@Param("id")Long id, @Param("role")Role role);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where id=#{id}")
	public UserPO queryUserPoById(Long id);
	
	@Select("select "+TABLE_FIELD+" from "+TABLE_SCHEMA+" where userName=#{userName}")
	public UserPO queryUserPoByUserName(String userName);
	
	@SelectProvider(type=UserSqlProvider.class, method="queryByRequest")
	public List<UserPO> queryUserListByRequest(UserQueryRequest request); 
	
	@SelectProvider(type=UserSqlProvider.class, method="queryCountByRequest")
	public int queryUserCountByRequest(UserQueryRequest request);
	
	/*@Select("select count(*) from `${table}`")
	public int test(@Param("table")String table);*/
}
