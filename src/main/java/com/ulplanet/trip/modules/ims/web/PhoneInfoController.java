package com.ulplanet.trip.modules.ims.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.service.PhoneInfoService;
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
@RequestMapping(value = "${adminPath}/ims/phone")
public class PhoneInfoController extends BaseController {
    @Resource
    private PhoneInfoService phoneInfoService;
    @Resource
    private StockOrderService stockOrderService;

    @ModelAttribute
    public PhoneInfo get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return phoneInfoService.get(id);
        }else{
            return new PhoneInfo();
        }
    }

    /**
     * 查询列表
     * @param phoneInfo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("ims:phone:view")
    @RequestMapping(value = {"/list",""})
    public String list(PhoneInfo phoneInfo, HttpServletRequest request, HttpServletResponse response, Model model){
        model.addAttribute("stockOrderId",phoneInfo.getStockOrderId());
        Page<PhoneInfo> page = this.phoneInfoService.findPage(new Page<>(request, response), phoneInfo);
        model.addAttribute("page",page);
        return "modules/ims/phoneList";
    }

    /**
     * 保存
     * @param phoneInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("ims:phone:edit")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(PhoneInfo phoneInfo,Model model, RedirectAttributes redirectAttributes) {

        if (!beanValidator(model, phoneInfo)){
            return form(phoneInfo, model);
        }
        phoneInfo = phoneInfoService.savePhoneInfo(phoneInfo);
        if(phoneInfo!=null){
            addMessage(redirectAttributes,"保存手机信息"+phoneInfo.getCode()+"成功");
        }else{
            addMessage(redirectAttributes,"保存手机信息"+phoneInfo.getCode()+"失败");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?stockOrderId=" + phoneInfo.getStockOrderId() + "&repage";
    }

    /**
     * 报废
     * @param phoneInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("ims:phone:refund")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(PhoneInfo phoneInfo, RedirectAttributes redirectAttributes) {
        phoneInfo.setStatus(9000);
        phoneInfo = this.phoneInfoService.updatePhoneInfo(phoneInfo);
        if(phoneInfo!=null) {
            addMessage(redirectAttributes, "报废" + phoneInfo.getCode() + "成功");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?repage";
    }

    /**
     * 退换货
     * @param phoneInfo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("ims:phone:refund")
    @RequestMapping(value = "/refund",method = RequestMethod.GET)
    public String refund(PhoneInfo phoneInfo, RedirectAttributes redirectAttributes) {
        phoneInfo = this.phoneInfoService.startRefund(phoneInfo);
        if(phoneInfo!=null) {
            addMessage(redirectAttributes, "退货" + phoneInfo.getCode() + "成功");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?repage";
    }

    /**
     * 查询表单
     * @param phoneInfo
     * @param model
     * @return
     */
    @RequiresPermissions("ims:phone:view")
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(PhoneInfo phoneInfo,Model model) {
        model.addAttribute("order", phoneInfo);
        model.addAttribute("orderList",stockOrderService.getOrderIds(phoneInfo.getStockOrderId()));
        return "modules/ims/phoneForm";
    }

    /**
     * 获取指定订单下的到货手机数量
     * @param stockOrderId
     * @return
     */
    @RequiresPermissions("ims:phone:view")
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @ResponseBody
    public String count(@RequestParam(value = "stockOrderId",required = false)String stockOrderId,
                        @RequestParam(value = "code",required = false)String code) {
        PhoneInfo phoneInfo = new PhoneInfo();
        if(StringUtils.isNotBlank(code)){
            phoneInfo.setCode(code);
        }
        if(StringUtils.isNotBlank(stockOrderId)){
            phoneInfo.setStockOrderId(stockOrderId);
        }
        int num = phoneInfoService.queryDeliverPhone(phoneInfo);
        return "{\"count\":\"" + num + "\"}";
    }
}
