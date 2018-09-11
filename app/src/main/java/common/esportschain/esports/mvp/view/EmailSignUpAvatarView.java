package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/14
 */

public interface EmailSignUpAvatarView  extends BaseView {
    void getSignUpData(EmailSignUpAvatarModel emailSignUpAvatarModel);
}
