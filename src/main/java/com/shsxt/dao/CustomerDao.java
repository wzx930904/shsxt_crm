package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.Customer;
import com.shsxt.vo.CustomerVo;

public interface CustomerDao {

	@Select("select id,name from t_customer where is_valid=1 and state=0")
	@ResultType(value=CustomerVo.class)
	List<CustomerVo> findAll();

	PageList<Customer> selectForPage(CustomerQuery customerQuery, PageBounds buildPageBounds);

	@Select("select khno,name from t_customer where id=#{cusId}")
	Customer findById(@Param(value="cusId")Integer cusId);

	@Update("update t_customer set state=1,update_date=now() where khno=#{cusNo}")
	void updateLossState(String cusNo);

	List<Customer> findLossCustomer();

	List<Customer> findLossCustomerNoOrderLongTime();

	void updateStates(@Param(value="ids")String ids);
	

}
