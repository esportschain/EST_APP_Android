package common.esportschain.esports.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import common.esportschain.esports.event.SignUpEvent;
import common.esportschain.esports.mvp.model.EmailSignUpModel;
import common.esportschain.esports.mvp.presenter.EmailSignUpPresenter;
import common.esportschain.esports.mvp.view.EmailSignUpView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.utils.EmailCheckUtils;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/13
 * 邮箱注册
 */

public class EmailSignUpActivity extends MvpActivity<EmailSignUpPresenter> implements EmailSignUpView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.et_sign_up_input_email)
    EditText etSignUpInputEmail;

    @BindView(R.id.bt_sign_up_send_code)
    Button btSendCode;

    private String mEmail;
    private String mParam;
    private String mSig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.sign_up_title));
        ivBack.setImageResource(R.mipmap.back);
        etSignUpInputEmail.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void getCodeSuccess(EmailSignUpModel emailSignUpModel) {
        if (new UserInfoDbManger().loadAll().size() == 0) {
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setEmail(mEmail);
            new UserInfoDbManger().insertOrReplace(userInfo1);
        } else {
            UserInfo userInfo2 = new UserInfoDbManger().loadAll().get(0);
            userInfo2.setEmail(mEmail);
            new UserInfoDbManger().update(userInfo2);
        }

        pushActivity(this, EmailSignUpCodeActivity.class);
        ToastUtil.showToast(getResources().getString(R.string.code_been_send));
    }

    @OnClick({R.id.iv_back, R.id.bt_sign_up_send_code})
    public void onClick(View v) {
        if (v.equals(ivBack)) {
            this.finish();
        } else if (v.equals(btSendCode)) {
            if (etSignUpInputEmail.getText() != null) {
                mEmail = etSignUpInputEmail.getText().toString();
                if (EmailCheckUtils.checkEmail(mEmail)) {
                    mParam = AuthParam.AuthParam("", "");
                    mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_SEND_VCODE,
                            "email", mEmail, "", "", "", "", "-1", mParam);
                    mvpPresenter.getCode(mParam, mSig, mEmail);
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.email_format_is_incorrect));
                }
            } else {
                ToastUtil.showToast(getResources().getString(R.string.please_enter_your_email));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(SignUpEvent event) {
        if ("1".equals(event.mSignUpSuccess)) {
            finish();
        }
    }

}
