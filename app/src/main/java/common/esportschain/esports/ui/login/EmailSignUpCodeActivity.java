package common.esportschain.esports.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import common.esportschain.esports.event.SignUpEvent;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.presenter.EmailSignUpCodePresenter;
import common.esportschain.esports.mvp.view.EmailSignUpCodeView;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class EmailSignUpCodeActivity extends MvpActivity<EmailSignUpCodePresenter> implements EmailSignUpCodeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.et_sign_up_code)
    EditText etSignUpCode;

    @BindView(R.id.tv_sign_up_time)
    TextView tvSignUpTime;

    @BindView(R.id.bt_email_sign_up_input_code)
    Button btEmailSignUpInputCode;

    private String mParam;
    private String mSig;
    private String mCode;
    private String mEmail;
    private String brand;
    private CountDownTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.verification_code));
        ivBack.setImageResource(R.mipmap.back);
        brand = String.format(getResources().getString(R.string.sign_up_code_time), "60");
        tvSignUpTime.setText(brand);

        //按钮显示倒计时
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String brand = String.format(getResources().getString(R.string.sign_up_code_time), millisUntilFinished / 1000);
                //SpannableStringBuilder  初始化
                SpannableString spannableString = new SpannableString(brand);
                //设置文字颜色  文字颜色  文字开始截取的位置  文字结束的位置 设置属性
                spannableString.setSpan(new ForegroundColorSpan(
                                Color.parseColor("#31B4FF")),
                        spannableString.length() - 3,
                        spannableString.length() - 1,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                tvSignUpTime.setText(spannableString);
            }

            @Override
            public void onFinish() {
                tvSignUpTime.setText(brand);
            }
        }.start();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign_up_code;
    }

    @OnClick({R.id.iv_back, R.id.bt_email_sign_up_input_code})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(btEmailSignUpInputCode)) {
//            pushActivity(this, EmailSignUpPasswordActivity.class);

            mParam = AuthParam.AuthParam("", "");
            mSig = AuthSIG.AuthToken("Member", "App", "checkVcode", "-1", "", "");
            if (etSignUpCode.getText() != null) {
                if (etSignUpCode.getText().toString().length() == 4) {
                    mCode = etSignUpCode.getText().toString();
                    UserInfo userInfo = new UserInfoDbManger().loadAll().get(0);
                    mEmail = userInfo.getEmail();
                    mvpPresenter.getVerificationCode(mParam, mSig, mEmail, mCode);
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.code_format_error));
                }
            } else {
                ToastUtil.showToast(getResources().getString(R.string.please_enter_code));
            }
        }
    }

    @Override
    public void getVerificationCode(EmailSignUpCodeModel emailSignUpCodeModel) {
        //"key": "37d2981bb95418fb1165cb28a231fda8" // 注册时需要的key
        //存储key
        //进入下一个界面

        UserInfo userInfo = new UserInfoDbManger().loadAll().get(0);
        userInfo.setKey(emailSignUpCodeModel.getResult().getKey());
        new UserInfoDbManger().update(userInfo);

        Intent intent = new Intent(this, EmailSignUpPasswordActivity.class);
        intent.putExtra("status", "1");
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(SignUpEvent event) {
        if ("1".equals(event.mSignUpSuccess)) {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        //注销注册
        EventBus.getDefault().unregister(this);
    }
}
