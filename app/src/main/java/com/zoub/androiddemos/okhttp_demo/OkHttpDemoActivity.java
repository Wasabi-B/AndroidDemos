package com.zoub.androiddemos.okhttp_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.zoub.androiddemos.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpDemoActivity extends AppCompatActivity {

    private OkHttpClient client;
    private static final String DEFAULT_URL = "http:www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_demo);
        initOkHttp();
    }

    private void initOkHttp() {
        client = new OkHttpClient();

    }

    public void execute(View view) throws IOException {
        synchronousgetRequest(DEFAULT_URL);
    }

    /**
     * 异步Get请求
     * -初始化OkhttpClient
     * -创建request对象
     * -通过Okhttpclient和Request对象构建Call
     * -call#enqueue提交异步请求
     */
    private void asynchronousGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.d("异步Get请求,onResponse:" + response.body().string());
            }
        });
    }

    /**
     * 同步Get请求
     */
    private void synchronousgetRequest(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        final Call call = client.newCall(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response execute = call.execute();
                    LogUtils.d("同步Get请求："+execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Post方式提交String
     */
    private void postStringRequest(String params) {

    }

    /**
     * Post方式提交流
     */
    private void postSteamRequest() {

    }

    /**
     * Post方式提交文件
     */
    private void postFileRequest() {

    }

    /**
     * Post方式提交表单
     */
    private void postFormRequest() {

    }

    /**
     * Post方式提交分块请求
     */
    private void postMultipartRequest() {

    }

    /**
     * 拦截器应用
     * */

    /**
     * 自定义DNS服务
     * */


}
