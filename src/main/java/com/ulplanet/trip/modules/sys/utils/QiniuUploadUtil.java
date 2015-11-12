package com.ulplanet.trip.modules.sys.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛云上传工具类
 * Created by makun on 2015/9/17.
 */

public class QiniuUploadUtil {

    public static final String QINIU_AK						= "n2vApSyhCB2Skg73W1ZrH_mk-ii5PUD-kk8AubZf";

    public static final String QINIU_SK						= "2DV4NJhJzzusfzcdTmdgmrHmYe_PnlhPM7qQELjL";

    public static final String QINIU_URL 					= "http://7xluz8.dl1.z0.glb.clouddn.com/";
    private Auth auth = Auth.create(QINIU_AK,QINIU_SK) ;
    private UploadManager uploadManager = new UploadManager();
    // 简单上传，使用默认策略
    private String getUpToken0(){
        return auth.uploadToken("bucket");
    }

    // 覆盖上传
    private String getUpToken1(){
        return auth.uploadToken("bucket", "key");
    }

    // 设置指定上传策略
    private String getUpToken2(){
        return auth.uploadToken("bucket", null, 3600, new StringMap()
                .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
                .put("callbackBody", "key=$(key)&hash=$(etag)"));
    }

    // 设置预处理、去除非限定的策略字段
    private String getUpToken3(){
        return auth.uploadToken("bucket", null, 3600, new StringMap()
                .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
                .putNotEmpty("persistentPipeline", ""), true);
    }

    /**
     * 生成上传token
     *
     * @param bucket  空间名
     * @param key     key，可为 null
     * @param expires 有效时长，单位秒。默认3600s
     * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
     *        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
     * @param strict  是否去除非限定的策略字段，默认true
     * @return 生成的上传token
     */
    public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict){
        return auth.uploadToken(bucket);
    }

    /**
     * 上传文件
     * @param data      byte数组
     * @param key       文件名称
     * @param token     生成的token
     * @return
     */
    public String upload(byte[] data,String key,String token) {
        try {

            Response response = uploadManager.put(data, key, token);
            if(response.isOK()) {
                DefaultPutRet ret = response.jsonToObject(DefaultPutRet.class);
                return ret.key;
            }
            return null;
        }catch (QiniuException e){
            e.printStackTrace();
            return null;
        }

    }

}