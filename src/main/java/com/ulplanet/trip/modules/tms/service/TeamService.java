package com.ulplanet.trip.modules.tms.service;

import com.google.common.collect.Lists;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.tms.dao.TeamDao;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import com.ulplanet.trip.modules.tms.entity.Team;
import io.rong.ApiHttpClient;
import io.rong.models.SdkHttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅行团小组Service
 * Created by zhangxd on 15/8/11.
 */
@Service
public class TeamService extends CrudService<TeamDao, Team> {

    Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamDao teamDao;

    @Override
    public Team get(String id) {
        Team team = teamDao.get(id);

        List<GroupUser> userList = teamDao.findUserList(team);
        team.setUserList(userList);

        List<String> ids = new ArrayList<>();
        for (GroupUser groupUser : userList) {
            ids.add(groupUser.getId());
        }

        team.setSelectIds(StringUtils.join(ids, ","));
        return team;
    }

    public List<GroupUser> getPreUserList(Team team) {
        return teamDao.findPreUserList(team);
    }


    public void saveTeam(Team team) throws Exception {
        if (StringUtils.isBlank(team.getId())) {
            team.preInsert();

            insertTeamUsers(team);
            teamDao.insert(team);

        } else {
            team.preUpdate();

            insertTeamUsers(team);
            deleteTeamUsers(team);
            teamDao.update(team);
        }
    }

    /**
     * 批量加入聊天组
     * @param team 聊天组
     * @throws Exception
     */
    private void insertTeamUsers(Team team) throws Exception {
        String addIds = team.getAddUserIds();
        if (StringUtils.isNotBlank(addIds)) {
            String[] ids = StringUtils.split(addIds, ",");
            List<String> users = Lists.newArrayList(ids);
            try {
                SdkHttpResult result = ApiHttpClient.joinGroupBatch(users, team.getId(), team.getName());
                if (result.getHttpCode() == 200) {
                    teamDao.insertUsers(new Parameter(new Object[][]{{"teamId", team.getId()}, {"users", users}}));
                } else {
                    logger.error("加入聊天组失败");
                    throw new RuntimeException("加入聊天组失败，请重新操作");
                }
            } catch (Exception e) {
                logger.error("加入聊天组异常", e);
                throw new RuntimeException("加入聊天组失败，请重新操作");
            }
        }
    }

    /**
     * 批量删除聊天组成员
     * @param team 聊天组
     * @throws Exception
     */
    private void deleteTeamUsers(Team team) throws Exception{
        String delIds = team.getDelUserIds();
        if (StringUtils.isNotBlank(delIds)) {
            String[] ids = StringUtils.split(delIds, ",");
            List<String> users = Lists.newArrayList(ids);
            try {
                SdkHttpResult result = ApiHttpClient.quitGroupBatch(users, team.getId());
                if (result.getHttpCode() == 200) {
                    teamDao.deleteUsers(new Parameter(new Object[][]{{"teamId", team.getId()}, {"users", users}}));
                } else {
                    logger.error("退出聊天组失败");
                    throw new RuntimeException("退出聊天组失败，请重新操作");
                }
            } catch (Exception e) {
                logger.error("退出聊天组异常", e);
                throw new RuntimeException("退出聊天组失败，请重新操作");
            }
        }
    }

    /**
     * 删除聊天组
     * @param team 聊天组
     * @throws Exception
     */
    public void deleteTeam(Team team) throws Exception {
        try {
            SdkHttpResult result = ApiHttpClient.dismissGroup("", team.getId());
            if (result.getHttpCode() == 200) {
                this.teamDao.delete(team);
                this.teamDao.delAllTeamUsers(team);
            } else {
                logger.error(String.format("群组解散%s失败", team.getName()));
                throw new RuntimeException(String.format("群组解散%s失败，请重新操作", team.getName()));
            }
        } catch (Exception e) {
            logger.error(String.format("群组解散%s异常", team.getName()), e);
            throw new RuntimeException(String.format("群组解散%s失败，请重新操作", team.getName()));
        }
    }
}
