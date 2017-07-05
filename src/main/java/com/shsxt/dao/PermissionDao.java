package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shsxt.model.Module;
import com.shsxt.model.Permission;

public interface PermissionDao {

	@Select("select count(1) from t_permission where role_id=#{roleId} and is_valid=1 and module_id=#{moduleId}")
	Integer count(@Param(value="roleId")Integer roleId, @Param(value="moduleId")Integer moduleId);

	@Delete("delete from t_permission where role_id=#{roleId} and module_id=#{moduleId}")
	void releaseModule(@Param(value="roleId")Integer roleId,@Param(value="moduleId")Integer moduleId);

	@Delete("delete from t_permission where role_id=#{roleId} and module_id in (${moduleIds})")
	void releaseModules(@Param(value="roleId")Integer roleId, @Param(value="moduleIds")String moduleIds);

	void insertBatch(@Param(value="permissions")List<Permission> permissions);

}
