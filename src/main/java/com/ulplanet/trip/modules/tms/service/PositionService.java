package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.JedisUtils;
import com.ulplanet.trip.common.utils.NumberHelper;
import com.ulplanet.trip.modules.tms.dao.PositionDao;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import com.ulplanet.trip.modules.tms.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 位置Service
 * Created by zhangxd on 15/10/20.
 */
@Service
@Transactional(readOnly = true)
public class PositionService extends CrudService<PositionDao, Position> {

    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    private final static double MIN_DISTANCE = 0; //最小距离(m)

    @Autowired
    private PositionDao positionDao;
    @Autowired
    private GroupUserService groupUserService;

    public List<Position> getRoute(Position position) {
        List<Position> route = new ArrayList<>();
        List<Position> positionList = positionDao.findList(position);
        Position lastPosition = null;
        for (Position ps : positionList) {
            if (lastPosition == null) {
                route.add(ps);
            } else {
                if (getDistance(ps, lastPosition) > MIN_DISTANCE) {
                    route.add(ps);
                }
            }
            lastPosition = ps;
        }
        return route;
    }

    private double getDistance(Position ps, Position lastPosition) {
        double x, y, distance;
        x = (lastPosition.getLng() - ps.getLng()) * PI * R
                * Math.cos(((ps.getLat() + lastPosition.getLat()) / 2) * PI / 180) / 180;
        y = (lastPosition.getLat() - ps.getLat()) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

    public List<Position> getRefreshRoute(Position position) {
        List<Position> route = new ArrayList<>();
        List<Position> positionList = positionDao.findList(position);
        Position lastPosition = null;
        if (position.getLng() != 0 || position.getLat() != 0) {
            lastPosition = position;
        }
        for (Position ps : positionList) {
            if (lastPosition == null) {
                route.add(ps);
            } else {
                if (getDistance(ps, lastPosition) > MIN_DISTANCE) {
                    route.add(ps);
                }
            }
            lastPosition = ps;
        }
        return route;
    }

    @SuppressWarnings("unchecked")
    public List<Position> getGroupPosition(String group) {

        Map<String, Object> userMap = JedisUtils.getObjectMap(group);
        List<Position> datas = new ArrayList<>();

        List<GroupUser> userList = groupUserService.findList(new GroupUser(group));
        Map<String, GroupUser> userKeyMap = new HashMap<>();
        for (GroupUser user : userList) {
            String userid = user.getId();
            userKeyMap.put(userid, user);
        }

        if (userMap != null) {
            for (Map.Entry<String, Object> userMapEntry : userMap.entrySet()) {
                String userid = userMapEntry.getKey();

                GroupUser user = userKeyMap.get(userid);
                if (user != null) {
                    Map<String, Object> pointMap = (Map<String, Object>) userMapEntry.getValue();
                    Position position = new Position();
                    position.setUserId(userid);
                    position.setName(user.getName());
                    position.setLat(NumberHelper.toDouble(pointMap.get("longitude"), 0d));
                    position.setLng(NumberHelper.toDouble(pointMap.get("latitude"), 0d));
                    datas.add(position);
                }
            }
        }

        return datas;
    }
}
