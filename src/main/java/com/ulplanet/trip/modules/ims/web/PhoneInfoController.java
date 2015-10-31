package com.ulplanet.trip.modules.ims.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.PhoneInfoBo;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.entity.StockOrder;
import com.ulplanet.trip.modules.ims.service.PhoneInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/10/13.
 */
@Controller
@RequestMapping(value = "${adminPath}/ims/phone")
public class PhoneInfoController extends BaseController {
    @Resource
    private PhoneInfoService phoneInfoService;

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
    @RequestMapping(value = {"/list",""})
    public String list(PhoneInfo phoneInfo, HttpServletRequest request, HttpServletResponse response, Model model){
        Page<PhoneInfo> page = this.phoneInfoService.findPage(new Page<>(request, response), phoneInfo);
        List<PhoneInfoBo> phoneInfoBos = new ArrayList<>();
        for(PhoneInfo phone : page.getList()){
            PhoneInfoBo phoneInfoBo = new PhoneInfoBo(phone);
            phoneInfoBos.add(phoneInfoBo);
        }
        Page<PhoneInfoBo> page1 =  new Page<>(request, response);
        page1.setCount(page.getCount());
        page1.setPageNo(page.getPageNo());
        page1.setPageSize(page.getPageSize());
        page1.setList(phoneInfoBos);
        model.addAttribute("page",page1);
        return "modules/ims/phoneList";
    }

    /**
     * 保存
     * @param phoneInfo
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(PhoneInfo phoneInfo,Model model, RedirectAttributes redirectAttributes) {

        if (!beanValidator(model, phoneInfo)){
            return form(phoneInfo, model);
        }
        if(StringUtils.isNotBlank(phoneInfo.getId())){
            phoneInfo = this.phoneInfoService.updatePhoneInfo(phoneInfo);
        }else {
            phoneInfo = this.phoneInfoService.addPhoneInfo(phoneInfo);
        }

        if(phoneInfo!=null){
            addMessage(redirectAttributes,"保存手机信息"+phoneInfo.getCode()+"成功");
        }else{
            addMessage(redirectAttributes,"保存手机信息"+phoneInfo.getCode()+"失败");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?repage";
    }

    /**
     * 报废
     * @param phoneInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(PhoneInfo phoneInfo, RedirectAttributes redirectAttributes) {
        phoneInfo.setStatus(9000);
        phoneInfo = this.phoneInfoService.updatePhoneInfo(phoneInfo);
        if(phoneInfo!=null) {
            addMessage(redirectAttributes, "报废" + phoneInfo.getId() + "成功");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?repage";
    }

    /**
     * 退换货
     * @param phoneInfo
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/refund",method = RequestMethod.GET)
    public String refund(PhoneInfo phoneInfo, RedirectAttributes redirectAttributes) {
        phoneInfo = this.phoneInfoService.startRefund(phoneInfo);
        if(phoneInfo!=null) {
            addMessage(redirectAttributes, "退货" + phoneInfo.getId() + "成功");
        }
        return "redirect:" + adminPath + "/ims/phone/list/?repage";
    }

    /**
     * 查询表单
     * @param phoneInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(PhoneInfo phoneInfo,Model model) {
        model.addAttribute("order", phoneInfo);
        return "modules/ims/phoneForm";
    }

    /**
     * 获取指定订单下的到货手机数量
     * @param stockOrderId
     * @return
     */
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
