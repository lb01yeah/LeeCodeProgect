package com.lee.android.recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

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
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_recyclerview_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.linear) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecyclerActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerAdapter.setSmallType(false);
            mRecyclerView.setAdapter(mRecyclerAdapter);

        } else if (itemId == R.id.grid) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerActivity.this, 3);
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerAdapter.setSmallType(true);
            mRecyclerView.setAdapter(mRecyclerAdapter);

        } else if (itemId == R.id.staggered) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            mRecyclerAdapter.setSmallType(true);
            mRecyclerView.setAdapter(mRecyclerAdapter);

        } else if (itemId == R.id.insert_item) {
            mRecyclerAdapter.notifyItemInserted(2);

        } else if (itemId == R.id.delete_item) {
            mRecyclerAdapter.notifyItemChanged(3);

        } else if (itemId == R.id.update_item) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();//获取第一个完全可见的Item的位置
                if (RecyclerView.NO_POSITION != position) {
                    ContentInfo authorInfo = mContentInfoList.get(position);
                    authorInfo.setNickName("Android");
                    authorInfo.setMotto("I am Android Man!");
                    authorInfo.setPortrait(R.mipmap.ic_launcher);
                    mRecyclerAdapter.notifyItemChanged(position);
                }

            }

        }
        return super.onOptionsItemSelected(item);
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
                //actionState : action状态类型，有三类 ACTION_STATE_DRAG （拖曳），ACTION_STATE_SWIPE（滑动），ACTION_STATE_IDLE（静止）
                int dragFlags = makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,ItemTouchHelper.UP|ItemTouchHelper.DOWN
                |ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT); //支持上下左右的拖拽
                int swipFlags = makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);//支持左右的滑动

                return makeMovementFlags(dragFlags,swipFlags); //返回0，表示不支持拖拽和滑动

            }

            /**
             * @param recyclerView attach的RecyclerView
             * @param viewHolder 拖动的Item
             * @param target 放置Item的目标位置
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition= viewHolder.getAdapterPosition(); //要拖拽的位置
                int toPosition = target.getAdapterPosition(); //要放置的目标位置
                mRecyclerAdapter.moveItem(fromPosition,toPosition);
                return true;
            }

            /**
             * @param viewHolder 滑动移除的Item
             * @param direction
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();//获取要滑动删除的Item的位置
                mRecyclerAdapter.removeItem(position);

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled(); //不支持长按拖拽效果直接返回false
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return super.isItemViewSwipeEnabled(); //不支持滑动效果直接返回false
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

}
