package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class EmailSignUpCodeModel extends BaseClassResultBean<EmailSignUpCodeModel.Object> {

    public static class Object {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
