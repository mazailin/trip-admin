package com.ulplanet.trip.modules.sys.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.sys.entity.Dict;

import java.util.List;

/**
 * 字典DAO接口
 * Created by zhangxd on 15/10/20.
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	List<String> findTypeList(Dict dict);
	
}
