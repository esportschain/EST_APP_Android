package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public interface EmailSignUpPasswordView extends BaseView {

    void postModifyPassword(EmailSignUpPasswordModel emailSignUpPasswordModel);
}
