package com.ulplanet.trip.modules.sys.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.sys.entity.Code;
import com.ulplanet.trip.modules.sys.entity.Dict;
import com.ulplanet.trip.modules.sys.service.CodeService;
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

/**
 * 编码Controller
 * Created by zhangxd on 15/11/30.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/code")
public class CodeController extends BaseController {

	@Autowired
	private CodeService codeService;

    @ModelAttribute
    public Code get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return codeService.get(id);
        }else{
            return new Code();
        }
    }
	
	@RequiresPermissions("sys:code:view")
	@RequestMapping(value = {"list", ""})
	public String list(Code code, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Code> page = codeService.findPage(new Page<>(request, response), code);
        model.addAttribute("page", page);
		return "modules/sys/codeList";
	}

    @RequiresPermissions("sys:code:view")
    @RequestMapping(value = "form")
    public String form(Code code, Model model) {
        model.addAttribute("code", code);
        return "modules/sys/codeForm";
    }

    @RequiresPermissions("sys:code:edit")
    @RequestMapping(value = "save")
    public String save(Code code, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, code)){
            return form(code, model);
        }
        codeService.update(code);
        addMessage(redirectAttributes, "保存编码规则成功'");
        return "redirect:" + adminPath + "/sys/code/?repage";
    }

}
