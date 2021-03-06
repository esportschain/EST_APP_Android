package common.esportschain.esports.ui.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.mvp.model.GamesDetailModel;
import common.esportschain.esports.mvp.presenter.GamesDetailPresenter;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/25
 */

public class GamesDetailActivity extends MvpActivity<GamesDetailPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.wb_games_detail)
    WebView wbGamedDetail;

    @BindView(R.id.bind_web_loading)
    RelativeLayout rlLoading;

    @BindView(R.id.games_detail_refreshLayout)
    RefreshLayout refreshLayout;

//    @BindView(R.id.refresh_header)
//    ClassicsHeader classicsHeader;

    private String mUrl;
    private String mParam;
    private String mSig;
    private String mAuthToken;
    private String encodeParam;
    private String mUrlStatus = "";

    private String mType;
    private JavaScriptInterface javaScriptInterface;
    private ArrayList<GamesDetailModel.GamesModel> options1Items = new ArrayList<>();
    private ArrayList<String> detail;


    private int webViewProgress;
    private CountDownTimer timer;
    private boolean isError = false;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.game_detail_title));
        ivBack.setImageResource(R.mipmap.back);
        Intent intent = getIntent();
        mParam = intent.getStringExtra("games_param");
        mSig = intent.getStringExtra("games_sig");
        mAuthToken = intent.getStringExtra("aus_token");

        setRefreshLayout();
        webRequest();
        timer();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_games_detail;
    }


    @Override
    protected void onStop() {
        super.onStop();
        wbGamedDetail.stopLoading();
        wbGamedDetail.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    /**
     * 1 steam 登录 2 FaceBook 登录 3 邮箱登录绑定登录状态
     */
    private String mStatus;

    public void webRequest() {
        javaScriptInterface = new JavaScriptInterface(mActivity);

        try {
            encodeParam = java.net.URLEncoder.encode(mParam, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mUrl = ApiStores.Url.BASE_URL + "/app.php?" +
                "&_param=" + encodeParam +
                "&sig=" + mSig +
                mAuthToken;

        wbGamedDetail.getSettings().setJavaScriptEnabled(true);
        wbGamedDetail.getSettings().setDomStorageEnabled(true);

        //js调Android
        wbGamedDetail.addJavascriptInterface(javaScriptInterface, "JsUtils");
        wbGamedDetail.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                mUrlStatus = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.setVisibility(View.GONE);
                isError = true;
                timer();
            }
        });

        wbGamedDetail.setWebChromeClient(new WebChromeClient() {
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

        wbGamedDetail.loadUrl(mUrl);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            if (wbGamedDetail.canGoBack()) {
                wbGamedDetail.goBack();
                mUrlStatus = "";
            } else {
                finish();
            }
        }
    }

    private class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        /**
         * 点击webwiew网页里按钮时候要做的事
         *
         * @param type
         * @param params
         */
        @JavascriptInterface
        public void callSelect(final String type, final String params) {
            EsportsApplication.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    EsportsApplication.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            mType = type;
                            StrToList(params);
                            showPickerView();
                        }
                    });
                }
            });
        }
    }

    private void StrToList(String result) {

        detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                GamesDetailModel.GamesModel entity = gson.fromJson(data.optJSONObject(i).toString(), GamesDetailModel.GamesModel.class);
                options1Items.add(entity);
                detail.add(entity.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                final String mId = options1Items.get(options1).getId();

                wbGamedDetail.post(new Runnable() {
                    @Override
                    public void run() {
                        wbGamedDetail.loadUrl("javascript:selectAfterAction('" + mType + "','" + mId + "')");
                    }
                });
            }
        })
                .setDividerColor(Color.BLACK)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(detail);
        pvOptions.show();
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && wbGamedDetail.canGoBack()) {
            wbGamedDetail.goBack();
            mUrlStatus = "";
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 刷新界面
     */
    private void setRefreshLayout() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if ("".equals(mUrlStatus)) {
                    wbGamedDetail.loadUrl(mUrl);
                } else {
                    wbGamedDetail.loadUrl(mUrlStatus);
                }
                refreshLayout.finishRefresh(1000);
            }
        });
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
