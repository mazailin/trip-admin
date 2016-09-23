package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.bo.JourneyPlanBo;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import com.ulplanet.trip.modules.tms.service.JourneyDayService;
import com.ulplanet.trip.modules.tms.service.JourneyPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by makun on 2015/10/30.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/journeyPlan")
public class JourneyPlanController  extends BaseController {
    @Resource
    private JourneyPlanService journeyPlanService;
    @Resource
    private JourneyDayService journeyDayService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public JourneyPlanBo get(@RequestParam String id,@RequestParam(value = "dayId") String dayId) {
        if (StringUtils.isNotBlank(id)){
            if(EhCacheUtils.get(dayId,id)!=null){
               return new JourneyPlanBo((JourneyPlan)EhCacheUtils.get(dayId,id));
            }
            return new JourneyPlanBo(journeyPlanService.get(id));
        }
        return null;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(JourneyPlan journeyPlan) {
        if(StringUtils.isBlank(journeyPlan.getId())){
            journeyPlan.setIsNewRecord(false);
        }
        journeyPlanService.save(journeyPlan);
        return journeyPlan;
    }

    @RequestMapping(value = "/saveTemp")
    @ResponseBody
    public Object saveTemp(JourneyPlan journeyPlan) {
        if(StringUtils.isBlank(journeyPlan.getId())){
            journeyPlan.setIsNewRecord(false);
            journeyPlan.preInsert();
        }
        EhCacheUtils.put(journeyPlan.getDayId(),journeyPlan.getId(),journeyPlan);
        return journeyPlan;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(JourneyPlan journeyPlan) {
        this.journeyPlanService.delete(journeyPlan);
        return "{\"status\":\"1\"}";
    }

    @RequestMapping(value = "/findTypeList",method = RequestMethod.POST)
    @ResponseBody
    public List<InfoBo> findTypeList(JourneyPlan journeyPlan,@RequestParam String groupId){
        if(StringUtils.isNotBlank(journeyPlan.getDayId())) {
            JourneyDay journeyDay = (JourneyDay)EhCacheUtils.get(groupId,journeyPlan.getDayId());
            if(journeyDay==null)journeyDay = journeyDayService.get(journeyPlan.getDayId());
            journeyPlan.setCityIds(journeyDay.getCityIds());
        }
        return journeyPlanService.getInfoList(journeyPlan.getType(), journeyPlan.getCityIds());
    }

    @RequestMapping(value = "/findType",method = RequestMethod.POST)
    @ResponseBody
    public InfoBo findType(JourneyPlan journeyPlan){
        return journeyPlanService.getInfo(journeyPlan.getType(),journeyPlan.getInfoId());
    }

}