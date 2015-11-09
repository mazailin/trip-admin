package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Hotel;
import com.ulplanet.trip.modules.tim.service.HotelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by makun on 2015/11/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/hotel")
public class HotelController extends BaseController {

    @Resource
    private HotelService hotelService;

    @ModelAttribute
    public Hotel get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return hotelService.get(id);
        }else{
            return new Hotel();
        }
    }

    @RequiresPermissions("tim:hotel:view")
    @RequestMapping(value = {"list", ""})
    public String list(Hotel hotel, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Hotel> page = hotelService.findPage(new Page<>(request, response), hotel);
        model.addAttribute("page", page);
        return "modules/tim/hotelList";
    }

    @RequiresPermissions("tim:hotel:view")
    @RequestMapping(value = "form")
    public String form(Hotel hotel, Model model) {
        model.addAttribute("hotel", hotel);
        return "modules/tim/hotelForm";
    }

    @RequiresPermissions("tim:hotel:edit")
    @RequestMapping(value = "save")
    public String save(Hotel hotel, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hotel)){
            return form(hotel, model);
        }
        hotelService.saveHotel(hotel);
        addMessage(redirectAttributes, "保存酒店'" + hotel.getName() + "'成功");
        return "redirect:" + adminPath + "/tim/hotel/list?repage";
    }

    @RequiresPermissions("tim:hotel:edit")
    @RequestMapping(value = "delete")
    public String delete(Hotel hotel, RedirectAttributes redirectAttributes) {
        hotelService.delete(hotel);
        addMessage(redirectAttributes, "删除酒店" + hotel.getName() + "成功");
        return "redirect:" + adminPath + "/tim/hotel/list?repage";
    }
}
