package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Flight;
import com.ulplanet.trip.modules.tim.service.FlightService;
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
@RequestMapping(value = "${adminPath}/tim/flight")
public class FlightController extends BaseController {

    @Resource
    private FlightService flightService;

    @ModelAttribute
    public Flight get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return flightService.get(id);
        }else{
            return new Flight();
        }
    }

    @RequiresPermissions("tim:flight:view")
    @RequestMapping(value = {"list", ""})
    public String list(Flight flight, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Flight> page = flightService.findPage(new Page<>(request, response), flight);
        model.addAttribute("page", page);
        return "modules/tim/flightList";
    }

    @RequiresPermissions("tim:flight:view")
    @RequestMapping(value = "form")
    public String form(Flight flight, Model model) {
        model.addAttribute("flight", flight);
        return "modules/tim/flightForm";
    }

    @RequiresPermissions("tim:flight:edit")
    @RequestMapping(value = "save")
    public String save(Flight flight, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, flight)){
            return form(flight, model);
        }
        flightService.saveFlight(flight);
        addMessage(redirectAttributes, "保存航班'" + flight.getFlightNo() + "'成功");
        return "redirect:" + adminPath + "/tim/flight/list?repage";
    }

    @RequiresPermissions("tim:flight:edit")
    @RequestMapping(value = "delete")
    public String delete(Flight flight, RedirectAttributes redirectAttributes) {
        flightService.delete(flight);
        addMessage(redirectAttributes, "删除航班" + flight.getFlightNo() + "成功");
        return "redirect:" + adminPath + "/tim/flight/list?repage";
    }
}
