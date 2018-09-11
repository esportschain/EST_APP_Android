package common.esportschain.esports.request;

import common.esportschain.esports.utils.DateTimeUtils;
import common.esportschain.esports.utils.DeviceUuidFactory;
import common.esportschain.esports.utils.GetVersionNumber;

/**
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class AuthParam {


    public static String AuthParam(String uid, String token) {
        /**
         * uuid | 手机的唯一标识
         * platform | 设备类型，安卓:'and'，苹果:'ios'
         * build | 客户端Build版本号 1.0.0 => 100
         * version | 客户端版本号 1
         * apiVersion | 服务端接口版本号 默认1
         * ts | 客户端时间戳
         * uid | 用户id 没有为空竖线必须有
         * token | token（登录时返回值，默认为空） 默认为空
         */

        String uuid = DeviceUuidFactory.getUDID() + "";
        String versionCode = GetVersionNumber.getVersionNumber() + "";
        String mDateTime = DateTimeUtils.getTime();

        String param = uuid + "|" + "and" + "|" + "200" + "|" + versionCode + "|" + "1" + "|" + mDateTime + "|" + uid + "|" + token;
        return param;
    }

}
