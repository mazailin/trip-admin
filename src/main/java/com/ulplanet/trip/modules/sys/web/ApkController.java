package com.ulplanet.trip.modules.sys.web;

import com.qiniu.util.StringMap;
import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.entity.Apk;
import com.ulplanet.trip.modules.sys.service.ApkService;
import com.ulplanet.trip.modules.sys.utils.QiniuUploadUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by makun on 2015/11/11.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/apk")
public class ApkController  extends BaseController {
    @Resource
    private ApkService apkService;

    @ModelAttribute
    public Apk get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return apkService.get(id);
        }else{
            return new Apk();
        }
    }


    @RequiresPermissions("sys:apk:upload")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(Apk apk,@RequestParam MultipartFile tarFile,Model model
    ) {
        apk = apkService.upload(apk,tarFile);
        return form(apk, model);
    }

    @RequiresPermissions("sys:apk:view")
    @RequestMapping(value = {"/list",""})
    public String findList(Apk apk, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Apk> page = apkService.findPage(new Page<>(request, response), apk);
        model.addAttribute("page", page);
        return "modules/sys/apkList";
    }

    @RequiresPermissions("sys:apk:upload")
    @RequestMapping(value = "/delete")
    public String delete(Apk apk,Model model,RedirectAttributes redirectAttributes) {
        ResponseBo responseBo = this.apkService.delete(apk.getId());
        addMessage(redirectAttributes, responseBo.getMsg());
        if(responseBo.getStatus()==1) {
            return "redirect:" + adminPath + "/sys/apk/list/?repage";
        }
        return form(apk, model);
    }

    @RequiresPermissions("sys:apk:upload")
    @RequestMapping(value = "/save")
    public String save(Apk apk,Model model,RedirectAttributes redirectAttributes) {
        ResponseBo responseBo = this.apkService.saveApk(apk);
        addMessage(redirectAttributes, responseBo.getMsg());
        if(responseBo.getStatus()==1) {
            return "redirect:" + adminPath + "/sys/apk/list/?repage";
        }
        return form(apk, model);
    }

    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(Apk apk,Model model) {
        model.addAttribute("apk", apk);
        return "modules/sys/apkForm";
    }
}
