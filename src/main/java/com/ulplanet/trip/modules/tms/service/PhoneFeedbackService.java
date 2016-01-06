package com.ulplanet.trip.modules.tms.service;

import com.alibaba.fastjson.JSON;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tms.bo.AppFeedbackBo;
import com.ulplanet.trip.modules.tms.dao.PhoneFeedbackDao;
import com.ulplanet.trip.modules.tms.entity.PhoneFeedback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2016/1/5.
 */
@Service("phoneFeedbackService")
public class PhoneFeedbackService extends CrudService<PhoneFeedbackDao,PhoneFeedback> {

    @Resource
    private PhoneFeedbackDao phoneFeedbackDao;

    public String findList(){
        List<PhoneFeedback> list = phoneFeedbackDao.findList(new PhoneFeedback());
        Map<String,Object> map = new HashMap<>();
        Map<String,Integer> count = new HashMap<>();
        List<PhoneFeedback> feedbackList = new ArrayList<>();
        for(PhoneFeedback p : list){
            if(p.getType() == 1){//评分
                String key = p.getId() + "_" + p.getScore();
                if(count.containsKey(key)){
                    int i = count.get(key);
                    count.put(key,++i);
                }else{
                    count.put(key,1);
                }
            }else if(p.getType() == 3 && StringUtils.isNotEmpty(p.getFeedback())){//评价
                feedbackList.add(p);
            }
        }
        List<PhoneFeedback> phoneFeedbacks = phoneFeedbackDao.getFunction();
        List<AppFeedbackBo> apps = new ArrayList<>();
        for(int i = 0; i < phoneFeedbacks.size();i++){
            AppFeedbackBo app = new AppFeedbackBo();
            app.setId(phoneFeedbacks.get(i).getId());
            app.setName(phoneFeedbacks.get(i).getName());
            List<AppFeedbackBo> s = new ArrayList<>();
            for(int j = 1;j <= 3;j++){
                String key = app.getId()+"_"+j;
                AppFeedbackBo a = new AppFeedbackBo();
                a.setId(j+"");
                if(count.containsKey(key)){
                    a.setName(count.get(key).toString());
                }else{
                    a.setName("0");
                }
                s.add(a);
            }
            app.setList(s);
            apps.add(app);
        }
        map.put("score",apps);
        map.put("feedback",feedbackList);
        return JSON.toJSONString(map);
    }

    public String getJourney(String groupId){
        List<PhoneFeedback> list = phoneFeedbackDao.getJourney(groupId);
        Map<String,Map<String,Object>> map = new HashMap<>();
        for(PhoneFeedback p : list){
            Map<String,Object> m;
            if(!map.containsKey(p.getId())){
                m = new HashMap<>();
                m.put("name",p.getName());
                int [] score = new int[5];
                score[p.getScore()-1]++;
                m.put("score",score);
                map.put(p.getId(),m);
            }else{
                m = map.get(p.getId());
                int [] score = (int[]) m.get("score");
                score[p.getScore()-1]++;
                m.put("score",score);
            }
        }
        for(String key : map.keySet()){
            Map<String,Object> m = map.get(key);
            int [] score = (int[]) m.get("score");
            int sum = 0;
            int num = 0;
            for(int i = 0 ; i < score.length;i++){
                sum += (i+1) * score[i];
                num += score[i];
            }
            BigDecimal b1 = new BigDecimal(sum);
            BigDecimal b2 = new BigDecimal(num);
            double avg = b1.divide(b2,1,BigDecimal.ROUND_HALF_EVEN).doubleValue();
            m.put("avg",avg);
            map.put(key,m);
        }
        return JSON.toJSONString(map);
    }




}
