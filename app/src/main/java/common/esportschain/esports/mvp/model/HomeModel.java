package common.esportschain.esports.mvp.model;

import com.google.gson.JsonObject;
import com.youcheng.publiclibrary.base.BaseClassResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class HomeModel extends BaseClassResultBean<HomeModel.Object> {

    public class Object {

        private HomeUserModel user;

        public HomeUserModel getUser() {
            return user;
        }

        public void setUser(HomeUserModel user) {
            this.user = user;
        }

        private List<HomeModelAccountListModel> account_list;

        public List<HomeModelAccountListModel> getAccount_list() {
            return account_list;
        }

        public void setAccount_list(List<HomeModelAccountListModel> account_list) {
            this.account_list = account_list;
        }
    }

    public class HomeUserModel {

        /**
         * uid : 10
         * nickname : liang1234
         * avatar : http://www.esportschain.com/uploads/avatar/10.png
         * money : 0.000000000
         * steam_status : 0
         */

        private String uid;
        private String nickname;
        private String avatar;
        private String money;
        private String steam_status;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSteam_status() {
            return steam_status;
        }

        public void setSteam_status(String steam_status) {
            this.steam_status = steam_status;
        }
    }

    public class HomeModelAccountListModel {
        /**
         * name:
         * icon : http://www.esportschain.com/images/pubg.png
         * accountname : 4564
         * uri : {"d":"App","c":"Pubg","m":"detail","accountname":"4564"} 把jsonObject转String 类型 不确定key有几个
         * "stats" : [{"field": "Games","val":"6"}]  "matchnum":"307","winnum":"6","toptennum":"51","kdr":"1.59"}
         */



        private String name;
        private String icon;
        private String accountname;
        private JsonObject uri;
        private ArrayList<StatusModelItem> status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getAccountname() {
            return accountname;
        }

        public void setAccountname(String accountname) {
            this.accountname = accountname;
        }

        public JsonObject getUri() {
            return uri;
        }

        public void setUri(JsonObject uri) {
            this.uri = uri;
        }

        public ArrayList<StatusModelItem> getStatus() {
            return status;
        }

        public void setStatus(ArrayList<StatusModelItem> status) {
            this.status = status;
        }
    }

    public class StatusModelItem{

        /**
         * field : Games
         * val : 6
         */

        private String field;
        private String val;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }
    }
}
