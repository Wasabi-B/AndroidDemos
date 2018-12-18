package com.zoub.androiddemos.recyclerview_demo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.zoub.androiddemos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.CrashUtils.init;

public class RecyclerViewDemo extends AppCompatActivity {

    private static final String TAG = "RecyclerViewDemo";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MyAdapter myAdapter;

    private List<RecyclerDemoItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        for (int i = 0; i < 50; i++) {
            RecyclerDemoItem item = new RecyclerDemoItem();
            item.title = "我是标题" + i;
            Random random = new Random();
            int r = random.nextInt(10);
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < r; j++) {
                builder.append("我是内容");
            }
            item.content = builder.toString();
            mData.add(item);
        }


        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(R.layout.item_recycler_demo, mData);
        recyclerView.setAdapter(myAdapter);

        final int[] distance = {0};
        final List<Integer> totalDistance = new ArrayList<>();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager layoutManager1 = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] a = null;
                int[] b = null;
                //获取每列完整的显示的第一排item的位置数组
//                int[] firstCompletelyVisibleItemPositions = layoutManager1.findFirstCompletelyVisibleItemPositions(a);
                //获取每列显示在第一排item的位置数组
                int[] firstVisibleItemPositions = layoutManager1.findFirstVisibleItemPositions(b);
                //第一列item的位置
                int firstVisibleItemPosition = firstVisibleItemPositions[0];
                int spanCount = layoutManager1.getSpanCount();
                View view1 = layoutManager1.findViewByPosition(firstVisibleItemPosition);
                if (firstVisibleItemPosition == 0) {
                    distance[0] = view1.getTop();
                } else {
                    int i = firstVisibleItemPosition / spanCount;
                    for (int x = 0; x < i; x++) {
                        int lastPos = firstVisibleItemPosition - (x + 1) * spanCount;
                        View lastView = layoutManager1.findViewByPosition(lastPos);
                        int height = lastView.getHeight();
                        distance[0] += height;
                    }
                }
                View curView = layoutManager1.findViewByPosition(firstVisibleItemPosition);
                if (curView.getTop() < 0 && firstVisibleItemPosition != 0) {
                    distance[0] += curView.getTop();
                }

//                Log.d(TAG, "onScrolled: vip长度：" + firstVisibleItemPositions.length);
//                Log.d(TAG, "onScrolled: 第一列item的位置：" + firstVisibleItemPosition);
                Log.d(TAG, "onScrolled: 第一列item的左上角的纵坐标：" + curView.getTop());
                Log.d(TAG, "onScrolled: 滑动的距离：" + distance[0]);
//                Log.d(TAG, "onScrolled:当前位置：" + firstVisibleItemPosition + "老的位置==" + oldP);


            }
        });


    }
}
