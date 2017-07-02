package com.shsxt.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shsxt.dao.PermissionDao;

@Service
public class PermissioinService {

	@Resource
	private PermissionDao permissionDao;

	public void addDoRelate(Integer roleId, Integer moduleId, boolean checked) {
		// TODO Auto-generated method stub
		
	}
}
