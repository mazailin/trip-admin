package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Scenic;
import com.ulplanet.trip.modules.tim.entity.ScenicFile;
import com.ulplanet.trip.modules.tim.service.ScenicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 景点Controller
 * Created by zhangxd on 15/11/08.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/scenic")
public class ScenicController extends BaseController {

    @Autowired
    private ScenicService scenicService;

    @ModelAttribute
     public Scenic get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return scenicService.get(id);
        }else{
            return new Scenic();
        }
    }

    @ModelAttribute
    public ScenicFile getFile(@RequestParam(required=false) String fileId) {
        if (StringUtils.isNotBlank(fileId)){
            return scenicService.getFileById(fileId);
        }else{
            return new ScenicFile();
        }
    }

    @RequiresPermissions("tim:scenic:view")
    @RequestMapping(value = {"list", ""})
    public String list(Scenic scenic, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Scenic> page = scenicService.findPage(new Page<>(request, response), scenic);
        model.addAttribute("page", page);
        return "modules/tim/scenicList";
    }

    @RequiresPermissions("tim:scenic:view")
    @RequestMapping(value = "form")
    public String form(Scenic scenic, Model model) {
        model.addAttribute("scenic", scenic);
        return "modules/tim/scenicForm";
    }

    @RequiresPermissions("tim:scenic:edit")
    @RequestMapping(value = "save")
    public String save(Scenic scenic, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, scenic)){
            return form(scenic, model);
        }

        boolean isNew = StringUtils.isEmpty(scenic.getId());
        scenic = scenicService.saveScenic(scenic);
        addMessage(redirectAttributes, "保存景点'" + scenic.getName() + "'成功");
        if (isNew) {
            return "redirect:" + adminPath + "/tim/scenic/image?repage&id=" + scenic.getId();
        }
        return "redirect:" + adminPath + "/tim/scenic/list?repage";
    }

    @RequiresPermissions("tim:scenic:edit")
    @RequestMapping(value = "delete")
    public String delete(Scenic scenic, RedirectAttributes redirectAttributes) {
        scenicService.delete(scenic);
        addMessage(redirectAttributes, "删除景点" + scenic.getName() + "成功");
        return "redirect:" + adminPath + "/tim/scenic/list?repage";
    }

    @RequiresPermissions("tim:scenic:view")
    @RequestMapping(value = "image")
    public String image(Scenic scenic, Model model) {
        model.addAttribute("scenic", scenic);
        return "modules/tim/scenicImage";
    }

    @RequiresPermissions("tim:scenic:edit")
    @RequestMapping(value = "uploadData")
    @ResponseBody
    public Map<String, Object> uploadData(ScenicFile scenicFile, @RequestParam("file") MultipartFile file) {
        return scenicService.uploadData(scenicFile, file);
    }

    @RequiresPermissions("tim:scenic:view")
    @RequestMapping(value="findScenicFiles")
    @ResponseBody
    public Map<String, Object> getUpload(Scenic scenic) {
        return scenicService.findScenicFiles(scenic.getId());
    }

    @RequiresPermissions("tim:scenic:edit")
    @RequestMapping(value="deleteFile")
    @ResponseBody
    public void deleteFile(ScenicFile scenicFile) {
        scenicService.deleteScenicFile(scenicFile);
    }

    @RequiresPermissions("tim:scenic:edit")
    @RequestMapping(value="updateFile")
    @ResponseBody
    public void updateFile(ScenicFile scenicFile) {
        scenicService.updateScenicFile(scenicFile);
    }

}
