package common.esportschain.esports.weight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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

public class WithdrawWalletDialog {

    Context context;
    AlertDialog dialog;
    private TextView tvTitle;
    private EditText etWalletMoney;
    private Button btNext;

    public WithdrawWalletDialog(Activity context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context, R.style.mDialogTheme).create();
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        attributes.width = (int) (dm.widthPixels * 1);
        attributes.gravity = Gravity.BOTTOM;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(attributes);
        window.setContentView(R.layout.dialog_withdraw_wallet);

        tvTitle = (TextView) window.findViewById(R.id.positiveButton);
        etWalletMoney = window.findViewById(R.id.et_wallet_address);
        btNext = window.findViewById(R.id.bt_wallet_next);
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

    public void cancel() {
        dialog.cancel();
    }

}
