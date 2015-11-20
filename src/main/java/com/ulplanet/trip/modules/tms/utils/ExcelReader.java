package com.ulplanet.trip.modules.tms.utils;

import com.ulplanet.trip.common.utils.DateUtils;
import com.ulplanet.trip.common.utils.StringUtils;
import javafx.scene.control.Cell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by makun on 2015/11/18.
 */
public class ExcelReader {
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    public String checkExcelTitle(InputStream is,List<String> list,String fileName) {
        try {
            if(fileName.toLowerCase().endsWith("xls"))
                wb = new HSSFWorkbook(is);
            else if(fileName.toLowerCase().endsWith("xlsx"))
                this.wb = new XSSFWorkbook(is);
            else return "文件名错误";
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        if(colNum<list.size()){
            return "列数不正确";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String temp = getStringCellValue(row,i).toString();
            if(!temp.equals(list.get(i))){
                int j = i+1;
                sb.append("第"+j+"列 表头错误，应为"+list.get(i)+"<br>");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param is
     * @param cols
     * @param clazz
     * @param <T> the type of keys maintained by this map
     * @return
     * @throws Exception
     */
    public <T> Map<String, Object> readExcelContent(InputStream is,List<String> cols,Class<T> clazz) throws Exception {
        Map<String, Object> result = new HashMap<>();
        StringBuffer sb = new StringBuffer();
//        try {
//            if(fileName.toLowerCase().endsWith("xls"))
//                this.wb = new HSSFWorkbook(is);
//            if(fileName.toLowerCase().endsWith("xlsx"))
//                this.wb = new XSSFWorkbook(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        List<T> list = new ArrayList<>();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            T object = clazz.newInstance();
            row = sheet.getRow(i);
            for(int j = 0;j < cols.size();j++){
                String temp = getStringCellValue(row,j).toString();
                String str;
                if((str = checkCol(j,temp))!=null){
                    PropertyDescriptor pd = new PropertyDescriptor(cols.get(j), clazz);
                    Method method = pd.getWriteMethod();//获得写方法
                    method.invoke(object,str);
                }else{
                    sb.append("第"+i+"行，"+cols.get(j)+"列，数据：== "+temp+" ==参数不正确<br>");
                }
            }
            list.add(object);
        }
        result.put("error",sb.toString());
        result.put("data",list);
        return result;
    }

    private String checkCol(int num,String col){
        if(StringUtils.isBlank(col)){
            return null;
        }
        switch (num){
            case 0 : {//护照
                if(col.matches("1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]{7,8}|E[0-9]{8}")){
                    return col;
                }
                return null;
            }
            case 1 : return col;//姓名
            case 2 : {//电话
                        if(col.length()<=1)return null;
                        if(col.matches("[+]?([0-9]+[-]?)+")){
                            return col;
                        }
                        return null;
                    }
            case 3 : {//性别
                try {
                    if(col.equals("男"))col="1";
                    else if("女".equals(col))col="2";
                    else if("F".equals(col))col="2";
                    else if("M".equals(col))col="1";
                    Integer i = Integer.parseInt(col);
                    if(i >= 0 && i <= 2)return i.toString();
                    return null;
                }catch (Exception e){
                    return null;
                }
            }
            case 4 : {//游客类型
                try {
                    if(col.equals("导游"))col="0";
                    else if("游客".equals(col))col="1";
                    Integer i = Integer.parseInt(col);
                    if(i >= 0 && i <= 2)return i.toString();
                    return null;
                }catch (Exception e){
                    return null;
                }
            }
            case 5 : {//出生日期
                return checkDate(col);
            }
            case 6 : return col;//出生地
            case 7 : {//签发日期
                return checkDate(col);
            }
            case 8 : return col;//签发地
            case 9 : {//到期日期
                return checkDate(col);
            }
            case 10 : {//邮箱
                if(col.matches("[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+")){
                    return col;
                }
                return null;
            }
            default:return null;
        }
    }

    private String checkDate(String col){
        try {
            DateUtils.parseDate(col,"yyyy-MM-dd","yyyy/MM/dd");
            return col;
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String [] args){

        System.out.print((01>>011));
    }


    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param row Excel单元格
     * @return String 单元格数据内容
     */
    private Object getStringCellValue(Row row,int column) {
        Object val = "";
        try{
            org.apache.poi.ss.usermodel.Cell cell = row.getCell(column);
            if (cell != null){
                if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC){
                    DecimalFormat df = new DecimalFormat("#");
                    val = df.format(cell.getNumericCellValue());
                }else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING){
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA){
                    val = cell.getCellFormula();
                }else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN){
                    val = cell.getBooleanCellValue();
                }else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR){
                    val = cell.getErrorCellValue();
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val;
    }


}
