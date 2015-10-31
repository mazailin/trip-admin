package com.ulplanet.trip.modules.tim.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tim.entity.Food;
import com.ulplanet.trip.modules.tim.entity.FoodFile;

import java.util.List;

/**
 * 美食管理Dao
 * Created by zhangxd on 15/10/26.
 */
@MyBatisDao
public interface FoodDao extends CrudDao<Food> {

    int insertFile(FoodFile foodFile);

    List<FoodFile> findFoodFiles(FoodFile foodFile);

    int deleteFoodFileById(String id);

    int deleteFoodFileByFood(String food);

}
