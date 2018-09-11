package common.esportschain.esports.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import common.esportschain.esports.R;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/21
 */

public class WithdrawWalletAddressDialog extends Dialog {

    private TextView tvTitle;
    private EditText etWalletMoney;
    private Button btNext;

    public WithdrawWalletAddressDialog(Context context) {
        super(context, R.style.CustomizeDialog);
        setCustomDialog(R.layout.dialog_withdraw_wallet);
    }

    public WithdrawWalletAddressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WithdrawWalletAddressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setCustomDialog(int ResId) {
        View mView = LayoutInflater.from(getContext()).inflate(ResId, null);
        tvTitle = mView.findViewById(R.id.positiveButton);
        etWalletMoney = mView.findViewById(R.id.et_wallet_address);
        btNext = mView.findViewById(R.id.bt_wallet_next);
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

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public void setEtWalletMoney(EditText etWalletMoney) {
        this.etWalletMoney = etWalletMoney;
    }

    public Button getBtNext() {
        return btNext;
    }

    public void setBtNext(Button btNext) {
         btNext = btNext;
    }

    public void setWalletTitle(String walletTitle) {
         tvTitle.setText(walletTitle);
    }

    public void setEtWalletMoney(String walletMoney) {
         etWalletMoney.setText(walletMoney);
    }

    public String getEtWalletMoney() {
        if (etWalletMoney.getText() != null) {
            return etWalletMoney.getText().toString();
        }
        return "";
    }

    public void setOnNextClickListener(View.OnClickListener listener) {
         btNext.setOnClickListener(listener);
    }
}
