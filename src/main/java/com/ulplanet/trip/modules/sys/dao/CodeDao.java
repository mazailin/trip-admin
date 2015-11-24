package com.ulplanet.trip.modules.sys.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.sys.entity.Code;

/**
 * 编码DAO接口
 * Created by zhangxd on 15/11/23.
 */
@MyBatisDao
public interface CodeDao extends CrudDao<Code> {

    int updateNext(Code code);

}
