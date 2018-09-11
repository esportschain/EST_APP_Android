package common.esportschain.esports.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youcheng.publiclibrary.base.BaseView;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.event.AccountSharedPreferences;
import common.esportschain.esports.event.BindEvent;
import common.esportschain.esports.mvp.model.LoginModel;
import common.esportschain.esports.mvp.presenter.BindWebViewPresenter;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.utils.AndroidBug5497Workaround;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/21
 */

public class BindWebViewActivity extends MvpActivity<BindWebViewPresenter> implements BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.bind_wb)
    WebView wbBind;

    @BindView(R.id.bind_web_loading)
    RelativeLayout rlLoading;

    private String mUrl;
    private String mParam;
    private String mSig;
    private UserInfo userInfo;
    private String encodeParam;

    private JavaScriptInterface javaScriptInterface;

    private int webViewProgress;
    private CountDownTimer timer;
    private boolean isError = false;

    /**
     * 1 steam 登录 2 FaceBook 登录 3 邮箱登录绑定登录状态
     */
    private String mStatus;
    /**
     * 1直接steam 登录 或者Facebook 直接登录 2邮箱登录之后绑定steam账号
     */
    private String mType;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidBug5497Workaround.assistActivity(this);
        Intent intent = getIntent();
        mStatus = intent.getStringExtra("login_status");
        mType = intent.getStringExtra("login_type");
        webUrlRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wbBind.stopLoading();
        wbBind.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.bind_title));
        ivBack.setImageResource(R.mipmap.back);
        clearWebViewCache();
        timer();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_bind_webview;
    }

    public void webUrlRequest() {
        javaScriptInterface = new JavaScriptInterface(mActivity);

        if (new UserInfoDbManger().loadAll().size() != 0) {
            userInfo = new UserInfoDbManger().loadAll().get(0);
            mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
        } else {
            mParam = AuthParam.AuthParam("", "");
        }

        try {
            encodeParam = java.net.URLEncoder.encode(mParam, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mUrl = ApiStores.Url.BASE_URL + "/app.php?" +
                "&_param=" + encodeParam +
                "&sig=" + mSig +
                "&c=" + "Member" +
                "&d=" + "App" +
                "&m=" + "thirdLogin" +
                "&thirdtype=" + mStatus;

        wbBind.getSettings().setJavaScriptEnabled(true);
        //设置 缓存模式
        wbBind.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //js调Android
        wbBind.addJavascriptInterface(javaScriptInterface, "JsUtils");

        wbBind.setWebViewClient(new WebViewClient() {
            /**
             * 在应用内加载网页
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            /**
             * webview 加载失败
             * @param view
             * @param request
             * @param error
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                isError = true;
                timer();
            }
        });

        wbBind.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                //当进度走到100的时候做自己的操作，我这边是弹出dialog
                webViewProgress = progress;
                if (progress == 100) {
                    if (rlLoading.getVisibility() != View.GONE) {
                        rlLoading.setVisibility(View.GONE);
                    }
                } else {
                    if (rlLoading.getVisibility() != View.VISIBLE) {
                        rlLoading.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        wbBind.loadUrl(mUrl);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        }
    }

    private class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void callThirdLogin(final String string) { //点击webwiew网页里按钮时候要做的事
            EsportsApplication.runOnUIThread(new Runnable() {
                @Override
                public void run() {

                    LoginModel loginModel = new Gson().fromJson(string, LoginModel.class);

                    if (new UserInfoDbManger().loadAll().size() == 0) {
                        UserInfo userInfo1 = new UserInfo();
                        userInfo1.setAvatar(loginModel.getResult().avatar);
                        userInfo1.setAuthkeys(loginModel.getResult().authkey);
                        userInfo1.setToken(loginModel.getResult().token);
                        userInfo1.setUId(String.valueOf(loginModel.getResult().uid));
                        userInfo1.setNickName(loginModel.getResult().nickname);
                        new UserInfoDbManger().insertOrReplace(userInfo1);
                    } else {
                        UserInfo userInfo2 = new UserInfoDbManger().loadAll().get(0);
                        userInfo2.setAvatar(loginModel.getResult().avatar);
                        userInfo2.setAuthkeys(loginModel.getResult().authkey);
                        userInfo2.setToken(loginModel.getResult().token);
                        userInfo2.setUId(String.valueOf(loginModel.getResult().uid));
                        userInfo2.setNickName(loginModel.getResult().nickname);
                        new UserInfoDbManger().update(userInfo2);
                    }

                    if ("1".equals(mType)) {
                        AccountSharedPreferences.setIsLogin(true);
                        ToastUtil.showToast(getResources().getString(R.string.login_success));
                        pushActivity(mActivity, HomeActivity.class);
                        finish();
                    } else if ("2".equals(mType)) {
                        EventBus.getDefault().post(new BindEvent("1"));
                        finish();
                    }
                }
            });
        }
    }

    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }

    public void timer() {
        //倒计时
        timer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isError) {
                    rlLoading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                if (webViewProgress < 100) {
                    ToastUtil.showToast(getResources().getString(R.string.connection_fail));
                    finish();
                } else if (isError) {
                    ToastUtil.showToast(getResources().getString(R.string.connection_fail));
                    finish();
                }
            }
        }.start();
    }
}

