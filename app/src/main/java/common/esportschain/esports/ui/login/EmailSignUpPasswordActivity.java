package common.esportschain.esports.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.event.AccountSharedPreferences;
import common.esportschain.esports.event.SignUpEvent;
import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;
import common.esportschain.esports.mvp.presenter.EmailSignUpPasswordPresenter;
import common.esportschain.esports.mvp.view.EmailSignUpPasswordView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.utils.MD5Util;
import common.esportschain.esports.utils.PwdCheckUtil;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/13
 * <p>
 * 注册输入密码
 * 修改密码
 */

public class EmailSignUpPasswordActivity extends MvpActivity<EmailSignUpPasswordPresenter> implements EmailSignUpPasswordView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.et_sign_up_one_password)
    EditText etSignUpOnePassword;

    @BindView(R.id.et_sign_up_two_password)
    EditText etSignUpTwoPassword;

    @BindView(R.id.bt_sign_up_password_next)
    Button btSignUpSuccess;

    @BindView(R.id.tv_sign_up_set_old_password_instructions)
    TextView tvSettingOldPasswordInstructiongs;

    @BindView(R.id.et_sign_up_old_password)
    EditText etSignUpOldPassword;

    private String mNewPassword;
    private String mPNewPassword;
    private String mOldPassword;

    private String mMD5NewPassword;
    private String mMD5PNewPassword;
    private String mMd5OldPassword;

    private int mStatus;

    private String mParam;
    private String mSig;
    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mStatus = Integer.parseInt(intent.getStringExtra("status"));
        //1 注册进入 2 修改密码进入
        if (mStatus == 1) {
            tvSettingOldPasswordInstructiongs.setVisibility(View.GONE);
            etSignUpOldPassword.setVisibility(View.GONE);
        } else if (mStatus == 2) {
            tvSettingOldPasswordInstructiongs.setVisibility(View.VISIBLE);
            etSignUpOldPassword.setVisibility(View.VISIBLE);
        }

        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.password));
        ivBack.setImageResource(R.mipmap.back);

    }

    @Override
    public void initData() {
        super.initData();
        userInfo = new UserInfoDbManger().loadAll().get(0);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign_up_password;
    }

    @OnClick({R.id.iv_back, R.id.bt_sign_up_password_next})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(btSignUpSuccess)) {
            if (mStatus == 1) {
                if (etSignUpOnePassword.getText() != null) {
                    if (etSignUpTwoPassword.getText() != null) {
                        if (etSignUpOnePassword.getText().toString().equals(etSignUpTwoPassword.getText().toString())) {
                            mNewPassword = etSignUpOnePassword.getText().toString();
                            mPNewPassword = etSignUpTwoPassword.getText().toString();
                            if (PwdCheckUtil.isLetterDigit(mNewPassword) && PwdCheckUtil.isLetterDigit(mPNewPassword)) {
                                if (PwdCheckUtil.isLetterDigitSpecial(mNewPassword) && PwdCheckUtil.isLetterDigitSpecial(mPNewPassword)) {
                                    AccountSharedPreferences.setPassWord(mNewPassword);
                                    Intent intent = new Intent(this, EmailSignUpAvatarActivity.class);
                                    intent.putExtra("new_password", mNewPassword);
                                    intent.putExtra("pnew_password", mPNewPassword);
                                    startActivity(intent);
                                } else {
                                    ToastUtil.showToast(getResources().getString(R.string.password_digits));
                                }
                            } else {
                                ToastUtil.showToast(getResources().getString(R.string.password_letters));
                            }
                        } else {
                            ToastUtil.showToast(getResources().getString(R.string.password_twice_is_inconsistent));
                        }
                    } else {
                        ToastUtil.showToast(getResources().getString(R.string.please_password_again));
                    }
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.please_enter_your_password));
                }
            } else if (mStatus == 2) {
                if (etSignUpOldPassword.getText() != null) {
                    if (etSignUpOnePassword.getText() != null) {
                        if (etSignUpTwoPassword.getText() != null) {
                            if (etSignUpOnePassword.getText().toString().equals(etSignUpTwoPassword.getText().toString())) {
                                mNewPassword = etSignUpOnePassword.getText().toString();
                                mPNewPassword = etSignUpTwoPassword.getText().toString();
                                mOldPassword = etSignUpOldPassword.getText().toString();
                                if (PwdCheckUtil.isLetterDigit(mNewPassword) && PwdCheckUtil.isLetterDigit(mPNewPassword)) {
                                    if (PwdCheckUtil.isLetterDigitSpecial(mNewPassword) && PwdCheckUtil.isLetterDigitSpecial(mPNewPassword)) {
                                        String ps = AccountSharedPreferences.getPassWord();
                                        if (ps.equals(mOldPassword)) {

                                            if (!ps.equals(mNewPassword) && !ps.equals(mPNewPassword)) {

                                                mMD5NewPassword = MD5Util.encodeMD5(mNewPassword);
                                                mMD5PNewPassword = MD5Util.encodeMD5(mPNewPassword);
                                                mMd5OldPassword = MD5Util.encodeMD5(mOldPassword);

                                                mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());

                                                mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_MODIFY_PASSWORD,
                                                        "old_pwd", mMd5OldPassword, "new_pwd", mMD5NewPassword, "rnew_pwd", mMD5PNewPassword,
                                                        userInfo.getAuthkeys(), mParam);

                                                mvpPresenter.postModifyPassword(mParam, mSig, mMd5OldPassword, mMD5NewPassword, mMD5PNewPassword);
                                            } else {
                                                ToastUtil.showToast(getResources().getString(R.string.new_ps_can_old_ps));
                                            }

                                        } else {
                                            ToastUtil.showToast(getResources().getString(R.string.old_ps_error_new_ps));
                                        }

                                    } else {
                                        ToastUtil.showToast(getResources().getString(R.string.password_digits));

                                    }
                                } else {
                                    ToastUtil.showToast(getResources().getString(R.string.password_letters));
                                }
                            } else {
                                ToastUtil.showToast(getResources().getString(R.string.password_twice_is_inconsistent));
                            }
                        } else {
                            ToastUtil.showToast(getResources().getString(R.string.please_password_again));
                        }
                    } else {
                        ToastUtil.showToast(getResources().getString(R.string.please_enter_your_password));
                    }
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.pleases_enter_original_password));
                }
            }
        }
    }

    /**
     * 修改密码之后的回调
     *
     * @param emailSignUpPasswordModel
     */
    @Override
    public void postModifyPassword(EmailSignUpPasswordModel emailSignUpPasswordModel) {
        ToastUtil.showToast(getResources().getString(R.string.modify_password_success));
        logoutDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(SignUpEvent event) {
        if ("1".equals(event.mSignUpSuccess)) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    private void logoutDialog() {
        new UserInfoDbManger().deleteAll();
        AccountSharedPreferences.setIsLogin(false);
        AccountSharedPreferences.setEmailLogin(false);
        AccountSharedPreferences.setPassWord("");
        finish();
        Intent intent = new Intent(mActivity, LoginActivity.class);
        startActivity(intent);
    }
}
