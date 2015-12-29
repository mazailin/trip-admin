package com.ulplanet.trip.modules.tms.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.modules.tms.dao.PositionDao;
import com.ulplanet.trip.modules.tms.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
