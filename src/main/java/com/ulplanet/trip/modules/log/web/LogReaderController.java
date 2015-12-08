package com.ulplanet.trip.modules.log.web;

import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.log.service.LogReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by makun on 2015/12/2.
 */
@Controller
@RequestMapping(value = "${adminPath}/log/logReader")
public class LogReaderController extends BaseController {


    @Resource
    private LogReaderService logReaderService;

    @RequestMapping(value = {"/getLog"},method = RequestMethod.POST)
    @ResponseBody
    public Object getLog(@RequestParam(value = "fromDate")String fromDate,@RequestParam(value = "toDate")String toDate) {
        return logReaderService.getLog(fromDate,toDate);
    }
    @RequestMapping(value = {"/list",""},method = RequestMethod.GET)
    public Object list() {
        return "modules/log/logReader";
    }
}
