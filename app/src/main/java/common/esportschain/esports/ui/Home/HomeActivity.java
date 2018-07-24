package common.esportschain.esports.ui.home;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

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
import common.esportschain.esports.event.BindEvent;
import common.esportschain.esports.event.ModifyAvatarEvent;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
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
        getRequestKey(ApiStores.APP_M_DETAIL);
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
                mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
                homeRefreshLayout.finishRefresh(1000);
            }
        });
        //加载更多
        homeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
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
            pushActivity(this, SettingActivity.class);
        }
    }

    @Override
    public void getHomeData(HomeModel homeModel) {
        this.homeModel = homeModel;
        mStatus = homeModel.getResult().getUser().getSteam_status();

        homeModelAccountListModels.clear();
        homeModelAccountListModels = homeModel.getResult().getAccount_list();
        initRecyclerView();
    }

    @Override
    public void postDeviceToken(NullModel nullModel) {

    }

    private void initRecyclerView() {
        homeListAdapter = new HomeListAdapter(this, homeModelAccountListModels, homeModelAccountListModels, homeModel, mStatus);

        homeListAdapter.setOnRecyclerViewItemClickListener(new HomeListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    //点击整个条目跳转
//                    Intent intent = new Intent(mActivity, WalletActivity.class);
//                    intent.putExtra("wallet_money", homeModel.getResult().getUser().getMoney());
//                    startActivity(intent);
                } else {
                    //0未绑定 1已绑定steam绑定状态
                    if ("0".equals(homeModel.getResult().getUser().getSteam_status())) {
                        //未绑定steam 点击跳转到绑定界面
                        Intent intent = new Intent(mActivity, BindWebViewActivity.class);
                        intent.putExtra("login_status", "1");
                        intent.putExtra("login_type", "2");
                        startActivity(intent);
                    } else if (homeModel.getResult().getAccount_list().size() == 0) {

                    } else {
                        //进入对战游戏详情界面
                        getURIStr(position);
                        Intent intent = new Intent(mActivity, GamesDetailActivity.class);
                        intent.putExtra("games_sig", mGamesDetailSig);
                        intent.putExtra("aus_token", mGameAusTokenSig);
                        startActivity(intent);
                    }
                }
            }
        });

        recyclerView.setAdapter(homeListAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifyAvatar(ModifyAvatarEvent event) {
        if (mEventStatus.equals(event.getMidifyAvatar())) {
            getRequestKey(ApiStores.APP_M_DETAIL);
            mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifLoginData(BindEvent bindEvent) {
        if (mEventStatus.equals(bindEvent.getmBindEventSteam())) {
            getRequestKey(ApiStores.APP_M_DETAIL);
            mvpPresenter.getHomeData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_DETAIL, "1");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void updateDeviceToken(UMPushDeviceEvent umPushDeviceEvent) {
        if (!"".equals(umPushDeviceEvent.getmDeviceEvent())) {
            getRequestKey(ApiStores.APP_M_UPDATE);
            mvpPresenter.postUmDevice(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_UPDATE,
                    umPushDeviceEvent.getmDeviceEvent());
        }
    }

    public void getURIStr(int position) {
        JsonObject uriJson = homeModelAccountListModels.get(position - 1).getUri();
        //获取key的值
        Set<String> aa = uriJson.keySet();
        //Set类型转成list
        List<String> list1 = new ArrayList<String>(aa);
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
        //进行Sig签名
        mGamesDetailSig = AuthSIG.AuthTokenGames(mSortsinRemove, userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());

        String keyValue = "";
        for (int j = 0; j < list1.size(); j++) {
            keyValue += "&" + list1.get(j) + "=" + je.getAsJsonObject().get(list1.get(j));
        }
        mGameAusTokenSig = keyValue.replace("\"", "");
    }

    private void getRequestKey(String m) {
        userInfo = new UserInfoDbManger().loadAll().get(0);
        mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
        mSig = AuthSIG.AuthToken(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, m,
                userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());
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
