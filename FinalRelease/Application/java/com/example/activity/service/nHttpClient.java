package com.example.activity.service;

import android.content.Context;
import android.os.Looper;
import com.example.activity.constants.config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;


public class nHttpClient {

    private static String BASE_URL = config.HOST;

    private static AsyncHttpClient asyncHttpClient = new SyncHttpClient();
    private static AsyncHttpClient syncHttpClient = new AsyncHttpClient();

    /**
     * post请求带参数
     * @param myHttpClientRespenseHandler
     */
    private static void post(String url, RequestParams requestParams, MyHttpClientRespenseHandler myHttpClientRespenseHandler) {
        getClient().setTimeout(20000);
        getClient().post(BASE_URL+url, requestParams, myHttpClientRespenseHandler);
    }

    /**
     * post请求不带参数
     * @param myHttpClientRespenseHandler
     */
    private static void post(MyHttpClientRespenseHandler myHttpClientRespenseHandler) {
        syncHttpClient.setTimeout(20000);
        syncHttpClient.post(BASE_URL, myHttpClientRespenseHandler);
    }

    /**
     * get请求
     * @param myHttpClientRespenseHandler
     */
    private static void get( MyHttpClientRespenseHandler myHttpClientRespenseHandler) {
        getClient().setTimeout(20000);
        getClient().get(BASE_URL, myHttpClientRespenseHandler);
    }

    /**
     *
     * @return
     */
    private static AsyncHttpClient getClient() {
        if (Looper.myLooper() == null) {
            return syncHttpClient;
        } else {
            return asyncHttpClient;
        }
    }

    /**
     * 取消请求（根据context）
     */
    public static void cancelRequest(Context context) {
        getClient().cancelRequests(context, true);
    }

    /**
     * 获取数据
     */
    public static void getAll(String what ,MyHttpClientRespenseHandler myHttpClientRespenseHandler ) {
        RequestParams requestParams=new RequestParams();
        requestParams.put("key", what);

        post(myHttpClientRespenseHandler);
    }

    /**
     * 下载文件方式1
     *
     * @param url
     */
    public static void downloadFile(Context context, String url,
                                    BinaryHttpResponseHandler response) {
        getClient().get(context, url, response);
    }

    /**
     * 下载文件方式2
     *
     * @param url
     */
    public static void downLoadFile2(Context context,String url,FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler) {
        getClient().get(context, url, fileAsyncHttpResponseHandler);
    }
}
