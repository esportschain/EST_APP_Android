package com.youcheng.publiclibrary.base;


import java.io.Serializable;


/**
 * @author qiaozhenxin
 * 返回结果base类
 * 根据接口返回定义
 */
public class BaseResultBean implements Serializable {

    public static int RESULT_SUCCESS = 0;
    public static int RESULT_RET_SUCCESS= 0;
    public static int RESULT_RET_THREE= 300;
    public static int RESULT_RET_FOUR= 400;
    public static int RESULT_RET_FIVE= 500;
    private int code;
    private int ret;
    private String msg;

    public String getInfo() {
        return msg;
    }

    public void setInfo(String info) {
        this.msg = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
