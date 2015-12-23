package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.ProductDiscard;
import com.ulplanet.trip.modules.iom.service.ProductDiscardService;
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
 * 产品报废Controller
 * Created by zhangxd on 15/12/15.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/discard")
public class ProductDiscardController extends BaseController {

    @Autowired
    private ProductDiscardService productDiscardService;

    @ModelAttribute
    public ProductDiscard get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productDiscardService.get(id);
        }else{
            return new ProductDiscard();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductDiscard productDiscard, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductDiscard> page = productDiscardService.findPage(new Page<>(request, response), productDiscard);
        model.addAttribute("page", page);
        return "modules/iom/productDiscardList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(ProductDiscard productDiscard, Model model) {
        model.addAttribute("productDiscard", productDiscard);
        return "modules/iom/productDiscardForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(ProductDiscard productDiscard, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productDiscard)){
            return form(productDiscard, model);
        }
        productDiscardService.saveProductDiscard(productDiscard);
        addMessage(redirectAttributes, "报废产品'" + productDiscard.getProduct().getName() + "'成功");
        return "redirect:" + adminPath + "/iom/product/discard/list?repage";
    }

}
