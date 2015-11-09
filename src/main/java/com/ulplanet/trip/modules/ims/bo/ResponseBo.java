package com.ulplanet.trip.modules.ims.bo;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ResponseBo{

    public ResponseBo(){}

    public ResponseBo(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private int status;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":" + status +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }

    public static ResponseBo getResult(int i){
        if(i>0) return new ResponseBo(1,"保存数据成功");
        else return new ResponseBo(0,"保存数据失败");
    }
}
