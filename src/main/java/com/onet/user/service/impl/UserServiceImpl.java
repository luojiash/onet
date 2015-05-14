package com.onet.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.onet.common.dto.BaseResponse;
import com.onet.common.dto.CommRes;
import com.onet.common.dto.IBaseResponse;
import com.onet.common.dto.PageRes;
import com.onet.common.manager.ObjectConvertManager;
import com.onet.user.constant.Role;
import com.onet.user.dao.UserDao;
import com.onet.user.dao.UserSettingDAO;
import com.onet.user.dto.UserDTO;
import com.onet.user.dto.UserQueryRequest;
import com.onet.user.po.UserPO;
import com.onet.user.po.SettingPO;
import com.onet.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private ObjectConvertManager objectConvertManager;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserSettingDAO userSettingDAO;
	
	@Override
	public IBaseResponse add(UserDTO userDto) {
		if (isUserNameExist(userDto.getUserName())) {
			return new BaseResponse(false, "用户名已被注册");
		}
		IBaseResponse baseResponse = new BaseResponse(true);
		UserPO po = objectConvertManager.toUserPO(userDto);
		po.setRegisterDate(new Date());
		userDao.insertUser(po);
		Long userId = po.getId();
		userDto.setId(userId);
		return baseResponse;
	}

	@Override
	public IBaseResponse edit(UserDTO userDto) {
		UserPO po = objectConvertManager.toUserPO(userDto);
		userDao.updateUser(po);
		IBaseResponse baseResponse = new BaseResponse(true);
		return baseResponse;
	}

	@Override
	public IBaseResponse editUserMode(Long id, boolean active) {
		IBaseResponse baseResponse = new BaseResponse(true);
		UserPO po = userDao.queryUserPoById(id);
		if (null == po) {
			baseResponse.setResult(false);
			baseResponse.setReturnMessage("用户不存在，id:"+id);
			return baseResponse;
		}
		po.setActive(active);
		userDao.updateUser(po);
		return baseResponse;
	}

	@Override
	public IBaseResponse editUserRole(Long id, Role role) {
		userDao.updateUserRole(id, role);
		return new BaseResponse(true);
	}

	@Override
	public CommRes<UserDTO> queryUser(Long id) {
		UserPO po = userDao.queryUserPoById(id);
		UserDTO dto = objectConvertManager.toUserDTO(po);
		CommRes<UserDTO> res = new CommRes<UserDTO>(dto);
		if (null == po) {
			res.setRs(0);
			res.setMsg("用户不存在，id:"+id);
		}
		return res;
	}
	@Override
	public CommRes<UserDTO> queryUser(String userName) {
		UserPO po = userDao.queryUserPoByUserName(userName);
		UserDTO dto = objectConvertManager.toUserDTO(po);
		CommRes<UserDTO> res = new CommRes<UserDTO>(dto);
		if (null == po) {
			res.setRs(0);
			res.setMsg("用户不存在，userName:"+userName);
		}
		return res;
	}
	@Override
	public PageRes<UserDTO> queryUserList(UserQueryRequest request) {
		List<UserPO> pos = userDao.queryUserListByRequest(request);
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for (UserPO po : pos) {
			dtos.add(objectConvertManager.toUserDTO(po));
		}
		int total = userDao.queryUserCountByRequest(request);
		PageRes<UserDTO> res = new PageRes<UserDTO>();
		res.setList(dtos);
		res.setPageNo(request.getPageNo());
		res.setPageSize(request.getPageSize());
		res.setTotal(total);
		return res;
	}

	@Override
	public boolean isUserNameExist(String userName) {
		UserPO existUser = userDao.queryUserPoByUserName(userName);
		if (null != existUser) {
			return true;
		}
		return false;
	}

	@Override
	public SettingPO queryUserSetting(Long userid) {
		SettingPO po = userSettingDAO.queryByUserid(userid);
		return po;
	}

	@Override
	public IBaseResponse updateSetting(SettingPO settingPO) {
		userSettingDAO.update(settingPO);
		return new BaseResponse(true);
	}

}
