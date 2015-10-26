package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.CarRental;
import com.ulplanet.trip.modules.tim.entity.Country;
import com.ulplanet.trip.modules.tim.service.CarRentalService;
import com.ulplanet.trip.modules.tim.service.CountryService;
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
 * 租车Controller
 * Created by zhangxd on 15/10/23.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/car")
public class CarRentalController extends BaseController {

    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private CountryService countryService;

    @ModelAttribute
    public CarRental get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return carRentalService.get(id);
        }else{
            return new CarRental();
        }
    }

    @RequiresPermissions("tim:car:view")
    @RequestMapping(value = {"list", ""})
    public String list(CarRental carRental, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CarRental> page = carRentalService.findPage(new Page<>(request, response), carRental);
        model.addAttribute("page", page);
        List<Country> countryList = countryService.findList(new Country());
        countryList.add(0, new Country("", ""));
        model.addAttribute("countryList", countryList);
        return "modules/tim/carRentalList";
    }

    @RequiresPermissions("tim:car:view")
    @RequestMapping(value = "form")
    public String form(CarRental carRental, Model model) {
        List<Country> countryList = countryService.findList(new Country());
        model.addAttribute("countryList", countryList);
        model.addAttribute("carRental", carRental);
        return "modules/tim/carRentalForm";
    }

    @RequiresPermissions("tim:car:edit")
    @RequestMapping(value = "save")
    public String save(CarRental carRental, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, carRental)){
            return form(carRental, model);
        }
        carRentalService.saveCarRental(carRental);
        addMessage(redirectAttributes, "保存租车信息成功");
        return "redirect:" + adminPath + "/tim/car/list?repage";
    }

    @RequiresPermissions("tim:car:edit")
    @RequestMapping(value = "delete")
    public String delete(CarRental carRental, RedirectAttributes redirectAttributes) {
        carRentalService.delete(carRental);
        addMessage(redirectAttributes, "删除租车信息成功");
        return "redirect:" + adminPath + "/tim/car/list?repage";
    }

}
