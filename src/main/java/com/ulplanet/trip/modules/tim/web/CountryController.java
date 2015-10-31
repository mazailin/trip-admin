package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Country;
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

/**
 * 国家Controller
 * Created by zhangxd on 15/10/23.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/country")
public class CountryController extends BaseController {

    @Autowired
    private CountryService countryService;

    @ModelAttribute
    public Country get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return countryService.get(id);
        }else{
            return new Country();
        }
    }

    @RequiresPermissions("tim:country:view")
    @RequestMapping(value = {"list", ""})
    public String list(Country country, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Country> page = countryService.findPage(new Page<>(request, response), country);
        model.addAttribute("page", page);
        return "modules/tim/countryList";
    }

    @RequiresPermissions("tim:country:view")
    @RequestMapping(value = "form")
    public String form(Country country, Model model) {
        model.addAttribute("country", country);
        return "modules/tim/countryForm";
    }

    @RequiresPermissions("tim:country:edit")
    @RequestMapping(value = "save")
    public String save(Country country, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, country)){
            return form(country, model);
        }
        countryService.saveCountry(country);
        addMessage(redirectAttributes, "保存国家'" + country.getName() + "'成功");
        return "redirect:" + adminPath + "/tim/country/list?repage";
    }

    @RequiresPermissions("tim:country:edit")
    @RequestMapping(value = "delete")
    public String delete(Country country, RedirectAttributes redirectAttributes) {
        countryService.delete(country);
        addMessage(redirectAttributes, "删除国家" + country.getName() + "成功");
        return "redirect:" + adminPath + "/tim/country/list?repage";
    }

    /**
     * 验证名称是否有效
     * @param oldName
     * @param name
     * @return
     */
    @ResponseBody
    @RequiresPermissions("tim:country:edit")
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (oldName != null && oldName.equals(name)) {
            return "true";
        } else if (name != null && countryService.getCountryByName(name) == null) {
            return "true";
        }
        return "false";
    }

}
