package com.ulplanet.trip.modules.log.service;

import com.ulplanet.trip.common.log.LogReader;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.modules.log.bo.LogReaderBo;
import com.ulplanet.trip.modules.log.bo.LogReaderCountryBo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.*;

/**
 * Created by makun on 2015/12/2.
 */
@Service
public class LogReaderService {

    public Map<String,List<LogReaderCountryBo>> getLog(String fromDate,String toDate){
        LogReader logReader;
        Map<String,List<LogReaderCountryBo>> kind = new HashMap<>();
        try {
            Date fDate = DateUtils.parseDate(fromDate,"yyyy-MM-dd");
            Date tDate = DateUtils.parseDate(toDate, "yyyy-MM-dd");
            long from = fDate.getTime();
            long to = tDate.getTime();
            int number = (int)(to - from)/86400000;
            Calendar cal = Calendar.getInstance();
            cal.setTime(fDate);
            for(int i = 0;i < number;i++) {
                logReader = new LogReader();
                Map<String, Map<String, String>> map = logReader.statistics(logReader.getMapList(DateUtils.formatDate(cal.getTime(),"yyyy-MM-dd")));
                for (String key : map.keySet()) {
                    Map<String, String> m = map.get(key);
                    getCount(m, kind);
                }
                cal.add(Calendar.DATE,1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return kind;

    }

    /**
     *
     * @param m 初始统计Map
     * @param kind 输出统计Map
     */
    private void getCount(Map<String,String> m,Map<String,List<LogReaderCountryBo>> kind){
        String country = m.get("_package");
        String date = m.get("date").substring(0, 10);
        int num = 0;
        /**按类型统计**/
        while(num < 18) {
            String key = "param_"+num;
            if(kind.containsKey(key)) {
                List<LogReaderCountryBo> list = kind.get(key);
                boolean flag = true;//是否包含国家标志
                /**按国家统计**/
                for(LogReaderCountryBo logReaderCountryBo : list){
                    if(country.equals(logReaderCountryBo.getId())){
                        List<LogReaderBo> list1 = logReaderCountryBo.getList();
                        boolean flag1 = true;
                        /**统计点击次数**/
                        for(LogReaderBo logReaderBo : list1){
                            if(date.equals(logReaderBo.getTime())){//如果存在说明统计错误直接跳过
                                flag1 = false;
                                break;
                            }
                        }
                        if(flag1){//没有添加
                            LogReaderBo logReaderBo = new LogReaderBo();
                            logReaderBo.setTime(date);
                            if(m.containsKey(key)) {
                                logReaderBo.setNumber(Integer.parseInt(m.get(key)));
                            }else{
                                logReaderBo.setNumber(0);
                            }
                            list1.add(logReaderBo);
                        }
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    LogReaderCountryBo logReaderCountryBo = new LogReaderCountryBo();
                    logReaderCountryBo.setId(country);
                    logReaderCountryBo.setName(m.get("country"));
                    List<LogReaderBo> list1 = logReaderCountryBo.getList();
                    LogReaderBo logReaderBo = new LogReaderBo();
                    logReaderBo.setTime(date);
                    if(m.containsKey(key)) {
                        logReaderBo.setNumber(Integer.parseInt(m.get(key)));
                    }else{
                        logReaderBo.setNumber(0);
                    }
                    list1.add(logReaderBo);
                    list.add(logReaderCountryBo);
                }
            }else{
                List<LogReaderCountryBo> list = new ArrayList<>();
                LogReaderCountryBo logReaderCountryBo = new LogReaderCountryBo();
                logReaderCountryBo.setId(country);
                logReaderCountryBo.setName(m.get("country"));
                List<LogReaderBo> list1 = logReaderCountryBo.getList();
                LogReaderBo logReaderBo = new LogReaderBo();
                logReaderBo.setTime(date);
                if(m.containsKey(key)) {
                    logReaderBo.setNumber(Integer.parseInt(m.get(key)));
                }else{
                    logReaderBo.setNumber(0);
                }
                list1.add(logReaderBo);
                list.add(logReaderCountryBo);
                kind.put(key,list);
            }
            num++;
        }


    }

}
