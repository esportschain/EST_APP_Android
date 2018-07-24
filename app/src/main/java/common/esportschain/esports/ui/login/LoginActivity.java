package common.esportschain.esports.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youcheng.publiclibrary.utils.AppActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.event.LoginEvent;
import common.esportschain.esports.mvp.model.LoginModel;
import common.esportschain.esports.mvp.presenter.LoginPresenter;
import common.esportschain.esports.mvp.view.LoginView;
import common.esportschain.esports.ui.home.BindWebViewActivity;
import common.esportschain.esports.utils.GlideUtil;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 * <p>
 * 登录界面
 */

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;

    @BindView(R.id.tv_login_app_name)
    TextView tvLoginAppName;

    @BindView(R.id.tv_login_app_content)
    TextView tvLoginAppContent;

    @BindView(R.id.ll_steam_login)
    LinearLayout llSteamLogin;

    @BindView(R.id.ll_facebook_login)
    LinearLayout llFacebookLogin;

    @BindView(R.id.bt_login_email)
    Button btLoginEmail;

    /**
     * 邮箱注册
     */
    @BindView(R.id.tv_sign_up_email)
    TextView tvSignUpEmail;

    private long exitTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        GlideUtil.loadRandImg(this, R.mipmap.logo, ivLoginLogo);

        String signUpText = tvSignUpEmail.getText().toString();
        SpannableString spannableString = new SpannableString(signUpText);
        //设置文字颜色  文字颜色  文字开始截取的位置  文字结束的位置 设置属性
        spannableString.setSpan(new ForegroundColorSpan(
                        Color.parseColor("#31B4FF")),
                0,
                spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        tvSignUpEmail.setText(spannableString);

        tvSignUpEmail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public int getContentViewId() {
//        Glide.loadImg(this, "http://p6cdles5p.bkt.clouddn.com/pic_1522403333628688610.jpg", ivLoginLogo);
        return R.layout.activity_login;
    }

    @Override
    public void getLoginData(LoginModel loginModel) {

    }

    @OnClick({R.id.ll_steam_login, R.id.ll_facebook_login, R.id.bt_login_email, R.id.tv_sign_up_email})
    public void onClick(View v) {
        if (v.equals(llSteamLogin)) {
            Intent intent = new Intent(this, BindWebViewActivity.class);
            intent.putExtra("login_status", "1");
            intent.putExtra("login_type","1");
            startActivity(intent);
        } else if (v.equals(llFacebookLogin)) {
            Intent intent = new Intent(this, BindWebViewActivity.class);
            intent.putExtra("login_status", "2");
            intent.putExtra("login_type","1");
            startActivity(intent);
        } else if (v.equals(btLoginEmail)) {
            pushActivity(this, EmailLoginActivity.class);
        } else if (v.equals(tvSignUpEmail)) {
            pushActivity(this, EmailSignUpActivity.class);
//            Intent intent = new Intent(this, EmailSignUpAvatarActivity.class);
//            intent.putExtra("new_password", "1234");
//            intent.putExtra("pnew_password", "1234");
//            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(LoginEvent event) {
        if ("1".equals(event.mLoginEvent)) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            ToastUtil.showToast(getResources().getString(R.string.to_exit));
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 推出app
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
        } else {
            AppActivityManager.getInstance().killAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
