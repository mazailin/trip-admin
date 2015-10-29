package com.ulplanet.trip.modules.tms.utils;

import com.ulplanet.trip.common.utils.SpringContextHolder;
import com.ulplanet.trip.modules.crm.dao.CustomerDao;
import com.ulplanet.trip.modules.crm.entity.Customer;
import com.ulplanet.trip.modules.tms.dao.GroupDao;
import com.ulplanet.trip.modules.tms.entity.Group;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class GroupUtils {
    private static CustomerDao customerDao = SpringContextHolder.getBean(CustomerDao.class);
    private static GroupDao groupDao = SpringContextHolder.getBean(GroupDao.class);

    public static List<Customer> getCustomer(){
        List<Customer> list = customerDao.findList(new Customer());
        return list;
    }

    public static List<Group> getGroupList(){
        List<Group> list = groupDao.findList(new Group());
        return list;
    }

}
