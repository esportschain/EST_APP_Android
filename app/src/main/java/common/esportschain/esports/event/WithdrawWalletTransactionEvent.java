package common.esportschain.esports.event;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/21
 */

public class WithdrawWalletTransactionEvent {
    private String walletTransaction;

    public WithdrawWalletTransactionEvent(String walletTransaction) {
        this.walletTransaction = walletTransaction;
    }

    public String getWalletTransaction() {
        return walletTransaction;
    }

    public void setWalletTransaction(String walletTransaction) {
        this.walletTransaction = walletTransaction;
    }
}
