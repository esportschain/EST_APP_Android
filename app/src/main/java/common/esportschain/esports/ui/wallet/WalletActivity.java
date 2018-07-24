package common.esportschain.esports.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import common.esportschain.esports.EsportsApplication;
import common.esportschain.esports.R;
import common.esportschain.esports.adapter.WalletListAdapter;
import common.esportschain.esports.base.MvpActivity;
import common.esportschain.esports.event.UMPushEvent;
import common.esportschain.esports.event.WithdrawWalletTransactionEvent;
import common.esportschain.esports.database.UserInfo;
import common.esportschain.esports.database.UserInfoDbManger;
import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;
import common.esportschain.esports.mvp.presenter.WalletPresenter;
import common.esportschain.esports.mvp.view.WalletView;
import common.esportschain.esports.request.ApiStores;
import common.esportschain.esports.request.AuthParam;
import common.esportschain.esports.request.AuthSIG;
import common.esportschain.esports.utils.ToastUtil;
import common.esportschain.esports.weight.CustomizeDialog;
import common.esportschain.esports.weight.WithdrawWalletAddressDialog;
import common.esportschain.esports.weight.WithdrawWalletTransactionDialog;

/**
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public class WalletActivity extends MvpActivity<WalletPresenter> implements WalletView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_withdraw_to_my_wallet)
    TextView tvWithdrawToMyWallet;

    @BindView(R.id.recyc_wallet_list)
    RecyclerView recyclerViewWallet;

    @BindView(R.id.wallet_refreshLayout)
    RefreshLayout refreshLayout;

    private UserInfo userInfo;
    private String mParam;
    private String mSig;
    private int page = 1;
    private String mMoney;

    private String etWalletAddress;

    private WalletListAdapter walletListAdapter;
    private List<WalletModel.WalletItemModel> walletModelList = new ArrayList<>();

    private String isEnd;
    private WithdrawWalletAddressDialog withdrawWalletDialog;
    private WithdrawWalletTransactionDialog withdrawWalletTransactionDialog;
    private CustomizeDialog mCustomizeDialog;
    private CustomizeDialog mBalanceNoEnoughDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mMoney = intent.getStringExtra("wallet_money");
        //注册观察者
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        super.initData();
        userInfo = new UserInfoDbManger().loadAll().get(0);
        mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
        mSig = AuthSIG.AuthToken(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET,
                userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());

        mvpPresenter.getWalletData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET, String.valueOf(page));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvTitle.setText(getResources().getString(R.string.wallet_title));
        ivBack.setImageResource(R.mipmap.back);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWallet.setLayoutManager(layoutManager);
        setRefreshLayout();
        setLoadLayout();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_wallet;
    }

    @OnClick({R.id.iv_back, R.id.tv_withdraw_to_my_wallet})
    public void onClick(View view) {
        if (view.equals(ivBack)) {
            finish();
        } else if (view.equals(tvWithdrawToMyWallet)) {
            double moneyDouble = Double.parseDouble(mMoney);
            if (moneyDouble > 0.0) {
                inputAddress();
            } else {
                balanceNotEnough();
            }
        }
    }

    /**
     * 请求数据成功
     *
     * @param walletModel
     */
    @Override
    public void getWalletData(WalletModel walletModel) {
        isEnd = walletModel.getResult().getIsEnd();
        walletModelList.clear();
        walletModelList = walletModel.getResult().getList();
        initRecyclerView();
    }

    /**
     * 提现成功成功
     *
     * @param nullModel
     */
    @Override
    public void getWalletWithdrawWallet(NullModel nullModel) {
        //请求成功刷新界面
        mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
        mSig = AuthSIG.AuthToken(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET,
                userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());

        mvpPresenter.getWalletData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET, String.valueOf(page));
        //关闭所有的dialog
        EventBus.getDefault().post(new WithdrawWalletTransactionEvent("1"));

    }

    /**
     * 提现手续费请求成功展示dialog
     *
     * @param walletTexModel
     */
    @Override
    public void getWalletTax(WalletTexModel walletTexModel) {
        showConfirmTex(walletTexModel.getResult().getMessage());
    }

    /**
     * 实现RecyclerView
     */
    private void initRecyclerView() {
        walletListAdapter = new WalletListAdapter(this, walletModelList, walletModelList, mMoney);
        recyclerViewWallet.setAdapter(walletListAdapter);
    }

    /**
     * 关闭dialog
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifyAvatar(WithdrawWalletTransactionEvent event) {
        if ("1".equals(event.getWalletTransaction())) {
            withdrawWalletTransactionDialog.dismiss();
            withdrawWalletDialog.dismiss();
        }
    }

    /**
     * 显示提现成功dialog
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void withdrawSuccess(final UMPushEvent event) {
        Log.e("显示event接收的请求", event.mMsg + "==" + event.mType);
        EsportsApplication.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if ("1".equals(event.getmType())) {
                    /**
                     * 请求成功展示
                     */
                    shoWithdrawDialog(event.getmMsg());
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.cash_withdrawal_failure));
                }
            }
        });

    }

    /**
     * 余额不足dialog
     */
    public void balanceNotEnough() {
        mBalanceNoEnoughDialog = new CustomizeDialog(mActivity);
        mBalanceNoEnoughDialog.setTitle(getResources().getString(R.string.insufficient_balance));
        mBalanceNoEnoughDialog.setSingleButton(true);
        mBalanceNoEnoughDialog.setBtConfirmText(getResources().getString(R.string.confirm));
        mBalanceNoEnoughDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBalanceNoEnoughDialog.dismiss();
            }
        });
        mBalanceNoEnoughDialog.show();
    }

    /**
     * 输入地址提现dialog
     */
    public void inputAddress() {
        withdrawWalletDialog = new WithdrawWalletAddressDialog(mActivity);
        withdrawWalletDialog.setCanceledOnTouchOutside(true);
        withdrawWalletDialog.setOnNextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWalletAddress = withdrawWalletDialog.getEtWalletMoney();
                if (!"".equals(etWalletAddress)) {
                    showConfirmDialogShow(etWalletAddress);
                } else {
                    ToastUtil.showToast(getResources().getString(R.string.please_enter_wallet_address));
                }
            }
        });
        withdrawWalletDialog.show();
    }

    /**
     * 提现dialog
     *
     * @param address
     */
    public void showConfirmDialogShow(final String address) {
        withdrawWalletTransactionDialog = new WithdrawWalletTransactionDialog(mActivity);
        withdrawWalletTransactionDialog.setAmount(mMoney + getResources().getString(R.string.est));
        withdrawWalletTransactionDialog.setAddress(address);

        //关闭整体
        withdrawWalletTransactionDialog.setonClickClose(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new WithdrawWalletTransactionEvent("1"));
            }
        });

        //返回
        withdrawWalletTransactionDialog.setBackLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawWalletTransactionDialog.dismiss();
            }
        });

        /**
         * 提现
         */
        withdrawWalletTransactionDialog.setConfirm(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
                mSig = AuthSIG.AuthToken(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_PUTFORWARD,
                        userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());
                //手续费请求
                mvpPresenter.showTexDialog(mParam, mSig, ApiStores.APP_C_MEMBER,
                        ApiStores.APP_D_APP, ApiStores.APP_M_PUTFORWARD, address, "1");
            }
        });
        withdrawWalletTransactionDialog.show();
    }


    /**
     * 提现手续费dialog
     *
     * @param title
     */
    public void showConfirmTex(String title) {
        mCustomizeDialog = new CustomizeDialog(mActivity);
        mCustomizeDialog.setTitle(title);
        mCustomizeDialog.setSingleButton(true);
        mCustomizeDialog.setBtConfirmText(getResources().getString(R.string.confirm));
        mCustomizeDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParam = AuthParam.AuthParam(userInfo.getUId(), userInfo.getToken());
                mSig = AuthSIG.AuthToken(ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_PUTFORWARD,
                        userInfo.getAuthkeys() + "", userInfo.getUId(), userInfo.getToken());
                mvpPresenter.putForWord(mParam, mSig, ApiStores.APP_C_MEMBER,
                        ApiStores.APP_D_APP, ApiStores.APP_M_PUTFORWARD, etWalletAddress, "2");
                mCustomizeDialog.dismiss();
            }
        });
        mCustomizeDialog.show();
    }

    /**
     * 提现成功dialog
     *
     * @param title
     */
    public void shoWithdrawDialog(String title) {
        mCustomizeDialog = new CustomizeDialog(mActivity);
        mCustomizeDialog.setTitle(title);
        mCustomizeDialog.setSingleButton(true);
        mCustomizeDialog.setBtConfirmText(getResources().getString(R.string.confirm));
        mCustomizeDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomizeDialog.dismiss();
            }
        });
        mCustomizeDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (withdrawWalletDialog != null) {
            withdrawWalletDialog.dismiss();
        }
        if (withdrawWalletTransactionDialog != null) {
            withdrawWalletTransactionDialog.dismiss();
        }
        if (mBalanceNoEnoughDialog != null) {
            mBalanceNoEnoughDialog.dismiss();
        }
        if (mCustomizeDialog != null) {
            mCustomizeDialog.dismiss();
        }

        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新界面
     */
    private void setRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mvpPresenter.getWalletData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET, String.valueOf(page));
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    /**
     * 加载更多
     */
    private void setLoadLayout() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mvpPresenter.getWalletData(mParam, mSig, ApiStores.APP_C_MEMBER, ApiStores.APP_D_APP, ApiStores.APP_M_WALLET, String.valueOf(page));
                refreshLayout.finishLoadMore(1000);
            }
        });
    }
}
