package com.youcheng.publiclibrary.base;

import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youcheng.publiclibrary.listener.NetworkListener;
import com.youcheng.publiclibrary.utils.Environment;
import com.youcheng.publiclibrary.utils.UIUtil;
import com.youcheng.publiclibrary.widget.LoadingDialog;


/**
 * @author qiaozhenxin
 */
public abstract class BaseFragment extends Fragment implements NetworkListener {

    public BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Environment.getInstance().addNetworkListener(this);
        initView(savedInstanceState);
        initData();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView =inflater.inflate(getContentViewId(),container,false);
        return mRootView;
    }

    public abstract int getContentViewId();

    /**
     * 初始化View的方法，子类如果有View的初始化，自己覆盖实现
     */
    public void initView(Bundle savedInstanceState){

    }
    /**
     * 初始化数据，加载数据的方法，自己覆盖实现
     */
    public void initData() {

    }

    public  void hideSoftInput(Activity activity) {
        if (activity != null) {
            UIUtil.hideSoftInput(activity, activity.getCurrentFocus());
        }
    }

    public  void showSoftInput(Activity activity) {
        if (activity != null) {
            UIUtil.showSoftInput(activity, activity.getCurrentFocus());
        }
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
            progressDialog.setTitle("loading...");
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
    public void onWifiConnected(NetworkInfo networkInfo) {

    }

    @Override
    public void onMobileConnected(NetworkInfo networkInfo) {

    }

    @Override
    public void onDisconnected(NetworkInfo networkInfo) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Environment.getInstance().removeNetworkListener(this);
    }
}
