package com.onet.user.service;

import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.user.constant.Role;
import com.onet.user.dto.UserDTO;
import com.onet.user.dto.UserQueryRequest;
import com.onet.user.po.SettingPO;

public interface UserService {
	public IBaseResponse add(UserDTO userDto);
	public IBaseResponse edit(UserDTO userDto);
	public IBaseResponse editUserMode(Long id, boolean active);
	public IBaseResponse editUserRole(Long id, Role role);
	
	public CommRes<UserDTO> queryUser(Long id);
	public CommRes<UserDTO> queryUser(String userName);
	public PageRes<UserDTO> queryUserList(UserQueryRequest request); 
	
	public boolean isUserNameExist(String userName);
	
	public SettingPO queryUserSetting(Long userid);
	public IBaseResponse updateSetting(SettingPO settingPO);
}
