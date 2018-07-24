package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public class EmailLoginModel extends BaseClassResultBean<EmailLoginModel.Object> {

    public static class Object {
        private int uid;
        private String nickname;
        private String token;
        private String authkey;
        private String avatar;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAuthkey() {
            return authkey;
        }

        public void setAuthkey(String authkey) {
            this.authkey = authkey;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
