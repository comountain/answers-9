package com.example.activity.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MyApplication;
import com.example.activity.R;
import butterknife.OnClick;

public class MyActivity extends BaseActivity {
    private String curuser;
    private String game_score;
    private String nickname;
    private String rank;
    private int[] icons={R.drawable.touxiang1,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.touxiang5,R.drawable.touxiang6};

    @Override
    int getLayoutId() {
        return R.layout.activity_my;
    }
    @Override
    void getPreIntent(){
        curuser=((MyApplication)getApplication()).getAccount();
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
    void initView(){
        ImageView ima = (ImageView)findViewById(R.id.my_touxiang);
        ima.setBackgroundResource(icons[((MyApplication)getApplication()).getImageId()]);
        TextView ni=(TextView)findViewById(R.id.mynick);
        ni.setText(nickname);
        TextView sc = (TextView) findViewById(R.id.myscore);
        if(Integer.parseInt(game_score) <= 200)
        {
            sc.setTextColor(Color.GRAY);
            rank += " 下一阶段:200";
        }
        else if(Integer.parseInt(game_score) <= 400)
        {
            sc.setTextColor(Color.GREEN);
            rank += " 下一阶段:400";
        }
        else if(Integer.parseInt(game_score) <= 600)
        {
            sc.setTextColor(Color.BLUE);
            rank += " 下一阶段:600";
        }
        else if(Integer.parseInt(game_score) <= 800)
        {
            sc.setTextColor(Color.YELLOW);
            rank += " 下一阶段:800";
        }
        else
            sc.setTextColor(Color.RED);
        sc.setText(rank);
    }
    @OnClick({R.id.my_1,R.id.my_2, R.id.mess_to_home, R.id.mess_to_main, R.id.mess_to_mine})
    public void onViewClicked(View view) {
        switch(view.getId())
        {
            case R.id.my_1:
                Intent intent = new Intent(MyActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.my_2:
                Intent intent4 = new Intent(MyActivity.this, AddquestActivity.class);
                startActivity(intent4);
                break;
            case R.id.mess_to_home:
                Intent intent2 = new Intent(MyActivity.this, CreateRoomActivity.class);
                startActivity(intent2);
                break;
            case R.id.mess_to_main:
                Intent intent1 = new Intent(MyActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.mess_to_mine:
                Intent intent3 = new Intent(MyActivity.this, FriendActivity.class);
                startActivity(intent3);
                break;
        }
    }
    public void logout(View view){
        ((MyApplication)getApplication()).removeUser();
        Intent intent = new Intent(MyActivity.this,lsActivity.class);
        startActivity(intent);
    }
}
