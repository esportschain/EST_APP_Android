package common.esportschain.esports.upush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import common.esportschain.esports.R;
import common.esportschain.esports.event.UMPushEvent;

/**
 * @author liangzhaoyou
 */

public class MyPushIntentService extends UmengMessageService {

    private static final String TAG = MyPushIntentService.class.getName();
    private String messageType = "";
    private String messageMsg = "";

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));

            Map<String, String> extra = msg.extra;
            Intent intentAct = new Intent();
            if (extra != null) {
                messageType = extra.get("type");
                messageMsg = extra.get("msg");
            }

            EventBus.getDefault().post(new UMPushEvent(messageType, messageMsg));

            Log.e("输出友盟统计的数据", msg.title + "\n" + msg.ticker + "\n" + msg.text + "\n" + messageType + "\n" + messageMsg + "\n");

            intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
            showNotifications(context, msg, intentAct);


            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {

                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {

                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

        } catch (Exception e) {

        }
    }

    public void showNotifications(Context context, UMessage msg, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setColor(Color.parseColor("#41b5ea"))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(100, builder.build());
    }
}
