package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.service.ProductService;
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
 * 产品Controller
 * Created by zhangxd on 15/12/01.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @ModelAttribute
    public Product get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productService.get(id);
        }else{
            return new Product();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Product> page = productService.findPage(new Page<>(request, response), product);
        model.addAttribute("page", page);
        return "modules/iom/productList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(Product product, Model model) {
        model.addAttribute("product", product);
        return "modules/iom/productForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(Product product, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, product)){
            return form(product, model);
        }
        productService.saveProduct(product);
        addMessage(redirectAttributes, "保存产品'" + product.getName() + "'成功");
        return "redirect:" + adminPath + "/iom/product/list?repage";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "delete")
    public String delete(Product product, RedirectAttributes redirectAttributes) {
        productService.delete(product);
        addMessage(redirectAttributes, "删除产品" + product.getName() + "成功");
        return "redirect:" + adminPath + "/iom/product/list?repage";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"store"})
    public String store(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Product> page = productService.findPage(new Page<>(request, response), product);
        model.addAttribute("page", page);
        return "modules/iom/productStore";
    }

}
