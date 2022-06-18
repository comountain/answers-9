package com.example.activity.activity;


import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.constants.config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.HashMap;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MarketActivity extends BaseActivity {
    @Override
    int getLayoutId(){return R.layout.activity_market;}

    @Override
    void initView()
    {
        ImageView money1 = (ImageView) findViewById(R.id.money1);
        money1.bringToFront();
    }

    @OnClick({R.id.market_ret, R.id.market_brave, R.id.market_weak, R.id.market_brave_buy, R.id.market_weak_buy})
    public void onViewClicked(View v)
    {
        TextView show = (TextView)findViewById(R.id.market_show);
        switch (v.getId())
        {
            case R.id.market_ret:
                Intent intent1 = new Intent(MarketActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.market_brave:
                show.setText("骑士之心，能让你赢得比赛后获取更多积分,失败后减少更多积分。谨慎使用。");
                break;
            case R.id.market_weak:
                show.setText("懦夫药水，使用后比赛胜负不再影响积分，专为菜鸟设计。也许适合你？");
                break;
            case R.id.market_brave_buy:
                AlertDialog textTips = new AlertDialog.Builder(MarketActivity.this)
                        .setTitle("是否花费500卷轴购买？")
                        .setMessage("购买后立即生效，持续三场比赛（匹配模式和好友对战模式）")
                        .setPositiveButton("购买", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int money = ((MyApplication)getApplication()).getMoney();
                                if(money < 500)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(),"卷轴不足，参与游戏获取更多卷轴吧",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                                else if(((MyApplication)getApplication()).getWeak() > 0)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(),"还在懦夫药水效果下，不可购买",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                                else
                                {
                                    int brave = ((MyApplication)getApplication()).getBrave();
                                    ((MyApplication)getApplication()).setBrave(brave + 3);
                                    ((MyApplication)getApplication()).setMoney(money - 500);
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    RequestParams params = new RequestParams();
                                    params.put("username",((MyApplication)getApplication()).getAccount());
                                    params.put("change", -500);
                                    client.get(config.URL_UPDATE_MONEY,params, new JsonHttpResponseHandler() {
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
                                    client = new AsyncHttpClient();
                                    params = new RequestParams();
                                    params.put("username",((MyApplication)getApplication()).getAccount());
                                    params.put("change", 3);
                                    client.get(config.URL_UPDATE_BRAVE,params, new JsonHttpResponseHandler() {
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
                                    Toast toast = Toast.makeText(getApplicationContext(),"购买成功",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        })
                        .setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.market_weak_buy:
                AlertDialog a = new AlertDialog.Builder(MarketActivity.this)
                        .setTitle("是否花费500卷轴购买？")
                        .setMessage("购买后立即生效，持续三场比赛（匹配模式和好友对战模式）")
                        .setPositiveButton("购买", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int money = ((MyApplication)getApplication()).getMoney();
                                if(money < 500)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(),"卷轴不足，参与游戏获取更多卷轴吧",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                                else if(((MyApplication)getApplication()).getBrave() > 0)
                                {
                                    Toast toast = Toast.makeText(getApplicationContext(),"还在骑士之心效果下，不可购买",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                                else
                                {
                                    int weak = ((MyApplication)getApplication()).getWeak();
                                    ((MyApplication)getApplication()).setWeak(weak + 3);
                                    ((MyApplication)getApplication()).setMoney(money - 500);
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    RequestParams params = new RequestParams();
                                    params.put("username",((MyApplication)getApplication()).getAccount());
                                    params.put("change", -500);
                                    client.get(config.URL_UPDATE_MONEY,params, new JsonHttpResponseHandler() {
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
                                    client = new AsyncHttpClient();
                                    params = new RequestParams();
                                    params.put("username",((MyApplication)getApplication()).getAccount());
                                    params.put("change", 3);
                                    client.get(config.URL_UPDATE_WEAK,params, new JsonHttpResponseHandler() {
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
                                    Toast toast = Toast.makeText(getApplicationContext(),"购买成功",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        })
                        .setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

}