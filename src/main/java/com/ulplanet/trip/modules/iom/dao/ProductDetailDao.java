package com.ulplanet.trip.modules.iom.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;

import java.util.List;

/**
 * 产品明细Dao
 * Created by zhangxd on 15/12/01.
 */
@MyBatisDao
public interface ProductDetailDao extends CrudDao<ProductDetail> {

    List<ProductDetail> findAvlList(ProductDetail productDetail);

    List<ProductDetail> findInDetailList(ProductDetail productDetail);

    ProductDetail findByDevice(ProductDetail productDetail);
}
