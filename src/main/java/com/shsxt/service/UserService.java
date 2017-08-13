package com.shsxt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.controller.BaseController;
import com.shsxt.dao.UserDao;
import com.shsxt.dao.UserRoleDao;
import com.shsxt.dto.UserQuery;
import com.shsxt.exception.ParamException;
import com.shsxt.model.User;
import com.shsxt.model.UserRole;
import com.shsxt.util.MD5Util;
import com.shsxt.util.UserIDBase64;
import com.shsxt.vo.UserLoginIdentity;

@Service
public class UserService {

	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleDao userRoleDao;
	/**
	 * 用户登录
	 * @param userName用户名
	 * @param password密码
	 * @return
	 */
	public UserLoginIdentity findLoginUser(Integer id) {
		AssertUtil.intIsEmpty(id, "用户不存在");
//		if (id == null || id < 1) {
//			throw new ParamException(100, "用户不存在");
//		}
		User user = userDao.findById(id);
		UserLoginIdentity uli = createUserLoginIdentity(user);
		return uli;
	}
	
	/**
	 * 获取登录用户信息
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserLoginIdentity login(String userName,String password) {
		
		// 非空验证
		AssertUtil.stringIsEmpty(userName, 100, "请输入用户名");
		AssertUtil.stringIsEmpty(password, 101, "请输入密码");
//		if (StringUtils.isBlank(userName)) {
//			throw new ParamException(100, "请输入用户名");
//		}
		
//		if (StringUtils.isBlank(password)) {
//			throw new ParamException(101,"请输入密码");
//		}
		
		// 根据用户名查询用户在验证
		User user = userDao.findUsrByUserName(userName);
		AssertUtil.objectIsEmpty(user, 102, "用户名密码错误，请重新输入");
//		if (user == null) {
//			throw new ParamException(102,"用户名密码错误，请重新输入");
//		}
		
		// 密码验证：需要MD5加密
		if (!MD5Util.md5Method(password).equals(user.getPassword())) {
			throw new ParamException(103, "用户名密码错误，请重新输入");
		}
		
		// 构建登录实体
		UserLoginIdentity uli = createUserLoginIdentity(user);
		return uli;
	}
	
	/**
	 * 查询客户经理
	 * @return
	 */
	public List<User> findCustomerManager() {
		return userDao.findByRoleName("客户经理");
	}
	
	/**
	 * 构建登录实体
	 * @param user
	 * @return
	 */
	public static UserLoginIdentity createUserLoginIdentity(User user) {
		UserLoginIdentity uli = new UserLoginIdentity();
		uli.setUserName(user.getUserName());
		uli.setRealName(user.getTrueName());
		uli.setUserIdString(UserIDBase64.encoderUserID(user.getId()));
		return uli;
	}

	public Map<String, Object> selectForPage(UserQuery userQuery) {
		PageList<User> users = userDao.selectForPage(userQuery,userQuery.buildPageBounds());
		Map<String, Object> map = new HashMap<>();
		map.put("rows", users);
		map.put("total", users.getPaginator().getTotalCount());
		return map;
	}

	public void add(User user) {
		checkParams(user);
		User userByName = userDao.findUsrByUserName(user.getUserName());
		AssertUtil.isTrue(userByName != null, "该用户已存在");
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		String password = MD5Util.md5Method(user.getPassword());
		user.setPassword(password);
		userDao.add(user);
		saveUserRoles(user);
	}

	private void saveUserRoles(User user) {
		List<UserRole> userRoles = new ArrayList<>();
		for (Integer roleId : user.getRoleIds()) {
			UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setIsValid(1);
			userRole.setUserId(user.getId());
			userRole.setCreateDate(new Date());
			userRole.setUpdateDate(new Date());
			userRoles.add(userRole);
		}
		userRoleDao.insertBatch(userRoles);
	}

	public void update(User user) {
		checkParams(user);
		User userById = userDao.findById(user.getId());
		AssertUtil.objectIsEmpty(userById, "该用户不存在");
		if (userById.getUserName() != user.getUserName()) {
			User userByName = userDao.findUsrByUserName(user.getUserName());
			AssertUtil.isTrue(userByName != null, "该用户已存在");
		}
		userRoleDao.deleteBatch(user.getId());
		String password = user.getPassword();
		user.setPassword(MD5Util.md5Method(password));
		user.setUpdateDate(new Date());
		userDao.update(user);
		saveUserRoles(user);
	}

	public void delete(String ids) {
		AssertUtil.stringIsEmpty(ids, "请选择要删除的记录");
		userDao.deleteBatch(ids);
	}
	
	public void checkParams(User user) {
		AssertUtil.stringIsEmpty(user.getUserName(), "请输入用户名");
		AssertUtil.stringIsEmpty(user.getPassword(), "请输入密码");
		AssertUtil.stringIsEmpty(user.getPhone(), "请输入联系电话");
		AssertUtil.isTrue(user.getRoleIds() == null, "请选择角色");
	}
}
