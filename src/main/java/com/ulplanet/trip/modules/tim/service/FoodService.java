package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.tim.dao.FoodDao;
import com.ulplanet.trip.modules.tim.entity.FileMeta;
import com.ulplanet.trip.modules.tim.entity.Food;
import com.ulplanet.trip.modules.tim.entity.FoodFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 美食Service
 * Created by zhangxd on 15/10/26.
 */
@Service
public class FoodService extends CrudService<FoodDao, Food> {

    @Autowired
    private FoodDao foodDao;

    public void saveFood(Food food) {
        if (StringUtils.isBlank(food.getId())){
            food.preInsert();
            foodDao.insert(food);
        } else {
            food.preUpdate();
            foodDao.update(food);
        }
    }

    public void delete(Food food) {
        foodDao.delete(food);
        foodDao.deleteFoodFileByFood(food.getId());
    }

    public Map<String, Object> uploadData(String foodId, MultipartFile file) {

        FileMeta fileMeta = new FileMeta();
        if (!StringUtils.isEmpty(foodId) && file.getSize() > 0) {
            FileIndex ufi = new FileIndex();
            ufi.setmUpfile(file);
            ufi.setTruename(file.getOriginalFilename());
            ufi.setMcode("food");
            ufi = FileManager.save(ufi);
            FoodFile foodFile = new FoodFile(null, foodId);
            foodFile.setName(ufi.getTruename());
            foodFile.setType("2");
            foodFile.setPath(ufi.getPath());
            foodFile.preInsert();
            foodDao.insertFile(foodFile);

            fileMeta.setName(ufi.getTruename());
            fileMeta.setId(ufi.getFileid());
            fileMeta.setPath(ufi.getPath());
            fileMeta.setType("2");
            fileMeta.setDescription("");
        } else {
            fileMeta.setError("上传失败");
        }

        LinkedList<FileMeta> files = new LinkedList<>();
        files.add(fileMeta);

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public Map<String, Object> findFoodFiles(String foodId) {
        FoodFile foodFile = new FoodFile(null, foodId);

        List<FoodFile> foodFileList = foodDao.findFoodFiles(foodFile);
        LinkedList<FileMeta> files = new LinkedList<>();
        for (FoodFile bean : foodFileList) {
            FileMeta fileMeta = new FileMeta();
            fileMeta.setName(bean.getName());
            fileMeta.setId(bean.getId());
            fileMeta.setType(bean.getType());
            fileMeta.setPath(bean.getPath());
            fileMeta.setSize("10000");
            fileMeta.setDescription(bean.getDescription());
            files.add(fileMeta);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public void deleteFoodFile(String id) {
        foodDao.deleteFoodFileById(id);
    }
}
