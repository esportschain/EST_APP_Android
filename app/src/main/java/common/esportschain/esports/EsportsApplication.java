package common.esportschain.esports;

import android.content.Context;
import android.os.Handler;

import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.youcheng.publiclibrary.base.BaseApplication;
import com.youcheng.publiclibrary.retrofit.AppClient;

import org.greenrobot.eventbus.EventBus;

import common.esportschain.esports.database.AbstractDatabaseManager;
import common.esportschain.esports.event.UMPushDeviceEvent;
import common.esportschain.esports.request.ApiStores;

/**
 * @author liangzhaoyou
 * @date 2018/6/11
 */

public class EsportsApplication extends BaseApplication {

    private static Context mContext;

    private static Handler mHandler;

    private String appkey = "";
    private String channel = "";
    private String umPush = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler();
        /**
         * 设置服务器地址
         */
        AppClient.setApiServerUrl(ApiStores.Url.BASE_URL);

        //初始化数据库
        AbstractDatabaseManager.initOpenHelper(this, "ESTDB");

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(false);
        refreshString();
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, umPush);
        //注册推送服务
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                EventBus.getDefault().postSticky(new UMPushDeviceEvent(deviceToken));
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        //完全自定义处理
//        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 在主线程中刷新UI
     *
     * @param r
     */
    public static void runOnUIThread(Runnable r) {
        EsportsApplication.getMainHandler().post(r);
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    /**
     * 下拉刷新控件的国际化
     */
    public void refreshString() {

        //"下拉可以刷新";
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.header_pulling);
        //"正在刷新...";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.header_refreshing);
        //"正在加载...";
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.header_loading);
        //"释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.header_release);
        //"刷新完成";
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.header_finish);
        //"刷新失败";
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.header_failed);
        //"上次更新 M-d HH:mm";
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.header_update);
        //"'Last update' M-d HH:mm";
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.header_update);

        //"上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.footer_pulling);
        //"释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.footer_release);
        //"正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.footer_loading);
        //"正在加载...";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.footer_refreshing);
        //"加载完成";
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish);
        //"加载失败";
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.footer_failed);
        //"全部加载完成";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.footer_nothing);
    }
}