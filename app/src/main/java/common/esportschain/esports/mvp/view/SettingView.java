package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.SettingModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public interface SettingView extends BaseView {
    void postModifyAvatar(SettingModel settingModel);
}
