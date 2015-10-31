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

    @RequestMapping(value = "/get")
    @ResponseBody
    public JourneyDay get(@RequestParam(value = "id") String id) {
            return journeyDayService.get(id);
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
    @ResponseBody
    public Object save(JourneyDay journeyDay) {
        String cityName = journeyDay.getTitle().replaceAll(",","-");
        journeyDay.setTitle(cityName);
        if(StringUtils.isBlank(journeyDay.getId()))journeyDay.setIsNewRecord(false);
        this.journeyDayService.save(journeyDay);
        return new JourneyDayBo(journeyDay);

    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(JourneyDay journeyDay) {
        this.journeyDayService.delete(journeyDay);
        return "{\"status\":\"1\"}";
    }

    @RequestMapping(value = "/getTemplate")
    @ResponseBody
    public Object getTemplate(@RequestParam(value = "groupId")String groupId){
        List<JourneyDayBo> journeyDayBos = journeyDayService.queryList(groupId);
        return journeyDayBos;
    }


}
