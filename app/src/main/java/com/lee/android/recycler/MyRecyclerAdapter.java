package com.lee.android.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lee.android.R;
import com.lee.android.recycler.bean.ContentInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by android on 16-8-15.
 * RecyclerView 基础使用关键点同样有两点：继承重写 RecyclerView.Adapter 和 RecyclerView.ViewHolder
 * 设置布局管理器，控制布局效果
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<ContentInfo> mContentInfoList;

    //item view 的类型是否是小类型的
    private boolean mIsSmall = false;

    public MyRecyclerAdapter(List<ContentInfo> contentInfoList) {
        this.mContentInfoList = contentInfoList;
    }

    public void setSmallType(boolean isSmall){
        this.mIsSmall = isSmall;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*        LayoutInflater的作用类似于findViewById()。不同点是LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；而findViewById()是找xml布局文件下的具体widget控件(如Button、TextView等)。
        具体作用：
        1、对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入；
        2、对于一个已经载入的界面，就可以使用Activiyt.findViewById()方法来获得其中的界面元素。
        LayoutInflater 是一个抽象类，在文档中如下声明：
        public abstract class LayoutInflater extends Object
                获得 LayoutInflater 实例的三种方式
        1. LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater()
        2. LayoutInflater inflater = LayoutInflater.from(context);
        3. LayoutInflater inflater =  (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        其实，这三种方式本质是相同的*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = null;
        if (mIsSmall) {
            childView = inflater.inflate(R.layout.rc_small_card_layout,parent,false);
        } else {
            childView = inflater.inflate(R.layout.rc_card_layout,parent,false);
        }
        MyViewHolder viewHolder = new MyViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContentInfo contentInfo = mContentInfoList.get(position);
        holder.mPortraitView.setImageResource(contentInfo.getPortrait());
        holder.mNickNameView.setText(contentInfo.getNickName());
        holder.mMottoView.setText(contentInfo.getMotto());
    }

    @Override
    public int getItemCount() {
        if(mContentInfoList == null){
            return 0;
        }
        return mContentInfoList.size();
    }

    /*
    * 移动Item
    * */
    public void moveItem(int fromPosition,int toPosition){
        //数据的交换
        if (fromPosition < toPosition){
            for (int index=fromPosition; index<toPosition; index++)
                Collections.swap(mContentInfoList,index,index+1);
        } else {
            for (int index=fromPosition; index>toPosition;index--) {
                Collections.swap(mContentInfoList,index,index-1);
            }
        }

        notifyItemMoved(fromPosition,toPosition);
    }

    /*
    * 滑动Item
    * */
    public void removeItem(int position) {
        mContentInfoList.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mPortraitView;
        TextView mNickNameView;
        TextView mMottoView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mPortraitView = (ImageView) itemView.findViewById(R.id.iv_portrait);
            mNickNameView = (TextView) itemView.findViewById(R.id.tv_nickname);
            mMottoView = (TextView) itemView.findViewById(R.id.tv_motto);

        }
    }

}
