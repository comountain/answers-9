package com.example.activity.activity;

import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Chronometer;
import android.app.ProgressDialog;
import android.content.Intent;


import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.bean.QuestBean;
import com.example.activity.message.AnswerMessage;
import com.example.activity.message.CompeteMessage;
import com.example.activity.message.MatchMessage;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.example.activity.service.FragmentCallBack;


import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;


import butterknife.BindView;

import com.example.activity.constants.config;
import com.example.activity.utils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.example.activity.fragment.AnswerFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Handler;


public class AnswerActivity extends BaseActivity implements Chronometer.OnChronometerTickListener,FragmentCallBack {
    @BindView(R.id.vp_answer)
    volatile ViewPager vp_answer;

    @BindView(R.id._chro_exam)
    Chronometer chronometer;

    private volatile ArrayList<AnswerFragment> fragmentList;
    private volatile List<QuestBean> message;
    private String field;
    private String username;
    private String myanswer = "null";
    private String playertype;
    private String[] playername;
    private int wrapper_order=0;
    private int playernum;
    private int myscore = 0;
    private int second = 20;
    private boolean ifSub = false;
    private volatile int nowpager = 0;
    private TextView[] score;
    private TextView[] name;
    private ImageView[] player;
    private HashMap<String, Integer> UIhelper = new HashMap<>();
    private int[] icons={R.drawable.touxiang1,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.touxiang5,R.drawable.touxiang6};

    @Override
    int getLayoutId()
    {
        return R.layout.activity_answer;
    }

    @Override
    void getPreIntent()
    {
        username = ((MyApplication)getApplication()).getNickname();
        playertype = ((MyApplication)getApplication()).getPlaytype();
        playername = ((MyApplication)getApplication()).getPlayername();
        playernum = Integer.parseInt(getIntent().getExtras().get("num").toString().trim());
        field = getIntent().getExtras().get("field").toString().trim();
        if(playertype.equals("match"))
            wrapper_order = ((MyApplication)getApplication()).getWrapperOrder();
        else
        {
            Random random = new Random();
            wrapper_order = random.nextInt(config.maxWrapperSize);
        }
        score = new TextView[4];
        name = new TextView[4];
        player = new ImageView[4];
        score[1] = (TextView) findViewById(R.id.answer_score2);
        score[2] = (TextView) findViewById(R.id.answer_score3);
        score[3] = (TextView) findViewById(R.id.answer_score4);
        score[0] = (TextView) findViewById(R.id.answer_score1);
        name[0] = (TextView) findViewById(R.id.answer_name1);
        name[1] = (TextView) findViewById(R.id.answer_name2);
        name[2] = (TextView) findViewById(R.id.answer_name3);
        name[3] = (TextView) findViewById(R.id.answer_name4);
        player[0] = (ImageView) findViewById(R.id.answer_player1);
        player[1] = (ImageView) findViewById(R.id.answer_player2);
        player[2] = (ImageView) findViewById(R.id.answer_player3);
        player[3] = (ImageView) findViewById(R.id.answer_player4);
        int num = playername.length;
        for(int i = 0; i < num; i++)
            UIhelper.put(playername[i], i);
        turnstart();
    }

    public void turnstart()
    {
        int num = playername.length;
        switch (num)
        {
            case 1:
                name[0].setText(playername[0]);
                name[1].setVisibility(View.INVISIBLE);
                name[2].setVisibility(View.INVISIBLE);
                name[3].setVisibility(View.INVISIBLE);
                score[1].setVisibility(View.INVISIBLE);
                score[2].setVisibility(View.INVISIBLE);
                score[3].setVisibility(View.INVISIBLE);
                player[1].setVisibility(View.INVISIBLE);
                player[2].setVisibility(View.INVISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 2:
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setVisibility(View.INVISIBLE);
                name[3].setVisibility(View.INVISIBLE);
                score[2].setVisibility(View.INVISIBLE);
                score[3].setVisibility(View.INVISIBLE);
                player[2].setVisibility(View.INVISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 3:
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setText(playername[2]);
                name[3].setVisibility(View.INVISIBLE);
                score[3].setVisibility(View.INVISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 4:
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setText(playername[2]);
                name[3].setText(playername[3]);
        }
    }

    public void renew(HashMap<String, String> allscore)
    {
        int num = playername.length;
        for(int i = 0; i < num; i++)
        {
            String this_score = allscore.get(playername[i]);
            score[UIhelper.get(playername[i])].setText(this_score);
        }
    }

    @Override
    void initView() {
        setChronometer();
        initNet(field);
        vp_answer.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public void sendAnswer(String s){
        myanswer = s;
    }


    private void setChronometer() {
        chronometer.start();
        chronometer.setOnChronometerTickListener(this);
    }


    @Override
     public void onChronometerTick(Chronometer chronometer) {
        Button b = (Button)findViewById(R.id._btn_tool);
        b.setText(second+"");
        second--;
        int sub = ((MyApplication)getApplication()).getSubNow();
        if(sub == playernum)
        {
            ((MyApplication)getApplication()).restSubNow();
            if(nowpager == fragmentList.size()-1)
            {
                renew(((MyApplication)getApplication()).getGameresult());
                Thread th = new Thread(){
                    @Override
                    public void run()
                    {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((MyApplication)getApplication()).sendMessage(new CompeteMessage(username, myscore, 1));
                        Intent intent1 = new Intent(AnswerActivity.this,GradeActivity.class);
                        intent1.putExtra("grade",myscore+"");
                        intent1.putExtra("num",playertype);
                        startActivity(intent1);
                    }
                };
                th.start();
            }
            vp_answer.setCurrentItem(++nowpager);
            ifSub = false;
            Button subm = (Button) findViewById(R.id._btn_submit);
            subm.setText("提交答案");
            renew(((MyApplication)getApplication()).getGameresult());
            second = 20;
        }
        if (second == 0) {
            ((MyApplication)getApplication()).restSubNow();
            if(nowpager == fragmentList.size()-1)
            {
                renew(((MyApplication)getApplication()).getGameresult());
                Thread th = new Thread(){
                    @Override
                    public void run()
                    {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((MyApplication)getApplication()).sendMessage(new CompeteMessage(username, myscore, 1));
                        Intent intent1 = new Intent(AnswerActivity.this,GradeActivity.class);
                        intent1.putExtra("grade",myscore+"");
                        intent1.putExtra("num",playertype);
                        startActivity(intent1);
                    }
                };
                th.start();
            }
            else
            {
                vp_answer.setCurrentItem(++nowpager);
                ifSub = false;
                Button subm = (Button) findViewById(R.id._btn_submit);
                subm.setText("提交答案");
                renew(((MyApplication) getApplication()).getGameresult());
                second = 20;
            }
        }
    }


    private void initNet(String field)
    {
        fragmentList = new ArrayList<>();
        message = new ArrayList<>();
        LogUtils.e("initNet: 开始联网…………");
        final ProgressDialog progressDialog = new ProgressDialog(AnswerActivity.this, R.style.btnStyle);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("获取题目中...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("order",wrapper_order);
        params.put("field",field);
        client.get(config.URL_GET_QUESTION,params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                message.add(new QuestBean(
                                        i,
                                        obj.getString("type"),
                                        obj.getString("title"),
                                        obj.getString("optionA"),
                                        obj.getString("optionB"),
                                        obj.getString("optionC"),
                                        obj.getString("optionD"),
                                        obj.getString("answer"),
                                        obj.getString("field")
                                ));
                                fragmentList.add(new AnswerFragment(message.get(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        vp_answer.setAdapter(new MainAdapter(getSupportFragmentManager()));

                        LogUtils.e("initNet: 联网结束…………");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(AnswerActivity.this, "sbl", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        progressDialog.dismiss();

    }

    class MainAdapter extends FragmentPagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @OnClick({R.id._btn_submit,R.id._btn_tool,R.id._btn_message})
    public void onViewClicked(View v)
    {
        switch (v.getId())
        {
            case R.id._btn_submit:
                if(ifSub)
                    break;
                ifSub = true;
                Button subm = (Button) findViewById(R.id._btn_submit);
                subm.setText("已提交");
                int else_score = 0;
                if(second >= 17)
                    else_score = 10;
                else if(second >= 10)
                    else_score = 5;
                if(myanswer == "null")
                {
                    ifSub = false;
                    subm.setText("提交答案");
                    return;
                }
                if(myanswer.equals(message.get(nowpager).getAnswer()))
                {
                    int base_score = 15;
                    if(nowpager >= 2)
                        base_score += 5;
                    if(nowpager == 6)
                        base_score += 10;
                    myscore += base_score + else_score;
                }
                else
                {
                    myscore -= 10;
                    Button b = (Button) findViewById(R.id._btn_tool);
                    if(!playertype.equals("match"))
                    {
                        String wrong_all = "";
                        QuestBean q = message.get(nowpager);
                        wrong_all = wrong_all + q.getTitle() + "\n";
                        if(!q.getQ_type().equals("judge"))
                            wrong_all = wrong_all + "A：" + q.getOptionA() + " B: " + q.getOptionB() + " C: " + q.getOptionC() + " D: " + q.getOptionD() + "\n";
                        wrong_all = wrong_all + "你的回答是： " + q.getMyanswer() + " 正确答案是： " + q.getAnswer() + "\n";
                        ((MyApplication)getApplication()).addWrong(wrong_all);
                    }
                }
                myanswer = "null";
                if(nowpager == fragmentList.size() - 1) {
                    if(!playertype.equals("match"))
                    {
                        getGrade();
                        return;
                    }
                    ((MyApplication)getApplication()).sendMessage(new CompeteMessage(username, myscore, 0));
                    return;
                }
                ((MyApplication)getApplication()).setScore(myscore+"");
                if(!playertype.equals("match"))
                {
                    vp_answer.setCurrentItem(++nowpager);
                    second = 20;
                    ifSub = false;
                    subm.setText("提交答案");
                    renew(((MyApplication)getApplication()).getGameresult());
                }
                else
                {
                    ((MyApplication)getApplication()).sendMessage(new CompeteMessage(username, myscore, 0));
                }
            case R.id._btn_message:
            case R.id._btn_tool:
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            nowpager = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void getGrade() {
        ((MyApplication)getApplication()).sendMessage(new CompeteMessage(username, myscore, 1));
        Intent intent1 = new Intent(AnswerActivity.this,GradeActivity.class);
        intent1.putExtra("grade",myscore+"");
        intent1.putExtra("num",playertype);
        startActivity(intent1);
    }

}

