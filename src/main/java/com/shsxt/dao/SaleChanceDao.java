package com.shsxt.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dao.provider.SaleChanceBuilder;
import com.shsxt.dto.SaleChanceQuery;
import com.shsxt.model.SaleChance;

public interface SaleChanceDao {

	@SelectProvider(type=SaleChanceBuilder.class,method="selectForPageSql")
	public PageList<SaleChance> selectForPage(SaleChanceQuery query, PageBounds pageBounds);

	@Insert("insert into t_sale_chance (chance_source,customer_id,customer_name,cgjl,"
			+ "overview,link_man,link_phone,description,create_man,assign_man,assign_time,"
			+" state,dev_result,is_valid,create_date,update_date) values(#{chanceSource},#{customerId},"
			+ " #{customerName},#{cgjl},#{overview},#{linkMan},#{linkPhone},#{description},"
			+ " #{createMan},#{assignMan},#{assignTime},#{state},#{devResult},"
			+ " #{isValid},#{createDate},#{updateDate})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	int insert(SaleChance saleChance);

	@SelectProvider(type=SaleChanceBuilder.class,method="findByIdSql")
	public SaleChance findById(@Param(value="id")Integer id);

	@Update("update t_sale_chance set chance_source=#{chanceSource},customer_id=#{customerId},"
			+" customer_name=#{customerName},cgjl=#{cgjl},overview=#{overview},"
			+" link_man=#{linkMan},link_phone=#{linkPhone},description=#{description},"
			+" state=#{state},update_date=#{updateDate} where id=#{id}")
	int update(SaleChance saleChance);

	@Update("update t_sale_chance set is_valid=0 where id in (${ids})")
	int delete(@Param(value="ids")String ids);

	@Update("update t_sale_chance set dev_result=#{devResult},update_date=now() where id=#{id}")
	public void updateDevResult(@Param(value="id")Integer saleChanceId, @Param(value="devResult")int devResult); 

}
