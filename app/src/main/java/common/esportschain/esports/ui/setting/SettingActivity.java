package common.esportschain.esports.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.event.AccountSharedPreferences;
import common.esportschain.esports.event.ModifyAvatarEvent;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.mvp.model.SettingModel;
import common.esportschain.esports.mvp.presenter.SettingPresenter;
import common.esportschain.esports.mvp.view.SettingView;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.ui.login.EmailSignUpPasswordActivity;
import common.esportschain.esports.ui.login.LoginActivity;
import common.esportschain.esports.utils.GlideUtil;
import common.esportschain.esports.utils.MPermissionUtils;
import common.esportschain.esports.utils.ToastUtil;
import common.esportschain.esports.weight.CustomizeDialog;
import common.esportschain.esports.weight.EditAvatarDialog;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class SettingActivity extends MvpActivity<SettingPresenter> implements SettingView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.ll_setting_avatar)
    LinearLayout llSettingAvatar;

    @BindView(R.id.ll_setting_password)
    LinearLayout llSettingPassword;

    @BindView(R.id.iv_setting_modify_avatar)
    ImageView ivSettingModifyAvatar;

    @BindView(R.id.bt_logout)
    Button btLogout;

    private String mParam;
    private String mSig;
    private UserInfo userInfo;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.setting_title));
        ivBack.setImageResource(R.mipmap.back);
    }

    @Override
    public void initData() {
        super.initData();
        userInfo = new UserInfoDbManger().loadAll().get(0);
        GlideUtil.loadRandImg(mActivity, userInfo.getAvatar(), ivSettingModifyAvatar);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.iv_back, R.id.ll_setting_avatar, R.id.ll_setting_password, R.id.bt_logout})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(llSettingAvatar)) {
            chooseHeadPortrait();
        } else if (view.equals(llSettingPassword)) {
            Intent intent = new Intent(this, EmailSignUpPasswordActivity.class);
            intent.putExtra("status", "2");
            startActivity(intent);
        } else if (view.equals(btLogout)) {
            logoutDialog();
        }
    }

    /**
     * 选择头像
     */
    private void chooseHeadPortrait() {
        final EditAvatarDialog myDialog = new EditAvatarDialog(this);
        myDialog.setCameraButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                takePictures();
            }
        });
        myDialog.setPickerButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
                selectPictures();
            }
        });
        myDialog.setCancelButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
    }

    /**
     * 拍照权限判断
     * //拍照保存路径(原件)
     */
    private String absolutePath;

    public void takePictures() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissionUtils.requestPermissionsResult(this,
                    100,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    new MPermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            Log.e("获取到应用权限", "=====");
                            String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                            String outFilePath = "ESCAVATAR" + ".png";
                            File file = new File(outFileFolder, outFilePath);
                            absolutePath = file.getAbsolutePath();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri uri = Uri.fromFile(file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, 200);
                        }

                        @Override
                        public void onPermissionDenied() {
                            Log.e("没有获取获取到应用权限", "+++++");
                            MPermissionUtils.showTipsDialog(mActivity);
                        }

                    });
        } else {
            String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            String outFilePath = "ESCAVATAR" + ".png";
            File file = new File(outFileFolder, outFilePath);
            absolutePath = file.getAbsolutePath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 200);
        }
    }

    /**
     * 选择照片权限判断
     */
    public void selectPictures() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissionUtils.requestPermissionsResult(this, 200,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    new MPermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 100);
                        }

                        @Override
                        public void onPermissionDenied() {
                            MPermissionUtils.showTipsDialog(mActivity);
                        }
                    });
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        }
    }

    /**
     * 选择图片后的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(absolutePath)) {
                cropPhoto();
            }
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                if (!TextUtils.isEmpty(absolutePath)) {
                    cropPhoto();
                }
            }
        } else if (requestCode == 500) {
            UserInfo userInfo = new UserInfoDbManger().loadAll().get(0);
            mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
            mSig = AuthSIG.AuthToken("Member", "App", "modifyAvatar",
                    userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());

            mvpPresenter.postModifyAvatar(mParam, mSig, "Member", "App", "modifyAvatar", absolutePath);
        }
    }

    /**
     * 剪裁图片
     */
    protected void cropPhoto() {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(Uri.parse("file://" + absolutePath), "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("scale", true);
        intent.putExtra("circleCrop", true);
        intent.putExtra("aspectX", 700);
        intent.putExtra("aspectY", 699);
        intent.putExtra("outputX", 700);
        intent.putExtra("outputY", 699);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + absolutePath));
        //裁剪之后的数据是通过Intent返回
        startActivityForResult(intent, 500);
    }

    @Override
    public void postModifyAvatar(final SettingModel settingModel) {

        userInfo = new UserInfoDbManger().loadAll().get(0);
        userInfo.setAvatar(settingModel.getResult().getAvatar());
        new UserInfoDbManger().update(userInfo);
        EsportsApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                GlideUtil.loadRandImg(mActivity, absolutePath, ivSettingModifyAvatar);
            }
        });
        EventBus.getDefault().post(new ModifyAvatarEvent("1"));

        ToastUtil.showToast(settingModel.getResult().getAvatar());
    }

    private void logoutDialog() {
        final CustomizeDialog customDialog = new CustomizeDialog(this);
        customDialog.setTitle(getResources().getString(R.string.logout));
        customDialog.setContent(getResources().getString(R.string.log_out_infor_clear));
        customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
        customDialog.setBtCancelText(getResources().getString(R.string.cancel));
        customDialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountSharedPreferences.setIsLogin(false);
                Log.e("输出退出登录时状态", AccountSharedPreferences.getIsLogin() + "");
                new UserInfoDbManger().deleteAll();
                customDialog.dismiss();
                finish();
                Intent intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);
            }
        });
        customDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
