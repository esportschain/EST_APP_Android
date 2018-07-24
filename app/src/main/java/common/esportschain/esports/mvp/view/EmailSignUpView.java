package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.EmailSignUpModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public interface EmailSignUpView extends BaseView {
    void getCodeSuccess(EmailSignUpModel emailSignUpModel);
}
