package common.esportschain.esports.request;

import android.content.Context;
import android.util.Log;

import common.esportschain.esports.utils.DateTimeUtils;
import common.esportschain.esports.utils.DeviceUuidFactory;
import common.esportschain.esports.utils.GetVersionNumber;
import common.esportschain.esports.utils.MD5Util;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/13
 */

public class AuthSIG {

    public static String AuthToken(String c, String d, String m, String authKey, String uid, String token) {

        //authkey：用户未登录时用 "-1"
        String param = AuthParam.AuthParam(uid, token);
        String authToken = "60b7d923078c0871e43259e273299dd5";
        String MD5One = MD5Util.encodeMD5(param + c + d + m + authToken + authKey);
        String MD5Two = MD5Util.encodeMD5(MD5One);
        return MD5Two;
    }

    public static String AuthTokens(String c, String d, String m, String authKey, String uid, String token, String others) {
        //authkey：用户未登录时用 "-1"
        String param = AuthParam.AuthParam(uid, token);
        String authToken = "60b7d923078c0871e43259e273299dd5";
        String MD5One = MD5Util.encodeMD5(param + c + d + m + others + authToken + authKey);
        String MD5Two = MD5Util.encodeMD5(MD5One);
        return MD5Two;
    }

    public static String AuthTokenGames(String games, String authKey, String uid, String token) {
        //authkey：用户未登录时用 "-1"
        String param = AuthParam.AuthParam(uid, token);
        String authToken = "60b7d923078c0871e43259e273299dd5";
        //Log.e ("输出游戏详情sig签名之前", param + games + authToken + authKey);
        String MD5One = MD5Util.encodeMD5(param + games + authToken + authKey);
        String MD5Two = MD5Util.encodeMD5(MD5One);
        return MD5Two;
    }

}
