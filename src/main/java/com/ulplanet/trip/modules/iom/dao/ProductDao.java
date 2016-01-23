package com.ulplanet.trip.modules.iom.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.iom.entity.Product;

import java.util.List;

/**
 * 产品Dao
 * Created by zhangxd on 15/12/01.
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {

    List<Product> findUseDetailList(Product product);

    List<Product> findNUseDetailList(Product product);

    void updateAvlPrice(Product product);

    void updateTotalAmt(Product product);

    void updateRsvAmt(Product product);

    void updateAvlAmt(Product product);

    void updateRentAmt(Product product);
}
