package com.zoub.androiddemos.okhttp_demo;

import android.os.health.HealthStats;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.zoub.androiddemos.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

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
//        synchronousgetRequest(DEFAULT_URL);
//        postSteamRequest();
//        postFormRequest();
//        asynchronousGetRequest(DEFAULT_URL);
//        postStringRequest();
//        postMultipartRequest();
        interceptorTest();
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
    private void postStringRequest() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType,requestBody))
                .build();
        Call call = client.newCall(request);
        printRequestMessage(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                printResponseMessage(response,"Post方式提交String");
            }
        });
    }

    /**
     * Post方式提交流
     * 与提交String不同的地方在于RequestBody构建方式不同，
     */
    private void postSteamRequest() {

        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown; charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am Jdqm.");
            }
        };

        final Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                printResponseMessage(response,"post方式提交流");
            }
        });
    }

    /**
     * 打印相应结果
     * */
    private void printResponseMessage(Response response,String postType) throws IOException {
        LogUtils.d(response.protocol()+
                " "+response.code()+" "+response.message());

        Headers headers = response.headers();
        for (int i=0;i<headers.size();i++){
            LogUtils.d(headers.name(i)+":"+headers.value(i));
        }

        LogUtils.d(postType+"：onResponse:"+response.body().string());
    }

    private void printRequestMessage(Request request){
        Headers headers = request.headers();
        for (int i=0;i<headers.size();i++){
            LogUtils.d(headers.name(i)+":"+headers.value(i));
        }

        LogUtils.d(request.body().toString());

    }

    /**
     * Post方式提交文件
     */
    private void postFileRequest() {
        MediaType mediaType = MediaType.parse("text/x-mardown; charset=utf-8");
        File file = new File("test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType,file))
                .build();

        Call call = client.newCall(request);
        String s = call.request().toString();
        LogUtils.d("request："+s);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                printResponseMessage(response,"Post类型提交文件");
            }
        });
    }

    /**
     * Post方式提交表单
     *
     * 使用RequestBody的实体类FormBody来描述请求体
     */
    private void postFormRequest() {
        RequestBody requestBody = new FormBody.Builder()
                .add("search","China")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                printResponseMessage(response,"Post方式提交表单");
            }
        });
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String IMGUA_CLIENT_ID = "...";
    /**
     * Post方式提交分块请求
     */
    private void postMultipartRequest() {

        MultipartBody body = new MultipartBody.Builder("AaB03x")
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition","form-data;name=\"title\""),
                        RequestBody.create(null,"Square Logo")
                )
                .addPart(
                        Headers.of("Content-Disposition","form-data;name=\"image\""),
                        RequestBody.create(MEDIA_TYPE_PNG,new File("website/static/logo-square.png"))
                )
                .build();

        Request request = new Request.Builder()
                .header("Authorization","Client-ID"+IMGUA_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.d(response.body().string());
            }
        });
    }

    /**
     * 拦截器应用
     * */
    private void interceptorTest(){
        OkHttpClient interceptorClient = new OkHttpClient.Builder()
                .addInterceptor(new LogginIntercepter())
                .build();

        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .header("User-Agent","OkHttp Example")
                .build();

        Call call = interceptorClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body!=null){
                    LogUtils.d("onResponse: "+response.body().string());
                    body.close();
                }

            }
        });


    }

    public class  LogginIntercepter implements  Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long startTime = System.nanoTime();

            LogUtils.d(String.format("Sending request %s on %s%n%s",request.url()
            ,chain.connection(),request.headers()));

            Response response = chain.proceed(request);

            long endTime = System.nanoTime();
            LogUtils.d(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(),(endTime - startTime) / 1e6d,
                    response.headers()));

            LogUtils.d("被拦截了");
            return response;
        }
    }


    /**
     * 自定义DNS服务
     * */
    

}
