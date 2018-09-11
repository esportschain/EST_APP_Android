package common.esportschain.esports.ui.login;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.R;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.event.SignUpEvent;
import common.esportschain.esports.mvp.model.EmailSignUpAvatarModel;
import common.esportschain.esports.mvp.presenter.EmailSignUpAvatarPresenter;
import common.esportschain.esports.mvp.view.EmailSignUpAvatarView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.utils.GlideUtil;
import common.esportschain.esports.utils.MD5Util;
import common.esportschain.esports.utils.MPermissionUtils;
import common.esportschain.esports.utils.ToastUtil;
import common.esportschain.esports.weight.EditAvatarDialog;

/**
 * @author liangzhaoyou
 * @date 2018/6/14
 */

public class EmailSignUpAvatarActivity extends MvpActivity<EmailSignUpAvatarPresenter> implements EmailSignUpAvatarView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_sign_up_avatar)
    ImageView ivAvatar;

    @BindView(R.id.et_sign_up_nickname)
    EditText etSignUpNickName;

    @BindView(R.id.bt_sign_up_finish)
    Button btSignUpFinish;

    private String mParam;
    private String mSig;
    private String mEmail;
    private String mNickName;
    private String mNewPassword;
    private String mMD5NewPassword;
    private String mPNewPassword;
    private String mMD5PNewPassword;
    private String mKey;
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mNewPassword = intent.getStringExtra("new_password");
        mPNewPassword = intent.getStringExtra("pnew_password");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.profile));
        ivBack.setImageResource(R.mipmap.back);
        GlideUtil.loadRandImg(mActivity, R.mipmap.avatar_gary, ivAvatar);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign_up_avatar_nickname;
    }

    @OnClick({R.id.iv_back, R.id.iv_sign_up_avatar, R.id.bt_sign_up_finish})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(ivAvatar)) {
            chooseHeadPortrait();
        } else if (view.equals(btSignUpFinish)) {
            if (null != etSignUpNickName.getText()) {

                UserInfo userInfo = new UserInfoDbManger().loadAll().get(0);
                mEmail = userInfo.getEmail();
                mKey = userInfo.getKey();
                mNickName = etSignUpNickName.getText().toString();
                mMD5NewPassword = MD5Util.encodeMD5(mNewPassword);
                mMD5PNewPassword = MD5Util.encodeMD5(mPNewPassword);

                mParam = AuthParam.AuthParam("", "");
                mSig = AuthSIG.AuthToken(mParam, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP,
                        mEmail, mKey, ApiStores.APP_M_REGISTER, mNickName, mMD5NewPassword, mMD5PNewPassword);

                if (null != absolutePath) {
                    mvpPresenter.photoCompression(mParam, mSig, mEmail, mNickName, mMD5NewPassword, mMD5PNewPassword, mKey, mActivity, absolutePath, type);
                } else {
                    mvpPresenter.postSignUp(mParam, mSig, mEmail, mNickName, mMD5NewPassword, mMD5PNewPassword, mKey);
                }
            } else {
                ToastUtil.showToast(getResources().getString(R.string.please_enter_nickname));
            }
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
     * 拍照保存路径(原件)
     */
    private String absolutePath;

    public void takePictures() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissionUtils.requestPermissionsResult(this, 100,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    new MPermissionUtils.OnPermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                            String currentTime = String.valueOf(System.currentTimeMillis());
                            String outFilePath = currentTime + ".png";
                            File file = new File(outFileFolder, outFilePath);
                            absolutePath = file.getAbsolutePath();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri uri = Uri.fromFile(file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, 200);
                        }

                        @Override
                        public void onPermissionDenied() {
                            showRefuse();
                        }

                    });
        } else {
            String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            String currentTime = String.valueOf(System.currentTimeMillis());
            String outFilePath = currentTime + ".png";
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
                            showRefuse();
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
        //拍照成功返回
        if (requestCode == 200 && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(absolutePath)) {
                cropPhoto();
            }
            //选择相册成功返回
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                absolutePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String aa = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                type = aa.substring(absolutePath.length() - 3, absolutePath.length());

                if (!TextUtils.isEmpty(absolutePath)) {
                    cropPhoto();
                }
            }
            //剪裁完毕上传文件
        } else if (requestCode == 500 && resultCode == RESULT_OK) {
            GlideUtil.loadRandImg(this, absolutePath, ivAvatar);
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
        //是否保持比例
        intent.putExtra("scale", true);
        intent.putExtra("circleCrop", true);
        intent.putExtra("aspectX", 700);
        intent.putExtra("aspectY", 699);
        intent.putExtra("outputX", 700);
        intent.putExtra("outputY", 699);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        // no face detection
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + absolutePath));
        //裁剪之后的数据是通过Intent返回
        startActivityForResult(intent, 500);
    }

    public void showRefuse() {
        MPermissionUtils.showTipsDialog(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void getSignUpData(EmailSignUpAvatarModel emailSignUpAvatarModel) {
        EventBus.getDefault().post(new SignUpEvent("1"));
        ToastUtil.showToast(getResources().getString(R.string.sign_up_success));
        finish();
    }
}
