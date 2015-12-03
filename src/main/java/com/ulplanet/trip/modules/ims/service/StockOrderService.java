package com.ulplanet.trip.modules.ims.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.ims.dao.StockOrderDao;
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
