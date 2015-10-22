package com.ulplanet.trip.modules.crm.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.crm.entity.Customer;

/**
 * 客户管理Dao
 * Created by zhangxd on 15/10/22.
 */
@MyBatisDao
public interface CustomerDao extends CrudDao<Customer> {

    Customer findByName(Customer customer);

}
