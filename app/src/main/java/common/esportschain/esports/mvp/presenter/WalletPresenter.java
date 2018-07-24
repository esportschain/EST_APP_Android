package common.esportschain.esports.mvp.presenter;

import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;
import common.esportschain.esports.mvp.view.WalletView;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public class WalletPresenter extends MyPresenter<WalletView> {

    public void getWalletData(String param, String sig, String c, String d, String m, String page) {
        mvpView.showLoading();
        addSubscription(apiStores.getWalletData(param, sig, c, d, m, page), new ApiCallback<WalletModel>() {
            @Override
            public void onSuccess(WalletModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.getWalletData(model);
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

    public void putForWord(String param, String sig, String c, String d, String m, String address, final String type) {
        mvpView.showLoading();
        addSubscription(apiStores.putForWard(param, sig, c, d, m, address, type), new ApiCallback<NullModel>() {
            @Override
            public void onSuccess(NullModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.getWalletWithdrawWallet(model);
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

    public void showTexDialog(String param, String sig, String c, String d, String m, String address, final String type) {
        mvpView.showLoading();
        addSubscription(apiStores.putForWardTex(param, sig, c, d, m, address, type), new ApiCallback<WalletTexModel>() {
            @Override
            public void onSuccess(WalletTexModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                    mvpView.getWalletTax(model);
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
