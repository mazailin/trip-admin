package com.ulplanet.trip.modules.iom.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.iom.entity.Product;
import com.ulplanet.trip.modules.iom.entity.ProductDetail;
import com.ulplanet.trip.modules.iom.entity.RentDetail;
import com.ulplanet.trip.modules.iom.service.ProductDetailService;
import com.ulplanet.trip.modules.iom.service.ProductService;
import com.ulplanet.trip.modules.iom.service.RentDetailService;
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
import java.util.List;

/**
 * 产品租赁明细Controller
 * Created by zhangxd on 15/12/17.
 */
@Controller
@RequestMapping(value = "${adminPath}/iom/product/rent/detail")
public class RentDetailController extends BaseController {

    @Autowired
    private RentDetailService rentDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

    @ModelAttribute
    public RentDetail get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return rentDetailService.get(id);
        }else{
            return new RentDetail();
        }
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = {"list", ""})
    public String list(RentDetail rentDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RentDetail> page = rentDetailService.findPage(new Page<>(request, response), rentDetail);
        model.addAttribute("page", page);
        return "modules/iom/rentDetailList";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "form")
    public String form(RentDetail rentDetail, Model model) {
        List<Product> productList = productService.findNUseDetailList(new Product());
        model.addAttribute("productList", productList);
        model.addAttribute("rentDetail", rentDetail);
        return "modules/iom/rentDetailForm";
    }

    @RequiresPermissions("iom:product:view")
    @RequestMapping(value = "yform")
    public String yform(RentDetail rentDetail, Model model) {
        List<ProductDetail> productDetailList = productDetailService.findAvlList(new ProductDetail());
        model.addAttribute("productDetailList", productDetailList);
        model.addAttribute("rentDetail", rentDetail);
        return "modules/iom/rentDetailYForm";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "save")
    public String save(RentDetail rentDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, rentDetail)){
            return form(rentDetail, model);
        }
        rentDetailService.saveRentDetail(rentDetail);
        addMessage(redirectAttributes, "保存产品租赁明细'" + rentDetail.getProduct().getName() + "'成功");
        return "redirect:" + adminPath + "/iom/product/rent/detail/list?rent.id="+ rentDetail.getRent().getId() +"&repage";
    }

    @RequiresPermissions("iom:product:edit")
    @RequestMapping(value = "ysave")
    public String ysave(RentDetail rentDetail, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, rentDetail)){
            return yform(rentDetail, model);
        }
        rentDetailService.saveYRentDetail(rentDetail);
        addMessage(redirectAttributes, "保存产品租赁明细'" + rentDetail.getProduct().getName() + "'成功");
        return "redirect:" + adminPath + "/iom/product/rent/detail/list?rent.id="+ rentDetail.getRent().getId() +"&repage";
    }

}
