package com.youcheng.publiclibrary.listener;

import android.net.NetworkInfo;
/**
 * @author qiaozhenxin
 * 网络环境监听
 */

public interface NetworkListener {
    /**
     * 连接wifi时
     * @param networkInfo Wifi网络连接的相关信息
     */
    void onWifiConnected(NetworkInfo networkInfo);
    /**
     * 连接手机数据网络时
     * @param networkInfo 手机网络连接的相关信息
     */
    void onMobileConnected(NetworkInfo networkInfo);

    /**
     * 断开网络连接时
     * @param networkInfo 断开网络的网络信息
     */
    void onDisconnected(NetworkInfo networkInfo);
}
