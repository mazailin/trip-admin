package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tms.entity.Team;
import com.ulplanet.trip.modules.tms.service.TeamService;
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
 * 旅行团小组Controller
 * Created by zhangxd on 2015/10/27.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService teamService;

    @ModelAttribute
    public Team get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return teamService.get(id);
        }else{
            return new Team();
        }
    }

    @RequestMapping(value = {"/list",""})
    public String list(Team team, HttpServletRequest request, HttpServletResponse response, Model model){
        Page<Team> page = this.teamService.findPage(new Page<>(request, response), team);
        model.addAttribute("page",page);
        return "modules/tms/teamList";
    }

    @RequestMapping(value = "form")
    public String form(Team team, Model model) {
        model.addAttribute("preUserList", this.teamService.getPreUserList(team));
        model.addAttribute("team", team);
        return "modules/tms/teamForm";
    }

    @RequestMapping(value = "save")
    public String save(Team team, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, team)){
            return form(team, model);
        }
        try {
            this.teamService.saveTeam(team);
            addMessage(redirectAttributes, "保存小组'" + team.getName() + "'成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage());
        }
        return "redirect:" + adminPath + "/tms/team/list?repage&group.id=" + team.getGroup().getId();
    }

    @RequestMapping(value = "delete")
    public String delete(Team team, RedirectAttributes redirectAttributes) {
        try {
            this.teamService.deleteTeam(team);
            addMessage(redirectAttributes, "删除小组" + team.getName() + "成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, e.getMessage());
        }
        return "redirect:" + adminPath + "/tms/team/list?repage&group.id=" + team.getGroup().getId();
    }

}
