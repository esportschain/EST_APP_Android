package common.esportschain.esports.mvp.presenter;

import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.model.EmailSignUpCodeModel;
import common.esportschain.esports.mvp.model.EmailSignUpPasswordModel;
import common.esportschain.esports.mvp.view.EmailSignUpAvatarView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.utils.Compressor;
import common.esportschain.esports.utils.ToastUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
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
                                String file) {

        Map<String, RequestBody> partMap = new HashMap<>();

        RequestBody bodyEmail = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), email);
        partMap.put("email", bodyEmail);

        RequestBody bodyNickname = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), nickname);
        partMap.put("nickname", bodyNickname);

        RequestBody bodyNewPassword = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), newPassword);
        partMap.put("new_pwd", bodyNewPassword);

        RequestBody bodyrNewPassword = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), rNewPassword);
        partMap.put("rnew_pwd", bodyrNewPassword);

        RequestBody bodykey = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), key);
        partMap.put("key", bodykey);

        ArrayMap<String, RequestBody> params = new ArrayMap<>();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(file).getName());
        params.put("avatar", requestBody);

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),
                Compressor.getDefault(((MvpActivity) mvpView).context).compressToFile(new File(file)));

        //,email, nickname, newPassword, rNewPassword, key,
        addSubscription(apiStores.getSignUpCode(param, sig, "Member", "App", "register", partMap, params, body),
                new ApiCallback<EmailSignUpAvatarModel>() {

                    @Override
                    public void onSuccess(EmailSignUpAvatarModel model) {
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
}
