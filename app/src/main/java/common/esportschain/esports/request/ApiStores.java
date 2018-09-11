package common.esportschain.esports.request;

import java.util.Map;

import common.esportschain.esports.mvp.model.EmailLoginModel;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.model.EmailSignUpModel;
import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.SettingModel;
import common.esportschain.esports.mvp.model.VerifyAccountModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author liangzhaoyou
 */
public interface ApiStores {

    public static final String APP_C_MEMBER = "Member";
    public static final String APP_D_APP = "App";
    public static final String APP_M_WALLET = "wallet";
    public static final String APP_M_PUTFORWARD = "putForward";
    public static final String APP_M_DETAIL = "detail";
    public static final String APP_M_UPDATE = "updated";
    public static final String APP_M_LOGIN = "login";
    public static final String APP_M_CHECKV_CODE = "checkVcode";
    public static final String APP_M_MODIFY_PASSWORD = "modifyPwd";
    public static final String APP_M_SEND_VCODE = "sendVcode";
    public static final String APP_M_SMODIFY_AVATAR = "modifyAvatar";
    public static final String APP_M_BIND_ID = "bindid";
    public static final String APP_M_GET_GAME_AUTH_DETAIL = "getGameAuthDetail";
    public static final String APP_M_BIND_ACCOUNT_TPL = "bindAccountTpl";
    public static final String APP_M_BIND_ACCOUNT = "bindAccount";
    public static final String APP_M_SUBMIT_GAME_AUTH = "submitGameAuth";
    public static final String APP_M_REGISTER = "register";
    public static final String APP_M_READ_MSG = "readMsg";


    public static final String APP_GAME_TYPE = "gametype";
    public static final String APP_PAGE = "page";
    public static final String APP_PiD = "pid";

    interface Url {
        //String BASE_URL = "http://www.esportschain.com";
//        String BASE_URL = "https://dev.esportschain.org";
        String BASE_URL = "https://esportschain.org/";
    }

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=login
     * 登录
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param email
     * @param pwd
     * @return
     */

    @FormUrlEncoded
    @POST("app.php")
    Observable<EmailLoginModel> getLogin(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("email") String email,
            @Field("pwd") String pwd);


    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=sendVcode
     * 发送验证码
     *
     * @param param
     * @param c
     * @param d
     * @param m
     * @param sig
     * @param email
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<EmailSignUpModel> getCode(
            @Query("_param") String param,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Query("sig") String sig,
            @Field("email") String email);

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=checkVcode
     * 验证码验证
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param email
     * @param vcode
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<EmailSignUpCodeModel> getVerificationCode(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("email") String email,
            @Field("vcode") String vcode);


    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=register
     * 注册
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param part
     * @return
     */
    @Multipart
    @POST("app.php")
    Observable<EmailSignUpAvatarModel> getSignUpCode(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @PartMap Map<String, RequestBody> body,
            @Part MultipartBody.Part part);

    /**
     * 注册没有上传头像
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param body
     * @param sig
     * @param c
     * @param d
     * @param m
     * @return
     */
    @Multipart
    @POST("app.php")
    Observable<EmailSignUpAvatarModel> postSignUpData(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @PartMap Map<String, RequestBody> body);


    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=modifyPwd
     * 修改密码
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param oldPwd
     * @param newPwd
     * @param rnewPwd
     * @return
     */

    @FormUrlEncoded
    @POST("app.php")
    Observable<EmailSignUpPasswordModel> postMidifyPassword(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("old_pwd") String oldPwd,
            @Field("new_pwd") String newPwd,
            @Field("rnew_pwd") String rnewPwd);

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=detail
     * 个人中心
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<HomeModel> getMineData(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("page") String page);

    //

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=modifyAvatar
     * 修改头像
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @return
     */
    @Multipart
    @POST("app.php")
    Observable<SettingModel> postAvatarModify(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Part MultipartBody.Part part);

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=wallet
     * 钱包
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<WalletModel> getWalletData(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("page") String page);

    //

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=putForward
     * 提现请求  type = 2
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param address
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<NullModel> putForWard(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("address") String address,
            @Field("type") String type);

    //

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=putForward
     * 提现手续费 type = 1
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param address
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<WalletTexModel> putForWardTex(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("address") String address,
            @Field("type") String type);

    /**
     * 提交deviceToken
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param deviceToken
     * @return
     */
    @FormUrlEncoded
    @POST("app.php")
    Observable<NullModel> putDevice(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("device_token") String deviceToken);

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=getGameAuthDetail
     * 获取绑定账号的数据
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param bindid
     * @return
     */
    @GET("app.php")
    Observable<VerifyAccountModel> getVerifyData(@Query("_param") String param,
                                                 @Query("sig") String sig,
                                                 @Query("c") String c,
                                                 @Query("d") String d,
                                                 @Query("m") String m,
                                                 @Query("bindid") String bindid);

    @FormUrlEncoded
    @POST("app.php")
    Observable<NullModel> postBindPudgData(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Field("gametype") String gametype,
            @Field("data") String data);

    //http://www.esportschain.com/app.php?d=App&c=Member&m=submitGameAuth

    /**
     * 上传认证图片
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param submitGameAuth
     * @return
     */

    @Multipart
    @POST("app.php")
    Observable<VerifyAccountModel> postAuthimg(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String submitGameAuth,
            @Query("bindid") String bind,
            @Part MultipartBody.Part part);


    /**
     * urldemo: http://www.esportschain.com/app.php?d=App&c=Member&m=readMsg
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @return
     */
    @GET("app.php")
    Observable<NullModel> msgRead(
            @Query("_param") String param,
            @Query("sig") String sig,
            @Query("c") String c,
            @Query("d") String d,
            @Query("m") String m,
            @Query("pid") String pid);

}
