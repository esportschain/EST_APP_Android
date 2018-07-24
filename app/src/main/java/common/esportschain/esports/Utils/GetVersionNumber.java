package common.esportschain.esports.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import common.esportschain.esports.EsportsApplication;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class GetVersionNumber {

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */

    public static int getVersionNumber() {
        PackageManager pm = EsportsApplication.getContext().getPackageManager();
        PackageInfo pinfo = null;
        try {
            pinfo = pm.getPackageInfo(EsportsApplication.getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int versionCode = pinfo.versionCode;
        return versionCode;
    }

}
