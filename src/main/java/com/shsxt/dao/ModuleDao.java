package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.model.Module;
import com.shsxt.vo.ModuleVO;

public interface ModuleDao {

	@Select("select id,module_name,module_style,url,opt_value,grade,orders,update_date from t_module where is_valid=1")
	PageList<Module> selectForPage(PageBounds buildPageBounds);

	@Select("select id,module_name from t_module where grade=#{grade} and is_valid=1 order by orders")
	List<Module> findByGrade(@Param(value="grade")Integer grade);

	Module findByModuleName(@Param(value="moduleName")String moduleName, @Param(value="parentId")Integer parentId);

	Module findByOptValue(@Param(value="optValue")String optValue);

	Module findById(@Param(value="id")Integer id);

	void insert(Module module);

	void update(Module moduleFromDb);

	void deleteBatch(@Param(value="ids")String ids);

	List<ModuleVO> findAll();

	@Select("select id,module_name,parent_id,opt_value"
			+ " from t_module where is_valid=1 and tree_path like '${treePath}%'")
	List<Module> findSonModules(@Param(value="treePath")String treePath);

	List<Module> findByIds(@Param(value="ancestorIds")String ancestorIds);

}
