package com.ulplanet.trip.modules.ims.service;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.modules.ims.dao.StockOrderDao;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import com.ulplanet.trip.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> result = new HashMap<>();
        if(stockOrderDao.insert(stockOrder) > 0){
            return stockOrder;
        }
        return null;
    }

    public StockOrder updateOrder(StockOrder stockOrder) {
        stockOrder.preUpdate();
        Map<String, Object> result = new HashMap<>();
        if(stockOrderDao.update(stockOrder) > 0){
            return stockOrder;
        }
        return null;
    }

    public Page<StockOrder> findStockOrders(int page, int size) {
        Page<StockOrder> pager = new Page<>(page,size);
        StockOrder stockOrder = new StockOrder();
        stockOrder.setPage(pager);
        List<StockOrder> stockOrders = stockOrderDao.findList(stockOrder);
//        List<StockOrderBo> stockOrderBos = new ArrayList<>();
//        for(StockOrder s : stockOrders){
//            stockOrderBos.add(new StockOrderBo(s));
//        }
        pager.setList(stockOrders);
        return pager;
    }

    public List<StockOrder> findListByParams(StockOrder stockOrder) {
        return stockOrderDao.findListByParams(stockOrder);
    }

    public int refund(String id) {
        StockOrder stockOrder = stockOrderDao.getById(id);
        double totalPrice = stockOrder.getTotalPrice();
        int quantity = stockOrder.getQuantity();
        double unitPrice = stockOrder.getUnitPrice();
        stockOrder.setQuantity(--quantity);
        stockOrder.setTotalPrice(totalPrice - unitPrice);
        stockOrder.preUpdate();
        stockOrder.setComment(stockOrder.getComment() + "/n 手机退货 on "
                + DateUtils.formatDate(new Date()) + " ==id==" + stockOrder.getId());
        return stockOrderDao.update(stockOrder);
    }
}
