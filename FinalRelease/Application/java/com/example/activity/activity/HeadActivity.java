package com.example.activity.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.constants.config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class HeadActivity extends BaseActivity {
    private int[] icons={R.drawable.touxiang1,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.touxiang5,R.drawable.touxiang6};

    @Override
    int getLayoutId()
    {
        return R.layout.activity_head;
    }

    @OnClick({R.id.head_ch1, R.id.head_ch2, R.id.head_ch3, R.id.head_ch4, R.id.head_ch5, R.id.head_ch6, R.id.head_ret})
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.head_ret:
                this.finish();
                break;
            case R.id.head_ch2:
                updateHead(1);
                break;
            case R.id.head_ch3:
                updateHead(2);
                break;
            case R.id.head_ch4:
                updateHead(3);
                break;
            case R.id.head_ch5:
                updateHead(4);
                break;
            case R.id.head_ch6:
                updateHead(5);
                break;
            case R.id.head_ch1:
                updateHead(0);
                break;
        }
    }

    public void updateHead(int i)
    {
        ((MyApplication)getApplication()).setImageId(i);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",((MyApplication)getApplication()).getAccount());
        params.put("change",i);
        client.get(config.URL_UPDATE_HEAD,params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }
        );
        Toast toast = Toast.makeText(getApplicationContext(),"更改成功",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}