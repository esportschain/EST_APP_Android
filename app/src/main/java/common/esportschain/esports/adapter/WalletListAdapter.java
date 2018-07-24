package common.esportschain.esports.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.youcheng.publiclibrary.base.adapter.BaseRecyclerAdapter;
import com.youcheng.publiclibrary.base.adapter.BaseViewHolder;

import java.util.Collection;
import java.util.List;

import common.esportschain.esports.R;
import common.esportschain.esports.mvp.model.WalletModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/20
 */

public class WalletListAdapter extends BaseRecyclerAdapter<WalletModel.WalletItemModel> {

    private static final int HEADER_TYPE = 0;
    private static final int ITEM_TYPE = 1;

    private Context mContext;
    private List<WalletModel.WalletItemModel> walletModelList;
    private String mMoney;

    public WalletListAdapter(Context context, Collection<WalletModel.WalletItemModel> collection,
                             List<WalletModel.WalletItemModel> walletModelList,
                             String money) {
        super(context, collection);
        this.mContext = context;
        this.walletModelList = walletModelList;
        this.mMoney = money;
    }

    /**
     * 无数据显示
     */
    private TextView tvWalletHeader;

    /**
     * 有数据显示
     */
    private TextView tvWalletTitle;
    private TextView tvWalletDate;
    private TextView tvWalletStatus;

    @Override
    protected void covert(BaseViewHolder holder, WalletModel.WalletItemModel data, int position) {
        if (holder.getItemViewType() == HEADER_TYPE) {
            tvWalletHeader = holder.getView().findViewById(R.id.tv_wallet_my_money);
            tvWalletHeader.setText(mMoney);
        } else {
            tvWalletTitle = holder.getView().findViewById(R.id.tv_wallet_item_title);
            tvWalletDate = holder.getView().findViewById(R.id.tv_wallet_item_date);
            tvWalletStatus = holder.getView().findViewById(R.id.tv_wallet_item_status);

            tvWalletTitle.setText(walletModelList.get(position - 1).getName());
            tvWalletDate.setText(walletModelList.get(position - 1).getCreation());

            if (walletModelList.get(position - 1).getType() == 1) {
                tvWalletStatus.setText("+" + walletModelList.get(position - 1).getMoney() +  mContext.getResources().getString(R.string.est));
                tvWalletStatus.setTextColor(mContext.getResources().getColor(R.color.text_FF_color_FA));
            } else if (walletModelList.get(position - 1).getType() == 2) {
                tvWalletStatus.setText("-" + walletModelList.get(position - 1).getMoney() + mContext.getResources().getString(R.string.est));
                tvWalletStatus.setTextColor(mContext.getResources().getColor(R.color.text_FF_color_38));
            }
        }
    }

    @Override
    protected int getContentView(int viewType) {
        if (viewType == HEADER_TYPE) {
            return R.layout.wallet_header;
        }
        return R.layout.item_wallet;
    }

    @Override
    public int getItemCount() {
        int walletListSize = walletModelList.size() + 1;
        return walletListSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }
}
