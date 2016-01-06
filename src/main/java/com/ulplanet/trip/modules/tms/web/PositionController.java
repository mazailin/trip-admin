package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tms.entity.Position;
import com.ulplanet.trip.modules.tms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 位置Controller
 * Created by zhangxd on 15/12/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/position")
public class PositionController extends BaseController {

	@Autowired
	private PositionService positionService;
	
	@ModelAttribute
	public Position get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return positionService.get(id);
		}else{
			return new Position();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Position position, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("route", positionService.getRoute(position));
		return "modules/tms/positionList";
	}

    @RequestMapping(value = "refresh")
    @ResponseBody
    public List<Position> refresh(Position position, HttpServletRequest request, HttpServletResponse response, Model model) {
        return positionService.getRefreshRoute(position);
    }

    @RequestMapping(value = "group")
    public String group(String group, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("group", group);
        model.addAttribute("points", positionService.getGroupPosition(group));
        return "modules/tms/positionGroup";
    }

    @RequestMapping(value = "group/refresh")
    @ResponseBody
    public List<Position> refreshGroup(String group, HttpServletRequest request, HttpServletResponse response, Model model) {
        return positionService.getGroupPosition(group);
    }

}
