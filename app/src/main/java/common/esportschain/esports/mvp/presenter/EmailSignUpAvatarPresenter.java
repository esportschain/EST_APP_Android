package common.esportschain.esports.mvp.presenter;

import android.content.Context;
import android.os.Build;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.view.EmailSignUpAvatarView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.utils.CompressionUtils;
import common.esportschain.esports.utils.ToastUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.OnCompressListener;

/**
 * @author liangzhaoyou
 * @date 2018/6/14
 */

public class EmailSignUpAvatarPresenter extends MyPresenter<EmailSignUpAvatarView> {

    public void uploadPhotoFile(String param,
                                String sig,
                                String email,
                                String nickname,
                                String newPassword,
                                String rNewPassword,
                                String key,
                                File file,
                                String pictype) {


        RequestBody requestFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        } else {
            if ("".equals(pictype) || null == pictype) {
                requestFile = RequestBody.create(MediaType.parse("image/png"), file);
            } else {
                requestFile = RequestBody.create(MediaType.parse("image/" + pictype), file);
            }
        }

        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Map<String, RequestBody> map = new HashMap<>();
        map.put("email", toRequestBody(email));
        map.put("nickname", toRequestBody(nickname));
        map.put("new_pwd", toRequestBody(newPassword));
        map.put("rnew_pwd", toRequestBody(rNewPassword));
        map.put("key", toRequestBody(key));

        //,email, nickname, newPassword, rNewPassword, key,
        addSubscription(apiStores.getSignUpCode(param, sig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_REGISTER,
                map, part),
                new ApiCallback<EmailSignUpAvatarModel>() {

                    @Override
                    public void onSuccess(EmailSignUpAvatarModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                            mvpView.getSignUpData(model);
                        } else {
                            ToastUtil.showToast(model.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    public void postSignUp(String param,
                           String sig,
                           String email,
                           String nickname,
                           String newPassword,
                           String rNewPassword,
                           String key) {


        Map<String, RequestBody> map = new HashMap<>();
        map.put("email", toRequestBody(email));
        map.put("nickname", toRequestBody(nickname));
        map.put("new_pwd", toRequestBody(newPassword));
        map.put("rnew_pwd", toRequestBody(rNewPassword));
        map.put("key", toRequestBody(key));

        //,email, nickname, newPassword, rNewPassword, key,
        addSubscription(apiStores.postSignUpData(param, sig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_REGISTER,
                map),
                new ApiCallback<EmailSignUpAvatarModel>() {

                    @Override
                    public void onSuccess(EmailSignUpAvatarModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                            mvpView.getSignUpData(model);
                        } else {
                            ToastUtil.showToast(model.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    /**
     * 图片压缩
     *
     * @param context
     * @param filePath
     */
    public void photoCompression(
            final String param,
            final String sig,
            final String email,
            final String nickname,
            final String newPassword,
            final String rNewPassword,
            final String key,
            Context context,
            final String filePath,
            final String picType) {
        CompressionUtils.getInstance().setZIP(context, filePath, new OnCompressListener() {
            @Override
            public void onStart() {
                mvpView.showLoading();
            }

            @Override
            public void onSuccess(File file) {
                /**
                 * 压缩成功 上传
                 */
                uploadPhotoFile(param,
                        sig,
                        email,
                        nickname,
                        newPassword,
                        rNewPassword,
                        key,
                        file,
                        picType);
            }

            @Override
            public void onError(Throwable e) {
                mvpView.dismissLoading();
            }
        });
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

}
