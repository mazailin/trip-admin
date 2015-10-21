package com.ulplanet.trip.modules.sys.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.sys.entity.Log;
import com.ulplanet.trip.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志Controller
 * Created by zhangxd on 15/10/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<>(request, response), log);
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}

}
