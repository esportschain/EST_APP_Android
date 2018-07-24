package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class SettingModel extends BaseClassResultBean<SettingModel.Object> {

    private static final long serialVersionUID = -4798422114739845251L;

    public static class Object {
        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
