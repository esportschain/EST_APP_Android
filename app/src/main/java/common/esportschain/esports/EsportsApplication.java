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
import common.esportschain.esports.upush.MyPushIntentService;

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

        AppClient.setApiServerUrl(ApiStores.Url.BASE_URL);


        AbstractDatabaseManager.initOpenHelper(this, "ESTDB");

        
        UMConfigure.setLogEnabled(false);
        refreshString();
       
        UMConfigure.init(this, appkey, channel, UMConfigure.DEVICE_TYPE_PHONE, umPush);
        
        PushAgent mPushAgent = PushAgent.getInstance(this);
        
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                
                EventBus.getDefault().postSticky(new UMPushDeviceEvent(deviceToken));
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    public static Context getContext() {
        return mContext;
    }


    public static void runOnUIThread(Runnable r) {
        EsportsApplication.getMainHandler().post(r);
    }

    public static Handler getMainHandler() {
        return mHandler;
    }


    public void refreshString() {


        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.header_pulling);

        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.header_refreshing);
        
        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.header_loading);
        
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.header_release);
        
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.header_finish);
        
        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.header_failed);
        
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.header_update);
        
        ClassicsHeader.REFRESH_HEADER_UPDATE = getString(R.string.header_update);

        
        ClassicsFooter.REFRESH_FOOTER_PULLING = getString(R.string.footer_pulling);
        
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.footer_release);
        
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.footer_loading);
        
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.footer_refreshing);
        
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish);
        
        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.footer_failed);
        
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getString(R.string.footer_nothing);
    }
}