package common.esportschain.esports.base;


import com.youcheng.publiclibrary.base.BasePresenter;
import com.youcheng.publiclibrary.retrofit.AppClient;

import common.esportschain.esports.request.ApiStores;

/**
 * @author liangzhaoyou
 */
public class MyPresenter<V> extends BasePresenter<V> {
    public ApiStores apiStores;

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }
}
