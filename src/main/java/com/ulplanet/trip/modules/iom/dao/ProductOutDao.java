package com.ulplanet.trip.modules.iom.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.iom.entity.ProductOut;

/**
 * 产品出库Dao
 * Created by zhangxd on 15/12/07.
 */
@MyBatisDao
public interface ProductOutDao extends CrudDao<ProductOut> {


}
