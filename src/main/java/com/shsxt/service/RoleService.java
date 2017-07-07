package com.shsxt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.base.AssertUtil;
import com.shsxt.dao.RoleDao;
import com.shsxt.model.Role;

@Service
public class RoleService {
	
	@Resource
	private RoleDao roleDao;

	public Map<String, Object> findAll() {
		List<Role> roles = roleDao.findAll();
		Map<String, Object> result = new HashMap<>();
		result.put("rows", roles);
		return result;
	}

	public void add(String roleName, String roleRemark) {
		AssertUtil.stringIsEmpty(roleName, "请输入角色名称");
		AssertUtil.stringIsEmpty(roleRemark, "请输入角色描述");
		Role temp = roleDao.findByRoleName(roleName);
		AssertUtil.isTrue(temp != null, "该角色已存在");
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRoleRemark(roleRemark);
		roleDao.insert(role);
		
	}

	public void update(Integer id, String roleName, String roleRemark) {
		AssertUtil.intIsEmpty(id, "请选择一条记录");
		AssertUtil.stringIsEmpty(roleName, "请输入角色名称");
		Role role = roleDao.findById(id);
		AssertUtil.isTrue(role == null, "该记录不存在");
		if (!role.getRoleName().equals(roleName)) {
			Role tempByName = roleDao.findByRoleName(roleName);
			AssertUtil.isTrue(tempByName != null, "该角色已存在，请重新输入");
		}
		role.setRoleName(roleName);
		role.setRoleRemark(roleRemark);
		roleDao.update(role);
	}

	public void deleteBatch(String ids) {
		AssertUtil.stringIsEmpty(ids, "请选择记录进行删除");
		roleDao.deleteBatch(ids);
	}

	public Role findById(Integer roleId) {
		AssertUtil.intIsEmpty(roleId, "请选择角色");
		Role role = roleDao.findById(roleId);
		AssertUtil.objectIsEmpty(role, "角色不存在");
		return role;
	}

	public List<Role> findRoleName() {
		List<Role> roles = roleDao.findRoleName();
		return roles;
	}

}
