package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tms.bo.JourneyBo;
import com.ulplanet.trip.modules.tms.bo.JourneyDayBo;
import com.ulplanet.trip.modules.tms.bo.SortBo;
import com.ulplanet.trip.modules.tms.dao.JourneyDayDao;
import com.ulplanet.trip.modules.tms.entity.JourneyDay;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@Service("journeyDay")
public class JourneyDayService extends CrudService<JourneyDayDao,JourneyDay> {

    @Resource
    private JourneyDayDao journeyDayDao;
    @Resource
    private JourneyPlanService journeyPlanService;

    public List<JourneyDayBo> queryList(String groupId){
        JourneyDay j = new JourneyDay();
        j.setGroupId(groupId);
        List<JourneyDay> list = journeyDayDao.findList(j);
        List<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for(JourneyDay journeyDay : list){
            JourneyPlan journeyPlan = new JourneyPlan();
            journeyPlan.setDayId(journeyDay.getId());
            List<JourneyPlan> plans = journeyPlanService.findList(journeyPlan);
            JourneyDayBo journeyDayBo = new JourneyDayBo(journeyDay);
            journeyDayBo.setList(plans);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;
    }

    @Override
    public void delete(JourneyDay journeyDay){
        journeyDayDao.delete(journeyDay);
        JourneyPlan journeyPlan = new JourneyPlan();
        journeyPlan.setDayId(journeyDay.getId());
        journeyPlanService.delete(journeyPlan);
    }

    /**
     * 保存排序
     * @param journeyBo
     * @return
     */
    public int sort(JourneyBo journeyBo){
        SortBo[] sortBos = journeyBo.getList();
        String groupId = journeyBo.getGroupId();
        List<JourneyDay> journeyDays = new ArrayList<>();
        List<JourneyPlan> journeyPlans = new ArrayList<>();
        List<JourneyDay> addDayList = new ArrayList<>();
        List<JourneyPlan> addPlanList = new ArrayList<>();
        for(int i = 0;i < sortBos.length;i++){
            JourneyDay journeyDay = new JourneyDay();
            SortBo sortBo = sortBos[i];
            if(!groupId.equals(sortBo.getGroupId())){
                journeyDay = journeyDayDao.get(sortBo.getId());
                journeyDay.preInsert();
                journeyDay.setDayNumber(i);
                journeyDay.setGroupId(groupId);
                addDayList.add(journeyDay);
            }else {
                journeyDay.setId(sortBo.getId());
                journeyDay.setDayNumber(i);
                journeyDays.add(journeyDay);
            }
            SortBo[] sortBos1 = sortBo.getChildren();
            for(int j = 0;j < sortBos1.length;j++){
                SortBo sortBo1 = sortBos1[j];
                JourneyPlan journeyPlan = new JourneyPlan();
                if(!groupId.equals(sortBo1.getGroupId())){
                    journeyPlan = journeyPlanService.get(sortBo1.getId());
                    journeyPlan.preInsert();
                    journeyPlan.setSort(j);
                    journeyPlan.setDayId(journeyDay.getId());
                    addPlanList.add(journeyPlan);
                }else {
                    journeyPlan.setId(sortBo1.getId());
                    journeyPlan.setSort(j);
                    journeyPlan.setDayId(journeyDay.getId());
                    journeyPlans.add(journeyPlan);
                }
            }
        }
        if(journeyPlans.size()>0){
            for(JourneyPlan journeyPlan : journeyPlans) {
                journeyPlan.preUpdate();
                journeyPlan.setIsNewRecord(false);
                journeyPlanService.save(journeyPlan);
            }
        }
        if(journeyDays.size()>0){
            for(JourneyDay journeyDay : journeyDays) {
                journeyDay.preUpdate();
                journeyDayDao.update(journeyDay);
            }
        }
        if(addPlanList.size()>0)journeyPlanService.inserts(addPlanList);
        if(addDayList.size()>0)journeyDayDao.inserts(addDayList);
        return 1;
    }

    public JourneyDay copy(JourneyDay journeyDay){
        journeyDay = journeyDayDao.get(journeyDay);
        journeyDay.preInsert();
        journeyDayDao.insert(journeyDay);
        return journeyDay;
    }

    public List<JourneyDayBo> getJourneyTemplate(String groupId){
        JourneyDay j = new JourneyDay();
        j.setGroupId(groupId);
        List<JourneyDay> list = journeyDayDao.findList(j);
        List<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for(JourneyDay journeyDay : list){
            JourneyPlan journeyPlan = new JourneyPlan();
            journeyPlan.setDayId(journeyDay.getId());
            List<JourneyPlan> plans = journeyPlanService.findList(journeyPlan);
            JourneyDayBo journeyDayBo = new JourneyDayBo(journeyDay);
            journeyDayBo.setList(plans);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;
    }



}
