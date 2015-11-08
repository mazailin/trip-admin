package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.tim.dao.GuideDao;
import com.ulplanet.trip.modules.tim.entity.FileMeta;
import com.ulplanet.trip.modules.tim.entity.Guide;
import com.ulplanet.trip.modules.tim.entity.GuideFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 导购Service
 * Created by zhangxd on 15/11/08.
 */
@Service
public class GuideService extends CrudService<GuideDao, Guide> {

    @Autowired
    private GuideDao guideDao;

    public Guide saveGuide(Guide guide) {
        if (StringUtils.isBlank(guide.getId())){
            guide.preInsert();
            guideDao.insert(guide);
        } else {
            guide.preUpdate();
            guideDao.update(guide);
        }
        return guide;
    }

    public void delete(Guide guide) {
        guideDao.delete(guide);
        guideDao.deleteGuideFileByGuide(guide.getId());
    }

    public Map<String, Object> uploadData(GuideFile guideFile, MultipartFile file) {

        FileMeta fileMeta = new FileMeta();
        if (!StringUtils.isEmpty(guideFile.getGuide()) && file.getSize() > 0) {
            FileIndex ufi = new FileIndex();
            ufi.setmUpfile(file);
            ufi.setTruename(file.getOriginalFilename());
            ufi.setMcode("guide");
            ufi = FileManager.save(ufi);
            guideFile.setName(ufi.getTruename());
            guideFile.setPath(ufi.getPath());
            guideFile.preInsert();
            guideDao.insertFile(guideFile);

            fileMeta.setName(ufi.getTruename());
            fileMeta.setId(guideFile.getId());
            fileMeta.setPath(ufi.getPath());
            fileMeta.setType(guideFile.getType());
            fileMeta.setDescription(guideFile.getDescription());
        } else {
            fileMeta.setError("上传失败");
        }

        LinkedList<FileMeta> files = new LinkedList<>();
        files.add(fileMeta);

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public Map<String, Object> findGuideFiles(String guideId) {
        GuideFile guideFile = new GuideFile(null, guideId);

        List<GuideFile> guideFileList = guideDao.findGuideFiles(guideFile);
        LinkedList<FileMeta> files = new LinkedList<>();
        for (GuideFile bean : guideFileList) {
            FileMeta fileMeta = new FileMeta();
            fileMeta.setName(bean.getName());
            fileMeta.setId(bean.getId());
            fileMeta.setType(bean.getType());
            fileMeta.setPath(bean.getPath());
            fileMeta.setDescription(bean.getDescription());
            files.add(fileMeta);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public void deleteGuideFile(GuideFile guideFile) {
        if (StringUtils.isNotEmpty(guideFile.getId())) {
            FileManager.delete(guideFile.getPath());
            guideDao.deleteGuideFileById(guideFile.getId());
        }
    }

    public GuideFile getFileById(String fileId) {
        return guideDao.getFileById(fileId);
    }


    public void updateGuideFile(GuideFile guideFile) {
        guideDao.updateGuideFile(guideFile);
    }
}
