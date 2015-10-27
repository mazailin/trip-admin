package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/group")
public class GroupController  extends BaseController {
    @Resource
    GroupService groupService;

    @ModelAttribute
    public Group get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return groupService.get(id);
        }else{
            return new Group();
        }
    }

    /**
     * 查询列表
     * @param request
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"/list",""})
    public String list(Group group, HttpServletRequest request, HttpServletResponse response, Model model){
        Page<Group> page = this.groupService.findPage(new Page<>(request, response), group);

        model.addAttribute("page",page);
        return "modules/tms/groupList";
    }

    /**
     * 保存
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Group group,Model model, RedirectAttributes redirectAttributes) {
        ResponseBo responseBo;
        if (!beanValidator(model, group)){
            return form(group, model);
        }
        if(StringUtils.isNotBlank(group.getId())){
            responseBo = this.groupService.updateGroup(group);
        }else {
            responseBo = this.groupService.addGroup(group);
        }

        addMessage(redirectAttributes,responseBo.getMsg());
        if(responseBo.getStatus()==1) {
            return "redirect:" + adminPath + "/tms/group/list/?repage";
        }
        return form(group, model);
    }

    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form(Group group,Model model) {
        model.addAttribute("group", group);
        return "modules/tms/groupForm";
    }


}
