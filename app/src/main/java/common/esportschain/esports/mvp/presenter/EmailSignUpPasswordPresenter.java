package common.esportschain.esports.mvp.presenter;

import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;
import common.esportschain.esports.mvp.view.EmailSignUpPasswordView;
import common.esportschain.esports.utils.ToastUtil;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class EmailSignUpPasswordPresenter extends MyPresenter<EmailSignUpPasswordView> {

    public void getVerificationCode(String param, String sig, String email, String code) {
        mvpView.showLoading();

        addSubscription(apiStores.getVerificationCode(param, sig, "Member", "App", "sendVcode", email, code),
                new ApiCallback<EmailSignUpCodeModel>() {
                    @Override
                    public void onSuccess(EmailSignUpCodeModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS && model.getRet() == BaseResultBean.RESULT_RET_SUCCESS) {
                            EmailSignUpCodeModel testModel = model;
//                            mvpView.(testModel);
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

    /**
     * postModifyPassword
     * @param param
     * @param sig
     * @param oldPassword
     * @param newPassword
     * @param rNewPassword
     */
    public void postModifyPassword(String param, String sig, String oldPassword, String newPassword, String rNewPassword) {
        mvpView.showLoading();

        addSubscription(apiStores.postMidifyPassword(param, sig, "Member", "App", "modifyPwd", oldPassword, newPassword, rNewPassword),
                new ApiCallback<EmailSignUpPasswordModel>() {
                    @Override
                    public void onSuccess(EmailSignUpPasswordModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS && model.getRet() == BaseResultBean.RESULT_RET_SUCCESS) {
                            mvpView.postModifyPassword(model);
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
