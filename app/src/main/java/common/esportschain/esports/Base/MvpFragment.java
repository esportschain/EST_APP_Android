package common.esportschain.esports.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youcheng.publiclibrary.base.BaseFragment;
import com.youcheng.publiclibrary.base.BasePresenter;
import com.youcheng.publiclibrary.base.BaseView;
import com.youcheng.publiclibrary.utils.FragmentUtils;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiaozhenxin
 *         根据项目需求自己定制
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    public P mvpPresenter;
    private View mRootView;
    private Unbinder mUnbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, mRootView);
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         * 友盟统计 根据项目需求自行添加
         */
//        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 友盟统计 根据项目需求自行添加
         */
//        MobclickAgent.onResume(getActivity());
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

    /**
     * 根据请求失败返回的错误码做UI上的处理
     */
    @Override
    public void requestFailure(int code, String msg) {
        if ("HTTP 401 Unauthorized".equals(msg)) {
            /**
             * 根据项目需求处理
             */
        } else {

        }
    }

    public void pushFragment(Fragment fragment) {
        hideSoftInput(getActivity());
        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);
//        FragmentUtils.addFragment(fragmentManager, fragment, R.id.fl_root, false, true);
    }

    public void popFragment() {
        hideSoftInput(getActivity());
        FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
        FragmentUtils.popFragment(fragmentManager);
    }

    public void pushActivity(Context packageContext, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(packageContext, cls);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        mUnbinder.unbind();
    }
}

