package com.ulplanet.trip.modules.ims.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface  StockOrderDao extends CrudDao<StockOrder> {
	List<StockOrder> findListByParams(StockOrder stockOrder);
	StockOrder getById(@Param("id") String id);

}
