package com.ulplanet.trip.modules.tms.dao;


import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.Parameter;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tms.entity.GroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangxd on 15/8/11.
 */
@MyBatisDao
public interface GroupUserDao extends CrudDao<GroupUser> {

    int insertGroupUser(GroupUser groupUser);

    int insertUsers(List<GroupUser> groupUser);
    int insertGroupUsers(List<GroupUser> groupUser);

    int updateGroupUser(GroupUser groupUser);

    int updateUser(GroupUser updateUser);

    int deleteGroupUser(GroupUser groupUser);

    List<String> findListByGroup(@Param("id") String id);

    String hasPassport(@Param("passport")String passport,@Param("groupId")String groupId);



    String findMaxCode(Parameter parameter);

    List<GroupUser> findUserByPassport(@Param("id") String id, @Param("group") String group);

    GroupUser getUserById(@Param("id") String id);

}
