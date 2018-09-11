package common.esportschain.esports.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youcheng.publiclibrary.utils.AppActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.R;
import common.esportschain.esports.adapter.HomeListAdapter;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.event.BindEvent;
import common.esportschain.esports.event.BindPubgAccountEvent;
import common.esportschain.esports.event.ModifyAvatarEvent;
import common.esportschain.esports.event.UMPushDeviceEvent;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.presenter.HomePresenter;
import common.esportschain.esports.mvp.view.HomeView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.ui.setting.SettingActivity;
import common.esportschain.esports.utils.ToastUtil;
import common.esportschain.esports.weight.CustomizeDialog;

/**
 * @author liangzhaoyou
 * @date 2018/6/15
 */

public class HomeActivity extends MvpActivity<HomePresenter> implements HomeView {

    @BindView(R.id.iv_right)
    ImageView ivRight;

    @BindView(R.id.home_refreshLayout)
    RefreshLayout homeRefreshLayout;

    @BindView(R.id.recyc_home_list)
    RecyclerView recyclerView;

    private HomeListAdapter homeListAdapter;
    private List<HomeModel.HomeModelAccountListModel> homeModelAccountListModels = new ArrayList<>();
    private HomeModel homeModel = new HomeModel();

    private String mParam;
    private String mSig;
    private String mStatus;
    private UserInfo userInfo;

    private String mGamesDetailParam;
    private String mGamesDetailSig;
    private String mGameAusTokenSig;

    private long exitTime = 0;
    private int page = 1;
    private String mEventStatus = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        userInfo = new UserInfoDbManger().loadAll().get(0);
        mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());

        mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                ApiStores.APP_PAGE, "1", "", "", "", "", userInfo.getAuthkeys(), mParam);
        mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ivRight.setImageResource(R.mipmap.setting);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //刷新界面
        homeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                        ApiStores.APP_PAGE, "1", "", "", "", "", userInfo.getAuthkeys(), mParam);
                mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
                homeRefreshLayout.finishRefresh(1000);
            }
        });
        //加载更多
        homeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                        ApiStores.APP_PAGE, String.valueOf(page), "", "", "", "", userInfo.getAuthkeys(), mParam);
                mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, String.valueOf(page));
                homeRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.iv_right})
    public void onClick(View view) {
        if (view.equals(ivRight)) {
            Intent intent = new Intent(this, SettingActivity.class);
            intent.putExtra("avatar_url", this.homeModel.getResult().getUser().getAvatar());
            startActivity(intent);
        }
    }

    @Override
    public void getHomeData(HomeModel homeModel) {
        this.homeModel = homeModel;
        mStatus = String.valueOf(homeModel.getResult().getUser().getStatus());

        homeModelAccountListModels.clear();
        homeModelAccountListModels = homeModel.getResult().getAccount_list();
        initRecyclerView();
        showPop(homeModel);
    }

    /**
     * 上传友盟deviceToken
     *
     * @param nullModel
     */
    @Override
    public void postDeviceToken(NullModel nullModel) {

    }

    /**
     * 消息已读
     *
     * @param nullModel
     */
    @Override
    public void msgRead(NullModel nullModel) {

    }

    /***
     * 展示未读消息
     * @param homeModelPop
     */
    private void showPop(final HomeModel homeModelPop) {
        if (homeModelPop.getResult().getUser().getPopup().size() != 0) {
            if (1 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(0).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(0).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(0).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
            if (2 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(1).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(1).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(1).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
            if (3 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(2).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(2).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(2).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
            if (4 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(3).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(3).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(3).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
            if (5 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(4).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(4).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(4).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
            if (6 <= homeModelPop.getResult().getUser().getPopup().size()) {
                if (1 == homeModelPop.getResult().getUser().getPopup().get(5).getType()) {
                    final CustomizeDialog customDialog = new CustomizeDialog(this);
                    customDialog.setTitle(getResources().getString(R.string.tips));
                    customDialog.setContent(homeModelPop.getResult().getUser().getPopup().get(5).getMsgX());
                    customDialog.setBtConfirmText(getResources().getString(R.string.confirm));
                    customDialog.setSingleButton(true);
                    customDialog.setOnPositiveListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                            messageRead(homeModelPop.getResult().getUser().getPopup().get(5).getPid());
                        }
                    });
                    customDialog.show();
                }
            }
        }
    }

    private void messageRead(String msgId) {
        mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());

        mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_READ_MSG,
                ApiStores.APP_PiD, msgId, "", "", "", "", userInfo.getAuthkeys(), mParam);
        mvpPresenter.msgRead(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_READ_MSG, msgId);
    }

    private void initRecyclerView() {
        homeListAdapter = new HomeListAdapter(this, homeModelAccountListModels, homeModelAccountListModels, homeModel, mStatus);
        homeListAdapter.setOnRecyclerViewItemClickListener(new HomeListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {

                int footer = homeListAdapter.getItemCount();

                if (position == 0) {
                    //点击headler
                } else if (position == footer - 1) {
                    //点击footer没有操作
                } else {
                    //0未绑定 1已绑定 steam绑定状态
                    if (1 == homeModel.getResult().getUser().getStatus()) {
                        //未绑定steam 点击跳转到绑定界面
                        Intent intent = new Intent(mActivity, BindWebViewActivity.class);
                        intent.putExtra("login_status", "1");
                        intent.putExtra("login_type", "2");
                        startActivity(intent);
                    } else if (homeModel.getResult().getAccount_list().size() == 0) {

                    } else if (2 == homeModel.getResult().getUser().getStatus() && null != homeModel.getResult().getAccount_list()) {

                    } else if (3 == homeModel.getResult().getUser().getStatus() && (4 == homeModel.getResult().getAccount_list().get(position - 1).getAccount_status()
                            || 3 == homeModel.getResult().getAccount_list().get(position - 1).getAccount_status()
                            || 2 == homeModel.getResult().getAccount_list().get(position - 1).getAccount_status())) {
                        //未绑定昵称的状态界面界面跳转

                        String gameType = String.valueOf(homeModel.getResult().getAccount_list().get(position - 1).getGametype());

                        Intent intent = new Intent(mActivity, BindPubgAccountActivity.class);
                        intent.putExtra("game_type", gameType);
                        startActivity(intent);
                    } else {
                        //进入对战游戏详情界面
                        getURIStr(position);
                        Intent intent = new Intent(mActivity, GamesDetailActivity.class);
                        intent.putExtra("games_param", mGamesDetailParam);
                        intent.putExtra("games_sig", mGamesDetailSig);
                        intent.putExtra("aus_token", mGameAusTokenSig);
                        startActivity(intent);
                    }
                }
            }
        });
        recyclerView.setAdapter(homeListAdapter);
    }

    /**
     * EvenBus 主线更新事件
     * 修改头像之后刷新界面
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifyAvatar(ModifyAvatarEvent event) {
        if (mEventStatus.equals(event.getMidifyAvatar())) {

            clearImageDiskCache(mActivity);

            mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                    ApiStores.APP_PAGE, "1", "", "", "", "", userInfo.getAuthkeys(), mParam);
            mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
        }
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EvenBus 主线程更新UI界面
     * 绑定Steam 之后更新界面
     *
     * @param bindEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifLoginData(BindEvent bindEvent) {
        if (mEventStatus.equals(bindEvent.getmBindEventSteam())) {
            mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                    ApiStores.APP_PAGE, "1", "", "", "", "", userInfo.getAuthkeys(), mParam);
            mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
        }
    }

    /**
     * EvenBus 主线程更新UI界面
     * 绑定Steam 之后更新界面
     *
     * @param bindEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifBindPudgAccount(BindPubgAccountEvent bindEvent) {
        if (mEventStatus.equals(bindEvent.getBindAccount())) {
            mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL,
                    ApiStores.APP_PAGE, "1", "", "", "", "", userInfo.getAuthkeys(), mParam);
            mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
        }
    }

    /**
     * EvenBus 粘性事件
     * 登录之后上传deviseToken到后台数据库
     *
     * @param umPushDeviceEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void updateDeviceToken(UMPushDeviceEvent umPushDeviceEvent) {
        if (!"".equals(umPushDeviceEvent.getmDeviceEvent())) {
            mSig = AuthSIG.getRequestSig(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_UPDATE,
                    "device_token", umPushDeviceEvent.getmDeviceEvent(), "", "", "", "",
                    userInfo.getAuthkeys(), mParam);
            mvpPresenter.postUmDevice(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_UPDATE,
                    umPushDeviceEvent.getmDeviceEvent());
        }
    }

    /**
     * 获取的数据在跳转界面时候使用Sig签名使用
     *
     * @param position
     */
    public void getURIStr(int position) {

        JsonObject uriJson = homeModelAccountListModels.get(position - 1).getDetail().getUri();
        //获取key的值
        Set<String> getKey = uriJson.keySet();
        //Set类型转成list
        List<String> list1 = new ArrayList<String>(getKey);
        //转换成String[]类型 获取key的值
        String[] b = list1.toArray(new String[list1.size()]);
        //按照字典排序
        Arrays.sort(b, String.CASE_INSENSITIVE_ORDER);
        //排序之后放入String[]中
        String[] Sorting = null;
        String sortsig = "";
        //gson获取排序后的value的值
        JsonElement je = new JsonParser().parse(uriJson + "");
        for (int i = 0; i < b.length; i++) {
            Sorting = b;
            sortsig += je.getAsJsonObject().get(Sorting[i]) + "";
        }

        //去掉字符串上的引号
        String mSortsinRemove = sortsig.replace("\"", "");

        mGamesDetailParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());

        //进行Sig签名
        mGamesDetailSig = AuthSIG.AuthTokenGames(mGamesDetailParam, mSortsinRemove, userInfo.getAuthkeys());

        String keyValue = "";
        for (int j = 0; j < list1.size(); j++) {
            keyValue += "&" + list1.get(j) + "=" + je.getAsJsonObject().get(list1.get(j));
        }

        mGameAusTokenSig = keyValue.replace("\"", "");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            ToastUtil.showToast(getResources().getString(R.string.to_exit));
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出app
     */
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
        } else {
            AppActivityManager.getInstance().killAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
