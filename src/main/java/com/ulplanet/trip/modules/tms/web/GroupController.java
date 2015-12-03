package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.crm.entity.Customer;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.entity.VersionTag;
import com.ulplanet.trip.modules.sys.service.VersionTagService;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.service.GroupService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/group")
public class GroupController  extends BaseController {
    @Resource
    GroupService groupService;
    @Resource
    VersionTagService versionTagService;

    @ModelAttribute
    public Group get(@RequestParam(required=false) String id) {

        Group group = new Group();

        if (StringUtils.isNotBlank(id)) {
            group = groupService.get(id);
        }

        return group;
    }

    /**
     * 查询列表
     * @param request
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("tms:group:view")
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
    @RequiresPermissions("tms:group:edit")
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

        addMessage(redirectAttributes, responseBo.getMsg());
        if(responseBo.getStatus()==1) {
            versionTagService.save(new VersionTag(group.getId(),1));
            return "redirect:" + adminPath + "/tms/group/list/?repage";
        }
        return form(group, model);
    }

    @RequestMapping(value = "/delete")
    @RequiresPermissions("tms:group:edit")
    public String delete(Group group,Model model, RedirectAttributes redirectAttributes) {
        ResponseBo responseBo = this.groupService.deleteGroup(group);
        addMessage(redirectAttributes,responseBo.getMsg());
        return "redirect:" + adminPath + "/tms/group/list/?repage";
    }

    @RequestMapping(value = "form",method = RequestMethod.GET)
    @RequiresPermissions("tms:group:view")
    public String form(Group group,Model model) {
        List<Customer> customers = groupService.getCustomer();
        group.setCustomers(customers);
        model.addAttribute("group", group);
        return "modules/tms/groupForm";
    }


}
