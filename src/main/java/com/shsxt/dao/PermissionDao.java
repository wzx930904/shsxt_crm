package com.shsxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PermissionDao {

	@Select("select count(1) from t_permission where role_id=#{roleId} and is_valid=1 and module_id=#{moduleId}")
	Integer count(@Param(value="roleId")Integer roleId, @Param(value="moduleId")Integer moduleId);

}
