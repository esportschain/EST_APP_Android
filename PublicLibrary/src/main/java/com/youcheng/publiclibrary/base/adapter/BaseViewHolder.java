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
    }

    public View getView() {
        return mView;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

}
