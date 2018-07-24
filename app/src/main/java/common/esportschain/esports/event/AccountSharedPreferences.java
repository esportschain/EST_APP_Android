package common.esportschain.esports.event;

import android.content.Context;
import android.content.SharedPreferences;

import common.esportschain.esports.EsportsApplication;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public class AccountSharedPreferences {

    private static AccountSharedPreferences instance = null;
    public static final String SHARED_PREFERENCE_NAME = "shared_preference_name";

    //是否第一次进入
    private static final String IS_LOGIN = "IS_LOGIN";

    public static synchronized AccountSharedPreferences sharedPreferences() {
        if (instance == null) {
            instance = new AccountSharedPreferences();
        }
        return instance;
    }

    private static SharedPreferences getPreferences() {
        return EsportsApplication.getContext().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void clear() {
        getPreferences().edit().clear().apply();
    }

    /**
     * 是否登录
     * @param isLogin
     */
    public static void setIsLogin(Boolean isLogin) {
        getPreferences().edit().putBoolean(IS_LOGIN, isLogin).apply();
    }

    public static Boolean getIsLogin() {
        return getPreferences().getBoolean(IS_LOGIN, false);
    }
}
