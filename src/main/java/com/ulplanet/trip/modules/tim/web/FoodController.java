package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Food;
import com.ulplanet.trip.modules.tim.entity.FoodFile;
import com.ulplanet.trip.modules.tim.service.FoodService;
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
 * 美食Controller
 * Created by zhangxd on 15/10/23.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/food")
public class FoodController extends BaseController {

    @Autowired
    private FoodService foodService;

    @ModelAttribute
     public Food get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return foodService.get(id);
        }else{
            return new Food();
        }
    }

    @ModelAttribute
    public FoodFile getFile(@RequestParam(required=false) String fileId) {
        if (StringUtils.isNotBlank(fileId)){
            return foodService.getFileById(fileId);
        }else{
            return new FoodFile();
        }
    }

    @RequiresPermissions("tim:food:view")
    @RequestMapping(value = {"list", ""})
    public String list(Food food, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Food> page = foodService.findPage(new Page<>(request, response), food);
        model.addAttribute("page", page);
        return "modules/tim/foodList";
    }

    @RequiresPermissions("tim:food:view")
    @RequestMapping(value = "form")
    public String form(Food food, Model model) {
        model.addAttribute("food", food);
        return "modules/tim/foodForm";
    }

    @RequiresPermissions("tim:food:edit")
    @RequestMapping(value = "save")
    public String save(Food food, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, food)){
            return form(food, model);
        }

        boolean isNew = StringUtils.isEmpty(food.getId());
        food = foodService.saveFood(food);
        addMessage(redirectAttributes, "保存美食'" + food.getName() + "'成功");
        if (isNew) {
            return "redirect:" + adminPath + "/tim/food/image?repage&id=" + food.getId();
        }
        return "redirect:" + adminPath + "/tim/food/list?repage";
    }

    @RequiresPermissions("tim:food:edit")
    @RequestMapping(value = "delete")
    public String delete(Food food, RedirectAttributes redirectAttributes) {
        foodService.delete(food);
        addMessage(redirectAttributes, "删除美食" + food.getName() + "成功");
        return "redirect:" + adminPath + "/tim/food/list?repage";
    }

    @RequiresPermissions("tim:food:view")
    @RequestMapping(value = "image")
    public String image(Food food, Model model) {
        model.addAttribute("food", food);
        return "modules/tim/foodImage";
    }

    @RequiresPermissions("tim:food:edit")
    @RequestMapping(value = "uploadData")
    @ResponseBody
    public Map<String, Object> uploadData(FoodFile foodFile, @RequestParam("file") MultipartFile file) {
        return foodService.uploadData(foodFile, file);
    }

    @RequiresPermissions("tim:food:view")
    @RequestMapping(value="findFoodFiles")
    @ResponseBody
    public Map<String, Object> getUpload(Food food) {
        return foodService.findFoodFiles(food.getId());
    }

    @RequiresPermissions("tim:food:edit")
    @RequestMapping(value="deleteFile")
    @ResponseBody
    public void deleteFile(FoodFile foodFile) {
        foodService.deleteFoodFile(foodFile);
    }

    @RequiresPermissions("tim:food:edit")
    @RequestMapping(value="updateFile")
    @ResponseBody
    public void updateFile(FoodFile foodFile) {
        foodService.updateFoodFile(foodFile);
    }

}
