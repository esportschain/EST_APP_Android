package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 * @author liangzhaoyou
 */
public class WalletTexModel extends BaseClassResultBean<WalletTexModel.Object> {

    public class Object {
        private String cost;
        private String message;

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
    }
}
