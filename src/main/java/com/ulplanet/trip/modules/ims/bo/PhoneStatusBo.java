package com.ulplanet.trip.modules.ims.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/23.
 */
public class PhoneStatusBo {
    private int id;
    private String value;

    public static List<PhoneStatusBo> init(){
        List<PhoneStatusBo> list = new ArrayList<>();
        list.add(new PhoneStatusBo(1,"待测试"));
        list.add(new PhoneStatusBo(2,"已测试"));
        list.add(new PhoneStatusBo(3,"待租"));
        list.add(new PhoneStatusBo(4,"已租"));
        list.add(new PhoneStatusBo(5,"维修"));
        list.add(new PhoneStatusBo(9000,"报废"));
        list.add(new PhoneStatusBo(9999,"退货"));
        return list;
    }

    private PhoneStatusBo(){}
    private PhoneStatusBo(int id,String value){
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public int getId() {
        return id;
    }

}
