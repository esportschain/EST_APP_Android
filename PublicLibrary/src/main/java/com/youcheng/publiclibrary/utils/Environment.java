package com.youcheng.publiclibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.youcheng.publiclibrary.listener.NetworkListener;
import com.youcheng.publiclibrary.log.LogUtil;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.LinkedList;

public final class Environment {

    private static final String TAG = Environment.class.getSimpleName();

    private Context mContext;

    private int mScreenWidth;
    private int mScreenHeight;

    private static class InstanceHolder {
        private static Environment instance = new Environment();
    }

    private Environment() {
    }

    public static Environment getInstance() {
        return InstanceHolder.instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**

     */
    public int getOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
  
     */
    public String getMobileType() {
        return Build.MODEL;
    }

    /**
   
     */
    public String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**

     */
    public String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     *
     * @return 
     */
    public int getScreenWidth() {
        if (0 == mScreenWidth) {

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);

            windowManager.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
        }
        return mScreenWidth;
    }

    /**

     */
    public String getSDPath() {
        if (isExternalStorageAvailable()) {
            return getExternalStorageDirectory() + "/"
                    + mContext.getPackageName();
        }
        return "";
    }

    /**

     */
    public boolean isExternalStorageAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**

     */
    public String getExternalStorageDirectory() {
        return android.os.Environment.getExternalStorageDirectory()
                .getAbsolutePath();
    }


    public int getScreenHeight() {
        if (0 == mScreenHeight) {

            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);

            windowManager.getDefaultDisplay().getMetrics(dm);
            mScreenHeight = dm.heightPixels;
        }
        return mScreenHeight;

    }

  
    public String getApplicationName() {
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(
                    mContext.getPackageName(), 0);
            String applicationName = (String) pm
                    .getApplicationLabel(applicationInfo);
            return applicationName + "_android";
        } catch (Exception e) {
            LogUtil.e(TAG, "getApplicationName()", e);
        }
        return "";
    }

  
     
    public int getMyVersionCode() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(
                    mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            LogUtil.e(TAG, "getMyVersionCode()", e);
        }
        return 0;
    }

    public String getIMSI() {

        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (!TextUtils.isEmpty(imsi)) {
            return imsi;
        } else {
            return "";
        }
    }

    public String getWifiMacAddress() {
        String macAddress = "";
        try {
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macAddress = info.getMacAddress();
        } catch (Exception e) {

        }
        return macAddress;
    }

    public String getMacAddress() {
        String wifiMacAddress = getWifiMacAddress();
        if (!TextUtils.isEmpty(wifiMacAddress)) {
            return wifiMacAddress;
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return macSerial;
    }

    public String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    public String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }


    public String getMyVersionName() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(
                    mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            LogUtil.e(TAG, "getMyVersionName()", e);
        }
        return null;
    }

   
    public String getPackageName() {
        return mContext.getPackageName();
    }

    public boolean isNetworkAvailable() {
  
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnected();
    }

    public boolean isWifi() {
      
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
  
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    public String getIMEI() {
        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


    public String getIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();
                        if (!ip.contains("::") && !ip.contains(":")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getDeviceIP()", e);
        }
        return "";
    }

    public StringBuilder getAllInfo() {
        StringBuilder info = new StringBuilder();

        @SuppressWarnings("rawtypes")
        Class clazz = null;
        try {
            clazz = Class.forName("android.os.Build");
        } catch (ClassNotFoundException ex) {
            try {
                clazz = Class.forName("miui.os.Build");
            } catch (ClassNotFoundException e) {
                LogUtil.e(TAG, "getAllInfo()", e);
            }
        }

        if (clazz != null) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);

                    info.append(field.getName());
                    info.append(" = ");
                    info.append(field.get(null));
                    info.append("\n");
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "getAllInfo()", e);
            }
        }
        return info;
    }

    public static void full(boolean enable, Activity activity) {
        if (enable) {
            WindowManager.LayoutParams lp = activity.getWindow()
                    .getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = activity.getWindow()
                    .getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(attr);
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    public static String formatSizeMb(long size) {
        long sz1MB = 1024 * 1024;
        if (size < 0) {
            return "" + size;
        }
        long size_ = size % sz1MB;
        long size__ = size_ * 10 % sz1MB;
        long size___ = size__ * 10 % sz1MB;
        String s = String.valueOf(size / sz1MB) + "."
                + String.valueOf(size_ * 10 / sz1MB)
                + String.valueOf(size__ * 10 / sz1MB)
                + String.valueOf(size___ * 10 / sz1MB);
        BigDecimal bd = new BigDecimal(s);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.format("%s", bd.toString()) + "MB";
    }

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private LinkedList<NetworkListener> listeners = new LinkedList<>();

    public void addNetworkListener(NetworkListener listener) {
        LogUtil.v();
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeNetworkListener(NetworkListener listener) {
        if (null != listener && listeners != null) {
            listeners.remove(listener);
        }
    }

    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    public LinkedList<NetworkListener> getListeners() {
        return listeners;
    }

    public void setListeners(LinkedList<NetworkListener> listeners) {
        this.listeners = listeners;
    }

    public String getChannel() {
        ApplicationInfo appInfo = null;
        String msg = "CHANNEL_UNKNOW";
        try {
            appInfo = mContext.getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
