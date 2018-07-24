package common.esportschain.esports;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.event.AccountSharedPreferences;
import common.esportschain.esports.mvp.presenter.NullPresenter;
import common.esportschain.esports.ui.home.HomeActivity;
import common.esportschain.esports.ui.login.LoginActivity;

/**
 * @author liangzhaoyou
 */
public class MainActivity extends MvpActivity<NullPresenter> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (AccountSharedPreferences.getIsLogin()) {
                    pushActivity(mActivity, HomeActivity.class);
                    finish();
                } else {
                    Intent intent1 = new Intent();
                    intent1.setClass(mActivity, LoginActivity.class);
                    startActivityForResult(intent1, 0);
                    finish();
                }
            }
        }, 2 * 1000);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
}
