package com.example.activity.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.constants.config;
import com.example.activity.utils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class InfoActivity extends BaseActivity {
    String Infouser;
    String Infonick;
    String Infoscore;
    String username;
    @Override
    int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    void getPreIntent() {
        username=((MyApplication)getApplication()).getNickname();
        Infouser="账号: "+((MyApplication)getApplication()).getAccount();
        Infonick=((MyApplication)getApplication()).getNickname();
        Infoscore="积分: "+((MyApplication)getApplication()).getGame_score();
    }

    @Override
    void initView() {
        TextView usertv=(TextView)findViewById(R.id.info_username);
        EditText nicktv=(EditText) findViewById(R.id.info_nick);
        TextView scoretv=(TextView)findViewById(R.id.info_gamescore);
        usertv.setText(Infouser);
        nicktv.setText(Infonick);
        scoretv.setText(Infoscore);
    }

    @OnClick({R.id.info_modify})
    public void onViewClicked(View v)
    {
        Intent intent = new Intent(InfoActivity.this, HeadActivity.class);
        startActivity(intent);
    }

    public void back(View view){
        Intent intent = new Intent(InfoActivity.this, MyActivity.class);
        startActivity(intent);
    }

}
