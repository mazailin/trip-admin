package com.ulplanet.trip.modules.tim.dao;

import com.ulplanet.trip.common.persistence.CrudDao;
import com.ulplanet.trip.common.persistence.annotation.MyBatisDao;
import com.ulplanet.trip.modules.tim.entity.Shop;
import com.ulplanet.trip.modules.tim.entity.ShopFile;

import java.util.List;

/**
 * 购物管理Dao
 * Created by zhangxd on 15/11/08.
 */
@MyBatisDao
public interface ShopDao extends CrudDao<Shop> {

    int insertFile(ShopFile shopFile);

    List<ShopFile> findShopFiles(ShopFile shopFile);

    int deleteShopFileById(String id);

    int deleteShopFileByShop(String shop);

    ShopFile getFileById(String fileId);

    int updateShopFile(ShopFile shopFile);
}
