package com.onet.user.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.onet.user.po.SettingPO;

@Repository
public interface UserSettingDAO {
	public static final String TABLE_SCHEMA = "t_user_setting";
	public static final String TABLE_FIELD = "id,userid,mdEnabled";
	
	@Insert("INSERT INTO " + TABLE_SCHEMA + " (" + TABLE_FIELD + ") VALUES("
			+ "#{id},#{userid},#{mdEnabled}"
			+ ")")
	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
	public int insert(SettingPO po);
	
	@Update("UPDATE " + TABLE_SCHEMA + " SET "
			+ "mdEnabled=#{mdEnabled}"
			+ " WHERE userid = #{userid}")
	public int update(SettingPO po);
	
	@Select("SELECT * FROM " + TABLE_SCHEMA + " WHERE userid = #{userid}")
	public SettingPO queryByUserid(Long userid);
}
