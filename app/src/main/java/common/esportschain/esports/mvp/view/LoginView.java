package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.LoginModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public interface LoginView extends BaseView {

    void getLoginData(LoginModel loginModel);
}
