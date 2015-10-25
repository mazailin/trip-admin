package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.City;
import com.ulplanet.trip.modules.tim.entity.Country;
import com.ulplanet.trip.modules.tim.service.CityService;
import com.ulplanet.trip.modules.tim.service.CountryService;
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
 * 城市Controller
 * Created by zhangxd on 15/10/23.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/city")
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @ModelAttribute
    public City get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return cityService.get(id);
        }else{
            return new City();
        }
    }

    @RequiresPermissions("tim:city:view")
    @RequestMapping(value = {"list", ""})
    public String list(City city, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<City> page = cityService.findPage(new Page<>(request, response), city);
        model.addAttribute("page", page);
        List<Country> countryList = countryService.findList(new Country());
        countryList.add(0, new Country("", SEARCHER_ITEM_ALL));
        model.addAttribute("countryList", countryList);
        return "modules/tim/cityList";
    }

    @RequiresPermissions("tim:city:view")
    @RequestMapping(value = "form")
    public String form(City city, Model model) {
        List<Country> countryList = countryService.findList(new Country());
        model.addAttribute("countryList", countryList);
        model.addAttribute("city", city);
        return "modules/tim/cityForm";
    }

    @RequiresPermissions("tim:city:edit")
    @RequestMapping(value = "save")
    public String save(City city, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, city)){
            return form(city, model);
        }
        cityService.saveCity(city);
        addMessage(redirectAttributes, "保存城市'" + city.getName() + "'成功");
        return "redirect:" + adminPath + "/tim/city/list?repage";
    }

    @RequiresPermissions("tim:city:edit")
    @RequestMapping(value = "delete")
    public String delete(City city, RedirectAttributes redirectAttributes) {
        cityService.delete(city);
        addMessage(redirectAttributes, "删除城市" + city.getName() + "成功");
        return "redirect:" + adminPath + "/tim/city/list?repage";
    }

    /**
     * 验证名称是否有效
     * @param oldName
     * @param city
     * @return
     */
    @ResponseBody
    @RequiresPermissions("tim:city:edit")
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, City city) {
        String name = city.getName();
        if (oldName != null && oldName.equals(name)) {
            return "true";
        } else if (name != null && cityService.getCityByName(city) == null) {
            return "true";
        }
        return "false";
    }

}
