package com.ulplanet.trip.modules.ims.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.dao.PhoneInfoDao;
import com.ulplanet.trip.modules.ims.dao.StockOrderDao;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import com.ulplanet.trip.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by makun on 2015/10/22.
 */
@Service("stockOrderService")
public class StockOrderService extends CrudService<StockOrderDao,StockOrder> {

    @Resource
    StockOrderDao stockOrderDao;
    @Resource
    PhoneInfoDao phoneInfoDao;

    public StockOrder saveOrder(StockOrder stockOrder){
        if(StringUtils.isNotBlank(stockOrder.getId())){
            stockOrder =  this.updateOrder(stockOrder);
        }else {
            stockOrder = this.addOrder(stockOrder);
        }
        return stockOrder;
    }

    public StockOrder addOrder(StockOrder stockOrder) {
        stockOrder.preInsert();
        stockOrder.setBuyer(UserUtils.getUser().getName());
        if(stockOrderDao.insert(stockOrder) > 0){
            return stockOrder;
        }
        return null;
    }

    public StockOrder updateOrder(StockOrder stockOrder) {
        stockOrder.preUpdate();
        StockOrder old = stockOrderDao.get(stockOrder);
        int oldNumber = old.getQuantity();
        int number = stockOrder.getQuantity();
        if(number < oldNumber){
            PhoneInfo phoneInfo = new PhoneInfo();
            phoneInfo.setStockOrderId(stockOrder.getId());
            if(phoneInfoDao.queryDeliverPhone(phoneInfo) > number){
                return null;
            }
        }else if(number > oldNumber){
            stockOrder.setStatus(1);
        }
        if(stockOrderDao.update(stockOrder) > 0){
            return stockOrder;
        }
        return null;
    }


    public int refund(String id) {
        StockOrder stockOrder = stockOrderDao.getById(id);
        double totalPrice = stockOrder.getTotalPrice();
        int quantity = stockOrder.getQuantity();
        double unitPrice = stockOrder.getUnitPrice();
        stockOrder.setQuantity(--quantity);
        stockOrder.setTotalPrice(totalPrice - unitPrice);
        stockOrder.preUpdate();
        stockOrder.setComment(stockOrder.getComment() + "<br> 手机退货 on "
                + DateUtils.formatDate(new Date()) + "==id==" + stockOrder.getId());
        return stockOrderDao.update(stockOrder);
    }

    public List<StockOrder> getOrderIds(String id){
        StockOrder stockOrder = new StockOrder();
        stockOrder.setStatus(1);
        stockOrder.setInsurance(null);
        List<StockOrder> list = stockOrderDao.findListByParams(stockOrder);
        if(StringUtils.isNotBlank(id)) {
            stockOrder = new StockOrder();
            stockOrder.setId(id);
            stockOrder = stockOrderDao.get(id);
            if (stockOrder.getStatus() == 3) {
                list.add(stockOrder);
            }
        }
        return list;
    }
}
