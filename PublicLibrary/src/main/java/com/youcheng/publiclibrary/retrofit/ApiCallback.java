package com.youcheng.publiclibrary.retrofit;


import com.google.gson.Gson;
import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.log.LogUtil;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * retrofit 回调方法
 */
public abstract class ApiCallback<M extends BaseResultBean> extends Subscriber<M> {


    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (e instanceof HttpException && e instanceof UnknownHostException) {
            //httpException.response().errorBody().string()
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            onFailure(code, msg);
        } else {
            onFailure(0, e.getMessage());
        }
        if (e instanceof SocketTimeoutException) {
            onFailure(0, e.getMessage());
        }

    }

    @Override
    public void onNext(M model) {
        int code = model.getCode();
        int ret = model.getRet();
//        if (!TextUtils.isEmpty(code)) {
//            switch (code) {
//
//            }
//        }
        if (code != -1) {
            switch (code) {

            }
        }
        if (ret != -1) {

        }
        LogUtil.json(new Gson().toJson(model));
        onSuccess(model);
    }

    @Override
    public void onCompleted() {
//        onFinish();
    }
}
