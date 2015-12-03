package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.service.ProductDetailService;
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
 * 产品明细Controller
 * Created by zhangxd on 15/12/01.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/detail")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductDetailService productDetailService;

    @ModelAttribute
    public ProductDetail get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productDetailService.get(id);
        }else{
            return new ProductDetail();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductDetail productDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductDetail> page = productDetailService.findPage(new Page<>(request, response), productDetail);
        model.addAttribute("page", page);
        return "modules/iom/proDetailList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(ProductDetail productDetail, Model model) {
        model.addAttribute("productDetail", productDetail);
        return "modules/iom/proDetailForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(ProductDetail productDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productDetail)){
            return form(productDetail, model);
        }
        productDetailService.saveProductDetail(productDetail);
        addMessage(redirectAttributes, "保存产品明细'" + productDetail.getDevice() + "'成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductDetail productDetail, RedirectAttributes redirectAttributes) {
        productDetailService.delete(productDetail);
        addMessage(redirectAttributes, "删除产品明细" + productDetail.getDevice() + "成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

}
