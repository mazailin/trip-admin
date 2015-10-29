package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.bo.JourneyDayBo;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.service.JourneyDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/journeyDay")
public class JourneyDayController  extends BaseController {

    @Resource
    private JourneyDayService journeyDayService;

    @ModelAttribute
    public JourneyDay get(@RequestParam(required=false) String id,@RequestParam(value = "groupId",required=false) String group) {
        if (StringUtils.isNotBlank(id)){
            return journeyDayService.get(id);
        }else{
            JourneyDay journeyDay = new JourneyDay();
            if(StringUtils.isNotBlank(group)){
                journeyDay.setGroupId(group);
            }
            return journeyDay;
        }
    }

    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Object queryList(@RequestParam(value = "id")String id){
        return journeyDayService.queryList(id);
    }

    @RequestMapping(value = {"/list",""})
    public String list(JourneyDay journeyDay,  Model model, RedirectAttributes redirectAttributes){
        if(journeyDay.getGroupId()==null){
            addMessage(redirectAttributes, "groupId为空");
            return "redirect:" + adminPath + "/tms/group/list/?repage";
        }
        List<JourneyDayBo> journeyDayBos = journeyDayService.queryList(journeyDay.getGroupId());
        model.addAttribute("list", journeyDayBos);
        model.addAttribute("groupId", journeyDay.getGroupId());
        return "modules/tms/journeyDayList";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(JourneyDay journeyDay,Model model, RedirectAttributes redirectAttributes) {
        this.journeyDayService.save(journeyDay);
        addMessage(redirectAttributes, "添加成功");
        return "redirect:" + adminPath + "/tms/journeyDay/list/?groupId=" + journeyDay.getGroupId() + "&repage";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(JourneyDay journeyDay) {
        this.journeyDayService.delete(journeyDay);
        return "{\"status\":\"1\"}";
    }


}
