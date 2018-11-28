package com.zoub.androiddemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoub.androiddemos.ipc.IpcDemosActivity;
import com.zoub.androiddemos.okhttp_demo.OkHttpDemoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    private static final String[] TITLE = {"IPC","OKHTTP"};
    private static final Class<?>[] ACTIVITY = {IpcDemosActivity.class,OkHttpDemoActivity.class};

    private List<ItemEntity> itemEntities = new ArrayList<>();
    private MenuAdapter mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        for (int i=0;i<TITLE.length;i++){
            ItemEntity entity = new ItemEntity();
            entity.itemName = TITLE[i];
            entity.itemClass = ACTIVITY[i];
            itemEntities.add(entity);
        }
    }

    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdpater = new MenuAdapter(R.layout.item_menu,itemEntities);
        rv.setAdapter(mAdpater);
        mAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(MainActivity.this,ACTIVITY[position]);
                startActivity(i);
            }
        });
    }
}
