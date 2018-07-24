package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.EmailLoginModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public interface EmailLoginView extends BaseView {

    void emailLoginData(EmailLoginModel emailLoginModel);
}
