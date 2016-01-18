package io.qingmayun;

import com.alibaba.fastjson.JSON;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.HttpClientUtils;
import com.ulplanet.trip.common.utils.SecurityUtils;
import io.qingmayun.modules.QingResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makun on 2016/1/14.
 */
public class QingHttpClient {

    private static final Logger log = LoggerFactory.getLogger(QingHttpClient.class);
    private final static String URL = "https://api.qingmayun.com/20150822";
    private final static String ACCOUNT_SID = "d61d307dc4e2436cb5f3e7412f566f81";
    private final static String AUTH_TOKEN = "c5d4a6be89ab439a92d363c896c6caad";
    private final static String APP_ID = "a3597886f74149c8aca8cd5f1a3b8919";
    private final static String CREATE = "/create/client";
    private final static String CLOSE = "/close/client";

    /**
     * 创建账户
     * @param phone
     * @return
     */
    public QingResultInfo create(String phone){
        String url = URL + CREATE;
        Map<String,String> map = new HashMap<>();
        String timestamp = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        map.put("accountSid",ACCOUNT_SID);
        map.put("appId",APP_ID);
        map.put("mobile",phone);
        map.put("timestamp",timestamp);
        map.put("sig", SecurityUtils.MD5(ACCOUNT_SID+AUTH_TOKEN+timestamp));
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        String response = httpClientUtils.post(url,map);
        log.warn("轻码云创建接口：" + response);
        if(response!=null){
            QingResultInfo qingResultInfo = JSON.parseObject(response,QingResultInfo.class);
            return qingResultInfo;
        }
        return null;
    }

    /**
     * 关闭账户
     * @param clientNumber
     * @return
     */
    public QingResultInfo close(String clientNumber){
        String url = URL + CLOSE;
        Map<String,String> map = new HashMap<>();
        String timestamp = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        map.put("accountSid",ACCOUNT_SID);
        map.put("appId",APP_ID);
        map.put("clientNumber",clientNumber);
        map.put("timestamp",timestamp);
        map.put("sig", SecurityUtils.MD5(ACCOUNT_SID+AUTH_TOKEN+timestamp));
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        String response = httpClientUtils.post(url,map);
        log.warn("轻码云删除接口：" + response);
        if(response!=null){
            QingResultInfo qingResultInfo = JSON.parseObject(response,QingResultInfo.class);
            return qingResultInfo;
        }
        return null;
    }




}
