package com.youcheng.publiclibrary.base;

import android.app.Activity;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.youcheng.publiclibrary.listener.NetworkListener;
import com.youcheng.publiclibrary.utils.AppActivityManager;
import com.youcheng.publiclibrary.utils.Environment;
import com.youcheng.publiclibrary.widget.LoadingDialog;

import java.util.LinkedList;
import java.util.List;


/**
 * @author qiaozhenxin
 */
public abstract class BaseActivity extends AppCompatActivity implements NetworkListener {

    /**
     * 存储全局的activity
     */
    private List<Activity> mActivities = new LinkedList<>();

    /**
     * 当前前台的activity
     */
    private Activity mForegroundActivity;
    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppActivityManager.getInstance().addActivity(this);
        AppActivityManager.getActivityStackInfos();
        mActivity = this;
        synchronized (mActivities) {
            mActivities.add(this);
        }
        Environment.getInstance().addNetworkListener(this);
        setContentView(getContentViewId());
        initView(savedInstanceState);
        initData();
    }

    public abstract int getContentViewId();

    /**
     * 初始化View的方法，子类如果有View的初始化，自己覆盖实现
     */
    public void initView(Bundle savedInstanceState) {

    }

    /**
     * 初始化数据，加载数据的方法，自己覆盖实现
     */
    public void initData() {

    }
    /**
     * 对话框进度条
     */
    public LoadingDialog progressDialog;

    /**
     * 显示进度条
     *
     * @return 对话框进度条
     */
    public LoadingDialog showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(mActivity);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("loading");
        }
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示进度条
     *
     * @param message 进度条显示提示内容
     * @return 对话框进度条
     */
    public LoadingDialog showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new LoadingDialog(mActivity);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle(message);
        }
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            BaseApplication.getMainThreadHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 1600);
        }
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mActivity = this;
        mForegroundActivity = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 友盟统计 根据项目需求自行添加
         */
//        MobclickAgent.onPause(this);
        mForegroundActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mForegroundActivity = this;
        /**
         * 友盟统计 根据项目需求自行添加
         */
//        MobclickAgent.onResume(this);
    }

    /**
     * 关闭页面时
     */
    @Override
    public void onDestroy() {
        synchronized (mActivities) {
            AppActivityManager.getInstance().killActivity(this);
        }
        Environment.getInstance().removeNetworkListener(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 获取前台activity
     *
     * @return
     */
    public Activity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public void onWifiConnected(NetworkInfo networkInfo) {
        Log.e("WifiConnected===", "======");
    }

    @Override
    public void onMobileConnected(NetworkInfo networkInfo) {
        Log.e("onMobileConnected===", "======");
    }

    @Override
    public void onDisconnected(NetworkInfo networkInfo) {
        Log.e("onDisconnected===", "======");
    }


    /**
     * 点击除EditText的空白处键盘消失
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
