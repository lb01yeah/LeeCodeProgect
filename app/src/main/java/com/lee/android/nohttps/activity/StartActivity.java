package com.lee.android.nohttps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.lee.android.R;
import com.lee.android.base.ToolBarActivity;
import com.lee.android.nohttps.adapter.RecyclerListMultiAdapter;
import com.lee.android.nohttps.entity.ListItem;
import com.lee.android.nohttps.nohttp.CallServer;
import com.lee.android.nohttps.util.OnItemClickListener;
import com.lee.android.view.ListRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ListRecyclerView recyclerView = (ListRecyclerView) findViewById(R.id.rv_start_activity);

        List<ListItem> listItems = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.activity_start_items);
        String[] titleDes = getResources().getStringArray(R.array.activity_start_items_des);

        for(int i=0;i<titles.length;i++){
            listItems.add(new ListItem(titles[i],titleDes[i]));
        }

        RecyclerListMultiAdapter listAdapter = new RecyclerListMultiAdapter(listItems,mItemClickListener);
        recyclerView.setAdapter(listAdapter);
    }

    /**
     *list item 的单击事件
     *
     * */
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            goItemPager(position);
        }
    };

    private void goItemPager(int position){
        Intent mIntent = null;
        switch (position) {
            case 0:// 最原始使用方法
                mIntent = new Intent(this, OriginalActivity.class);
                break;
        }

        if (mIntent != null)
            startActivity(mIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 程序退出时取消所有请求
        CallServer.getRequestInstance().cancelAll();

        // 程序退出时停止请求队列，如果这里的NoHttpRequestQueue是单例模式，NoHttp所在的进程没杀死而停止了队列，会导致再打开app不能请求网络
        CallServer.getRequestInstance().stopAll();

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
