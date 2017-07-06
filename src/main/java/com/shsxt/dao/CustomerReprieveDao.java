package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shsxt.model.CustomerReprieve;



public interface CustomerReprieveDao {

	@Select("select id,measure from t_customer_reprieve where loss_id=#{lossId} and is_valid=1")
	List<CustomerReprieve> findAll(@Param(value="lossId")Integer lossId);

	@Insert("insert into t_customer_reprieve (loss_id,measure,is_valid,create_date,update_date)"
			+"values(#{lossId},#{measure},1,now(),now())")
	void add(@Param(value="lossId")Integer lossId, @Param(value="measure")String measure);

	@Update("update t_customer_reprieve set measure=#{measure},update_date=now() where id=#{id}")
	void update(@Param(value="id")Integer id, @Param(value="measure")String measure);

	@Update("update t_customer_reprieve set is_valid=0 where id=#{id}")
	void delete(@Param(value="id")Integer id);

	
	
}
