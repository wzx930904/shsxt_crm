package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.UserQuery;
import com.shsxt.model.User;

public interface UserDao {

	@Select("select id,user_name,password,true_name,email,phone from t_user"
			+" where id=#{id} and is_valid=1")
	public User findById(@Param(value="id")Integer id);
	
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	@Select("select id,user_name,password,true_name,email,phone from t_user"
			+ " where user_name=#{userName} and is_valid=1")
	public User findUsrByUserName(@Param(value="userName")String userName);
	
	@Select("select t1.id,t1.user_name,t1.true_name from t_user t1 left join t_user_role t2 on t1.id=t2.user_id"
			+ " left join t_role t3 on t2.role_id=t3.id where t3.role_name=#{roleName}")
	List<User> findByRoleName(@Param(value="roleName")String roleName);

	public PageList<User> selectForPage(UserQuery userQuery, PageBounds buildPageBounds);

	public void add(User user);

	public void update(User user);

	public void deleteBatch(@Param(value="ids")String ids);
}
