package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.ProductOutDao;
import com.ulplanet.trip.modules.iom.entity.ProductOut;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品出库Service
 * Created by zhangxd on 15/12/11.
 */
@Service
public class ProductOutService extends CrudService<ProductOutDao, ProductOut> {

    @Autowired
    private ProductOutDao productOutDao;
    @Autowired
    private CodeService codeService;

    public void saveProductOut(ProductOut productOut) {
        if (StringUtils.isBlank(productOut.getId())) {
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_OUT);
            productOut.setCode(code);
            productOut.preInsert();
            productOutDao.insert(productOut);
        } else {
            productOut.preUpdate();
            productOutDao.update(productOut);
        }
    }

}
