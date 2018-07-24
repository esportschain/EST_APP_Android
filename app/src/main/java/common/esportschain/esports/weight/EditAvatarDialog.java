package common.esportschain.esports.weight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import common.esportschain.esports.R;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/14
 */

public class EditAvatarDialog {

    Context context;
    AlertDialog dialog;
    private TextView cameraButton, pickerButton, cancelButton;

    public EditAvatarDialog(Activity context) {
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
        window.setContentView(R.layout.dialog_choose_avatar);
        cameraButton = (TextView) window.findViewById(R.id.positiveButton);
        pickerButton = (TextView) window.findViewById(R.id.neutralButton);
        cancelButton = (TextView) window.findViewById(R.id.negativeButton);
    }

    public void setCameraButton(final View.OnClickListener listener) {
        cameraButton.setOnClickListener(listener);
    }

    public void setPickerButton(final View.OnClickListener listener) {
        pickerButton.setOnClickListener(listener);
    }

    public void setCancelButton(final View.OnClickListener listener) {
        cancelButton.setOnClickListener(listener);
    }

    public void cancel() {
        dialog.cancel();
    }
}
