package com.example.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.activity.R;
import com.example.activity.constants.config;
import com.example.activity.utils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
public class SignupActivity extends BaseActivity {
    @Override
    int getLayoutId() {
        return R.layout.activity_signup;
    }

    @OnClick({R.id.register_start,R.id.register_back})
    public void onViewClicked(View view){
        switch(view.getId()){
            case R.id.register_back:
                this.finish();
                break;
            case R.id.register_start:
                EditText accountEt = findViewById(R.id.account);
                EditText passwordEt = findViewById(R.id.password);
                EditText repwdEt = findViewById(R.id.repwd);
                EditText nicknameEt=findViewById(R.id.nickname);
                //获取用户名和密码
                String account =accountEt.getText().toString();
                String password =passwordEt.getText().toString();
                String repwd=repwdEt.getText().toString();
                String nickname=nicknameEt.getText().toString();
                //表单验证
                //判断用户名是否为空
                if (account==null && "".equals(account)){
                    //用户名为空
                    //Toast方法适用于不严重的提醒情况 content：上下文 text:提示的信息内容
                    //Toast.makeText(MainActivity.this, "用户姓名不能为空！", Toast.LENGTH_SHORT).show();
                    showDialog("用户名不能为空!");
                    return;//终止方法执行
                }
                //密码验证
                //判断密码是否为空
                if (password==null || "".equals(password)){
                    //判断密码不能为空
                    //Toast.makeText(MainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    showDialog("密码不能为空");
                    return;
                }
                //验证两次密码是否相同
                if (!password.equals(repwd)){
                    //Toast.makeText(MainActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    showDialog("两次密码不一致");
                    return;
                }
                if(nickname==null || "".equals(nickname)){
                    showDialog("昵称不能为空");
                }
                //用户名不为空
                //比较输入的用户名是否已经被注册存在
                ChecklsDataAleadylnDBorNot(account,password,nickname);
                //用户名已存在
                //Toast.makeText(MainActivity.this, "该用户名已存在！", Toast.LENGTH_SHORT).show();

                //终止方法执行

                // reg(account,password,nickname);
                //保存用户名和密码
                //  SharedPreferences.Editor editor=spf.edit();
                // editor.putString("account",account);//账号名
                // editor.putString("password",password);//密码
                //  editor.apply();//提交数据


        }
    }

    private void reg(String account, String password, String nickname) {
    }

    private boolean ChecklsDataAleadylnDBorNot(String account,String password,String nickname) {
        final boolean[] log = new boolean[1];
        LogUtils.e("initNet: 开始联网…………");
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.btnStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("验证中...");
        progressDialog.show();
        Button btn = (Button) findViewById(R.id._btn_message);
        String url = config.HOST;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", account);
        params.put("password",password);
        params.put("nickname",nickname);
        client.get(config.URL_SIGNUP, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        super.onSuccess(statusCode, headers, response);
                        if(response=="Succeed") {
                            LogUtils.e("initNet: 联网结束…………");
                            log[0] = true;
                        }
                        else{
                            log[0]=false;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        if(responseString.equals("Succeed")) {
                            LogUtils.e("initNet: 联网结束…………");
                            log[0] = true;
                            Toast.makeText(SignupActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                            //跳转到登录页面
                            Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            log[0]=false;
                            showDialog("用户名已存在");
                        }
                        Toast.makeText(SignupActivity.this, responseString, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        progressDialog.dismiss();
        return log[0];
    }


    public void showDialog(String msg){
        //1、创建AlertDialog.Builder对象
        AlertDialog.Builder builder=new AlertDialog.Builder(SignupActivity.this);
        //2、设置提示窗口相关信息
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
}

