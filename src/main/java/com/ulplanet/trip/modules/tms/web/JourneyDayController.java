package com.ulplanet.trip.modules.tms.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.bo.*;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import com.ulplanet.trip.modules.tms.service.GroupService;
import com.ulplanet.trip.modules.tms.service.JourneyDayService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    private GroupService groupService;

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
    public JourneyDay getJourneyDay(@RequestParam(value = "id") String id,@RequestParam String groupId) {
            if(EhCacheUtils.get(groupId,id)!=null){
                return (JourneyDay)EhCacheUtils.get(groupId,id);
            }
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
        Group group = new Group();
        group.setId(journeyDay.getGroupId());
        List<Group> groups = groupService.getGroupList(group);
        model.addAttribute("list", journeyDayBos);
        model.addAttribute("groupList",groups);
        model.addAttribute("groupId", journeyDay.getGroupId());
        return "modules/tms/journeyDayList";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Object save(JourneyDay journeyDay) {
        String cityName = journeyDay.getTitle().replaceAll(",", "-");
        journeyDay.setTitle(cityName);
        if(StringUtils.isBlank(journeyDay.getId()))journeyDay.setIsNewRecord(false);
        this.journeyDayService.save(journeyDay);
        return new JourneyDayBo(journeyDay);

    }

    /**
     * 暂存信息
     * @param journeyDay
     * @return
     */
    @RequestMapping(value = "/saveTemp",method = RequestMethod.POST)
    @ResponseBody
    public Object saveTemp(JourneyDay journeyDay) {
        String cityName = journeyDay.getTitle().replaceAll(",", "-");
        journeyDay.setTitle(cityName);
        if(StringUtils.isBlank(journeyDay.getId())){
            journeyDay.setIsNewRecord(false);
            journeyDay.preInsert();
        }
        EhCacheUtils.put(journeyDay.getGroupId(),journeyDay.getId(),journeyDay);
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


//    @RequestMapping(value = "/sort",method = RequestMethod.POST)
//    @ResponseBody
//    public Object sort(JourneyBo journeyBo){
//        journeyDayService.sort(journeyBo);
//        return new ResponseBo(1,"success");
//    }

    /**
     * 行程保存并排序
     * @param json
     * @return
     */
    @RequestMapping(value = "/sort",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false)
    public Object sort(@RequestParam("json")String json){
        json = json.replaceAll("&quot;","\"");
        JourneyBo journeyBo = JSONObject.parseObject(json,JourneyBo.class);
        journeyDayService.sort(journeyBo);
        return new ResponseBo(1,"success");
    }

    @RequestMapping(value = "/copy",method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = false)
    public Object copy(JourneyDay journeyDay){

        return journeyDayService.copy(journeyDay);
    }

    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public String preview(@RequestParam("json")String json,Model model){
        json = json.replaceAll("&quot;","\"");
        JourneyBo journeyBo = JSONObject.parseObject(json,JourneyBo.class);
        model.addAttribute("list",journeyDayService.preview(journeyBo));
        return "modules/tms/JourneyPreview";
    }

}
