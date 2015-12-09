package com.ulplanet.trip.modules.ims.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.dao.PhoneInfoDao;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/10/22.
 */
@Service("phoneInfoService")
public class PhoneInfoService extends CrudService<PhoneInfoDao,PhoneInfo> {

    @Resource
    private PhoneInfoDao phoneInfoDao;
    @Resource
    private StockOrderService stockOrderService;


    public PhoneInfo savePhoneInfo(PhoneInfo phoneInfo){
        if(StringUtils.isNotBlank(phoneInfo.getId())){
            if(phoneInfo.getStatus()!=null && phoneInfo.getStatus() == 9999){
                return null;
            }
            phoneInfo = this.updatePhoneInfo(phoneInfo);
        }else {
            phoneInfo = this.addPhoneInfo(phoneInfo);
        }
        return phoneInfo;
    }


    public PhoneInfo addPhoneInfo(PhoneInfo phoneInfo) {
        if(phoneInfo.getStatus()==9999){
            stockOrderService.refund(phoneInfo.getStockOrderId());
        }
        stockOrderFull(phoneInfo,null);
        phoneInfo.preInsert();
        if(phoneInfoDao.insert(phoneInfo) > 0) {
            return phoneInfo;
        }
        return null;
    }

    public PhoneInfo updatePhoneInfo(PhoneInfo phoneInfo) {
        PhoneInfo old = phoneInfoDao.get(phoneInfo);
        if(old.getStatus()==9999){
            return null;
        }
        if(!old.getStockOrderId().equals(phoneInfo.getStockOrderId())){
            stockOrderFull(phoneInfo,old);
        }
        phoneInfo.preUpdate();
        if(phoneInfoDao.update(phoneInfo) > 0){
            return phoneInfo;
        }
        return null;
    }

    public PhoneInfo startRefund(PhoneInfo phoneInfo) {
        PhoneInfo p = phoneInfoDao.getById(phoneInfo.getId());
        if(p.getStatus()==9999) {
            return p;
        }
        phoneInfo.setStatus(9999);
        if("1".equals(phoneInfo.getRefundFlag())){//退货时，库存数量减1
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


    public int queryDeliverPhone(PhoneInfo phoneInfo) {
        return phoneInfoDao.queryDeliverPhone(phoneInfo);
    }

    private void stockOrderFull(PhoneInfo newPhoneInfo,PhoneInfo oldPhoneInfo){
        int num1 = phoneInfoDao.queryDeliverPhone(newPhoneInfo);
        StockOrder o1 = stockOrderService.get(newPhoneInfo.getStockOrderId());
        if(oldPhoneInfo!=null){
            StockOrder o2 = stockOrderService.get(oldPhoneInfo.getStockOrderId());
            o2.setStatus(1);
            stockOrderService.updateOrder(o2);
        }
        if(++num1 >= o1.getQuantity()){//判断新增手机后订单是否已满
            o1.setStatus(3);
            stockOrderService.updateOrder(o1);
        }
    }

}
