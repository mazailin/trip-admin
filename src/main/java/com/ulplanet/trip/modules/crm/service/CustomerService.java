package com.ulplanet.trip.modules.crm.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.modules.crm.dao.CustomerDao;
import com.ulplanet.trip.modules.crm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户Service
 * Created by zhangxd on 15/10/20.
 */
@Service
public class CustomerService extends CrudService<CustomerDao, Customer> {

    @Autowired
    private CustomerDao customerDao;

    public Customer getUserByName(String name) {
        return customerDao.findByName(new Customer(null, name));
    }

    public void saveCustomer(Customer customer) {
        if (StringUtils.isBlank(customer.getId())){
            customer.preInsert();
            customerDao.insert(customer);
        }else{
            // 更新用户数据
            customer.preUpdate();
            customerDao.update(customer);
        }
    }
}
