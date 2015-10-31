package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.bo.JourneyPlanBo;
import com.ulplanet.trip.modules.tms.dao.JourneyPlanDao;
import com.ulplanet.trip.modules.tms.entity.Flight;
import com.ulplanet.trip.modules.tms.entity.JourneyPlan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makun on 2015/10/28.
 */
@Service("journeyPlan")
public class JourneyPlanService extends CrudService<JourneyPlanDao,JourneyPlan> {
    @Resource
    private JourneyPlanDao journeyPlanDao;
    @Resource
    private FlightService flightService;

    public JourneyPlan saveJourneyPlan(JourneyPlan journeyPlan){
        save(journeyPlan);
        return journeyPlan;
    }

    public JourneyPlanBo getInfo(String id){
        JourneyPlan journeyPlan = journeyPlanDao.get(id);
        JourneyPlanBo journeyPlanBo = new JourneyPlanBo(journeyPlan);
        List<InfoBo> list = getInfoList(journeyPlanBo.getType(), journeyPlanBo.getCityIds(), null);
        if(list != null){
            List<InfoBo> l = journeyPlanDao.findInfoByTableName(tableName(journeyPlanBo.getType()),null,journeyPlan.getInfoId()) ;
            InfoBo infoBo = null;
            if(l.size()>0){
                infoBo = l.get(0);
                journeyPlanBo.setName(infoBo.getName());
                journeyPlanBo.setDescription(infoBo.getDescription());
                journeyPlanBo.setLatitude(infoBo.getLatitude());
                journeyPlanBo.setLongitude(infoBo.getLongitude());
            }
            journeyPlanBo.setInfos(list);
            return journeyPlanBo;
        }
        if(journeyPlanBo.getType() == 3){//航班
            List<InfoBo> flightList = flightService.findListByIds(journeyPlanBo.getCityIds());
            journeyPlanBo.setInfos(flightList);
            Flight flight = flightService.get(journeyPlanBo.getInfoId());
            if(StringUtils.isNotBlank(flight.getId())) {
                journeyPlanBo.setName(flight.getFlightNo());
                journeyPlanBo.setDescription(flight.toString());
            }
            return journeyPlanBo;
        }
        return journeyPlanBo;
    }

    public List<InfoBo> getInfoList(int type,String ids,String infoId){
        String table = tableName(type);
        if(table != null){
            String [] arr = null;
            if(ids.indexOf(",")>0){
                arr = ids.split(",");
            }
            List<InfoBo> infoBo = journeyPlanDao.findInfoByTableName(table, arr, infoId);
            return infoBo;
        }
        return null;
    }

    public List<InfoBo> getInfoList(int type,String ids){
        String table = tableName(type);
        if(table != null){
            String [] arr = null;
            if(ids.indexOf(",")>0){
                arr = ids.split(",");
            }
            List<InfoBo> infoBo = journeyPlanDao.findInfoByTableName(table,arr,null);
            return infoBo;
        }
        if(type == 3){//航班
            List<InfoBo> flightList = flightService.findListByIds(ids);
            return flightList;
        }
        return new ArrayList<>();
    }

    private String tableName(int id){
        switch (id){
            case 5 : return "hotel";//旅馆
            case 6 : return "food";//餐饮
            case 7 : return "scenic";//景点
            case 8 : return "shop";//商店
            default:return null;
        }

    }



}
