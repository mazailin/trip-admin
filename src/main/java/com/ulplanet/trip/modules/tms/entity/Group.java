package com.ulplanet.trip.modules.tms.entity;

import com.ulplanet.trip.common.persistence.DataEntity;
import com.ulplanet.trip.modules.crm.entity.Customer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxd on 15/8/11.
 */
public class Group extends DataEntity<Group> {

    private String name;
    private String description;
    private String customer;
    private String customerName;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date fromDate;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date toDate;
    private String chatId;
    private String chatName;
    private String telFunction;

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getTelFunction() {
        return telFunction;
    }

    public void setTelFunction(String telFunction) {
        this.telFunction = telFunction;
    }

    private List<Customer> customers;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @NotNull
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }


}
