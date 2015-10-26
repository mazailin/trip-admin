package com.ulplanet.trip.modules.ims.utils;

import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.SpringContextHolder;
import com.ulplanet.trip.modules.ims.bo.PhoneInfoBo;
import com.ulplanet.trip.modules.ims.bo.PhoneStatusBo;
import com.ulplanet.trip.modules.ims.dao.PhoneInfoDao;
import com.ulplanet.trip.modules.ims.dao.StockOrderDao;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.entity.StockOrder;

import java.util.List;


/**
 * Created by Administrator on 2015/10/23.
 */
public class PhoneUtils {
    private static StockOrderDao stockOrderDao = SpringContextHolder.getBean(StockOrderDao.class);
    public static List<PhoneStatusBo> getStatus(){
        if(EhCacheUtils.get("phoneStatus")==null){
            List<PhoneStatusBo> list = PhoneStatusBo.init();
            EhCacheUtils.put("phoneStatus",list);
            return list;
        }
        return (List<PhoneStatusBo>) EhCacheUtils.get("phoneStatus");
    }

    public static List<StockOrder> getOrderIds(){
        StockOrder stockOrder = new StockOrder();
        stockOrder.setStatus(1);
        stockOrder.setInsurance(null);
        List<StockOrder> list = stockOrderDao.findListByParams(stockOrder);
        return list;
    }
}
