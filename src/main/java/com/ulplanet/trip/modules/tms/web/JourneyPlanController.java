package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.bo.JourneyPlanBo;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import com.ulplanet.trip.modules.tms.service.JourneyDayService;
import com.ulplanet.trip.modules.tms.service.JourneyPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public JourneyPlanBo get(@RequestParam(required=false) String id,@RequestParam(required=false) String cityIds) {
        if (StringUtils.isNotBlank(id)){
            return journeyPlanService.getJourneyPlan(id,cityIds);
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

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(JourneyPlan journeyPlan) {
        this.journeyPlanService.delete(journeyPlan);
        return "{\"status\":\"1\"}";
    }

    @RequestMapping(value = "/copy")
    @ResponseBody
    @Transactional(readOnly = false)
    public Object copy(JourneyPlan journeyPlan) {
        return journeyPlanService.copy(journeyPlan);
    }


    @RequestMapping(value = "/findTypeList",method = RequestMethod.POST)
    @ResponseBody
    public List<InfoBo> findTypeList(JourneyPlan journeyPlan){
        if(StringUtils.isNotBlank(journeyPlan.getDayId())) {
            JourneyDay journeyDay = journeyDayService.get(journeyPlan.getDayId());
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
