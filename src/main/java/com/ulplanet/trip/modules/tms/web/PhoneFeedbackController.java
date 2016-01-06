package com.ulplanet.trip.modules.tms.web;

import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tms.entity.Group;
import com.ulplanet.trip.modules.tms.service.GroupService;
import com.ulplanet.trip.modules.tms.service.PhoneFeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by makun on 2016/1/5.
 */
@Controller
@RequestMapping(value = "${adminPath}/tms/feedback")
public class PhoneFeedbackController extends BaseController {

    @Resource
    private PhoneFeedbackService phoneFeedbackService;
    @Resource
    private GroupService groupService;

    @RequestMapping(value = "/app",method = RequestMethod.GET)
    public String app(){
        return "modules/tms/appFeedback";
    }

    @RequestMapping(value = "/journey",method = RequestMethod.GET)
    public String journey(Model model){
        model.addAttribute("groupList",groupService.getGroupList(new Group()));
        return "modules/tms/journeyFeedback";
    }

    @RequestMapping(value = "/app/getList",method = RequestMethod.POST)
    @ResponseBody
    public String getList(){
        return phoneFeedbackService.findList();
    }

    @RequestMapping(value = "/journey/getList",method = RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestParam String groupId){
        return phoneFeedbackService.getJourney(groupId);
    }
}
