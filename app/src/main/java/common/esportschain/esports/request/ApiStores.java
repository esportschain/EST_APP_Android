package common.esportschain.esports.request;

import java.util.Map;

import common.esportschain.esports.mvp.model.EmailLoginModel;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.model.EmailSignUpModel;
import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.LoginModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.SettingModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;
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

    interface Url {
        //String BASE_URL = "http://www.esportschain.com";
        String BASE_URL = "https://dev.esportschain.org";
    }

    //登录

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=login
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


    //发送验证码

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=sendVcode
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

    //验证码验证

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=checkVcode
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

    //注册

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=register
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param paramsText
     * @param params
     * @param file
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
            @PartMap Map<String, RequestBody> paramsText,
            @PartMap Map<String, RequestBody> params,
            @Part("avatar\"; filename=\"avatarPhoto.jpg") RequestBody file);

    //修改密码

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=modifyPwd
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

    //个人中心

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=detail
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

    //修改头像

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=modifyAvatar
     *
     * @param param
     * @param sig
     * @param c
     * @param d
     * @param m
     * @param params
     * @param file
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
            @PartMap Map<String, RequestBody> params,
            @Part("avatar\"; filename=\"avatarPhoto.jpg") RequestBody file);

    //钱包

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=wallet
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

    //提现请求  type = 2

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=putForward
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

    //提现手续费 type = 1

    /**
     * http://www.esportschain.com/app.php?d=App&c=Member&m=putForward
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
     * Unauthorized
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
}
