package com.ulplanet.trip.modules.tim.service;

import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.tim.dao.ShopDao;
import com.ulplanet.trip.modules.tim.entity.FileMeta;
import com.ulplanet.trip.modules.tim.entity.Shop;
import com.ulplanet.trip.modules.tim.entity.ShopFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 购物Service
 * Created by zhangxd on 15/11/08.
 */
@Service
public class ShopService extends CrudService<ShopDao, Shop> {

    @Autowired
    private ShopDao shopDao;

    public Shop saveShop(Shop shop) {
        if (StringUtils.isBlank(shop.getId())){
            shop.preInsert();
            shopDao.insert(shop);
        } else {
            shop.preUpdate();
            shopDao.update(shop);
        }
        return shop;
    }

    public void delete(Shop shop) {
        shopDao.delete(shop);
        shopDao.deleteShopFileByShop(shop.getId());
    }

    public Map<String, Object> uploadData(ShopFile shopFile, MultipartFile file) {

        FileMeta fileMeta = new FileMeta();
        if (!StringUtils.isEmpty(shopFile.getShop()) && file.getSize() > 0) {
            FileIndex ufi = new FileIndex();
            ufi.setmUpfile(file);
            ufi.setTruename(file.getOriginalFilename());
            ufi.setMcode("shop");
            ufi = FileManager.save(ufi);
            shopFile.setName(ufi.getTruename());
            shopFile.setPath(ufi.getPath());
            shopFile.preInsert();
            shopDao.insertFile(shopFile);

            fileMeta.setName(ufi.getTruename());
            fileMeta.setId(shopFile.getId());
            fileMeta.setPath(ufi.getPath());
            fileMeta.setType(shopFile.getType());
            fileMeta.setDescription(shopFile.getDescription());
        } else {
            fileMeta.setError("上传失败");
        }

        LinkedList<FileMeta> files = new LinkedList<>();
        files.add(fileMeta);

        Map<String, Object> result = new HashMap<>();
        result.put("files", files);
        return result;
    }

    public Map<String, Object> findShopFiles(String shopId) {
        ShopFile shopFile = new ShopFile(null, shopId);

        List<ShopFile> shopFileList = shopDao.findShopFiles(shopFile);
        LinkedList<FileMeta> files = new LinkedList<>();
        for (ShopFile bean : shopFileList) {
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

    public void deleteShopFile(ShopFile shopFile) {
        if (StringUtils.isNotEmpty(shopFile.getId())) {
            FileManager.delete(shopFile.getPath());
            shopDao.deleteShopFileById(shopFile.getId());
        }
    }

    public ShopFile getFileById(String fileId) {
        return shopDao.getFileById(fileId);
    }


    public void updateShopFile(ShopFile shopFile) {
        shopDao.updateShopFile(shopFile);
    }
}
