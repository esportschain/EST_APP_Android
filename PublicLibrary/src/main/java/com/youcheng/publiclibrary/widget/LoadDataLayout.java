package com.youcheng.publiclibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youcheng.publiclibrary.R;

public class LoadDataLayout extends FrameLayout {

    private Context context;

    /**
     * 绑定显示数据的控件
     */
    private View bindView;

    /**
     * 空数据时的布局
     */
    private View emptyView;
    private ImageView emptyImg;
    private TextView emptyTv;
    private int emptyImgId;
    private String emptyString;
    /**
     * 错误时的布局
     */
    private View errorView;
    private ImageView errorImg;
    private TextView errorTv;
    private int errorImgId;
    private String errorString;
    /**
     * 数据加载时的布局
     */
    private View loadingView;
    private ProgressBar loadingPb;
    private TextView loadingTv;
    private int loadingImgId;
    private String loadingString;


    public LoadDataLayout(Context context) {
        this(context, null);
    }

    public LoadDataLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadDataLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //居中
        params.gravity = Gravity.CENTER;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadDataLayout, 0, 0);

        /**
         * 数据为空的布局
         */
        int emptyLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_empty_layout, R.layout.page_empty);
        emptyImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_empty_img, 0);
        emptyString = array.getString(R.styleable.LoadDataLayout_ldl_empty_tv);
        //获取空布局View
        emptyView = View.inflate(context, emptyLayoutId, null);

//        if (emptyLayoutId == R.layout.page_empty) {//如果是自带的布局
//            emptyImg = (ImageView) emptyView.findViewById(R.id.iv_empty);
//            emptyTv = (TextView) emptyView.findViewById(R.id.tv_empty);
//        }
        addView(emptyView, params);
        /**
         * 数据错误时的布局
         */
        int errorLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_error_layout, R.layout.page_error);
        errorImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_error_img, 0);
        errorString = array.getString(R.styleable.LoadDataLayout_ldl_error_tv);
        errorView = View.inflate(context, errorLayoutId, null);

//        if (errorLayoutId == R.layout.page_error) {//如果是自带的布局
//            errorImg = (ImageView) errorView.findViewById(R.id.iv_error);
//            errorTv = (TextView) errorView.findViewById(R.id.tv_error);
//        }

        addView(errorView, params);

        /**
         * 数据加载时的布局
         */
        int loadingLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_loading_layout, R.layout.page_loading);
        loadingImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_loading_img, 0);
        loadingString = array.getString(R.styleable.LoadDataLayout_ldl_loading_tv);
        loadingView = View.inflate(context, loadingLayoutId, null);

//        if (loadingLayoutId == R.layout.page_loading) {//如果是自带的布局
//            loadingPb = (ProgressBar) loadingView.findViewById(R.id.pb_loading);
//            loadingTv = (TextView) loadingView.findViewById(R.id.tv_loading);
//        }
        addView(loadingView, params);

        array.recycle();
        setGoneAll();

    }

    /**
     * 隐藏全部
     */
    private void setGoneAll() {
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
    }

    /**
     * @param s 提示文字
     * @param callBack 设置图片回调接口
     */
    public void showEmpty(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (emptyTv != null) {
            if (!TextUtils.isEmpty(s)) {
                emptyTv.setText(s);
            } else if (!TextUtils.isEmpty(emptyString)) {
                emptyTv.setText(emptyString);
            }
        }
        if (emptyImg != null) {
            if (emptyImgId != 0) {
                emptyImg.setImageResource(emptyImgId);
                emptyImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(emptyImg);
                emptyImg.setVisibility(VISIBLE);
            }
            if (emptyImgId == 0 && callBack == null){
                emptyImg.setVisibility(GONE);
            }
        }
        setGoneAll();
        emptyView.setVisibility(VISIBLE);
    }

    /**
     * 设置显示文字
     * @param s 提示文字
     */
    public void showEmpty(String s) {
        showEmpty(s, null);
    }

    /**
     * 设置图片
     * @param callBack 设置图片回调接口
     */
    public void showEmpty(SetImgCallBack callBack){
        showEmpty(null,callBack);
    }

    /**
     * 显示默认样式
     */
    public void showEmpty() {
        showEmpty(null,null);
    }

    /**
     * 显示错误布局
     *
     * @param s
     * @param callBack
     */
    public void showError(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }

        if (errorTv != null) {
            if (!TextUtils.isEmpty(s)) {
                errorTv.setText(s);
            } else if (!TextUtils.isEmpty(errorString)) {
                errorTv.setText(errorString);
            }
        }

        if (errorImg != null) {
            if (errorImgId != 0) {
                errorImg.setImageResource(errorImgId);
                errorImg.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                callBack.setImg(errorImg);
                errorImg.setVisibility(VISIBLE);
            }

            if (errorImgId == 0 && callBack == null){
                errorImg.setVisibility(GONE);
            }

        }

        setGoneAll();
        errorView.setVisibility(VISIBLE);
    }

    public void showError(String s) {
        showError(s, null);
    }

    public void showError(SetImgCallBack callBack){
        showError(null,callBack);
    }

    public void showError() {
        showError(null,null);
    }

    public void showSuccess() {
        if (bindView != null) {
            bindView.setVisibility(View.VISIBLE);
        }
        setGoneAll();
    }

    /**
     * 显示加载布局
     *
     * @param s
     * @param callBack
     */
    public void showLoading(String s, SetImgCallBack callBack) {
        if (bindView != null) {
            bindView.setVisibility(GONE);
        }
        if (loadingTv != null) {
            if (!TextUtils.isEmpty(s)) {
                loadingTv.setText(s);
            } else if (!TextUtils.isEmpty(loadingString)) {
                loadingTv.setText(loadingString);
            }
        }
        if (loadingPb != null) {
            if (loadingImgId != 0) {
                loadingPb.setVisibility(VISIBLE);
            }
            if (callBack != null) {
                loadingPb.setVisibility(VISIBLE);
            }

            if (loadingImgId == 0 && callBack == null){
                loadingPb.setVisibility(GONE);
            }

        }
        setGoneAll();
        loadingView.setVisibility(VISIBLE);
    }

    public void showLoading(String s) {
        showLoading(s, null);
    }

    public void showLoading(SetImgCallBack callBack) {
        showLoading(null,callBack);
    }

    public void showLoading() {
        showLoading(null,null);
    }

    public interface SetImgCallBack {
        void setImg(ImageView img);
    }

    /**
     * 设置点击屏幕重新加载数据
     *
     * @param listener
     */
    public void setRefreshListener(OnClickListener listener) {
        emptyView.setOnClickListener(listener);
        errorView.setOnClickListener(listener);
    }

    /**
     * 设置显示数据的View
     *
     * @param view
     */
    public void setBindView(View view) {
        this.bindView = view;
    }
}
