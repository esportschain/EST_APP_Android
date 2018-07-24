package com.youcheng.publiclibrary.base;
/**
 * @author qiaozhenxin
 * data为集合
 */
public class BaseClassResultBean<T> extends BaseResultBean {

    private T data;

    public T getResult() {
        return data;
    }

    public void setResult(T data) {
        this.data = data;
    }
}
