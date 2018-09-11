package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;

/**
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public interface WalletView extends BaseView {
    /**
     *
     * 获取钱包详情数据
     * @param walletModel
     */
    void getWalletData(WalletModel walletModel);

    /**
     * 提现
     * @param nullModel
     */
    void getWalletWithdrawWallet(NullModel nullModel);

    /**
     * 钱包手续费
     * @param walletTexModel
     */
    void getWalletTax(WalletTexModel walletTexModel);
}
