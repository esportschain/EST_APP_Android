package common.esportschain.esports.mvp.presenter;

import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.model.EmailSignUpModel;
import common.esportschain.esports.mvp.view.EmailSignUpCodeView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.utils.ToastUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class EmailSignUpCodePresenter extends MyPresenter<EmailSignUpCodeView> {

    public void getVerificationCode(String param, String sig, String email, String code) {
        mvpView.showLoading();

        addSubscription(apiStores.getVerificationCode(param, sig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_CHECKV_CODE, email, code),
                new ApiCallback<EmailSignUpCodeModel>() {
                    @Override
                    public void onSuccess(EmailSignUpCodeModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS && model.getRet() == BaseResultBean.RESULT_RET_SUCCESS) {
                            EmailSignUpCodeModel testModel = model;
                            mvpView.getVerificationCode(testModel);
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


    public void getCode(String param, String sig, String email) {
        mvpView.showLoading();

        addSubscription(apiStores.getCode(param, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_SEND_VCODE, sig, email), new ApiCallback<EmailSignUpModel>() {
            @Override
            public void onSuccess(EmailSignUpModel model) {
                mvpView.dismissLoading();
                if (model.getCode() == BaseResultBean.RESULT_SUCCESS && model.getRet() == BaseResultBean.RESULT_RET_SUCCESS) {
                    EmailSignUpModel testModel = model;
                    mvpView.getCodeSuccess(testModel);
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
