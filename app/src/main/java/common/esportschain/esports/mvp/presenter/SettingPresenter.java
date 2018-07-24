package common.esportschain.esports.mvp.presenter;

import android.support.v4.util.ArrayMap;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.model.SettingModel;
import common.esportschain.esports.mvp.view.SettingView;
import common.esportschain.esports.utils.Compressor;
import common.esportschain.esports.utils.ToastUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class SettingPresenter extends MyPresenter<SettingView> {
    public void postModifyAvatar(String param,
                                 String sig,
                                 String c,
                                 String d,
                                 String m,
                                 String file) {

        ArrayMap<String, RequestBody> params = new ArrayMap<>();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(file).getName());
        params.put("avatar", requestBody);

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),
                Compressor.getDefault(((MvpActivity) mvpView).context).compressToFile(new File(file)));

        //,email, nickname, newPassword, rNewPassword, key,
        addSubscription(apiStores.postAvatarModify(param, sig, c, d, m, params, body),
                new ApiCallback<SettingModel>() {

                    @Override
                    public void onSuccess(SettingModel model) {
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                            mvpView.postModifyAvatar(model);
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
