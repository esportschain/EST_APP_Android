package common.esportschain.esports.mvp.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.youcheng.publiclibrary.base.BaseClassResultBean;

import java.util.List;

/**
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
         * "uid": 1,           // 用户id
         * "nickname": "hq",   // 用户昵称
         * "avatar": "http://www.esportschain.com/uploads/avatar/1.png",   // 用户头像
         * "money": "50.000000000",                                        // 钱包余额
         * "status": 3, // 用户状态 1未绑定steam 2用户游戏列表为空或未开放权限(此时存在msg提示) 3正常显示帐号列表
         * "msg": "",   // 状态的错误消息
         * "popup": [   // 弹窗及其他，目前只有弹窗，list内有几个按顺序弹几个
         * {
         * "pid": "1_1",  // 弹窗ID,设置已读消息用
         * "type": 1,     // 1为弹窗
         * "msg": "Congratulations! Your PUBG account verification is finished，you can withdraw your EST now！" // 弹窗内容
         * }
         * ]
         */
        private String uid;
        private String nickname;
        private String avatar;
        private String money;
        private int status;
        private String msg;
        private List<PopupBean> popup;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<PopupBean> getPopup() {
            return popup;
        }

        public void setPopup(List<PopupBean> popup) {
            this.popup = popup;
        }

        public class PopupBean {
            /**
             * pid : 1_1
             * type : 1
             * msg : Congratulations! Your PUBG account verification is finished，you can withdraw your EST now！
             */

            private String pid;
            private int type;

            @SerializedName("msg")
            private String msgX;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getMsgX() {
                return msgX;
            }

            public void setMsgX(String msgX) {
                this.msgX = msgX;
            }
        }
    }

    public class HomeModelAccountListModel {
        /**
         * {
         * "icon": "http://www.esportschain.com/images/pubg.png",
         * "name": "PLAYERUNKNOWN'S BATTLEGROUNDS",
         * "gametype": 9,
         * "account_status": 1,
         * "account_msg": "",
         * "detail": {
         * "accountname": "bluesky_baiyun",
         * "uri": {
         * "d": "App",
         * "c": "Pubg",
         * "m": "detail",
         * "accountname": "bluesky_baiyun"
         * },
         * "stats": [{
         * "field": "Games",
         * "val": "307"
         * },
         * {
         * "field": "Win",
         * "val": "6"
         * },
         * {
         * "field": "Top10",
         * "val": "51"
         * },
         * {
         * "field": "KDR",
         * "val": "1.59"
         * }
         * ]
         * }
         * }
         */

        private String icon;
        private String name;
        private int gametype;
        private int account_status;
        private String account_msg;
        private DetailBean detail;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGametype() {
            return gametype;
        }

        public void setGametype(int gametype) {
            this.gametype = gametype;
        }

        public int getAccount_status() {
            return account_status;
        }

        public void setAccount_status(int account_status) {
            this.account_status = account_status;
        }

        public String getAccount_msg() {
            return account_msg;
        }

        public void setAccount_msg(String account_msg) {
            this.account_msg = account_msg;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public class DetailBean {
            /**
             * accountname : bluesky_baiyun
             * uri : {"d":"App","c":"Pubg","m":"detail","accountname":"bluesky_baiyun"}
             * stats : [{"field":"Games","val":"307"},{"field":"Win","val":"6"},{"field":"Top10","val":"51"},{"field":"KDR","val":"1.59"}]
             */

            private String accountname;
            private JsonObject uri;

            public JsonObject getUri() {
                return uri;
            }

            public void setUri(JsonObject uri) {
                this.uri = uri;
            }

            private List<StatsBean> stats;

            public String getAccountname() {
                return accountname;
            }

            public void setAccountname(String accountname) {
                this.accountname = accountname;
            }



            public List<StatsBean> getStats() {
                return stats;
            }

            public void setStats(List<StatsBean> stats) {
                this.stats = stats;
            }

            public class StatsBean {
                /**
                 * field : Games
                 * val : 307
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
    }
}
