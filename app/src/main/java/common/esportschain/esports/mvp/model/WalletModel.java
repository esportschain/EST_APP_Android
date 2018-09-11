package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

import java.util.List;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public class WalletModel extends BaseClassResultBean<WalletModel.Object> {

    public class Object {
        private String isEnd;
        private List<WalletItemModel> list;
        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getIsEnd() {
            return isEnd;
        }

        public void setIsEnd(String isEnd) {
            this.isEnd = isEnd;
        }

        public List<WalletItemModel> getList() {
            return list;
        }

        public void setList(List<WalletItemModel> list) {
            this.list = list;
        }
    }

    public class WalletItemModel {
        /**
         * name : 2 PUBG GAMES
         * creation : 2018-06-14 15:23:00
         * type : 1
         * money : 12.2334928343
         */
        /**
         *项目名称
          */

        private String name;
        /**
         * 交易时间
         */
        private String creation;
        /**
         * 1增加 2减少
         */
        private int type;
        /**
         *  数额
         */
        private String money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreation() {
            return creation;
        }

        public void setCreation(String creation) {
            this.creation = creation;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
