package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductInDao;
import com.ulplanet.trip.modules.iom.entity.ProductIn;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品入库Service
 * Created by zhangxd on 15/12/11.
 */
@Service
public class ProductInService extends CrudService<ProductInDao, ProductIn> {

    @Autowired
    private ProductInDao productInDao;
    @Autowired
    private CodeService codeService;

    public void saveProductIn(ProductIn productIn) {
        if (StringUtils.isBlank(productIn.getId())) {
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_IN);
            productIn.setCode(code);
            productIn.preInsert();
            productInDao.insert(productIn);
        } else {
            productIn.preUpdate();
            productInDao.update(productIn);
        }
    }

}
