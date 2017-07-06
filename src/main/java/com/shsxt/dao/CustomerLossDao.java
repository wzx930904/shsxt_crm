package com.shsxt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shsxt.dto.CustomerLossQuery;
import com.shsxt.dto.CustomerQuery;
import com.shsxt.model.CustomerLoss;

public interface CustomerLossDao {

	
	PageList<CustomerLoss> selectForPage(CustomerLossQuery query, PageBounds buildPageBounds);

	CustomerLoss loadById(@Param(value="lossId")Integer lossId);

	void insertBatch(List<CustomerLoss> customerLosses);

}
