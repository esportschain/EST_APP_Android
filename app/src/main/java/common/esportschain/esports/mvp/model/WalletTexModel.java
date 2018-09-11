package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 * @author liangzhaoyou
 */
public class WalletTexModel extends BaseClassResultBean<WalletTexModel.Object> {

    public class Object {
        /**
         * 状态判断 3 跳转认证界面 2 提示手续费的dialog
         */
        private String status;
        /**
         * 提现手续费金额
         */
        private String cost;
        /**
         * 提现手续费提示信息
         */
        private String message;
        /**
         * 游戏类型
         */
        private String gametype;
        /**
         * 绑定的id
         */
        private String bindid;



        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGametype() {
            return gametype;
        }

        public void setGametype(String gametype) {
            this.gametype = gametype;
        }

        public String getBindid() {
            return bindid;
        }

        public void setBindid(String bindid) {
            this.bindid = bindid;
        }
    }
}
