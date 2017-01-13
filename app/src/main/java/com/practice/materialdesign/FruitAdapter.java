package com.practice.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者：忆 on 2017/1/12 20:42
 * 功能：
 */

public class FruitAdapter extends RecyclerView.Adapter <FruitAdapter.ViewHolder>{

    private Context mContext;
    private List<Fruit> mFruitList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_img);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    /**
     * 封装构造方法
     * @param fruitList
     */
    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;
    }

    /**
     * 创建一个viewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext = parent.getContext();
        }
            View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(positon);
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImgId());
                mContext.startActivity(intent);
            }
        });
        return  holder;
    }

    /**
     * 绑定viewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(FruitAdapter.ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImgId()).into(holder.fruitImage);
    }

    /**
     * 获取总数目
     * @return
     */
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
