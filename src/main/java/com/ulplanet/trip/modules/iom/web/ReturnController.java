package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.Return;
import com.ulplanet.trip.modules.iom.service.ReturnService;
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
 * 产品归还Controller
 * Created by zhangxd on 15/12/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/return")
public class ReturnController extends BaseController {

    @Autowired
    private ReturnService returnService;

    @ModelAttribute
    public Return get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return returnService.get(id);
        }else{
            return new Return();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(Return re, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Return> page = returnService.findPage(new Page<>(request, response), re);
        model.addAttribute("page", page);
        return "modules/iom/returnList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(Return re, Model model) {
        model.addAttribute("return", re);
        return "modules/iom/returnForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(Return re, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, re)){
            return form(re, model);
        }
        returnService.saveReturn(re);
        addMessage(redirectAttributes, "保存产品归还单'" + re.getCode() + "'成功");
        return "redirect:" + adminPath + "/iom/product/return/list?repage";
    }

}
