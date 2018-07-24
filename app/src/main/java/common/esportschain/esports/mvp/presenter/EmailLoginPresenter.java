package common.esportschain.esports.mvp.presenter;

import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailLoginModel;
import common.esportschain.esports.mvp.view.EmailLoginView;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public class EmailLoginPresenter extends MyPresenter<EmailLoginView> {

    public void getEmailLoginData(String param, String sig, String c, String d, String m, String email, String pwd) {
        mvpView.showLoading();
        addSubscription(apiStores.getLogin(param, sig, c, d, m, email, pwd), new ApiCallback<EmailLoginModel>() {
            @Override
            public void onSuccess(EmailLoginModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS && model.getRet() == BaseResultBean.RESULT_RET_SUCCESS) {
                    mvpView.emailLoginData(model);
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
