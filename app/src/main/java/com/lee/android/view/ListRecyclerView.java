package com.lee.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by android on 16-7-1.
 * 用于全局的基础列表模板
 */
public class ListRecyclerView extends RecyclerView{
    public ListRecyclerView(Context context) {
        super(context);
    }

    public ListRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setItemAnimator(null);// Fix update progress falsh.
        addItemDecoration(new ListRecyclerCardItemDecoration());
        setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
