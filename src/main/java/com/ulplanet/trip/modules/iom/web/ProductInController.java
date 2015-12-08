package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.ProductIn;
import com.ulplanet.trip.modules.iom.service.ProductInService;
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
 * 产品入库Controller
 * Created by zhangxd on 15/12/07.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/in")
public class ProductInController extends BaseController {

    @Autowired
    private ProductInService productInService;

    @ModelAttribute
    public ProductIn get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productInService.get(id);
        }else{
            return new ProductIn();
        }
    }

    @RequiresPermissions("iom:product:in:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductIn productIn, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductIn> page = productInService.findPage(new Page<>(request, response), productIn);
        model.addAttribute("page", page);
        return "modules/iom/productInList";
    }

    @RequiresPermissions("iom:product:in:view")
    @RequestMapping(value = "form")
    public String form(ProductIn productIn, Model model) {
        model.addAttribute("productIn", productIn);
        return "modules/iom/productInForm";
    }

    @RequiresPermissions("iom:product:in:edit")
    @RequestMapping(value = "save")
    public String save(ProductIn productIn, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productIn)){
            return form(productIn, model);
        }
        productInService.saveProductIn(productIn);
        addMessage(redirectAttributes, "保存产品入库单成功");
        return "redirect:" + adminPath + "/iom/product/in/list?repage";
    }

    @RequiresPermissions("iom:product:in:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductIn productIn, RedirectAttributes redirectAttributes) {
        productInService.delete(productIn);
        addMessage(redirectAttributes, "删除产品入库单成功");
        return "redirect:" + adminPath + "/iom/product/in/list?repage";
    }

}
