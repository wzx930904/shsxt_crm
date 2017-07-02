package com.shsxt.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shsxt.base.BaseQuery;
import com.shsxt.base.ResultInfo;
import com.shsxt.dao.ModuleDao;
import com.shsxt.model.Module;
import com.shsxt.service.ModuleService;
import com.shsxt.service.PermissioinService;
import com.shsxt.vo.ModuleVO;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController{
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private PermissioinService permissionService;
	
	@RequestMapping("index")
	public String index() {
		return "module";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(BaseQuery bq){
		Map<String, Object> result = moduleService.selectForPage(bq);
		return result;
	}
	
	@RequestMapping("find_by_grade")
	@ResponseBody
	public List<Module> findByGrade(Integer grade) {
		List<Module> result = moduleService.findByGrade(grade);
		return result;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ResultInfo add(Module module){
		moduleService.add(module);
		return success("添加成功");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResultInfo update(Module module) {
		moduleService.update(module);
		return success("修改成功");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids) {
		moduleService.deleteBatch(ids);
		return success("删除成功");
	}
	
	@RequestMapping("relate_permission")
	public String relatePermission(Integer roleId,Model model){
		List<ModuleVO> moduleVOs = moduleService.findAllModule(roleId);
		model.addAttribute("modules", moduleVOs);
		model.addAttribute("roleId", roleId);
		return "role_module";
	}
	
	@RequestMapping("dorelate")
	@ResponseBody
	public ResultInfo doRelate(Integer roleId,Integer moduleId,boolean checked) {
		permissionService.addDoRelate(roleId,moduleId,checked);
		return success("删除成功");
	}
}
