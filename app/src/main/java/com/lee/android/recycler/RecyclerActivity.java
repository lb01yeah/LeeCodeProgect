package com.lee.android.recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lee.android.R;
import com.lee.android.recycler.bean.ContentInfo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private List<ContentInfo> mContentInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        initData();
    }

    private void initData() {
        mContentInfoList = new ArrayList<>();
        for (int counter =0;counter<6;counter++){
            ContentInfo conterInfo1 = new ContentInfo();
            conterInfo1.setNickName("nickName1");
            conterInfo1.setMotto("Hello,nickName1");
            mContentInfoList.add(conterInfo1);

            ContentInfo conterInfo2 = new ContentInfo();
            conterInfo2.setNickName("nickName2");
            conterInfo2.setMotto("Hello,nickName2");
            mContentInfoList.add(conterInfo2);

            ContentInfo conterInfo3 = new ContentInfo();
            conterInfo3.setNickName("nickName3");
            conterInfo3.setMotto("Hello,nickName3");
            mContentInfoList.add(conterInfo3);
        }
    }

    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapter = new MyRecyclerAdapter(mContentInfoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //ItemTouchHelper 用于实现RecyclerView Item 拖拽效果的类
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback(){

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return super.isItemViewSwipeEnabled();
            }
        });

    }

}
