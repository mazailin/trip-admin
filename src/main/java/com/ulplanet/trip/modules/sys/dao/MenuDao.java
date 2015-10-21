package com.ulplanet.trip.modules.sys.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.sys.entity.Menu;

import java.util.List;

/**
 * 菜单DAO接口
 * Created by zhangxd on 15/10/20.
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	List<Menu> findByParentIdsLike(Menu menu);

	List<Menu> findByUserId(Menu menu);
	
	int updateParentIds(Menu menu);
	
	int updateSort(Menu menu);
	
}
