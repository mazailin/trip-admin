package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductDetailDao;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.sys.service.CodeService;
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
    @Autowired
    private CodeService codeService;

    public void saveProductDetail(ProductDetail productDetail) {
        if (StringUtils.isBlank(productDetail.getId())) {
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_DETAIL);
            productDetail.setCode(code);
            productDetail.preInsert();
            productDetailDao.insert(productDetail);
        } else {
            productDetail.preUpdate();
            productDetailDao.update(productDetail);
        }
    }

}
