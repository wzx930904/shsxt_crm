package com.shsxt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.base.AssertUtil;
import com.shsxt.base.BaseQuery;
import com.shsxt.dao.ModuleDao;
import com.shsxt.dao.PermissionDao;
import com.shsxt.exception.ParamException;
import com.shsxt.model.Module;
import com.shsxt.vo.ModuleVO;

import constant.ModuleGrade;

@Service
public class ModuleService {

	@Resource
	private ModuleDao moduleDao;
	@Resource
	private PermissionDao permissionDao;

	public Map<String, Object> selectForPage(BaseQuery bq) {
		PageList<Module> modules = moduleDao.selectForPage(bq.buildPageBounds());
		Map<String, Object> result = new HashMap<>();
		result.put("rows", modules);
		result.put("total", modules.getPaginator().getTotalCount());
		return result;
	}

	public List<Module> findByGrade(Integer grade) {
		AssertUtil.objectIsEmpty(grade, "请选择层级");
		List<Module> modules = moduleDao.findByGrade(grade);
		return modules;
	}

	public void add(Module module) {
		//基本参数验证
		checkParams(module);
		
		//同级下模块名称唯一验证
		if (module.getGrade() != ModuleGrade.ROOT.getGrade()) {
			checkModuleNameUnique(module);
		}
		
		//权限值唯一验证
		Module moduleFromDb = moduleDao.findByOptValue(module.getOptValue());
		AssertUtil.isTrue(moduleFromDb != null, "该权限值已存在");
		
		//构建一个tree_path=,1,
		String treePath = null;
		if (module.getGrade() != ModuleGrade.ROOT.getGrade()) {
			treePath = buildTreePath(module);
		}
		module.setTreePath(treePath);
		module.setCreateDate(new Date());
		module.setUpdateDate(new Date());
		module.setIsValid(1);
		//保存
		moduleDao.insert(module);
	}

	public void update(Module module) {
		//基本参数验证
		Integer id = module.getId();
		AssertUtil.intIsEmpty(id, "请选择模块id");
		checkParams(module);
		
		//根据id获取模块记录
		Module moduleFromDb = moduleDao.findById(module.getId());
		AssertUtil.objectIsEmpty(moduleFromDb, "该模块不存在");
		
		//验证模块名称唯一
		String moduleName = module.getModuleName();
		if (!moduleName.equals(moduleFromDb.getModuleName()) 
				&& module.getGrade() != ModuleGrade.ROOT.getGrade()) {
			checkModuleNameUnique(module);
		}
		
		//构建一个treePath=,1,
		String treePath = moduleFromDb.getTreePath();
		if (module.getGrade() != ModuleGrade.ROOT.getGrade()
				&& !module.getParentId().equals(moduleFromDb.getParentId())) {
			treePath = buildTreePath(module);
		}
		BeanUtils.copyProperties(module, moduleFromDb);//copy属性
		module.setUpdateDate(new Date());
		module.setTreePath(treePath);
		
		moduleDao.update(module);
	}
	
	/**
	 * 基本参数验证
	 * @param module
	 */
	public static void checkParams(Module module){
		//基本参数验证
		String moduleName = module.getModuleName();
		AssertUtil.stringIsEmpty(moduleName, "请输入模块名称");
		Integer orders = module.getOrders();
		AssertUtil.intIsEmpty(orders, "请输入排序值");
		String optValue = module.getOptValue();
		AssertUtil.stringIsEmpty(optValue, "请输入权限值");
		
		if (module.getGrade() != ModuleGrade.ROOT.getGrade() && module.getParentId() == null) {
			throw new ParamException("请选择父级菜单");
		}
	}
	
	/**
	 * 模块名唯一验证
	 * @param module
	 */
	private void checkModuleNameUnique(Module module) {
			Module moduleFromDb = moduleDao.findByModuleName(module.getModuleName().trim(),module.getParentId());
			AssertUtil.isTrue(moduleFromDb != null, "模块名已存在");
	}
	
	/**
	 * 构建treePath
	 * @param module
	 * @return
	 */
	public String buildTreePath(Module module) {
		Module parentModule = moduleDao.findById(module.getParentId());
		AssertUtil.isTrue(parentModule == null, "该父级不存在");
		String parentTreePath = parentModule.getTreePath();
		String treePath = module.getTreePath();
		if (StringUtils.isBlank(parentTreePath)) {
			treePath = ","+module.getParentId()+",";
		} else {
			treePath = parentTreePath+module.getParentId()+",";
		}
		return treePath;
	}

	public void deleteBatch(String ids) {
		
		AssertUtil.stringIsEmpty(ids, "请选择记录进行删除");
		moduleDao.deleteBatch(ids);
	}

	public List<ModuleVO> findAllModule(Integer roleId) {
		AssertUtil.intIsEmpty(roleId, "请选择角色id");
		List<ModuleVO> modules = moduleDao.findAll();
		for (ModuleVO moduleVO : modules) {
			Integer count = permissionDao.count(roleId,moduleVO.getId());
			if(null == count || count == 0) {
				moduleVO.setChecked(false);
			} else {
				moduleVO.setChecked(true);
			}
		}
		return modules;
	}

	/**
	 * 根据Id获取对象
	 * @param moduleId
	 * @return
	 */
	public Module findById(Integer moduleId) {
		AssertUtil.intIsEmpty(moduleId, "请选择模块");
		Module module = moduleDao.findById(moduleId);
		AssertUtil.objectIsEmpty(module, "该模块不存在");
		return module;
	}
}
