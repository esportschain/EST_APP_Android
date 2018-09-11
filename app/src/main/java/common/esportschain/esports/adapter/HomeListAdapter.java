package common.esportschain.esports.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youcheng.publiclibrary.base.adapter.BaseRecyclerAdapter;
import com.youcheng.publiclibrary.base.adapter.BaseViewHolder;

import java.util.Collection;
import java.util.List;

import common.esportschain.esports.R;
import common.esportschain.esports.mvp.model.HomeModel;
import common.esportschain.esports.ui.wallet.WalletActivity;
import common.esportschain.esports.utils.GlideUtil;

/**
 * @author liangzhaoyou
 * @date 2018/6/20
 */

public class HomeListAdapter extends BaseRecyclerAdapter<HomeModel.HomeModelAccountListModel> {

    /**
     * 没有绑定steam
     */
    private static final int NO_DATA_BIND_TYPE = 0;
    /**
     * 没有游戏显示
     */
    private static final int NO_BIND_GAMES_TYPE = 1;
    /**
     * 正常显示游戏列表
     */
    private static final int BIND_GAMES_TYPE = 2;
    /**
     * 添加标题
     */
    private static final int HANDER_TYPE = 3;
    /**
     * 没有绑定昵称已经获取到账号列表
     */
    private static final int BIND_ACCOUNT = 4;
    /**
     * 没有绑定昵称没有获取到账号列表
     */
    private static final int BIND_ACCOUNT_NULL_LIST = 5;
    /**
     * 展示挖矿
     */
    private static final int SHOW_MINING = 6;

    private List<HomeModel.HomeModelAccountListModel> homeModelAccountListModelList;
    private HomeModel homeUserModel;
    private String type;
    private Context mContext;

    public HomeListAdapter(Context context, Collection<HomeModel.HomeModelAccountListModel> collection,
                           List<HomeModel.HomeModelAccountListModel> homeModelAccountListModelList,
                           HomeModel homeUserModel,
                           String type) {
        super(context, collection);
        this.mContext = context;
        this.homeModelAccountListModelList = homeModelAccountListModelList;
        this.homeUserModel = homeUserModel;
        this.type = type;
    }

    @Override
    protected int getContentView(int viewType) {
        if (viewType == NO_DATA_BIND_TYPE) {
            return R.layout.item_home_no_bind;
        } else if (viewType == SHOW_MINING) {
            return R.layout.item_home_show_mining;
        } else if (viewType == NO_BIND_GAMES_TYPE) {
            return R.layout.item_home_no_games;
        } else if (viewType == HANDER_TYPE) {
            return R.layout.home_header;
        } else if (viewType == BIND_ACCOUNT) {
            return R.layout.item_home_no_bind_account;
        } else if (viewType == BIND_ACCOUNT_NULL_LIST) {
            return R.layout.item_home_bind_account_no_game_list;
        }
        return R.layout.item_home;
    }

    /**
     * 头部显示信息
     */
    private ImageView ivHomeAvatar;
    private TextView tvHomeNickName;
    private TextView tvMoney;
    private TextView tvMyGame;
    private ImageView ivHomeTitleRight;

    /**
     * 没有绑定显示item
     */
    private ImageView ivNoBindIcon;
    private TextView tvNoBindTitle;
    private TextView tvNoBindContent;

    /**
     * 已经绑定没有数据显示item
     */
    private ImageView ivNoGamesIcon;
    private TextView tvNoGamesTitle;
    private TextView tvNoGamesContent;

    /**
     * 显示绑定游戏昵称界面
     */
    private ImageView ivBindAccounIcon;
    private TextView tvBindAccountName;
    private TextView tvBindAccountStatus;
    private TextView tvBindAccountPrompt;

    /**
     * 已经绑定steam没有获取到游戏列表
     */
    private TextView tvBindNoGameList;

    /**
     * 有数据显示item
     */
    private ImageView ivGamesIcon;
    private TextView tvGamesName;
    private TextView tvGamesNickkname;
    private LinearLayout llGamesDataShow;
    private TextView tvGamesMatchNum;
    private TextView tvGamesPrompt;
    private TextView tvGamesWinRate;
    private TextView tvWinRatePrompt;
    private TextView tvGamesKAD;
    private TextView tvKADPrompt;
    private TextView tvGamesMMR;
    private TextView tvMMRPrompt;

    @Override
    protected void covert(BaseViewHolder holder, HomeModel.HomeModelAccountListModel data, final int position) {

        //显示顶部的heander
        if (holder.getItemViewType() == HANDER_TYPE) {
            ivHomeAvatar = holder.itemView.findViewById(R.id.iv_home_avatar);
            tvHomeNickName = holder.itemView.findViewById(R.id.tv_home_nickname);
            tvMoney = holder.itemView.findViewById(R.id.tv_home_wallet_amount);
            tvMyGame = holder.itemView.findViewById(R.id.tv_home_wallet_my_game);
            ivHomeTitleRight = holder.itemView.findViewById(R.id.iv_home_title_right);

            GlideUtil.loadRandImg(mContext, homeUserModel.getResult().getUser().getAvatar(), ivHomeAvatar);

            tvHomeNickName.setText(homeUserModel.getResult().getUser().getNickname());
            tvMoney.setText(homeUserModel.getResult().getUser().getMoney() + mContext.getResources().getString(R.string.est));

            if (1 == homeUserModel.getResult().getUser().getStatus() || 2 == homeUserModel.getResult().getUser().getStatus()) {
                ivHomeTitleRight.setVisibility(View.GONE);
            }

            // TODO: 2018/6/20  点击控件进入详情界面
            tvMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (1 != homeUserModel.getResult().getUser().getStatus() && 2 != homeUserModel.getResult().getUser().getStatus()) {
                        Intent intent = new Intent(mContext, WalletActivity.class);
                        intent.putExtra("wallet_money", homeUserModel.getResult().getUser().getMoney());
                        mContext.startActivity(intent);
                    }
                }
            });
            //没有数据需要绑定账号显示
        } else if (holder.getItemViewType() == SHOW_MINING) {


        } else if (holder.getItemViewType() == NO_DATA_BIND_TYPE) {
            ivNoBindIcon = holder.itemView.findViewById(R.id.iv_home_no_bind);
            tvNoBindTitle = holder.itemView.findViewById(R.id.tv_home_no_bind_title);
            tvNoBindContent = holder.itemView.findViewById(R.id.tv_home_no_bind_content);
            tvMyGame.setVisibility(View.VISIBLE);
            //没有游戏
        } else if (holder.getItemViewType() == NO_BIND_GAMES_TYPE) {
            ivNoGamesIcon = holder.itemView.findViewById(R.id.iv_home_no_games);
            tvNoGamesTitle = holder.itemView.findViewById(R.id.tv_home_no_games_title);
            tvNoGamesContent = holder.itemView.findViewById(R.id.tv_home_no_games_content);
            tvMyGame.setVisibility(View.VISIBLE);
            //没有绑定游戏
        } else if (holder.getItemViewType() == BIND_ACCOUNT) {
            tvMyGame.setVisibility(View.VISIBLE);
            ivBindAccounIcon = holder.itemView.findViewById(R.id.iv_no_bind_account);
            tvBindAccountName = holder.itemView.findViewById(R.id.tv_bind_account_name);
            tvBindAccountStatus = holder.itemView.findViewById(R.id.tv_bind_account_status);
            tvBindAccountPrompt = holder.itemView.findViewById(R.id.tv_bind_account_prompt);

            if (2 == homeUserModel.getResult().getAccount_list().get(position - 1).getAccount_status()) {
                tvBindAccountPrompt.setVisibility(View.VISIBLE);
            } else if (3 == homeUserModel.getResult().getAccount_list().get(position - 1).getAccount_status()) {
                tvBindAccountStatus.setText("in progress");
                tvBindAccountPrompt.setVisibility(View.GONE);
            }
            //正常显示item
        } else if (holder.getItemViewType() == BIND_ACCOUNT_NULL_LIST) {
            tvMyGame.setVisibility(View.GONE);
            tvBindNoGameList = holder.itemView.findViewById(R.id.tv_bind_account_no_game_list);
        } else {
            tvMyGame.setVisibility(View.VISIBLE);
            llGamesDataShow = holder.itemView.findViewById(R.id.ll_games_data_show);

            ivGamesIcon = holder.itemView.findViewById(R.id.iv_home_games_logo);

            tvGamesName = holder.itemView.findViewById(R.id.tv_home_games_name);
            tvGamesNickkname = holder.itemView.findViewById(R.id.tv_home_games_nickname);
            tvGamesMatchNum = holder.itemView.findViewById(R.id.tv_games_matchnum);
            tvGamesPrompt = holder.itemView.findViewById(R.id.tv_games_prompt);
            tvGamesWinRate = holder.itemView.findViewById(R.id.tv_games_winrate);
            tvWinRatePrompt = holder.itemView.findViewById(R.id.tv_winrate_prompt);
            tvGamesKAD = holder.itemView.findViewById(R.id.tv_games_kad);
            tvKADPrompt = holder.itemView.findViewById(R.id.tv_kad_prompt);
            tvGamesMMR = holder.itemView.findViewById(R.id.tv_games_mmr);
            tvMMRPrompt = holder.itemView.findViewById(R.id.tv_mmr_prompt);

            GlideUtil.loadRandImg(mContext, homeUserModel.getResult().getAccount_list().get(position - 1).getIcon(), ivGamesIcon);
            //昵称
            tvGamesNickkname.setText(mContext.getResources().getString(R.string.recent_match));
            //游戏名字
            tvGamesName.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getAccountname());

            if (0 != homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().size()) {
                //匹配的次数
                tvGamesMatchNum.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(0).getVal());
                tvGamesPrompt.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(0).getField());
                //胜率
                tvGamesWinRate.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(1).getVal());
                tvWinRatePrompt.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(1).getField());
                //击杀死亡比
                //＜击杀数加助攻数＞÷死亡数
                tvGamesKAD.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(2).getVal());
                tvKADPrompt.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(2).getField());
                //前十次数
                //匹配等级
                tvGamesMMR.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(3).getVal());
                tvMMRPrompt.setText(homeUserModel.getResult().getAccount_list().get(position - 1).getDetail().getStats().get(3).getField());
            } else {
                llGamesDataShow.setVisibility(View.GONE);
                tvGamesNickkname.setText(mContext.getResources().getString(R.string.in_progress));
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemList;
        if ("1".equals(type) || homeModelAccountListModelList.size() == 0) {
            itemList = homeModelAccountListModelList.size() + 3;
        } else {
            itemList = homeModelAccountListModelList.size() + 2;
        }
        return itemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //第一个item应该加载Header
            return HANDER_TYPE;
        } else if (position == getItemCount() - 1) {
            //挖矿提示
            return SHOW_MINING;
        } else if ("1".equals(type)) {
            //没有数据绑定数据显示的layout状态
            return NO_DATA_BIND_TYPE;
        } else if ("2".equals(type) && 0 == homeModelAccountListModelList.size()) {
            //绑定为空列表展示
            return BIND_ACCOUNT_NULL_LIST;
        } else if ("3".equals(type)) {
            if (4 == homeModelAccountListModelList.get(position - 1).getAccount_status()
                    || 3 == homeModelAccountListModelList.get(position - 1).getAccount_status()
                    || 2 == homeModelAccountListModelList.get(position - 1).getAccount_status()) {
                return BIND_ACCOUNT;
            }
        }
        //正常视图
        return BIND_GAMES_TYPE;
    }
}
