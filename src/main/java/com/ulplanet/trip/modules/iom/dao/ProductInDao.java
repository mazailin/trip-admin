package com.ulplanet.trip.modules.iom.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.ProductIn;

import java.util.List;

/**
 * 产品入库Dao
 * Created by zhangxd on 15/12/07.
 */
@MyBatisDao
public interface ProductInDao extends CrudDao<ProductIn> {

    void insertProDetail(Parameter parameter);

    List<ProductDetail> findDetail(String id);
}
