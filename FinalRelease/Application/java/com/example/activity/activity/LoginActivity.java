package com.example.activity.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.constants.config;
import com.example.activity.utils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private Activity oContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        oContext = this;
        addActivity();
        //在打开页面时初始化共享存储对象spf  "users"表名
        //spf=getSharedPreferences("users", Context.MODE_PRIVATE);
    }

    public void addActivity() {
        ((MyApplication)getApplication()).addActivity_(oContext);// 调用myApplication的添加Activity方法
    }

    public void login(View view) {
        EditText accountET = findViewById(R.id.login_account);
        EditText passwordET = findViewById(R.id.login_password);
        String account = accountET.getText().toString();
        String password = passwordET.getText().toString();
        if (account == null || "".equals(account)) {
            showDialog("用户名不能为空!");
            return;
        }
        //判断密码是否为空
        if (password == null || "".equals(password)) {
            showDialog("密码不能为空!");
            return;
        }
        //验证登录
        loginensure(account, password);
    }
    public void back(View view){
        this.finish();
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("提示");
        builder.setMessage(msg);//提示信息
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);//点击空白区域不能被关掉  true能被关掉
        builder.show();//显示提示框
    }

    private boolean loginensure(String account, String password) {
        final boolean[] log = new boolean[1];
        LogUtils.e("initNet: 开始联网…………");
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.btnStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("验证中...");
        progressDialog.show();
        Button btn = (Button) findViewById(R.id._btn_message);
        String url = config.HOST;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", account);
        params.put("password", password);
        client.get(config.URL_LOGIN, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject obj=response;
                        String re = "false";
                        try {
                            re = obj.getString("log");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!re.equals("true"))
                        {
                            log[0] = true;
                            showDialog("用户名或密码错误！");
                            return;
                        }
                        String nickname = null;
                        String game_score=null;
                        String username = null;
                        int money = -1;
                        int id = -1;
                        int image_id = -1;
                        int weak = 0;
                        int brave = 0;
                        try {
                            nickname= obj.getString("nickname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            game_score=obj.getString("game_score");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            id= Integer.parseInt(obj.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            image_id= Integer.parseInt(obj.getString("image_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            username = obj.getString("username");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            money = Integer.parseInt(obj.getString("money"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            weak = Integer.parseInt(obj.getString("weak"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            brave = Integer.parseInt(obj.getString("brave"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ((MyApplication)getApplication()).setGame_score(game_score);
                        ((MyApplication)getApplication()).setNickname(nickname);
                        LogUtils.e("initNet: 联网结束…………");
                        showDialog("登录成功！");
                        ((MyApplication)getApplication()).createUser(id, image_id, Integer.parseInt(game_score),username,nickname);
                        ((MyApplication)getApplication()).setWeak(weak);
                        ((MyApplication)getApplication()).setBrave(brave);
                        ((MyApplication)getApplication()).setMoney(money);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        ((MyApplication)getApplication()).setUsername(account);
                        intent.putExtra("account", account);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        if(responseString.equals("Failure")) {
                            LogUtils.e("initNet: 联网结束…………");
                            log[0] = true;
                            showDialog("用户名或密码错误！");

                        }
                        Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        progressDialog.dismiss();
        return log[0];
    }

}
