package common.esportschain.esports.utils;

import android.util.Log;

import com.youcheng.publiclibrary.utils.UIUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/5
 */

public class LogUtils {

    private static boolean isLog = false;

    public static void LogErrorUtil(final String Tag, final String LogStr) {
        UIUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isLog) {
                    Log.e(Tag, LogStr);
                }
            }
        });
    }
}
