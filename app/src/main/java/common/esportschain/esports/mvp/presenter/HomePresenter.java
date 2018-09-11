package common.esportschain.esports.mvp.presenter;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.view.HomeView;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class HomePresenter extends MyPresenter<HomeView> {

    public void getHomeData(String param, String sig, String c, String d, String m, String page) {
//        mvpView.showLoading();
        addSubscription(apiStores.getMineData(param, sig, c, d, m, page), new ApiCallback<HomeModel>() {
            @Override
            public void onSuccess(HomeModel model) {
//                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.getHomeData(model);
                } else {
//                    ToastUtil.showToast(model.getInfo());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.dismissLoading();
            }
        });
    }

    public void postUmDevice(String param, String sig, String c, String d, String m, String device) {
        mvpView.showLoading();
        addSubscription(apiStores.putDevice(param, sig, c, d, m, device), new ApiCallback<NullModel>() {
            @Override
            public void onSuccess(NullModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.postDeviceToken(model);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.dismissLoading();
            }
        });
    }


    public void msgRead(String param, String sig, String c, String d, String m, String pid) {
        mvpView.showLoading();
        addSubscription(apiStores.msgRead(param, sig, c, d, m, pid), new ApiCallback<NullModel>() {
            @Override
            public void onSuccess(NullModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.postDeviceToken(model);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.dismissLoading();
            }
        });
    }
}
