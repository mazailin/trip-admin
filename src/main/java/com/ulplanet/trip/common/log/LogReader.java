package com.ulplanet.trip.common.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.EhCacheUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.*;

/**
 * Created by makun on 2015/11/27.
 */
public class LogReader {

    private static String PATH = "D:\\log\\collection.log";

    private static String GOOGLE_URL = "https://maps.google.com/maps/api/geocode/json?latlng=%s,%s&language=zh-CN&sensor=false&key=AIzaSyDysuPLHS6doj3d6tspSKUTFXjWe3OeNyc";

    public LogReader(){

    }
    public LogReader(String path){
        this.PATH = path;
    }

    /**
     *
     * @param date format "yyyy-MM-dd"
     * @return Object List
     */
    public List<LogReaderBo> getObjectList(String date){
        String path = PATH;
        if(date!=null){
            path = PATH+"."+date;
        }
        File file = new File(path);
        if(file.exists()){
            try {
                List<LogReaderBo> list = new ArrayList<>();
                InputStream in = new FileInputStream(file);
                Reader r = new InputStreamReader(in);
                BufferedReader  reader = new BufferedReader(r);
                String line;
                while((line = reader.readLine()) != null){

                    Class<LogReaderBo> clazz = LogReaderBo.class;
                    LogReaderBo logReaderBo = clazz.newInstance();
                    int i;
                    if(line.indexOf("-- function --")>0){//信息收集
                        logReaderBo.setType("funtion");
                        i = 39;
                    }else if(line.indexOf("-- error --")>0){//错误信息
                        logReaderBo.setType("error");
                        i = 36;
                    }else continue;
                    line = line.substring(i);
                    String[] params = line.split(",");
                    for(String str : params){
                        String[] strs = str.split(":");
                        if(strs.length == 1){
                            String temp = strs[0];
                            if("package".equals(strs[0])){
                                temp = "_package";
                            }
                            try {
                                PropertyDescriptor pd = new PropertyDescriptor(temp, clazz);
                                Method method = pd.getWriteMethod();//获得写方法
                                method.invoke(logReaderBo, "");
                            }catch (Exception e){continue;}

                        }else if(strs.length > 1){
                            String temp = strs[0];
                            if("package".equals(strs[0])){
                                temp = "_package";
                            }
                            try {
                                PropertyDescriptor pd = new PropertyDescriptor(temp, clazz);
                                Method method = pd.getWriteMethod();//获得写方法
                                StringBuffer sb = new StringBuffer();
                                for(int k = 1;k<strs.length;k++){
                                    sb.append(strs[k]);
                                }
                                method.invoke(logReaderBo,sb.toString());
                            }catch (Exception e){continue;}
                        }
                    }
                    list.add(logReaderBo);
                }
                in.close();
                reader.close();
                r.close();
                return list;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param date format "yyyy-MM-dd"
     * @return Map List
     */
    public List<Map<String,String>> getMapList(String date){
        String path = PATH;
        if(date!=null){
            path = PATH+"."+date;
        }
        File file = new File(path);
        if(file.exists()){
            try {
                List<Map<String,String>> list = new ArrayList<>();
                InputStream in = new FileInputStream(file);
                Reader r = new InputStreamReader(in);
                BufferedReader  reader = new BufferedReader(r);
                String line;
                while((line = reader.readLine()) != null){
                    Map<String,String> map = new HashMap<>();
                    int i;
                    if(line.indexOf("-- function --")>0){//信息收集
                        map.put("type", "function");
                        i = 39;
                    }else if(line.indexOf("-- error --")>0){//错误信息
                        map.put("type", "error");
                        i = 36;
                    }else continue;
                    String d = line.substring(0,10);
                    line = line.substring(i);
                    String[] params = line.split(",");
                    for(String str : params){
                        String[] strs = str.split(":");
                        if(strs.length == 1){
                            String temp = strs[0];
                            if("package".equals(strs[0])){
                                temp = "_package";
                            }
                            if("date".equals(strs[0])){
                                map.put("date",d);
                                continue;
                            }
                            map.put(temp,"");
                        }else if(strs.length > 1){
                            String temp = strs[0];
                            if("package".equals(strs[0])){
                                temp = "_package";
                            }
                            if("date".equals(strs[0])){
                                map.put("date",d);
                                continue;
                            }
                            StringBuffer sb = new StringBuffer();
                            for(int k = 1;k<strs.length;k++){
                                sb.append(strs[k]);
                            }
                            map.put(temp,sb.toString());
                        }
                    }
                    list.add(map);
                }
                in.close();
                reader.close();
                r.close();
                return list;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }


    public Map<String,Map<String,String>> statistics(List<Map<String,String>> list){
        Map<String,Map<String,String>> infos = new HashMap<>();
        for(Map<String,String> map : list){
            Map<String,String> m = new HashMap<>();
            String date = map.get("date");
            String sim = map.get("userid");
            String[] strs;
            if(StringUtils.isNotEmpty(sim) && EhCacheUtils.get("logReader",sim)!=null){
                strs = (String[]) EhCacheUtils.get("logReader",sim);
            }else {
                strs = getCountry(sendGet(String.format(GOOGLE_URL, map.get("latitude"), map.get("longitude"))));
                EhCacheUtils.put("logReader",sim,strs);
            }
            if(infos.containsKey(date+strs[0])){//日期已存在
                m = infos.get(date+strs[0]);
            }else{//日期不存在
                m = loopEntry(map, m);
                m.put("country",strs[1]);
                m.put("_package",strs[0]);
            }
            m = getCount(map,m);
            infos.put(date+strs[0],m);
        }
        return infos;
    }

    public void export(Map<String,Map<String,String>> map){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(0);
        for(int i = 0 ;i < header().size();i++){//创建表头
            String head = header().get(i);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(head);
            sheet.autoSizeColumn(i, true);
            sheet.setColumnWidth(i, head.length() * 1024);
        }
        Set<String> set = map.keySet();
        int i = 1;
        for(String col : set){
            HSSFRow row1 = sheet.createRow(i);
            for(int j = 0;j < header().size();j++) {
                HSSFCell cell = row1.createCell(j);
                Map<String, String> m = map.get(col);
                boolean pFlag = false;
                if (m.containsKey("param_sum")) pFlag = true;
                if (j == 0) {
                    cell.setCellValue(col.substring(0,10));
                } else if (j <= 5) {
                    cell.setCellValue(m.get(body().get(j)));
                } else if (j <= 23) {
                    if (m.containsKey("param_" + (j - 6)))
                        cell.setCellValue(m.get("param_" + (j - 6)));
                } else if (j <= 41) {
                    if (m.containsKey("param_" + (j - 24)) && pFlag)
                        cell.setCellValue(new BigDecimal(m.get("param_" + (j - 24))).divide(new BigDecimal(m.get("param_sum")), 4, BigDecimal.ROUND_HALF_EVEN).toString());
                } else if (j == 42) {
                    if (pFlag)
                        cell.setCellValue(m.get("param_sum"));
                } else if (j <= 45) {
                    if (m.containsKey("errorfrom_" + (j - 43)))
                        cell.setCellValue(m.get("errorfrom_" + (j - 43)));
                } else if (j > 45) {
                    if (m.containsKey("errortype_" + (j - 46)))
                        cell.setCellValue(m.get("errorfrom_" + (j - 46)));
                }
                if(StringUtils.isBlank(cell.getStringCellValue())){
                    cell.setCellValue("0");
                }
            }
            i++;
        }
        try {
            File file = new File("D:\\report");
            if(!file.exists())file.mkdirs();
            FileOutputStream os = new FileOutputStream("D:\\report\\信息统计"+ DateUtils.formatDate(new Date(),"yyyyMMddSS")+".xls");
            wb.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> header(){
        List<String> list = new ArrayList<>();
        list.add("日期");
        list.add("用户ID");
        list.add("sim");
        list.add("手机型号");
        list.add("国家");
        list.add("版本号");
        list.add("天气"    );
        list.add("相机"    );
        list.add("工具箱"  );
        list.add("当地玩乐");
        list.add("地图"    );
        list.add("娱乐"    );
        list.add("音乐"    );
        list.add("视频"    );
        list.add("电话"    );
        list.add("聊天"    );
        list.add("wifi"    );
        list.add("紧急呼叫");
        list.add("一键还原");
        list.add("图库"    );
        list.add("翻译"    );
        list.add("租车"    );
        list.add("我的行程");
        list.add("个人信息");
        list.add("天气使用率"    );
        list.add("相机使用率"    );
        list.add("工具箱使用率"  );
        list.add("当地玩乐使用率");
        list.add("地图使用率"    );
        list.add("娱乐使用率"    );
        list.add("音乐使用率"    );
        list.add("视频使用率"    );
        list.add("电话使用率"    );
        list.add("聊天使用率"    );
        list.add("wifi使用率"    );
        list.add("紧急呼叫使用率");
        list.add("一键还原使用率");
        list.add("图库使用率"    );
        list.add("翻译使用率"    );
        list.add("租车使用率"    );
        list.add("我的行程使用率");
        list.add("个人信息使用率");
        list.add("功能使用总数");
        list.add("服务端错误次数");
        list.add("第三方错误次数");
        list.add("一般错误错误次数");
        list.add("灵云合成错误次数");
        list.add("科大讯飞错误次数");
        list.add("百度翻译错误次数");
        return list;
    }

    private static List<String> body(){
        List<String> list = new ArrayList<>();
        list.add("date");
        list.add("userid");
        list.add("sim");
        list.add("hinfo");
        list.add("country");
        list.add("version");
        return list;
    }


    private Map<String,String> getCount(Map<String,String> map,Map<String,String> m){
        if(map.containsKey("param")){
            Integer i = 1;
            if(m.containsKey("param_"+map.get("param").trim())){
                i = Integer.parseInt(m.get("param_" + map.get("param")));
                i++;
            }
            m.put("param_"+map.get("param").trim(),i.toString());
            if(m.containsKey("param_sum")){
                Integer j = Integer.parseInt(m.get("param_sum"));
                ++j;
                m.put("param_sum",j.toString());
            }else{
                m.put("param_sum","1");
            }
        }
        if(map.containsKey("errorfrom")){
            Integer i = 1;
            if(m.containsKey("errorfrom_"+map.get("errorfrom"))){
                i = Integer.parseInt(m.get("errorfrom_"+map.get("errorfrom")));
                i++;
            }
            m.put("errorfrom_"+map.get("errorfrom"), i.toString());
            if(m.containsKey("errorfrom_sum")){
                Integer j = Integer.parseInt(m.get("errorfrom_sum"));
                ++j;
                m.put("errorfrom_sum",j.toString());
            }else{
                m.put("errorfrom_sum","1");
            }
        }
        if (map.containsKey("errortype")){
            Integer i = 1;
            if(m.containsKey("errortype_"+map.get("errortype"))){
                i = Integer.parseInt(m.get("errortype_"+map.get("errortype")));
                i++;
            }
            m.put("errortype_"+map.get("errortype"),i.toString());
            if(m.containsKey("errortype_sum")){
                Integer j = Integer.parseInt(m.get("errortype_sum"));
                ++j;
                m.put("errortype_sum",j.toString());
            }else{
                m.put("errortype_sum","1");
            }
        }
        return m;

    }

    private Map<String,String> loopEntry(Map<String,String> map,Map<String,String> m){
        for(String key : map.keySet()){
            m.put(key,map.get(key));
        }
        return m;
    }

    private String parseFunctionType(String param){
        switch (param==null?"":param){
            case "0" : return "天气";
            case "1" : return "相机";
            case "2" : return "工具箱";
            case "3" : return "当地玩乐";
            case "4" : return "地图";
            case "5" : return "娱乐";
            case "6" : return "音乐";
            case "7" : return "视频";
            case "8" : return "电话";
            case "9" : return "聊天";
            case "10" : return "wifi";
            case "11" : return "紧急呼叫";
            case "12" : return "一键还原";
            case "13" : return "图片";
            case "14" : return "翻译";
            case "15" : return "租车";
            case "16" : return "我的行程";
            case "17" : return "个人信息";
            default:return  "";
        }

    }

    private String parseErrorType(String param){
        switch (param==null?"":param){
            case "1" : return "一般错误";
            case "2" : return "灵云合成";
            case "3" : return "科大讯飞";
            case "4" : return "百度翻译";
            default:return "";
        }
    }

    private String parseErrorFrom(String param){
        switch (param==null?"":param){
            case "1" : return "服务端";
            case "2" : return "第三方";
            default:return "";
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     * @param url  发送请求的URL
     * @return URL所代表远程资源的响应
     */

    private String sendGet(String url) {
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private String[] getCountry(String str){
        String [] strs = new String[2];
        JSONObject jsonObject = JSON.parseObject(str);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        if(jsonArray.size()==0){
            strs[0] = "none";
            strs[1] = "未获取到位置";
            return  strs;
        }
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        jsonArray = jsonObject1.getJSONArray("address_components");
        for(int i = 0;i < jsonArray.size();i++){
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            if(jsonObject2.getString("types").indexOf("country")>1){
                strs[0] = jsonObject2.getString("short_name");
                strs[1] = jsonObject2.getString("long_name");
                break;
            }
        }
        return  strs;

    }



    public static void main(String [] args) throws ParseException {

    }
}
