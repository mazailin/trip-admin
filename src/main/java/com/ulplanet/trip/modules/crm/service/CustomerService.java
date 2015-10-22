package com.ulplanet.trip.modules.crm.service;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.modules.sys.dao.LogDao;
import com.ulplanet.trip.modules.sys.entity.Log;
import org.springframework.stereotype.Service;

/**
 * 客户Service
 * Created by zhangxd on 15/10/20.
 */
@Service
public class CustomerService extends CrudService<LogDao, Log> {

	public Page<Log> findPage(Page<Log> page, Log log) {

		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}

		return super.findPage(page, log);

	}

}
