package com.example.activity.activity;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.message.MatchMessage;
import android.view.View;
import android.content.Intent;
import butterknife.OnClick;


public class WaitActivity extends BaseActivity {
    private String player;
    private String field;
    private StringBuilder sb = new StringBuilder();
    private String num;

    @Override
    int getLayoutId() {
        return R.layout.activity_wait;
    }

    @Override
    void getPreIntent() {
        player = ((MyApplication)getApplication()).getPlaytype();
        field = getIntent().getExtras().get("field").toString().trim();
        num = getIntent().getExtras().get("num").toString().trim();
    }

    @Override
    public void initView() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String username = ((MyApplication) getApplication()).getNickname();

                    ((MyApplication) getApplication()).sendMessage(new MatchMessage(username, num, field, 0, Integer.parseInt(((MyApplication)getApplication()).getGame_score())));
                    while (!((MyApplication) getApplication()).ifMatched()) {
                        continue;
                    }
                    Intent intent1 = new Intent(WaitActivity.this, AnswerActivity.class);
                    intent1.putExtra("field", field);
                    intent1.putExtra("num", num);
                    startActivity(intent1);
                }
                catch (Exception e)
                {
                    Thread.interrupted();
                }
            }
        };
        thread.start();
    }

    @OnClick({R.id.wait_ret})
    public void onViewClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.wait_ret:
                String username = ((MyApplication)getApplication()).getNickname();
                ((MyApplication) getApplication()).sendMessage(new MatchMessage(username, "2", field, 1, Integer.parseInt(((MyApplication)getApplication()).getGame_score())));
                Intent intent = new Intent(WaitActivity.this, MainActivity.class);
                startActivity(intent);
        }
    }

}