package com.ulplanet.trip.modules.ims.web;

import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.entity.PhoneInfo;
import com.ulplanet.trip.modules.ims.service.PhoneInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by makun on 2015/10/13.
 */
//@Controller
//@RequestMapping(value = "${adminPath}/ims/phone")
public class PhoneInfoController extends BaseController {
//    @Resource
//    private PhoneInfoService phoneInfoService;
//
//    @RequestMapping(value = "/findPhoneInfos")
//    @ResponseBody
//    public String findPhoneInfos(@RequestParam("page") int page,
//                                               @RequestParam("limit") int size, @RequestParam(value = "searchValue", required = false) String searchValue) {
//        return this.phoneInfoService.findPhoneInfos(page, size, searchValue);
//    }
//
//    @RequestMapping(value = "/save",method = RequestMethod.POST)
//    @ResponseBody
//    public String savePhoneInfo(PhoneInfo phoneInfo) {
//        if(StringUtils.isNotBlank(phoneInfo.getId())){
//            return  this.phoneInfoService.updatePhoneInfo(phoneInfo);
//        }
//        return this.phoneInfoService.addPhoneInfo(phoneInfo);
//    }
//
//    @RequestMapping(value = "/delete",method = RequestMethod.POST)
//    @ResponseBody
//    public String updatePhoneInfo(PhoneInfo phoneInfo) {
//        phoneInfo.setStatus(0);
//        return this.phoneInfoService.updatePhoneInfo(phoneInfo);
//    }
//
//    @RequestMapping(value = "/refund",method = RequestMethod.POST)
//    @ResponseBody
//    public String refund(PhoneInfo phoneInfo) {
//        return this.phoneInfoService.startRefund(phoneInfo);
//    }
//
//    @RequestMapping(value = "/form",method = RequestMethod.POST)
//    @ResponseBody
//    public String form(PhoneInfo phoneInfo) {
//        return this.phoneInfoService.get(phoneInfo);
//    }

}
