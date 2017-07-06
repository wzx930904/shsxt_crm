package com.shsxt.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.model.CustomerOrder;

public interface OrderDao {

	@Select("select id,cus_id,order_no,order_date,address,state,create_date,update_date,is_valid"
			+ " from t_customer_order where cus_id=#{customerId} and is_valid=1")
	PageList<CustomerOrder> selectForPage(@Param(value="customerId")Integer customerId, PageBounds buildPageBounds);

	@Select("select id,cus_id,order_no,order_date,address,state,create_date,update_date,is_valid"
			+ " from t_customer_order where id=#{orderId} and is_valid=1")
	CustomerOrder findById(@Param(value="orderId")Integer orderId);

	@Select("select order_date from t_customer_order where "
			+ " cus_id = #{id} order by order_date desc")
	Date findCustomerOrderDate(@Param(value="id")Integer id);

}
