package com.youcheng.publiclibrary.base.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author qiaozhenxin
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    private List<T> mList;
    private boolean mOpenAnimationEnable = true;
    private int mLastPosition = -1;

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener mOnRecyclerViewItemLongClickListener;

    public BaseRecyclerAdapter(Context context, Collection<T> collection) {
        setHasStableIds(false);
        this.mContext = context;
        this.mList = new ArrayList<>(collection);
    }

    private void addAnimate(BaseViewHolder holder, int postion) {
        if (mOpenAnimationEnable && mLastPosition < postion) {
            holder.itemView.setAlpha(0);
            holder.itemView.animate().alpha(1).start();
            mLastPosition = postion;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(getContentView(viewType), parent, false);

        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (mOnRecyclerViewItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnRecyclerViewItemClickListener.onItemClick(position);
                }
            });
        }
        if (mOnRecyclerViewItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnRecyclerViewItemLongClickListener.onItemLongClick(position);
                    return true;
                }
            });
        }
        covert(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public List<T> getmList() {
        return mList;
    }

    protected abstract int getContentView(int viewType);

    protected abstract void covert(BaseViewHolder holder, T data, int position);

    //    /**
//     * @param holder
//     * 添加动画
//     */
//    @Override
//    public void onViewAttachedToWindow(BaseViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        addAnimate(holder, holder.getLayoutPosition());
//    }

    public void setOpenAnimationEnable(boolean enabled) {
        this.mOpenAnimationEnable = enabled;
    }


    public BaseRecyclerAdapter<T> refresh(Collection<T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public BaseRecyclerAdapter<T> loadMore(Collection<T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }


    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }


    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView != null) {
            holder = (BaseViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        holder.setPosition(position);
        onBindViewHolder(holder, position);
        addAnimate(holder, position);
        return convertView;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.mOnRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(int position);
    }
}