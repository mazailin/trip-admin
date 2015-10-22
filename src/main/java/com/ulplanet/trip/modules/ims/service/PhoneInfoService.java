package com.ulplanet.trip.modules.ims.service;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.IdGen;
import com.ulplanet.trip.modules.ims.dao.PhoneInfoDao;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by makun on 2015/10/22.
 */
@Service("phoneInfoService")
public class PhoneInfoService extends CrudService<PhoneInfoDao,PhoneInfo> {

    @Resource
    private PhoneInfoDao phoneInfoDao;
    @Resource
    private StockOrderService stockOrderService;


    public Page<PhoneInfo> findPhoneInfos(int page, int size, String searchValue) {
        Page<PhoneInfo> pager = new Page<>(page,size);
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setPage(pager);
        List<PhoneInfo> phoneInfos = phoneInfoDao.findList(phoneInfo);
        pager.setList(phoneInfos);
        return pager;
    }

    public PhoneInfo addPhoneInfo(PhoneInfo phoneInfo) {
        phoneInfo.preInsert();
        phoneInfo.setCode(IdGen.uuid());
        Map<String, Object> result = new HashMap<>();
        if(phoneInfoDao.insert(phoneInfo) > 0) {
            return phoneInfo;
        }
        return null;
    }

    public PhoneInfo updatePhoneInfo(PhoneInfo phoneInfo) {
        phoneInfo.preUpdate();
        if(phoneInfoDao.update(phoneInfo) > 0){
            return phoneInfo;
        }
        return null;
    }

    public PhoneInfo startRefund(PhoneInfo phoneInfo) {
        phoneInfo = phoneInfoDao.getById(phoneInfo.getId());
        if(phoneInfo.getStatus().intValue()==9999) {
            return null;
        }
        phoneInfo.setStatus(9999);
        if("1".equals(phoneInfo.getRefundFlag())){//0更新 1退款
            if(stockOrderService.refund(phoneInfo.getStockOrderId()) <= 0){
                return null;
            }
        }
        phoneInfo.preUpdate();
        if(phoneInfoDao.update(phoneInfo)>0){
            return phoneInfo;
        }
        return null;
    }

    public List<PhoneInfo> findListByParams(PhoneInfo phoneInfo) {
        List<PhoneInfo> phoneInfos = phoneInfoDao.findListByParams(phoneInfo);
        return phoneInfos;
    }

    public int queryDeliverPhone(PhoneInfo phoneInfo) {
        return phoneInfoDao.queryDeliverPhone(phoneInfo);
    }

}
