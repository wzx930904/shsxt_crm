package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shsxt.model.CusDevPlan;

public interface CusDevPlanDao {

	@Select("select id,plan_item,plan_date,exe_affect,sale_chance_id"
			+" from t_cus_dev_plan where sale_chance_id=#{saleChanceId} and is_valid=1")
	public List<CusDevPlan> findSaleChancePlans(@Param(value="saleChanceId")Integer saleChanceId);

	@Insert("insert into t_cus_dev_plan (plan_item,plan_date,exe_affect,update_date,create_date,"
			+ " is_valid,sale_chance_id) values (#{planItem},#{planDate},#{exeAffect},#{updateDate},"
			+ " #{createDate},#{isValid},#{saleChanceId})") 
	void insert(CusDevPlan cusDevPlan);
	
	@Update("update t_cus_dev_plan set plan_item=#{planItem},exe_affect=#{exeAffect},plan_date=#{planDate},update_date=#{updateDate} where id=#{id}")
	public void update(CusDevPlan cusDevPlan);
		
	@Update("update t_cus_dev_plan set is_valid=0 where id=#{id}")
	public void delete(@Param("id")Integer id);
}
