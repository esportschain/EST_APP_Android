package com.youcheng.publiclibrary.base;

/**
 * BaseView接口
 *
 * @author qiaozhenxin
 */

public interface BaseView {
    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void hideLoading();

    void dismissLoading();

    /**
     * 根据请求失败返回的错误码做UI上的处理
     */
    void requestFailure(int code, String msg);

}
