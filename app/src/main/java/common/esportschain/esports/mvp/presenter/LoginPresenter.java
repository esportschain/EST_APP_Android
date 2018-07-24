package common.esportschain.esports.mvp.presenter;

import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.view.LoginView;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public class LoginPresenter extends MyPresenter<LoginView> {

    public void getLoginData(int nextpage) {
//        mvpView.showLoading();
//        addSubscription(apiStores.getList(nextpage), new ApiCallback<LoginModel>() {
//            @Override
//            public void onSuccess(LoginModel model) {
//                mvpView.dismissLoading();
//                if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
//                    LoginModel testModel = model;
//                    mvpView.getLoginData(testModel);
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                mvpView.dismissLoading();
//                if (msg.equals("HTTP 401 Unauthorized")) {
//                    /**
//                     * 根据项目需求处理
//                     */
//                }
//                Toast.makeText(EsportsApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
//            }
//
//        });
    }
}
