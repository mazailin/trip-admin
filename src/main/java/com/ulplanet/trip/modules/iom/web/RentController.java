package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.Rent;
import com.ulplanet.trip.modules.iom.service.RentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 产品租赁Controller
 * Created by zhangxd on 15/12/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/rent")
public class RentController extends BaseController {

    @Autowired
    private RentService rentService;

    @ModelAttribute
    public Rent get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return rentService.get(id);
        }else{
            return new Rent();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(Rent rent, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Rent> page = rentService.findPage(new Page<>(request, response), rent);
        model.addAttribute("page", page);
        return "modules/iom/rentList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(Rent rent, Model model) {
        model.addAttribute("rent", rent);
        return "modules/iom/rentForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(Rent rent, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, rent)){
            return form(rent, model);
        }
        rentService.saveRent(rent);
        addMessage(redirectAttributes, "保存产品租赁单'" + rent.getCode() + "'成功");
        return "redirect:" + adminPath + "/iom/product/rent/list?repage";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "delete")
    public String delete(Rent rent, RedirectAttributes redirectAttributes) {
        rentService.delete(rent);
        addMessage(redirectAttributes, "删除产品租赁单" + rent.getCode() + "成功");
        return "redirect:" + adminPath + "/iom/product/rent/list?repage";
    }

}
