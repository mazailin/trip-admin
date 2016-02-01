package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.service.ProductDetailService;
import com.ulplanet.trip.modules.iom.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品明细Controller
 * Created by zhangxd on 15/12/01.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/detail")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductService productService;

    @ModelAttribute
    public ProductDetail get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return productDetailService.get(id);
        }else{
            return new ProductDetail();
        }
    }

    @RequiresPermissions("iom:product:detail:view")
    @RequestMapping(value = {"list", ""})
    public String list(ProductDetail productDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ProductDetail> page = productDetailService.findPage(new Page<>(request, response), productDetail);
        model.addAttribute("page", page);
        return "modules/iom/proDetailList";
    }

    @RequiresPermissions("iom:product:detail:view")
    @RequestMapping(value = "form")
    public String form(ProductDetail productDetail, Model model) {
        List<Product> productList = productService.findUseDetailList(new Product());
        model.addAttribute("productList", productList);
        model.addAttribute("productDetail", productDetail);
        return "modules/iom/proDetailForm";
    }

    @RequiresPermissions("iom:product:detail:view")
    @RequestMapping(value = "in/list")
    public String inList(ProductDetail productDetail, HttpServletRequest request, HttpServletResponse response, String inId, Model model) {
        Page<ProductDetail> page = productDetailService.findInDetail(new Page<>(request, response), productDetail, inId);
        model.addAttribute("page", page);
        model.addAttribute("inId", inId);
        return "modules/iom/proInDetailList";
    }

    @RequiresPermissions("iom:product:detail:view")
    @RequestMapping(value = "in/form")
    public String inForm(ProductDetail productDetail, String inId, Model model) {
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("inId", inId);
        return "modules/iom/proInDetailForm";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "save")
    public String save(ProductDetail productDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productDetail)) {
            return form(productDetail, model);
        }
        productDetailService.saveProductDetail(productDetail);
        addMessage(redirectAttributes, "保存产品明细'" + productDetail.getDevice() + "'成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "in/save")
    public String inSave(ProductDetail productDetail, String inId, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, productDetail)) {
            return form(productDetail, model);
        }
        productDetailService.saveProductDetailIn(inId, productDetail);
        addMessage(redirectAttributes, "保存产品明细'" + productDetail.getDevice() + "'成功");
        redirectAttributes.addAttribute("inId", inId);
        redirectAttributes.addAttribute("product.id", productDetail.getProduct().getId());
        redirectAttributes.addAttribute("product.name", productDetail.getProduct().getName());
        redirectAttributes.addAttribute("product.code", productDetail.getProduct().getCode());
        return "redirect:" + adminPath + "/iom/product/detail/in/list";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "test")
    public String test(ProductDetail productDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        productDetailService.saveTestStatus(productDetail);
        addMessage(redirectAttributes, "产品明细'" + productDetail.getDevice() + "'测试成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "sale")
    public String sale(ProductDetail productDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        productDetailService.saveSaleStatus(productDetail);
        addMessage(redirectAttributes, "产品明细'" + productDetail.getDevice() + "'上架成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "repair")
    public String repair(ProductDetail productDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        productDetailService.saveRepairStatus(productDetail);
        addMessage(redirectAttributes, "产品明细'" + productDetail.getDevice() + "'送修成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductDetail productDetail, RedirectAttributes redirectAttributes) {
        productDetailService.delete(productDetail);
        addMessage(redirectAttributes, "删除产品明细" + productDetail.getDevice() + "成功");
        return "redirect:" + adminPath + "/iom/product/detail/list?repage";
    }

    /**
     * 验证设备是否有效
     * @param oldDevice
     * @param productDetail
     * @return
     */
    @ResponseBody
    @RequiresPermissions("iom:product:detail:edit")
    @RequestMapping(value = "checkDevice")
    public String checkName(String oldDevice, ProductDetail productDetail) {
        if (oldDevice != null && oldDevice.equals(productDetail.getDevice())) {
            return "true";
        } else if (productDetail.getDevice() != null && productDetailService.getDetailByDevice(productDetail) == null) {
            return "true";
        }
        return "false";
    }

}
