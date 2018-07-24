package com.youcheng.publiclibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

public class AppActivityManager {
    private static Stack<Activity> mActivityStack;
    private static AppActivityManager mAppActivityManager;

    private AppActivityManager() {
    }

    /**
     * 单一实例
     */
    public static AppActivityManager getInstance() {
        if (mAppActivityManager == null) {
            mAppActivityManager = new AppActivityManager();
        }
        return mAppActivityManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public static Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        for (int i = mActivityStack.size() - 1; i > 0; i--) {
            if (mActivityStack.get(i).getClass().equals(cls)) {
                killActivity(mActivityStack.get(i));
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public static void getActivityStackInfos() {
        Stack<Activity> mActivityStack = AppActivityManager.mActivityStack;
        System.out.println("Activity栈中有 " + mActivityStack.size() + "个Activity");
        for (int i = 0; i < mActivityStack.size(); i++) {
            Activity activity = mActivityStack.get(i);
            System.out.println(activity.getClass().getSimpleName());
        }
    }

    public static int activityNum() {
        return AppActivityManager.mActivityStack.size();
    }

    /**
     * 结束主Acitivity上面所有的Activity
     */
    public void killAllTopActivity() {
        Stack<Activity> mActivityStack = AppActivityManager.mActivityStack;
        for (int i = mActivityStack.size() - 1; i > 0; i--) {
            killTopActivity();
        }
    }

    /**
     * 获取指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (int i = mActivityStack.size() - 1; i > 0; i--) {
            if (mActivityStack.get(i).getClass().equals(cls)) {
                return (mActivityStack.get(i));
            }
        }
        return null;
    }
}
