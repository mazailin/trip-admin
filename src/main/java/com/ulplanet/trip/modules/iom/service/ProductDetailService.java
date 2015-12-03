package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductDetailDao;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品明细Service
 * Created by zhangxd on 15/12/01.
 */
@Service
public class ProductDetailService extends CrudService<ProductDetailDao, ProductDetail> {

    @Autowired
    private ProductDetailDao productDetailDao;

    public void saveProductDetail(ProductDetail productDetail) {
        if (StringUtils.isBlank(productDetail.getId())) {
            productDetail.preInsert();
            productDetailDao.insert(productDetail);
        } else {
            productDetail.preUpdate();
            productDetailDao.update(productDetail);
        }
    }

}
