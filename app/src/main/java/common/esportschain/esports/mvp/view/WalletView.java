package common.esportschain.esports.mvp.view;

import com.youcheng.publiclibrary.base.BaseView;

import common.esportschain.esports.mvp.model.NullModel;
import common.esportschain.esports.mvp.model.WalletModel;
import common.esportschain.esports.mvp.model.WalletTexModel;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 */

public interface WalletView extends BaseView {
    void getWalletData(WalletModel walletModel);

    void getWalletWithdrawWallet(NullModel nullModel);

    void getWalletTax(WalletTexModel walletTexModel);
}
