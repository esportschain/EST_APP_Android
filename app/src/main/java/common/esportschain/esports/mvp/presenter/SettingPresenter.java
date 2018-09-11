package common.esportschain.esports.mvp.presenter;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.youcheng.publiclibrary.base.BaseResultBean;
import com.youcheng.publiclibrary.retrofit.ApiCallback;

import java.io.File;

import common.esportschain.esports.base.MyPresenter;
import common.esportschain.esports.mvp.model.SettingModel;
import common.esportschain.esports.mvp.view.SettingView;
import common.esportschain.esports.utils.CompressionUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.OnCompressListener;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class SettingPresenter extends MyPresenter<SettingView> {

    public void postModifyAvatar(String param,
                                 String sig,
                                 String c,
                                 String d,
                                 String m,
                                 File file,
                                 String picType) {

        RequestBody requestFile;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        } else {
            if ("".equals(picType) || null == picType) {
                requestFile = RequestBody.create(MediaType.parse("image/png"), file);
            } else {
                Log.e("======Type", picType);
                requestFile = RequestBody.create(MediaType.parse("image/" + picType), file);
            }
        }

        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        //,email, nickname, newPassword, rNewPassword, key,
        addSubscription(apiStores.postAvatarModify(param, sig, c, d, m, part),
                new ApiCallback<SettingModel>() {

                    @Override
                    public void onSuccess(SettingModel model) {
                        mvpView.dismissLoading();
                        if (model.getCode() == BaseResultBean.RESULT_SUCCESS) {
                            mvpView.postModifyAvatar(model);
                        } else {
//                            ToastUtil.showToast(model.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.dismissLoading();
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
            final String c,
            final String d,
            final String m,
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
                postModifyAvatar(param, sig, c, d, m, file, picType);
            }

            @Override
            public void onError(Throwable e) {
                mvpView.dismissLoading();
            }
        });
    }
}
