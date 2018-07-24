package common.esportschain.esports.utils;

import android.widget.Toast;

import com.youcheng.publiclibrary.utils.UIUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/5
 */

public class ToastUtil {
    public static Toast mToast;

    /**
     * 立即连续弹吐司
     *
     * @param msg
     */
    public static void showToast(final String msg) {
        UIUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(UIUtil.getContext(), "", Toast.LENGTH_SHORT);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    public static void showToast(final int msg) {
        UIUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(UIUtil.getContext(), "", Toast.LENGTH_SHORT);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }
}
