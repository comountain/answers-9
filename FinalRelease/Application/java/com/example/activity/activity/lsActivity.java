package com.example.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.activity.R;

import butterknife.OnClick;

public class lsActivity extends BaseActivity {
    @Override
    int getLayoutId()
    {
        return R.layout.mainpage;
    }

    @OnClick({R.id.log,R.id.reg})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.log:
                Intent intent1 = new Intent(lsActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.reg:
                Intent intent2 = new Intent(lsActivity.this,SignupActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
