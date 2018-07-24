package com.youcheng.publiclibrary.listener;

public interface NetworkRequestListener<T> {

    void onSuccess(T data);

    void onFailure(int code, String msg);
}
