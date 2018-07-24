package com.youcheng.publiclibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.youcheng.publiclibrary.base.BaseApplication;
import com.youcheng.publiclibrary.log.LogUtil;

import java.io.File;


public class FileCacheUtil {

    public static final String TAG = "AppCache";
    private static FileCacheUtil sInstance;

    public static synchronized FileCacheUtil getInstance() {
        if (sInstance == null) {
            sInstance = new FileCacheUtil();
        }
        return sInstance;
    }

    public boolean isExistDataCacheByAbsolutePath(String cachefile) {

        boolean exist = false;

        File data = new File(cachefile);
        if (data.exists()) {
            exist = true;
        }
        return exist;

    }

    public static boolean isExternalStorageAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getExternalStorageDirectory() {
        return android.os.Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/android/data";
    }

    public String getSDPath() {
        if (isExternalStorageAvailable()) {
            return getExternalStorageDirectory() + "/"
                    + BaseApplication.getInstance().getPackageName();
        }
        return "";
    }


    public String getSDRootPath() {
        if (isExternalStorageAvailable()) {
            return android.os.Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        }
        return "";
    }

    public String getInternalDir() {
        return BaseApplication.getInstance().getFilesDir().getAbsolutePath();
    }

    public String getAppCacheDir() {
        String dir = getSDPath();
        if (TextUtils.isEmpty(dir)) {
            dir = getInternalDir();
        }

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        LogUtil.v(TAG, "dir" + dir);
        return dir;
    }

    public static File getCacheDirectory(Context context) {
        File file = new File(context.getExternalCacheDir(), "MyCache");
        if (!file.exists()) {
            boolean b = file.mkdirs();
            Log.e("file", "文件不存在  创建文件    " + b);
        } else {
            Log.e("file", "文件存在");
        }
        return file;
    }

    public String getImageCacheDir() {
        String cacheDir = FileCacheUtil.getInstance().getAppCacheDir()
                + "/imageCache";
        File appCacheDir = new File(cacheDir);
        if (!appCacheDir.exists()) {
            appCacheDir.mkdirs();
        }
        return cacheDir;
    }

}
