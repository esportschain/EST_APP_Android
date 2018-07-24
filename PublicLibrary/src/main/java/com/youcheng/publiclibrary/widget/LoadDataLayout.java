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

 
    private View bindView;


    private View emptyView;
    private ImageView emptyImg;
    private TextView emptyTv;
    private int emptyImgId;
    private String emptyString;
  
    private View errorView;
    private ImageView errorImg;
    private TextView errorTv;
    private int errorImgId;
    private String errorString;
  
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
     
        params.gravity = Gravity.CENTER;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadDataLayout, 0, 0);

        /**
         */
        int emptyLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_empty_layout, R.layout.page_empty);
        emptyImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_empty_img, 0);
        emptyString = array.getString(R.styleable.LoadDataLayout_ldl_empty_tv);
 
        emptyView = View.inflate(context, emptyLayoutId, null);


        addView(emptyView, params);
      
        int errorLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_error_layout, R.layout.page_error);
        errorImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_error_img, 0);
        errorString = array.getString(R.styleable.LoadDataLayout_ldl_error_tv);
        errorView = View.inflate(context, errorLayoutId, null);



        addView(errorView, params);


        int loadingLayoutId = array.getResourceId(R.styleable.LoadDataLayout_ldl_loading_layout, R.layout.page_loading);
        loadingImgId = array.getResourceId(R.styleable.LoadDataLayout_ldl_loading_img, 0);
        loadingString = array.getString(R.styleable.LoadDataLayout_ldl_loading_tv);
        loadingView = View.inflate(context, loadingLayoutId, null);


        addView(loadingView, params);

        array.recycle();
        setGoneAll();

    }

 
    private void setGoneAll() {
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
    }

  
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


    public void showEmpty(String s) {
        showEmpty(s, null);
    }


    public void showEmpty(SetImgCallBack callBack){
        showEmpty(null,callBack);
    }


    public void showEmpty() {
        showEmpty(null,null);
    }


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


    public void setRefreshListener(OnClickListener listener) {
        emptyView.setOnClickListener(listener);
        errorView.setOnClickListener(listener);
    }


    public void setBindView(View view) {
        this.bindView = view;
    }
}
