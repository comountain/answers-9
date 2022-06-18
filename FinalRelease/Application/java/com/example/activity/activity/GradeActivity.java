package com.example.activity.activity;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.constants.config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class GradeActivity extends BaseActivity {
    int change = 0;
    String grade;
    int money = 0;
    int money_add = 0;
    int brave_tran = 0;
    int weak_tran = 0;
    String playertype;
    boolean ret = false;

    @Override
    int getLayoutId()
    {
        return R.layout.activity_grade;
    }

    @Override
    void getPreIntent()
    {
        playertype = ((MyApplication)getApplication()).getPlaytype();
        grade = getIntent().getExtras().get("grade").toString().trim();
        money = ((MyApplication)getApplication()).getMoney();
    }

    @Override
    void initView()
    {
        if(playertype.equals("match"))
        {
            HashMap<String, String> result = ((MyApplication) getApplication()).getGameresult();
            int num = result.size();
            int[] score_order = new int[num];
            String show = "";
            int i = 0;
            for (Map.Entry<String, String> entry : result.entrySet())
            {
                score_order[i++] = Integer.parseInt(entry.getValue());
            }
            Arrays.sort(score_order);
            int order_now = 1;
            String champion="";
            List<String> if_add = new ArrayList<>();
            for(i = num - 1; i >= 0; i--)
            {
                for (Map.Entry<String, String> entry : result.entrySet())
                {
                    if(entry.getValue().equals(score_order[i]+"") && !if_add.contains(entry.getKey()))
                    {
                        if(order_now == 1)
                            champion = entry.getKey();
                        show += "第" + order_now +"名： " + entry.getKey() + " "+ entry.getValue() +"分\n";
                        order_now ++;
                        if_add.add(entry.getKey());
                        break;
                    }
                }
            }
            TextView championToShow = (TextView) findViewById(R.id.grade_title);
            if(!((MyApplication)getApplication()).getNickname().equals(champion))
            {
                if(((MyApplication)getApplication()).getBrave() > 0)
                {
                    change = -10;
                    money_add = 5;
                    brave_tran = -1;
                    championToShow.setText("可惜，再加油吧.. 使用骑士之心，积分" + change + " 卷轴 +" + money_add);
                }
                else if(((MyApplication)getApplication()).getWeak() > 0)
                {
                    change = 0;
                    money_add = 5;
                    weak_tran = -1;
                    championToShow.setText("可惜，再加油吧.. 服下懦夫药水" + " 卷轴 +" + money_add);
                }
                else
                {
                    change = -5;
                    money_add = 5;
                    championToShow.setText("可惜，再加油吧.. 积分" + change + " 卷轴 +" + money_add);
                }
            }
            else {
                if(((MyApplication)getApplication()).getBrave() > 0)
                {
                    change = 20;
                    money_add = 20;
                    brave_tran = -1;
                    championToShow.setText("恭喜！你击败了所有人！使用了骑士之心，积分 +" + change + " 卷轴 +" + money_add);
                }
                else if(((MyApplication)getApplication()).getWeak() > 0)
                {
                    change = 0;
                    money_add = 5;
                    weak_tran = -1;
                    championToShow.setText("恭喜！你击败了所有人！服下懦夫药水" + " 卷轴 +" + money_add);
                }
                else
                {
                    change = 10;
                    money_add = 5;
                    championToShow.setText("恭喜！你击败了所有人！积分" + change + " 卷轴 +" + money_add);
                }
            }
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username",((MyApplication)getApplication()).getAccount());
            params.put("change",change);
            client.get(config.URL_UPDATE_SCORE,params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
            TextView gradeToShow = (TextView) findViewById(R.id.grade_grade);
            gradeToShow.setText(show);
        }
        else
        {
            if(Integer.parseInt(grade) > 130)
            {
                change = 5;
                money_add = 10;
            }
            else
            {
                money_add = 5;
            }
            TextView titleToShow = (TextView) findViewById(R.id.grade_title);
            String title = "本次练习得分： " + grade;
            if(change == 5)
                title = title + " 积分 +5" + " 卷轴 +" + money_add;
            titleToShow.setText(title);
            TextView gradeToShow = (TextView) findViewById(R.id.grade_grade);
            gradeToShow.setTextSize(12);
            String wrong_all = "";
                for(int i = 0; i < ((MyApplication)getApplication()).getWrong().size(); i++)
                {
                    String q = ((MyApplication)getApplication()).getWrong().get(i);
                    wrong_all = wrong_all + q + "\n";

                }
            ((MyApplication)getApplication()).resetWrong();
                gradeToShow.setText(wrong_all);
        }
        if(Integer.parseInt(((MyApplication)getApplication()).getGame_score()) + change < 0)
            ((MyApplication)getApplication()).setGame_score("0");
        else
            ((MyApplication)getApplication()).setGame_score(Integer.parseInt(((MyApplication)getApplication()).getGame_score()) + change+"");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",((MyApplication)getApplication()).getAccount());
        params.put("change",change);
        client.get(config.URL_UPDATE_SCORE,params, new JsonHttpResponseHandler() {
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
        ((MyApplication)getApplication()).setMoney(money_add + money);
        client = new AsyncHttpClient();
        params = new RequestParams();
        params.put("username",((MyApplication)getApplication()).getAccount());
        params.put("change", money_add);
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
        if(weak_tran == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"懦夫！",
                    Toast.LENGTH_SHORT);
            toast.show();
            ((MyApplication)getApplication()).setWeak(((MyApplication)getApplication()).getWeak() - 1);
            client = new AsyncHttpClient();
            params = new RequestParams();
            params.put("username",((MyApplication)getApplication()).getAccount());
            params.put("change", weak_tran);
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
        }
        if(brave_tran == -1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"勇者！",
                    Toast.LENGTH_SHORT);
            toast.show();
            ((MyApplication)getApplication()).setBrave(((MyApplication)getApplication()).getBrave() - 1);
            client = new AsyncHttpClient();
            params = new RequestParams();
            params.put("username",((MyApplication)getApplication()).getAccount());
            params.put("change", brave_tran);
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
        }
    }

    @OnClick({R.id.grade_return})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.grade_return:
                ret = true;
                ((MyApplication)getApplication()).resetClient();
                Intent intent1 = new Intent(GradeActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
        }
    }
}