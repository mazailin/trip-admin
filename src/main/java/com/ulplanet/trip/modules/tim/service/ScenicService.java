package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.tim.dao.ScenicDao;
import com.ulplanet.trip.modules.tim.entity.FileMeta;
import com.ulplanet.trip.modules.tim.entity.Scenic;
import com.ulplanet.trip.modules.tim.entity.ScenicFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 景点Service
 * Created by zhangxd on 15/11/08.
 */
@Service
public class ScenicService extends CrudService<ScenicDao, Scenic> {

    @Autowired
    private ScenicDao scenicDao;

    public Scenic saveScenic(Scenic scenic) {
        if (StringUtils.isBlank(scenic.getId())){
            scenic.preInsert();
            scenicDao.insert(scenic);
        } else {
            scenic.preUpdate();
            scenicDao.update(scenic);
        }
        return scenic;
    }

    public void delete(Scenic scenic) {
        scenicDao.delete(scenic);
        scenicDao.deleteScenicFileByScenic(scenic.getId());
    }

    public Map<String, Object> uploadData(ScenicFile scenicFile, MultipartFile file) {

        FileMeta fileMeta = new FileMeta();
        if (!StringUtils.isEmpty(scenicFile.getScenic()) && file.getSize() > 0) {
            FileIndex ufi = new FileIndex();
            ufi.setmUpfile(file);
            ufi.setTruename(file.getOriginalFilename());
            ufi.setMcode("scenic");
            ufi = FileManager.save(ufi);
            scenicFile.setName(ufi.getTruename());
            scenicFile.setPath(ufi.getPath());
            scenicFile.preInsert();
            scenicDao.insertFile(scenicFile);

            fileMeta.setName(ufi.getTruename());
            fileMeta.setId(scenicFile.getId());
            fileMeta.setPath(ufi.getPath());
            fileMeta.setType(scenicFile.getType());
            fileMeta.setDescription(scenicFile.getDescription());
        } else {
            fileMeta.setError("上传失败");
        }

        LinkedList<FileMeta> files = new LinkedList<>();
        files.add(fileMeta);

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public Map<String, Object> findScenicFiles(String scenicId) {
        ScenicFile scenicFile = new ScenicFile(null, scenicId);

        List<ScenicFile> scenicFileList = scenicDao.findScenicFiles(scenicFile);
        LinkedList<FileMeta> files = new LinkedList<>();
        for (ScenicFile bean : scenicFileList) {
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

    public void deleteScenicFile(ScenicFile scenicFile) {
        if (StringUtils.isNotEmpty(scenicFile.getId())) {
            FileManager.delete(scenicFile.getPath());
            scenicDao.deleteScenicFileById(scenicFile.getId());
        }
    }

    public ScenicFile getFileById(String fileId) {
        return scenicDao.getFileById(fileId);
    }


    public void updateScenicFile(ScenicFile scenicFile) {
        scenicDao.updateScenicFile(scenicFile);
    }
}
