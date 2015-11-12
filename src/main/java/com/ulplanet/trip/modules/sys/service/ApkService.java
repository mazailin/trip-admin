package com.ulplanet.trip.modules.sys.service;

import com.ulplanet.trip.common.security.Digests;
import com.ulplanet.trip.common.service.CrudService;
import com.ulplanet.trip.common.utils.Encodes;
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

    public Apk upload(String name, String description,MultipartFile file,MultipartFile[] osFiles) {
        QiniuUploadUtil uploadUtil = new QiniuUploadUtil();
        String token = uploadUtil.uploadToken("tripapk", null, 3600, null, true);
        Apk uploadAPK = new Apk();
        StringBuffer sb = new StringBuffer();
        try {
            byte [] bytes = file.getBytes();
            CommonsMultipartFile cf= (CommonsMultipartFile)file;
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File f = fi.getStoreLocation();
            String packageName = "";
            String version = "";
            String size = "";
            byte[] md5Bytes = Digests.md5(bytes);
            String md5 = Encodes.encodeHex(md5Bytes);
            size = f.length()+"";

            if(f.exists()){
                AndroidApk apk = new AndroidApk(f);
                packageName = apk.getPackageName();
                version = apk.getAppVersion();
                for(int z = 0;z<osFiles.length;z++){
                    byte [] bytes1 = osFiles[z].getBytes();
                    CommonsMultipartFile cf1= (CommonsMultipartFile)osFiles[z];
                    DiskFileItem fi1 = (DiskFileItem)cf1.getFileItem();
                    File f2 = fi1.getStoreLocation();
                    if(f2.exists()) {
                        String key = uploadUtil.upload(bytes1, name+"so"+z+".so", token);
                        sb.append(key);
                        sb.append(",");
                    }
                }
            }
            if(name.indexOf(".apk")<0){
                name = name + ".apk";
            }
            String key = uploadUtil.upload(bytes,name, token);
            uploadAPK.setName(key);
            uploadAPK.setDescription(description);
            uploadAPK.setUrl(QiniuUploadUtil.QINIU_URL + key);
            uploadAPK.setVersion(version);
            uploadAPK.setPackageName(packageName);
            uploadAPK.setMd5(md5);
            uploadAPK.setSize(size);
            uploadAPK.setSoUrls(sb.toString());
            this.saveApk(uploadAPK);
            return uploadAPK;
        }catch (IOException e){
            e.printStackTrace();
        }
        return uploadAPK;
    }



    public ResponseBo saveApk(Apk apk) {
        int i = 0;
        if(apk.getId()==null) {
            List<Apk> list = apkDao.findByParam(apk);
            if (list.size() > 0) {
                Apk apk1 = list.get(0);
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
