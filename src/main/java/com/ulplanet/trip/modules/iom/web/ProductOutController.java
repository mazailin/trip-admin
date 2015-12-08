package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.ProductOut;
import com.ulplanet.trip.modules.iom.service.ProductOutService;
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
 * 产品出库Controller
 * Created by zhangxd on 15/12/07.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/out")
public class ProductOutController extends BaseController {

    @Autowired
    private ProductOutService productOutService;

    @ModelAttribute
    public ProductOut get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productOutService.get(id);
        }else{
            return new ProductOut();
        }
    }

    @RequiresPermissions("iom:product:out:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductOut productOut, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductOut> page = productOutService.findPage(new Page<>(request, response), productOut);
        model.addAttribute("page", page);
        return "modules/iom/productOutList";
    }

    @RequiresPermissions("iom:product:out:view")
    @RequestMapping(value = "form")
    public String form(ProductOut productOut, Model model) {
        model.addAttribute("productOut", productOut);
        return "modules/iom/productOutForm";
    }

    @RequiresPermissions("iom:product:out:edit")
    @RequestMapping(value = "save")
    public String save(ProductOut productOut, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productOut)){
            return form(productOut, model);
        }
        productOutService.saveProductOut(productOut);
        addMessage(redirectAttributes, "保存产品出库单成功");
        return "redirect:" + adminPath + "/iom/product/out/list?repage";
    }

    @RequiresPermissions("iom:product:out:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductOut productOut, RedirectAttributes redirectAttributes) {
        productOutService.delete(productOut);
        addMessage(redirectAttributes, "删除产品出库单成功");
        return "redirect:" + adminPath + "/iom/product/out/list?repage";
    }

}
