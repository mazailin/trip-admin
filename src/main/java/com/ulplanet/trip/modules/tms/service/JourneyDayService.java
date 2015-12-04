package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.sys.entity.VersionTag;
import com.ulplanet.trip.modules.sys.service.VersionTagService;
import com.ulplanet.trip.modules.tim.service.CityService;
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
    @Resource
    private VersionTagService versionTagService;
    @Resource
    private CityService cityService;

    public List<JourneyDayBo> queryList(String groupId){
        JourneyDay j = new JourneyDay();
        j.setGroupId(groupId);
        List<JourneyDay> list = journeyDayDao.findList(j);
        List<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for(JourneyDay journeyDay : list){
            EhCacheUtils.put(journeyDay.getGroupId(),journeyDay.getId(),journeyDay);
            JourneyPlan journeyPlan = new JourneyPlan();
            journeyPlan.setDayId(journeyDay.getId());
            List<JourneyPlan> plans = journeyPlanService.findList(journeyPlan);
            for(JourneyPlan jp : plans){
                EhCacheUtils.put(jp.getDayId(),jp.getId(),jp);
            }
            JourneyDayBo journeyDayBo = new JourneyDayBo(journeyDay);
            journeyDayBo.setList(plans);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;
    }

    public JourneyDay getJourneyDay(String id,String groupId){
        JourneyDay j;
        if(EhCacheUtils.get(groupId,id)!=null){
            j = (JourneyDay)EhCacheUtils.get(groupId,id);
        }else {
            j = get(id);
        }
        if(StringUtils.isNotEmpty(j.getCityIds())){
            String[] ids = j.getCityIds().split(",");
            List<String> names = cityService.getNames(ids);
            StringBuffer sb = new StringBuffer();
            for(String name : names){
                sb.append(name);
                sb.append(",");
            }
            sb.delete(sb.lastIndexOf(","),sb.lastIndexOf(",")+1);
            j.setCityNames(sb.toString());
        }
        return j;
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
        List<JourneyDay> addDayList = new ArrayList<>();
        List<JourneyPlan> addPlanList = new ArrayList<>();
        for(int i = 0;i < sortBos.length;i++){//每天行程保存
            SortBo sortBo = sortBos[i];
            JourneyDay journeyDay ;
            if(!groupId.equals(sortBo.getGroupId())){
                if(EhCacheUtils.get(sortBo.getGroupId(),sortBo.getId())==null){
                    journeyDay = journeyDayDao.get(sortBo.getId());
                }else {
                    journeyDay = (JourneyDay) EhCacheUtils.get(sortBo.getGroupId(), sortBo.getId());
                }
                journeyDay.preInsert();
                journeyDay.setDayNumber(i+1);
                journeyDay.setGroupId(groupId);
                addDayList.add(journeyDay);
            }else {
                if(EhCacheUtils.get(groupId,sortBo.getId())==null){
                    journeyDay = journeyDayDao.get(sortBo.getId());
                }else {
                    journeyDay = (JourneyDay) EhCacheUtils.get(groupId, sortBo.getId());
                }
                journeyDay.preInsert();
                journeyDay.setDayNumber(i+1);
                addDayList.add(journeyDay);
            }
            SortBo[] sortBos1 = sortBo.getChildren();
            for(int j = 0;j < sortBos1.length;j++){//每天详细行程保存
                SortBo sortBo1 = sortBos1[j];
                JourneyPlan journeyPlan;
                if(!groupId.equals(sortBo1.getGroupId())){
                    if(EhCacheUtils.get(sortBo.getId(),sortBo1.getId()) == null){
                        journeyPlan = journeyPlanService.get(sortBo1.getId());
                    }else {
                        journeyPlan = (JourneyPlan) EhCacheUtils.get(sortBo.getId(), sortBo1.getId());
                    }
                    journeyPlan.preInsert();
                    journeyPlan.setSort(j);
                    journeyPlan.setDayId(journeyDay.getId());
                    addPlanList.add(journeyPlan);
                }else {
                    if(EhCacheUtils.get(sortBo.getId(),sortBo1.getId()) == null){
                        journeyPlan = journeyPlanService.get(sortBo1.getId());
                    }else {
                        journeyPlan = (JourneyPlan) EhCacheUtils.get(sortBo.getId(), sortBo1.getId());
                    }
                    journeyPlan.preInsert();
                    journeyPlan.setSort(j);
                    journeyPlan.setDayId(journeyDay.getId());
                    addPlanList.add(journeyPlan);
                }
            }
        }
        journeyDayDao.deleteByGroupId(groupId);//清空行程原有信息，重新插入
        if(addPlanList.size()>0)journeyPlanService.inserts(addPlanList);
        if(addDayList.size()>0)journeyDayDao.inserts(addDayList);
        versionTagService.save(new VersionTag(groupId, 2));
        return 1;
    }

    public JourneyDay copy(JourneyDay journeyDay){
        journeyDay = journeyDayDao.get(journeyDay);
        journeyDay.preInsert();
        EhCacheUtils.put(journeyDay.getGroupId(),journeyDay.getId(),journeyDay);
        return journeyDay;
    }


    public List<JourneyDayBo> preview(JourneyBo journeyBo){
        SortBo[] sortBos = journeyBo.getList();
        List<JourneyDayBo> addDayList = new ArrayList<>();
        for(int i = 0;i < sortBos.length;i++){//每天行程保存
            SortBo sortBo = sortBos[i];
            JourneyDay journeyDay ;
            if(EhCacheUtils.get(sortBo.getGroupId(),sortBo.getId())==null){
                journeyDay = journeyDayDao.get(sortBo.getId());
            }else {
                journeyDay = (JourneyDay) EhCacheUtils.get(sortBo.getGroupId(), sortBo.getId());
            }
            journeyDay.setDayNumber(i + 1);
            SortBo[] sortBos1 = sortBo.getChildren();
            List<JourneyPlan> journeyPlans = new ArrayList<>();
            for(int j = 0;j < sortBos1.length;j++){//每天详细行程保存
                SortBo sortBo1 = sortBos1[j];
                JourneyPlan journeyPlan;
                if(EhCacheUtils.get(sortBo.getId(),sortBo1.getId()) == null){
                    journeyPlan = journeyPlanService.get(sortBo1.getId());
                }else {
                    journeyPlan = (JourneyPlan) EhCacheUtils.get(sortBo.getId(), sortBo1.getId());
                }
                journeyPlan.setSort(j+1);
                journeyPlans.add(journeyPlan);
            }
            JourneyDayBo journeyDayBo = new JourneyDayBo(journeyDay);
            journeyDayBo.setList(journeyPlans);
            addDayList.add(journeyDayBo);
        }
        return addDayList;
    }



}
