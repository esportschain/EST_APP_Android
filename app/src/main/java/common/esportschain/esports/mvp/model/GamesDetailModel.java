package common.esportschain.esports.mvp.model;

import com.youcheng.publiclibrary.base.BaseClassResultBean;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/25
 */

public class GamesDetailModel extends BaseClassResultBean {

    public class GamesModel{

        /**
         * id : 2018-01
         * name : 2018-第01季
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
