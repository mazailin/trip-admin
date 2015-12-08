package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductDao;
import com.ulplanet.trip.modules.iom.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品Service
 * Created by zhangxd on 15/12/01.
 */
@Service
public class ProductService extends CrudService<ProductDao, Product> {

    @Autowired
    private ProductDao productDao;

    public void saveProduct(Product product) {
        if (StringUtils.isBlank(product.getId())){
            product.preInsert();
            productDao.insert(product);
        }else{
            product.preUpdate();
            productDao.update(product);
        }
    }

    public List<Product> findUseDetailList(Product product) {
        return productDao.findUseDetailList(product);
    }
}
