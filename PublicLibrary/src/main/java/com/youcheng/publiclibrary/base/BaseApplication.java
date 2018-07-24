package com.youcheng.publiclibrary.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;

import com.youcheng.publiclibrary.utils.Environment;

/**
 * @author qiaozhenxin
 */
public class BaseApplication extends Application {

    private static BaseApplication mContext;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Handler mMainThreadHandler;
    private static Looper mMainThreadLooper;
    private static BaseApplication mApp;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /**
         * 分包工具支持5.0以下系统
         */
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        /**
         *  初始化环境
         */
        Environment.getInstance().init(this);

        /**
         * 主线程和子线程
         */
        mMainThread = Thread.currentThread();
        /**
         * 主线程id
         * 当前线程id
         */
        mMainThreadId = android.os.Process.myTid();
        /**
         * 主线程handler
         */
        mMainThreadHandler = new Handler();
        mMainThreadLooper = getMainLooper();
    }

    public static BaseApplication getInstance() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }
    public static BaseApplication getAppContext() {
        return mApp;
    }
}
