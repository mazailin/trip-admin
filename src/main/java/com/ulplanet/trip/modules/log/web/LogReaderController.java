package com.ulplanet.trip.modules.log.web;

import com.ulplanet.trip.common.log.LogReader;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.log.service.LogReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/export")
    @ResponseBody
    public void export() throws ParseException {
        LogReader logReader = new LogReader();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseDate("2015-12-04", "yyyy-MM-dd"));
        /**获取多天**/
        Map<String,Map<String,String>> m = new HashMap<>();
        for(int i = 0;i<6;i++) {
            List<Map<String, String>> map = logReader.getMapList(DateUtils.formatDate(calendar.getTime(),"yyyy-MM-dd"));
            Map<String,Map<String,String>> infos = logReader.statistics(map);
            m.putAll(infos);
            calendar.add(Calendar.DATE,1);
        }
        /**获取指定天**/
//        List<Map<String, String>> map = logReader.getMapList("2015-11-30");
//        Map<String,Map<String,String>> m =logReader.statistics(map);
        logReader.export(m);
    }
}
