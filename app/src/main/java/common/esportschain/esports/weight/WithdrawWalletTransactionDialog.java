package common.esportschain.esports.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import common.esportschain.esports.R;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/21
 */

public class WithdrawWalletTransactionDialog extends Dialog {

    private TextView tvAmount;
    private TextView tvAddress;
    private LinearLayout llBackLeft;
    private LinearLayout llClose;
    private Button btConfirm;

    public WithdrawWalletTransactionDialog(Context context) {
        super(context, R.style.CustomizeDialog);
        setCustomDialog(R.layout.dialog_transaction_wallet);
    }

    public WithdrawWalletTransactionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WithdrawWalletTransactionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setCustomDialog(int ResId) {
        View mView = LayoutInflater.from(getContext()).inflate(ResId, null);
        tvAmount = mView.findViewById(R.id.tv_wallet_dialog_amount);
        tvAddress = mView.findViewById(R.id.tv_wallet_dialog_address);
        llBackLeft = mView.findViewById(R.id.ll_dialog_back);
        llClose = mView.findViewById(R.id.ll_dialog_close);
        btConfirm = mView.findViewById(R.id.bt_wallet_confirm);
        super.setContentView(mView);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public void setAmount(String amount) {
        tvAmount.setText(amount);
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    public void setonClickClose(View.OnClickListener listener) {
        llClose.setOnClickListener(listener);
    }

    public void setBackLeft(View.OnClickListener listener) {
        llBackLeft.setOnClickListener(listener);
    }

    public void setConfirm(View.OnClickListener listener) {
        btConfirm.setOnClickListener(listener);
    }
}
