package com.ulplanet.trip.modules.ims.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import com.ulplanet.trip.modules.ims.service.StockOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by makun on 2015/10/13.
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/phoneOrder")
public class StockOrderController  extends BaseController {

    @Resource
    private StockOrderService stockOrderService;


    @ModelAttribute
    public StockOrder get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return stockOrderService.get(id);
        }else{
            return new StockOrder();
        }
    }


    @RequiresPermissions("ims:phone:view")
    @RequestMapping(value = {"/list",""})
    public String findStockOrders(StockOrder stockOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StockOrder> page = this.stockOrderService.findPage(new Page<>(request, response), stockOrder);
        model.addAttribute("page", page);
        return "modules/ims/phoneOrderList";

    }

    @RequiresPermissions("ims:phone:edit")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(StockOrder stockOrder,Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, stockOrder)){
            return form(stockOrder, model);
        }
        if(stockOrderService.saveOrder(stockOrder)!=null){
            addMessage(redirectAttributes,"保存手机订单"+stockOrder.getOrderId()+"成功");
        }else{
            addMessage(redirectAttributes,"保存手机订单"+stockOrder.getOrderId()+"失败");
        }
        return "redirect:" + adminPath + "/ims/phoneOrder/list/?repage";
    }

    @RequiresPermissions("ims:phone:edit")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(StockOrder stockOrder, RedirectAttributes redirectAttributes) {
        stockOrder.setStatus(0);
        this.stockOrderService.updateOrder(stockOrder);
        addMessage(redirectAttributes, "保存手机订单" + stockOrder.getOrderId() + "成功");
        return "redirect:" + adminPath + "/ims/phoneOrder/list/?repage";
    }
    @RequiresPermissions("ims:phone:view")
    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form(StockOrder stockOrder,Model model) {
        model.addAttribute("order", stockOrder);
        return "modules/ims/phoneOrderForm";
    }




}
