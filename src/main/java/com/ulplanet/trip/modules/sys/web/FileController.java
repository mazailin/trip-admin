package com.ulplanet.trip.modules.sys.web;

import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件Controller
 * Created by zhangxd on 15/10/20.
 */
@Controller
@RequestMapping(value = "${adminPath}/userfiles")
public class FileController extends BaseController {

    @RequestMapping(value = "/{module}/{folder}/{name}.{type}", method = RequestMethod.GET)
    public void get(HttpServletRequest req, HttpServletResponse resp,
                    @PathVariable String module, @PathVariable String folder,
                    @PathVariable String name, @PathVariable String type)
            throws ServletException, IOException {
        String filepath = module + "/" + folder + "/" + name + "." + type;
        try {
            filepath = UriUtils.decode(filepath, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            logger.error(String.format("解释文件路径失败，URL地址为%s", filepath), e1);
        }
        File file = FileManager.getFileByRealpath(filepath);
        try {
            if (file == null) {
                logger.error(String.format("文件%s未找到", filepath));
            } else {
                FileCopyUtils.copy(new FileInputStream(file), resp.getOutputStream());
                resp.setHeader("Content-disposition", "attachment; filename=\""+file.getName()+"\"");
                resp.setHeader("Content-Type", "application/octet-stream");
            }
        } catch (FileNotFoundException e) {
            logger.error(String.format("文件%s未找到", filepath), e);
        }
    }
	
}
