package common.esportschain.esports.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import org.json.JSONObject;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.LoginModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.view.HomeView;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class HomePresenter extends MyPresenter<HomeView> {

    public void getHomeData(String param, String sig, String c, String d, String m, String page) {
        mvpView.showLoading();
        addSubscription(apiStores.getMineData(param, sig, c, d, m, page), new ApiCallback<HomeModel>() {
            @Override
            public void onSuccess(HomeModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    //{"d":"App","c":"Pubg","m":"detail","accountname":"4564"}
                    mvpView.getHomeData(model);
                } else {
                    ToastUtil.showToast(model.getInfo());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.dismissLoading();
                if ("HTTP 401 Unauthorized".equals(msg)) {
                    /**
                     * 根据项目需求处理
                     */
                }
                Toast.makeText(EsportsApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
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
                    //{"d":"App","c":"Pubg","m":"detail","accountname":"4564"}
                    mvpView.postDeviceToken(model);
                } else {
                    ToastUtil.showToast(model.getInfo());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.dismissLoading();
                if ("HTTP 401 Unauthorized".equals(msg)) {
                    /**
                     * 根据项目需求处理
                     */
                }
                Toast.makeText(EsportsApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
