package com.ulplanet.trip.modules.sys.service;

import com.qiniu.util.StringMap;
import com.ulplanet.trip.common.security.Digests;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.Encodes;
import com.ulplanet.trip.common.utils.FileManager;
import com.ulplanet.trip.common.utils.StringUtils;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.modules.ims.bo.ResponseBo;
import com.ulplanet.trip.modules.sys.dao.ApkDao;
import com.ulplanet.trip.modules.sys.entity.Apk;
import com.ulplanet.trip.modules.sys.utils.QiniuUploadUtil;
import net.erdfelt.android.apk.AndroidApk;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/11/11.
 */
@Service
public class ApkService  extends CrudService<ApkDao, Apk> {

    @Resource
    private ApkDao apkDao;

    public Apk upload(Apk uploadAPK,MultipartFile file) {
        try {
            byte [] bytes = file.getBytes();
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File f = fi.getStoreLocation();
            String size = "";
            byte[] md5Bytes = Digests.md5(bytes);
            String md5 = Encodes.encodeHex(md5Bytes);
            size = f.length()+"";
            if(f.exists()){
                FileIndex ufi = new FileIndex();
                ufi.setmUpfile(file);
                ufi.setTruename(file.getOriginalFilename());
                ufi.setMcode("apk");
                ufi = FileManager.save(ufi);
                uploadAPK.setUrl(ufi.getPath());
                uploadAPK.setMd5(md5);
                uploadAPK.setSize(size);
                this.saveApk(uploadAPK);
            }
            return uploadAPK;
        }catch (IOException e){
            e.printStackTrace();
        }
        return uploadAPK;
    }



    public ResponseBo saveApk(Apk apk) {
        int i = 0;
        if(StringUtils.isBlank(apk.getId())) {
            List<Apk> list = apkDao.findByParam(apk);
            if (list.size() > 0) {
                Apk apk1 = list.get(0);
                apk.setId(apk1.getId());
                apk1.setVersion(apk.getVersion());
                apk1.setName(apk.getName());
                apk1.setUrl(apk.getUrl());
                apk1.setDescription(apk.getDescription());
                apk1.setPackageName(apk.getPackageName());
                apk1.setSize(apk.getSize());
                apk1.setMd5(apk.getMd5());
                apk1.preUpdate();
                i = apkDao.update(apk1);
            } else {
                apk.preInsert();
                i = apkDao.insert(apk);
            }
        }else{
            i = apkDao.update(apk);
        }
        return ResponseBo.getResult(i);
    }

    public ResponseBo delete(String id) {
        Apk apk = new Apk();
        apk.setId(id);
        return ResponseBo.getResult(apkDao.delete(apk));
    }
}
