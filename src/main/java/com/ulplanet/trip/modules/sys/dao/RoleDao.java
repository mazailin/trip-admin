package com.ulplanet.trip.modules.sys.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * Created by zhangxd on 15/10/20.
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	Role getByName(Role role);
	
	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	int deleteRoleMenu(Role role);

	int insertRoleMenu(Role role);
	
}
