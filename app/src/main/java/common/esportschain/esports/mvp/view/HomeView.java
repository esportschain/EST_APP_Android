package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.NullModel;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public interface HomeView extends BaseView {

    /**
     * 获取首页数据
     *
     * @param homeModel
     */
    void getHomeData(HomeModel homeModel);

    /**
     * 更新deviceToken数据
     *
     * @param nullModel
     */
    void postDeviceToken(NullModel nullModel);

    /**
     * 消息已读接口
     *
     * @param nullModel
     */
    void msgRead(NullModel nullModel);
}
