package com.ulplanet.trip.modules.iom.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.iom.dao.RentDao;
import com.ulplanet.trip.modules.iom.entity.Rent;
import com.ulplanet.trip.modules.sys.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品租赁Service
 * Created by zhangxd on 15/12/17.
 */
@Service
public class RentService extends CrudService<RentDao, Rent> {

    @Autowired
    private RentDao rentDao;
    @Autowired
    private CodeService codeService;

    public void saveRent(Rent rent) {
        if (StringUtils.isBlank(rent.getId())){
            String code = codeService.getCode(CodeService.CODE_TYPE_PRODUCT_RENT, "");
            rent.preInsert();
            rent.setCode(code);
            rentDao.insert(rent);
        }else{
            rent.preUpdate();
            rentDao.update(rent);
        }
    }
}
