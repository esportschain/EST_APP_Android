package com.youcheng.publiclibrary.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author qiaozhenxin
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{


    private View mView;
    private int mPosition = -1;
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mView = itemView;

        /*
//         * 设置水波纹背景
//         */
//        if (itemView.getBackground() == null) {
//            TypedValue typedValue = new TypedValue();
//            Resources.Theme theme = itemView.getContext().getTheme();
//            int top = itemView.getPaddingTop();
//            int bottom = itemView.getPaddingBottom();
//            int left = itemView.getPaddingLeft();
//            int right = itemView.getPaddingRight();
//            if (theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
//                itemView.setBackgroundResource(typedValue.resourceId);
//            }
//            itemView.setPadding(left, top, right, bottom);
//        }
    }

    public View getView() {
        return mView;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}
