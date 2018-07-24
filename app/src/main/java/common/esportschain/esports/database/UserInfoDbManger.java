package common.esportschain.esports.database;

import org.greenrobot.greendao.AbstractDao;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public class UserInfoDbManger extends AbstractDatabaseManager<UserInfo,Long>{

    @Override
    AbstractDao<UserInfo, Long> getAbstractDao() {
        return daoSession.getUserInfoDao();
    }
}
