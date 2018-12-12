package com.zoub.androiddemos.recyclerview_demo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zoub.androiddemos.R;

import java.util.List;

/**
 * 项目名：     AndroidDemos
 * 包名：       com.zoub.androiddemos.recyclerview_demo
 * 文件名：     MyAdapter
 * 创建者：     Zoub
 * 创建时间：   2018/12/12
 * 描述：      TODO
 */

public class MyAdapter extends BaseQuickAdapter<RecyclerDemoItem,BaseViewHolder> {


    public MyAdapter(int layoutResId, @Nullable List<RecyclerDemoItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecyclerDemoItem item) {
        helper.setText(R.id.tv_title,item.title);
        helper.setText(R.id.tv_content,item.content);
    }
}
