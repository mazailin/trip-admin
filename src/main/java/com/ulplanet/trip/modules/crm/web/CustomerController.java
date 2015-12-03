package com.ulplanet.trip.modules.crm.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.crm.entity.Customer;
import com.ulplanet.trip.modules.crm.service.CustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户Controller
 * Created by zhangxd on 15/10/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/crm/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @ModelAttribute
    public Customer get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return customerService.get(id);
        }else{
            return new Customer();
        }
    }

    @RequiresPermissions("crm:customer:view")
    @RequestMapping(value = {"list", ""})
    public String list(Customer customer, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Customer> page = customerService.findPage(new Page<>(request, response), customer);
        model.addAttribute("page", page);
        return "modules/crm/customerList";
    }

    @RequiresPermissions("crm:customer:view")
    @RequestMapping(value = "form")
    public String form(Customer customer, Model model) {
        model.addAttribute("customer", customer);
        return "modules/crm/customerForm";
    }

    @RequiresPermissions("crm:customer:edit")
    @RequestMapping(value = "save")
    public String save(Customer customer, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, customer)){
            return form(customer, model);
        }
        customerService.saveCustomer(customer);
        addMessage(redirectAttributes, "保存客户'" + customer.getName() + "'成功");
        return "redirect:" + adminPath + "/crm/customer/list?repage";
    }

    @RequiresPermissions("crm:customer:edit")
    @RequestMapping(value = "delete")
    public String delete(Customer customer, RedirectAttributes redirectAttributes) {
        customerService.delete(customer);
        addMessage(redirectAttributes, "删除客户" + customer.getName() + "成功");
        return "redirect:" + adminPath + "/crm/customer/list?repage";
    }

    /**
     * 验证名称是否有效
     * @param oldName
     * @param name
     * @return
     */
    @ResponseBody
    @RequiresPermissions("crm:customer:edit")
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (oldName != null && oldName.equals(name)) {
            return "true";
        } else if (name != null && customerService.getUserByName(name) == null) {
            return "true";
        }
        return "false";
    }

}
