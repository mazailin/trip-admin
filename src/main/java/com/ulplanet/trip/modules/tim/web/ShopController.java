package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Shop;
import com.ulplanet.trip.modules.tim.entity.ShopFile;
import com.ulplanet.trip.modules.tim.service.ShopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 购物Controller
 * Created by zhangxd on 15/11/08.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/shop")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @ModelAttribute
     public Shop get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return shopService.get(id);
        }else{
            return new Shop();
        }
    }

    @ModelAttribute
    public ShopFile getFile(@RequestParam(required=false) String fileId) {
        if (StringUtils.isNotBlank(fileId)){
            return shopService.getFileById(fileId);
        }else{
            return new ShopFile();
        }
    }

    @RequiresPermissions("tim:shop:view")
    @RequestMapping(value = {"list", ""})
    public String list(Shop shop, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Shop> page = shopService.findPage(new Page<>(request, response), shop);
        model.addAttribute("page", page);
        return "modules/tim/shopList";
    }

    @RequiresPermissions("tim:shop:view")
    @RequestMapping(value = "form")
    public String form(Shop shop, Model model) {
        model.addAttribute("shop", shop);
        return "modules/tim/shopForm";
    }

    @RequiresPermissions("tim:shop:edit")
    @RequestMapping(value = "save")
    public String save(Shop shop, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, shop)){
            return form(shop, model);
        }

        boolean isNew = StringUtils.isEmpty(shop.getId());
        shop = shopService.saveShop(shop);
        addMessage(redirectAttributes, "保存购物'" + shop.getName() + "'成功");
        if (isNew) {
            return "redirect:" + adminPath + "/tim/shop/image?repage&id=" + shop.getId();
        }
        return "redirect:" + adminPath + "/tim/shop/list?repage";
    }

    @RequiresPermissions("tim:shop:edit")
    @RequestMapping(value = "delete")
    public String delete(Shop shop, RedirectAttributes redirectAttributes) {
        shopService.delete(shop);
        addMessage(redirectAttributes, "删除购物" + shop.getName() + "成功");
        return "redirect:" + adminPath + "/tim/shop/list?repage";
    }

    @RequiresPermissions("tim:shop:view")
    @RequestMapping(value = "image")
    public String image(Shop shop, Model model) {
        model.addAttribute("shop", shop);
        return "modules/tim/shopImage";
    }

    @RequiresPermissions("tim:shop:edit")
    @RequestMapping(value = "uploadData")
    @ResponseBody
    public Map<String, Object> uploadData(ShopFile shopFile, @RequestParam("file") MultipartFile file) {
        return shopService.uploadData(shopFile, file);
    }

    @RequiresPermissions("tim:shop:view")
    @RequestMapping(value="findShopFiles")
    @ResponseBody
    public Map<String, Object> getUpload(Shop shop) {
        return shopService.findShopFiles(shop.getId());
    }

    @RequiresPermissions("tim:shop:edit")
    @RequestMapping(value="deleteFile")
    @ResponseBody
    public void deleteFile(ShopFile shopFile) {
        shopService.deleteShopFile(shopFile);
    }

    @RequiresPermissions("tim:shop:edit")
    @RequestMapping(value="updateFile")
    @ResponseBody
    public void updateFile(ShopFile shopFile) {
        shopService.updateShopFile(shopFile);
    }

}
