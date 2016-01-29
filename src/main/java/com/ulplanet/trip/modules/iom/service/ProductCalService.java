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
    public void updateAmount(String productId) {
        this.updateTotalAmt(productId);
        this.updateRsvAmt(productId);
        this.updateAvlAmt(productId);
        this.updateRentAmt(productId);
    }

    // 更新总数
    private void updateTotalAmt(String productId) {
        this.productDao.updateTotalAmt(new Product(productId));
    }

    // 更新库存数量
    private void updateRsvAmt(String productId) {
        this.productDao.updateRsvAmt(new Product(productId));
    }

    // 更新平均数量
    private void updateAvlAmt(String productId) {
        this.productDao.updateAvlAmt(new Product(productId));
    }

    // 更新租出数量
    private void updateRentAmt(String productId) {
        this.productDao.updateRentAmt(new Product(productId));
    }

    /**
     * 计算产品平均价格
     */
    public void updateAvlPrice(String productId) {
        this.productDao.updateAvlPrice(new Product(productId));
    }
}
