package com.zoub.androiddemos;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 项目名：     AndroidDemos
 * 包名：       com.zoub.androiddemos
 * 文件名：     MenuAdapter
 * 创建者：     xiaob
 * 创建时间：   2018/11/4
 * 描述：      TODO
 */
public class MenuAdapter extends BaseQuickAdapter<ItemEntity,BaseViewHolder> {

    public MenuAdapter(int layoutResId, @Nullable List<ItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemEntity item) {

    }
}
