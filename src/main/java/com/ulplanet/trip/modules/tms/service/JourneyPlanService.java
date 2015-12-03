package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tim.service.FlightService;
import com.ulplanet.trip.modules.tms.bo.InfoBo;
import com.ulplanet.trip.modules.tms.bo.JourneyPlanBo;
import com.ulplanet.trip.modules.tms.dao.JourneyPlanDao;
import com.ulplanet.trip.modules.tim.entity.Flight;
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

    /**
     * 根据ID获取通用表列表信息
     * @param type
     * @param ids
     * @param infoId
     * @return
     */
    public List<InfoBo> getInfoList(int type,String ids,String infoId){
        String table = tableName(type);
        if(table != null){
            String [] arr = null;
            if(ids!=null){
                arr = ids.split(",");
            }
            List<InfoBo> infoBo = journeyPlanDao.findInfoByTableName(table, arr, infoId, col(type));
            return infoBo;
        }
        return new ArrayList<>();
    }

    /**
     * 获取所有信息
     * @param type
     * @param ids
     * @return
     */
    public List<InfoBo> getInfoList(int type,String ids){
        String table = tableName(type);
        if(table != null){
            String [] arr = null;
            if(ids!=null){
                arr = ids.split(",");
            }
            List<InfoBo> infoBo = journeyPlanDao.findInfoByTableName(table,arr,null,col(type));
            return infoBo;
        }
        if(type == 3){//航班
            List<InfoBo> flightList = flightService.findListByIds(ids);
            return flightList;
        }
        return new ArrayList<>();
    }

    /**
     * 获取关联信息
     * @param type
     * @param infoId
     * @return
     */
    public InfoBo getInfo(int type,String infoId){
        List<InfoBo> infoBos = getInfoList(type, null, infoId);
        if(infoBos.size()>0){
            return infoBos.get(0);
        }
        if(type == 3){//航班
            Flight flight = flightService.get(infoId);
            InfoBo infoBo = new InfoBo();
            infoBo.setId(flight.getId());
            infoBo.setDescription(flight.toString());
            infoBo.setName(flight.getFlightNo());
            return infoBo;
        }
        return new InfoBo();
    }


    public int updates(List<JourneyPlan> list){
        return journeyPlanDao.updates(list);
    }

    public int inserts(List<JourneyPlan> list){
        return journeyPlanDao.inserts(list);
    }


    public JourneyPlan copy(JourneyPlan journeyPlan){
        journeyPlan = journeyPlanDao.get(journeyPlan);
        journeyPlan.preInsert();
        journeyPlanDao.insert(journeyPlan);
        return journeyPlan;
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

    private String col(int id){
        switch (id){
            case 5 : return "phone";//旅馆
            case 6 : return "phone";//餐饮
            default:return "-1";
        }

    }



}
