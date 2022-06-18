package com.example.activity.activity;


import android.content.Intent;
import android.view.View;

import com.example.activity.MyApplication;
import com.example.activity.R;

import butterknife.OnClick;

public class NumActivity extends BaseActivity {
    @Override
    int getLayoutId()
    {
        return R.layout.activity_num;
    }

    @OnClick({R.id.num_ret, R.id.singleNum, R.id.TwoNum, R.id.ThreeNum, R.id.FourNum})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.num_ret:
                this.finish();
                break;
            case R.id.singleNum:
                Intent intent2 = new Intent(NumActivity.this,FieldChooseActivity.class);
                ((MyApplication)getApplication()).setPlaytype("single");
                intent2.putExtra("num", "1");
                startActivity(intent2);
                break;
            case R.id.TwoNum:
                Intent intent3 = new Intent(NumActivity.this,FieldChooseActivity.class);
                ((MyApplication)getApplication()).setPlaytype("match");
                intent3.putExtra("num", "2");
                startActivity(intent3);
                break;
            case R.id.ThreeNum:
                Intent intent4 = new Intent(NumActivity.this,FieldChooseActivity.class);
                ((MyApplication)getApplication()).setPlaytype("match");
                intent4.putExtra("num", "3");
                startActivity(intent4);
                break;
            case R.id.FourNum:
                Intent intent5 = new Intent(NumActivity.this,FieldChooseActivity.class);
                ((MyApplication)getApplication()).setPlaytype("match");
                intent5.putExtra("num", "4");
                startActivity(intent5);
                break;
        }
    }

}