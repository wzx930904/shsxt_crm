package com.shsxt.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.model.OrderDetails;

public interface OrderDetailsDao {

	@Select("select sum(sum) from t_order_details where order_id=#{orderId} and is_valid=1")
	Integer count(@Param(value="orderId")Integer orderId);

	@Select("select id,order_id,goods_name,goods_num,unit,price,sum,"
			+ "is_valid,create_date,update_date from t_order_details where order_id=#{orderId} and is_valid=1")
	PageList<OrderDetails> selectForPage(@Param(value="orderId")Integer orderId, PageBounds buildPageBounds);

}
