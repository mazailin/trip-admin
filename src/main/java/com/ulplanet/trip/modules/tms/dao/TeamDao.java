package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import com.ulplanet.trip.modules.tms.entity.Team;

import java.util.List;

/**
 * 旅行团小组Dao
 * Created by zhangxd on 15/8/11.
 */
@MyBatisDao
public interface TeamDao extends CrudDao<Team> {

    List<GroupUser> findUserList(Team team);

    List<GroupUser> findPreUserList(Team team);

    void delAllTeamUsers(Team team);

    void insertUsers(Parameter users);

    void deleteUsers(Parameter parameter);
}
