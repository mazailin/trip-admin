package com.ulplanet.trip.modules.crm.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.crm.service.AppUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移动端用户Controller
 * Created by zhangxd on 15/10/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/appuser")
public class AppUserController extends BaseController {

	@Autowired
	private AppUserService appUserService;
	
	@RequiresPermissions("crm:appuser:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppUser appUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AppUser> page = appUserService.findPage(new Page<>(request, response), appUser);
        model.addAttribute("page", page);
		return "modules/crm/appUserList";
	}

}
