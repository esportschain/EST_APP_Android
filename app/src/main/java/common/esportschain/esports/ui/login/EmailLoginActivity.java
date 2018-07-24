package common.esportschain.esports.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.event.AccountSharedPreferences;
import common.esportschain.esports.event.LoginEvent;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.mvp.model.EmailLoginModel;
import common.esportschain.esports.mvp.presenter.EmailLoginPresenter;
import common.esportschain.esports.mvp.view.EmailLoginView;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.ui.home.HomeActivity;
import common.esportschain.esports.utils.EmailCheckUtils;
import common.esportschain.esports.utils.MD5Util;
import common.esportschain.esports.utils.PwdCheckUtil;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public class EmailLoginActivity extends MvpActivity<EmailLoginPresenter> implements EmailLoginView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.et_log_in_email)
    EditText etEmail;

    @BindView(R.id.et_log_in_password)
    EditText etPassword;

    @BindView(R.id.bt_login)
    Button btLogIn;

    private String mParam;
    private String mSig;
    private String mEmail;
    private String mPassword;
    private String mMD5Password;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.email_login_title));
        ivBack.setImageResource(R.mipmap.back);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_email_login;
    }

    @OnClick({R.id.iv_back, R.id.bt_login})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(btLogIn)) {
            if (etEmail.getText() != null) {
                if (etPassword.getText() != null) {
                    if (EmailCheckUtils.checkEmail(etEmail.getText().toString())) {
                        if (PwdCheckUtil.isLetterDigit(etPassword.getText().toString())) {
                            mParam = AuthParam.AuthParam("", "");
                            mSig = AuthSIG.AuthToken("Member", "App", "login", "-1", "", "");
                            mEmail = etEmail.getText().toString();
                            mPassword = etPassword.getText().toString();
                            mMD5Password = MD5Util.encodeMD5(mPassword);
                            mvpPresenter.getEmailLoginData(mParam, mSig, "Member", "App", "login", mEmail, mMD5Password);

                        } else {
                            ToastUtil.showToast(getResources().getString(R.string.password_digits));
                        }
                    } else {
                        ToastUtil.showToast(getResources().getString(R.string.email_format_is_incorrect));
                    }
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.please_enter_your_password));
                }
            } else {
                ToastUtil.showToast(getResources().getString(R.string.please_enter_your_email));
            }
        }
    }

    @Override
    public void emailLoginData(EmailLoginModel emailLoginModel) {

        if (new UserInfoDbManger().loadAll().size() == 0) {
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setAvatar(emailLoginModel.getResult().getAvatar());
            userInfo1.setNickName(emailLoginModel.getResult().getNickname());
            userInfo1.setUId(emailLoginModel.getResult().getUid() + "");
            userInfo1.setAuthkeys(emailLoginModel.getResult().getAuthkey());
            userInfo1.setToken(emailLoginModel.getResult().getToken());
            new UserInfoDbManger().insertOrReplace(userInfo1);
        } else {
            UserInfo userInfo2 = new UserInfoDbManger().loadAll().get(0);
            userInfo2.setAvatar(emailLoginModel.getResult().getAvatar());
            userInfo2.setNickName(emailLoginModel.getResult().getNickname());
            userInfo2.setAuthkeys(emailLoginModel.getResult().getAuthkey());
            userInfo2.setUId(emailLoginModel.getResult().getUid() + "");
            userInfo2.setToken(emailLoginModel.getResult().getToken());
            new UserInfoDbManger().update(userInfo2);
        }

        for (int i = 0; i < new UserInfoDbManger().loadAll().size(); i++) {
            UserInfo userInfo2 = new UserInfoDbManger().loadAll().get(i);
            Log.e("输出DB保存的信息", userInfo2.getEmail() + "\n" +
                    userInfo2.getAvatar() + "\n" +
                    userInfo2.getKey() + "\n" +
                    userInfo2.getNickName() + "\n" +
                    userInfo2.getToken() + "\n" +
                    userInfo2.getAuthkey() + "\n" +
                    userInfo2.getUId());
        }

        AccountSharedPreferences.setIsLogin(true);
        ToastUtil.showToast(getResources().getString(R.string.login_success));
        EventBus.getDefault().post(new LoginEvent("1"));
        pushActivity(this, HomeActivity.class);
        finish();
    }
}
