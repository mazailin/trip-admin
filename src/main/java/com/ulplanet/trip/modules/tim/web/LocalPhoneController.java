package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Country;
import com.ulplanet.trip.modules.tim.entity.LocalPhone;
import com.ulplanet.trip.modules.tim.service.CountryService;
import com.ulplanet.trip.modules.tim.service.LocalPhoneService;
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
 * 当地电话Controller
 * Created by zhangxd on 16/01/11.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/phone")
public class LocalPhoneController extends BaseController {

    @Autowired
    private LocalPhoneService localPhoneService;
    @Autowired
    private CountryService countryService;

    @ModelAttribute
    public LocalPhone get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return localPhoneService.get(id);
        }else{
            return new LocalPhone();
        }
    }

    @RequiresPermissions("tim:phone:view")
    @RequestMapping(value = {"list", ""})
    public String list(LocalPhone localPhone, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<LocalPhone> page = localPhoneService.findPage(new Page<>(request, response), localPhone);
        model.addAttribute("page", page);
        List<Country> countryList = countryService.findList(new Country());
        countryList.add(0, new Country("", ""));
        model.addAttribute("countryList", countryList);
        return "modules/tim/localPhoneList";
    }

    @RequiresPermissions("tim:phone:view")
    @RequestMapping(value = "form")
    public String form(LocalPhone localPhone, Model model) {
        List<Country> countryList = countryService.findList(new Country());
        model.addAttribute("countryList", countryList);
        model.addAttribute("localPhone", localPhone);
        return "modules/tim/localPhoneForm";
    }

    @RequiresPermissions("tim:phone:edit")
    @RequestMapping(value = "save")
    public String save(LocalPhone localPhone, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, localPhone)){
            return form(localPhone, model);
        }
        localPhoneService.saveLocalPhone(localPhone);
        addMessage(redirectAttributes, "保存当地电话成功");
        return "redirect:" + adminPath + "/tim/phone/list?repage";
    }

    @RequiresPermissions("tim:phone:edit")
    @RequestMapping(value = "delete")
    public String delete(LocalPhone localPhone, RedirectAttributes redirectAttributes) {
        localPhoneService.delete(localPhone);
        addMessage(redirectAttributes, "删除当地电话成功");
        return "redirect:" + adminPath + "/tim/phone/list?repage";
    }

}
