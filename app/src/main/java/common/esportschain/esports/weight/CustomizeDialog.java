package common.esportschain.esports.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import common.esportschain.esports.R;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/11
 * 自定义dialog
 */

public class CustomizeDialog extends Dialog {

    public LinearLayout customLayout;
    private Button btConfirm;
    private Button btCancel;
    private TextView tvDialogTitle;
    private TextView tvDialogContent;
    private View viewTitleContent;
    private View viewCinfirmCancel;
    private ImageView ivClose;

    public CustomizeDialog(Context context) {
        super(context, R.style.CustomizeDialog);
        setCustomDialog(R.layout.dialog_customize);
    }

    public CustomizeDialog(Context context, int ResId) {
        super(context, R.style.CustomizeDialog);
        setCustomDialog(ResId);
    }

    private void setCustomDialog(int ResId) {
        View mView = LayoutInflater.from(getContext()).inflate(ResId, null);
        btConfirm = mView.findViewById(R.id.bt_confirm);
        btCancel = mView.findViewById(R.id.bt_cancel);
        tvDialogTitle = mView.findViewById(R.id.tv_dialog_title);
        tvDialogContent = mView.findViewById(R.id.tv_dialog_content);
        viewTitleContent = mView.findViewById(R.id.title_content_view);
        viewCinfirmCancel = mView.findViewById(R.id.confirm_cancel_view);
        super.setContentView(mView);
        this.setCanceledOnTouchOutside(false);
    }

    /**
     * 显示单个button
     *
     * @param isSingle
     */
    public void setSingleButton(boolean isSingle) {
        viewCinfirmCancel.setVisibility(isSingle ? View.GONE : View.VISIBLE);
        btCancel.setVisibility(isSingle ? View.GONE : View.VISIBLE);
    }

    /**
     * 显示标题下面的分割线
     *
     * @return
     */
    public void setTitleLineShow(boolean isShow) {
        viewTitleContent.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示内容信息
     *
     * @param contentString
     */
    public void setContent(String contentString) {
        tvDialogContent.setVisibility(View.VISIBLE);
        this.tvDialogContent.setText(contentString);
    }

    public void setContent(int titleId) {
        tvDialogContent.setVisibility(View.VISIBLE);
        this.tvDialogContent.setText(titleId);
    }

    /**
     * 设置显示按钮文字
     */
    public void setBtConfirmText(String confirm) {
        btConfirm.setText(confirm);
    }
    public void setBtCancelText(String confirm) {
        btCancel.setText(confirm);
    }

    public TextView getTitleView() {
        return tvDialogTitle;
    }

    public void setTitleView(TextView title) {
        this.tvDialogTitle = title;
    }

    public String getTitle() {
        return tvDialogTitle.getText().toString();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvDialogTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        tvDialogTitle.setText(titleId);
    }

    public TextView getContentView() {
        return tvDialogContent;
    }

    public void setContentView(TextView content) {
        this.tvDialogContent = content;
    }

    /**
     * 获取自定义view
     *
     * @return
     */
    public View getCustomView() {
        return customLayout;
    }

    /**
     * 添加自定义view
     *
     * @param childView
     */
    public void setCustomtView(View childView) {
        if (customLayout != null) {
            customLayout.removeAllViews();
            customLayout.addView(childView);
        }
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        btConfirm.setOnClickListener(listener);
    }

    /**
     * 取消键监听器
     *
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener) {
        btCancel.setOnClickListener(listener);
    }

    /**
     * 关闭dialog
     *
     * @param listener
     */
    public void setOnCloseListener(View.OnClickListener listener) {
        ivClose.setOnClickListener(listener);
    }
}