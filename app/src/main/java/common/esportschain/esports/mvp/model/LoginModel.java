package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/12
 */

public class LoginModel extends BaseClassResultBean<LoginModel.Object> {

    public static class Object {
        public int uid;
        public String nickname;
        public String token;
        public String authkey;
        public String avatar;
    }

    public class LoginItem {
        /**
         * uid : 1
         * nickname : hq
         * token : x6kp4OCnO3ngVhz9m2/MzbnAWfW2/CXFdsRk31/wtZu86NO5k/iCGCVl
         * authkey : -1
         * avatar : http://www.esportschain.com/uploads/avatar/1.png
         */

        private int uid;
        private String nickname;
        private String token;
        private int authkey;
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

        public int getAuthkey() {
            return authkey;
        }

        public void setAuthkey(int authkey) {
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
