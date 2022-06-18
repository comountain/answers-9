package com.example.activity.activity;

import android.graphics.Color;
import android.view.View;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.example.activity.MyApplication;
import com.example.activity.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    int weak = 0;
    int brave = 0;
    private String game_score;
    private String nickname;
    private String rank;
    private int[] icons={R.drawable.weaker, R.drawable.yonggan};


    @Override
    int getLayoutId()
    {
        return R.layout.activity_main;
    }

    @Override
    void getPreIntent()
    {
        brave = ((MyApplication)getApplication()).getBrave();
        weak = ((MyApplication)getApplication()).getWeak();
        nickname=((MyApplication)getApplication()).getNickname();
        game_score=((MyApplication)getApplication()).getGame_score();
        if(Integer.parseInt(game_score) <= 200)
            rank = "入门者";
        else if(Integer.parseInt(game_score) <= 400)
            rank = "潜力新星";
        else if(Integer.parseInt(game_score) <= 600)
            rank = "勤奋学者";
        else if(Integer.parseInt(game_score) <= 800)
            rank = "大师";
        else
            rank = "贤者本人";
    }

    @Override
    void initView()
    {
        TextView stateshow = (TextView)findViewById(R.id.main_state_show);
        ImageView state = (ImageView)findViewById(R.id.main_state);
        if(brave > 0)
        {
            state.setBackgroundResource(icons[1]);
            stateshow.setText("你的内心充满了勇气！ 效果：" + brave + " 回合");
            stateshow.setTextColor(Color.RED);
        }
        else if(weak > 0)
        {
            state.setBackgroundResource(icons[0]);
            stateshow.setText("你服下了懦夫药水... 效果：" + weak + " 回合");
            stateshow.setTextColor(Color.GRAY);
        }
        else
        {
            state.setVisibility(View.INVISIBLE);
            stateshow.setVisibility(View.INVISIBLE);
        }
        TextView sc=(TextView)findViewById(R.id.main_mine);
        String show = nickname + "\n" + "当前称号： " + rank;
        sc.setText(show);
        if(Integer.parseInt(game_score) <= 200)
            sc.setTextColor(Color.WHITE);
        else if(Integer.parseInt(game_score) <= 400)
            sc.setTextColor(Color.GREEN);
        else if(Integer.parseInt(game_score) <= 600)
            sc.setTextColor(Color.BLUE);
        else if(Integer.parseInt(game_score) <= 800)
            sc.setTextColor(Color.YELLOW);
        else
            sc.setTextColor(Color.RED);
        TextView tx = (TextView) findViewById(R.id.main_money);
        int money = ((MyApplication)getApplication()).getMoney();
        tx.setText(money+"");
    }

    @OnClick({R.id.btn_start_answer, R.id.main_to_main, R.id.main_to_home, R.id.main_to_mine, R.id.main_to_mess,R.id.main_to_market})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_start_answer:
                Intent intent1 = new Intent(MainActivity.this, NumActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_to_main:
                break;
            case R.id.main_to_home:
                Intent intent2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_to_mine:
                Intent intent3 = new Intent(MainActivity.this, FriendActivity.class);
                startActivity(intent3);
                break;
            case R.id.main_to_mess:
                Intent intent4 = new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent4);
                break;
            case R.id.main_to_market:
                Intent intent5 = new Intent(MainActivity.this, MarketActivity.class);
                startActivity(intent5);
                break;
        }
    }
}