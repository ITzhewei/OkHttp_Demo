package com.example.john.okhttp_demo.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZheWei on 2016/9/8.
 * 对OkHttp进行封装的工具类
 */
public class OkManager {
    private OkHttpClient mClient;
    private volatile static OkManager manager = null;
    private final String TAG = OkManager.class.getSimpleName();//得到类名,并用来当做标志
    private Handler mHandler;
    //向服务器提交json数据类型
    private static final MediaType MediaType_JSON = MediaType.parse("application/json;charset=utf-8");
    //向服务器提交字符串
    private static final MediaType MediaType_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    private OkManager() {
        mClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    //采用单例模式来得到实例
    public static OkManager getInstance() {
        if (manager == null) {
            synchronized (OkManager.class) {
                if (manager == null) {
                    manager = new OkManager();
                }
            }
        }
        return manager;
    }

    /**
     * 同步请求数据--->在开发中不使用,因为会阻塞UI线程
     */
    public String syncgetByUrl(String url) {
        Request request = new Request.Builder().url(url).build();//构建一个request对象
        Response response = null;
        try {
            response = mClient.newCall(request).execute();//同步请求数据
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步请求数据--->在开发 中使用
     */
    public void asyncJsonStringByUrl(String url, final Func1 callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(response.body().string(), callBack);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 异步请求数据--->得到jsonObject
     */
    public void asyncJsonObjectByUrl(String url, final Func4 callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 返回字节数组
     */
    public void asyncByteByUrl(String url, final Func2 callBack) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessByteMethod(response.body().bytes(), callBack);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 返回Bitmap
     */
    public void asyncBitmapByUrl(String url, final Func3 callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    byte[] bytes = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    callBack.onResponse(bitmap);
                }
            }
        });
    }

    /**
     * 向服务器发送表单数据
     */
    public void sendComplexForm(String url, Map<String, String> params, final Func4 callBack) {
        FormBody.Builder form_builder = new FormBody.Builder();//表单对象,包含以input开始的对象,以html表单为主
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                form_builder.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = form_builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();//采用post方式进行提交表单数据
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 向服务器发送json字符串
     */
    public void sendStringByPost(String url, String content, final Func4 callBack) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType_MARKDOWN, content)).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 请求返回结果是json字符串
     */
    public void onSuccessJsonStringMethod(final String jsonValue, final Func1 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(jsonValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 请求返回数据是byte字节数组
     */
    public void onSuccessByteMethod(final byte[] data, final Func2 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 返回类型是json对象
     */
    public void onSuccessJsonObjectMethod(final String jsonValue, final Func4 callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONObject(jsonValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface Func1 {
        void onResponse(String result);
    }

    public interface Func2 {
        void onResponse(byte[] result);
    }

    public interface Func3 {
        void onResponse(Bitmap result);
    }

    public interface Func4 {
        void onResponse(JSONObject result);
    }
}
