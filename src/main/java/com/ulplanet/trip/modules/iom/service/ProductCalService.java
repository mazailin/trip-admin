package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.iom.dao.ProductDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品数量价格计算Service
 * Created by zhangxd on 15/12/01.
 */
@Service
public class ProductCalService extends CrudService<ProductDao, Product> {

    @Autowired
    private ProductDao productDao;

    /**
     * 计算产品总数，在租数量，库存数量，可用数量
     */
    public void updateAmount(String productId, boolean useDetail) {

    }

    /**
     * 计算产品平均价格
     */
    public void updateAvlPrice(String productId) {

    }
}
