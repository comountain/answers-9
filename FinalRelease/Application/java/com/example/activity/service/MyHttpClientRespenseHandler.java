package com.example.activity.service;
import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public abstract class MyHttpClientRespenseHandler extends JsonHttpResponseHandler {
    private String TAG = "respensehandler";
    private Activity context;

    public MyHttpClientRespenseHandler(Activity context) {
        this.context = context;
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.i(TAG, "请求成功");
        try {
            super.onSuccess(statusCode, headers, response);
            List<JSONObject> turnResponse = new ArrayList<>();
            for(int i = 0; i < response.length(); i++)
                turnResponse.add(response.getJSONObject(i));
            success(statusCode, headers, turnResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, String s, Throwable throwable) {
        Log.i(TAG, "请求失败");
        faile();
    }


    public abstract void success(int statusCode, Header[] headers, List<JSONObject> turnResponse);
    public abstract void faile();
}
