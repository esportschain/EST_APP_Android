package common.esportschain.esports.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.youcheng.publiclibrary.base.BaseActivity;
import com.youcheng.publiclibrary.base.BasePresenter;
import com.youcheng.publiclibrary.base.BaseView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import common.esportschain.esports.R;

/**
 * @author qiaozhenxin
 *         根据项目需求自己定制
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    public P mvpPresenter;
    private Unbinder mUnbinder;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //添加推送统计
        PushAgent.getInstance(context).onAppStart();

        if (this instanceof BaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0];
            try {
                mvpPresenter = (P) mPresenterClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mvpPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this);
        context = this;
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public void dismissLoading() {
        dismissDialog();
    }

    @Override
    public void requestFailure(int code, String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        mUnbinder.unbind();
    }

    public void pushFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
//        FragmentUtils.addFragment(fragmentManager, fragment, R.id.fl_root, false, true);
    }


    public void pushActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
