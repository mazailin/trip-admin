package com.ulplanet.trip.modules.ims.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface  PhoneInfoDao extends CrudDao<PhoneInfo> {
	List<PhoneInfo> findListByParams(PhoneInfo phoneInfo);
	int queryDeliverPhone(PhoneInfo phoneInfo);
	PhoneInfo getById(@Param("id") String id);


}
