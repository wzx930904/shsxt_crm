package com.shsxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.UserRoleDao;
import com.shsxt.model.UserRole;

@Service
public class UserRoleService {
	
	@Resource
	private UserRoleDao userRoleDao;

	public List<UserRole> findUserRoles(Integer userId) {
		AssertUtil.intIsEmpty(userId, "请选择用户");
		List<UserRole> userRoles = userRoleDao.findUserRoles(userId);
		return userRoles;
	}

}
