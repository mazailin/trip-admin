package com.ulplanet.trip.modules.tim.web;

import com.ulplanet.trip.common.persistence.Page;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.web.BaseController;
import com.ulplanet.trip.modules.tim.entity.Guide;
import com.ulplanet.trip.modules.tim.entity.GuideFile;
import com.ulplanet.trip.modules.tim.service.GuideService;
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
 * 导购Controller
 * Created by zhangxd on 15/11/08.
 */
@Controller
@RequestMapping(value = "${adminPath}/tim/guide")
public class GuideController extends BaseController {

    @Autowired
    private GuideService guideService;

    @ModelAttribute
     public Guide get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return guideService.get(id);
        }else{
            return new Guide();
        }
    }

    @ModelAttribute
    public GuideFile getFile(@RequestParam(required=false) String fileId) {
        if (StringUtils.isNotBlank(fileId)){
            return guideService.getFileById(fileId);
        }else{
            return new GuideFile();
        }
    }

    @RequiresPermissions("tim:guide:view")
    @RequestMapping(value = {"list", ""})
    public String list(Guide guide, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Guide> page = guideService.findPage(new Page<>(request, response), guide);
        model.addAttribute("page", page);
        return "modules/tim/guideList";
    }

    @RequiresPermissions("tim:guide:view")
    @RequestMapping(value = "form")
    public String form(Guide guide, Model model) {
        model.addAttribute("guide", guide);
        return "modules/tim/guideForm";
    }

    @RequiresPermissions("tim:guide:edit")
    @RequestMapping(value = "save")
    public String save(Guide guide, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, guide)){
            return form(guide, model);
        }

        boolean isNew = StringUtils.isEmpty(guide.getId());
        guide = guideService.saveGuide(guide);
        addMessage(redirectAttributes, "保存导购'" + guide.getName() + "'成功");
        if (isNew) {
            return "redirect:" + adminPath + "/tim/guide/image?repage&id=" + guide.getId();
        }
        return "redirect:" + adminPath + "/tim/guide/list?repage";
    }

    @RequiresPermissions("tim:guide:edit")
    @RequestMapping(value = "delete")
    public String delete(Guide guide, RedirectAttributes redirectAttributes) {
        guideService.delete(guide);
        addMessage(redirectAttributes, "删除导购" + guide.getName() + "成功");
        return "redirect:" + adminPath + "/tim/guide/list?repage";
    }

    @RequiresPermissions("tim:guide:view")
    @RequestMapping(value = "image")
    public String image(Guide guide, Model model) {
        model.addAttribute("guide", guide);
        return "modules/tim/guideImage";
    }

    @RequiresPermissions("tim:guide:edit")
    @RequestMapping(value = "uploadData")
    @ResponseBody
    public Map<String, Object> uploadData(GuideFile guideFile, @RequestParam("file") MultipartFile file) {
        return guideService.uploadData(guideFile, file);
    }

    @RequiresPermissions("tim:guide:view")
    @RequestMapping(value="findGuideFiles")
    @ResponseBody
    public Map<String, Object> getUpload(Guide guide) {
        return guideService.findGuideFiles(guide.getId());
    }

    @RequiresPermissions("tim:guide:edit")
    @RequestMapping(value="deleteFile")
    @ResponseBody
    public void deleteFile(GuideFile guideFile) {
        guideService.deleteGuideFile(guideFile);
    }

    @RequiresPermissions("tim:guide:edit")
    @RequestMapping(value="updateFile")
    @ResponseBody
    public void updateFile(GuideFile guideFile) {
        guideService.updateGuideFile(guideFile);
    }

}
