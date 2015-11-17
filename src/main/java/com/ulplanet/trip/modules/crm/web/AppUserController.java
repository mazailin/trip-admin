package com.ulplanet.trip.modules.crm.web;

import com.qiniu.util.StringMap;
import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.crm.entity.AppUser;
import com.ulplanet.trip.modules.crm.service.AppUserService;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.utils.QiniuUploadUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@ModelAttribute
	public AppUser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return appUserService.get(id);
		}else{
			return new AppUser();
		}
	}
	
	@RequiresPermissions("crm:appuser:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppUser appUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AppUser> page = appUserService.findPage(new Page<>(request, response), appUser);
        model.addAttribute("page", page);
		return "modules/crm/appUserList";
	}

	@RequiresPermissions("crm:appuser:edit")
	@RequestMapping(value = "/save")
	public String save(AppUser appUser,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appUser)){
			return form(appUser, model);
		}
		ResponseBo responseBo = appUserService.saveAppUser(appUser);
		addMessage(redirectAttributes, responseBo.getMsg());
		if(responseBo.getStatus()==1) {
			appUserService.refresh();
			return "redirect:" + adminPath + "/crm/appuser/list?repage";
		}
		return form(appUser, model);
	}

	@RequiresPermissions("crm:appuser:edit")
	@RequestMapping(value = "/delete")
	public String delete(AppUser appUser,Model model, RedirectAttributes redirectAttributes) {
		ResponseBo responseBo = this.appUserService.deleteUser(appUser);
		addMessage(redirectAttributes, responseBo.getMsg());
		if(responseBo.getStatus()==1) {
			appUserService.refresh();
			return "redirect:" + adminPath + "/crm/appuser/list/?repage";
		}
		return form(appUser, model);
	}

	@RequiresPermissions("crm:appuser:view")
	@RequestMapping(value = "/form",method = RequestMethod.GET)
	public String form(AppUser appUser,Model model) {
		model.addAttribute("appUser", appUser);
		return "modules/crm/appUserForm";
	}

	@RequestMapping(value = "/hasPassport",method = RequestMethod.GET)
	@ResponseBody
	public AppUser hasPassport(@RequestParam(value = "passport")String passport){
		return appUserService.hasPassport(passport);
	}
}

