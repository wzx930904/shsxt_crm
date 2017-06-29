package com.shsxt.dao.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shsxt.dto.SaleChanceQuery;


public class SaleChanceBuilder {

	private static Logger logger = LoggerFactory.getLogger(SaleChanceBuilder.class);
	private static String COMMON_COLUMN = "t.id,t.customer_name,t.overview,t.customer_id,t.chance_source,t.link_man,t.state, "
			+" t.link_phone,t.create_date,t.create_man,t.cgjl,t.description,t.dev_result,t.assign_man";
	
	public String selectForPageSql(final SaleChanceQuery query) {
		SQL sql = new SQL(){{
			SELECT(COMMON_COLUMN);
			FROM("t_sale_chance t");
			WHERE("is_valid = 1");
			if (StringUtils.isNoneBlank(query.getCustomerName())) {
				AND().WHERE("customer_name like '%"+query.getCustomerName()+"%'");
			}
			if (StringUtils.isNoneBlank(query.getCreateMan())) {
				AND().WHERE("create_man like '%"+query.getCreateMan()+"%'");
			}
			if (StringUtils.isNoneBlank(query.getOverview())) {
				AND().WHERE("overview  like '%"+query.getOverview()+"%'");
			}
			if (query.getState() != null) {
				AND().WHERE("state=#{state}");
			}
			ORDER_BY("id desc");
		}};
		logger.info("营销机会管理的sql:{}",sql.toString());
		return sql.toString();
	}
	
	public String findByIdSql(@Param("id")Integer id) {
		SQL sql = new SQL(){{
			SELECT(COMMON_COLUMN);
			FROM("t_sale_chance t");
			WHERE("id=#{id} and is_valid=1");
		}};
		return sql.toString();
	}
}
