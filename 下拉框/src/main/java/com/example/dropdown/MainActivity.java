package com.example.dropdown;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_drop;
    private ListView mListView;
    private EditText mContent;
    private ArrayList<String> mList;
    private PopupWindow mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_drop = (ImageView) findViewById(R.id.iv_drop);
        mContent = (EditText) findViewById(R.id.et_content);
        iv_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDown();
            }
        });
        mListView = new ListView(this);
        //初始化数据
        mList = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            mList.add("aabbcc" + i);
        }
        mListView.setAdapter(new MyAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mContent.setText(mList.get(position));
                mPopup.dismiss();
            }
        });
    }

    private void showDropDown() {
        if (mPopup==null) {
            mPopup = new PopupWindow(mListView, mContent.getWidth(), 500 , true);
            mPopup.setBackgroundDrawable(new ColorDrawable());
        }
        mPopup.showAsDropDown(mContent);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.list_item, null);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                holder.ivDel = (ImageView) convertView.findViewById(R.id.iv_del);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(getItem(position));
            holder.ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    MyAdapter.this.notifyDataSetChanged();//刷新适配器
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvContent;
        public ImageView ivDel;
    }
}
