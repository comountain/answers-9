package com.example.activity.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.activity.BaseActivity;
import com.example.activity.message.GamerMessage;
import com.example.activity.message.MatchMessage;
import com.example.activity.service.CientService;

import butterknife.OnClick;

public class FieldChooseActivity extends BaseActivity {
    private String matchNum;
    private String num;

    @Override
    int getLayoutId()
    {
        return R.layout.activity_field_choose;
    }

    @Override
    void getPreIntent()
    {
        matchNum = ((MyApplication)getApplication()).getPlaytype();
        num = getIntent().getExtras().get("num").toString().trim();
    }


    @OnClick({R.id.field_1,R.id.field_2,R.id.field_3,R.id.field_4,R.id.field_5,R.id.field_6,R.id.field_7,R.id.ret})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.field_1:
                Intent intent1 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent12 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent13 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent1.putExtra("field","Literature");
                intent1.putExtra("num", num);
                intent12.putExtra("field","Literature");
                intent12.putExtra("num", num);
                intent13.putExtra("field","Literature");
                intent13.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent1);
                else if(matchNum.equals("single"))
                    startActivity(intent12);
                else
                    startActivity(intent13);
                break;
            case R.id.field_2:
                Intent intent2 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent22 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent23 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent23.putExtra("field","Literature");
                intent23.putExtra("num", num);
                intent2.putExtra("field","History");
                intent2.putExtra("num", num);
                intent22.putExtra("field","History");
                intent22.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent2);
                else if(matchNum.equals("single"))
                    startActivity(intent22);
                else
                    startActivity(intent23);
                break;
            case R.id.field_3:
                Intent intent3 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent32 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent33 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent33.putExtra("field","Literature");
                intent33.putExtra("num", num);
                intent3.putExtra("field","Films");
                intent3.putExtra("num", num);
                intent32.putExtra("field","Films");
                intent32.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent3);
                else if(matchNum.equals("single"))
                    startActivity(intent32);
                else
                    startActivity(intent33);
                break;
            case R.id.field_4:
                Intent intent4 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent42 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent43 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent43.putExtra("field","Literature");
                intent43.putExtra("num", num);
                intent4.putExtra("field","Art");
                intent4.putExtra("num", num);
                intent42.putExtra("field","Art");
                intent42.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent4);
                else if(matchNum.equals("single"))
                    startActivity(intent42);
                else
                    startActivity(intent43);
                break;
            case R.id.field_5:
                Intent intent5 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent52 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent53 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent53.putExtra("field","Literature");
                intent53.putExtra("num", num);
                intent5.putExtra("field","Geography");
                intent5.putExtra("num", num);
                intent52.putExtra("field","Geography");
                intent52.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent5);
                else if(matchNum.equals("single"))
                    startActivity(intent52);
                else
                    startActivity(intent53);
                break;
            case R.id.field_6:
                Intent intent6 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent62 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent63 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent63.putExtra("field","Literature");
                intent63.putExtra("num", num);
                intent6.putExtra("field","Science");
                intent6.putExtra("num", num);
                intent62.putExtra("field","Science");
                intent62.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent6);
                else if(matchNum.equals("single"))
                    startActivity(intent62);
                else
                    startActivity(intent63);
                break;
            case R.id.field_7:
                Intent intent7 = new Intent(FieldChooseActivity.this,WaitActivity.class);
                Intent intent72 = new Intent(FieldChooseActivity.this,AnswerActivity.class);
                Intent intent73 = new Intent(FieldChooseActivity.this,RoomActivity.class);
                intent73.putExtra("field","Literature");
                intent73.putExtra("num", num);
                intent7.putExtra("field","All");
                intent7.putExtra("num", num);
                intent72.putExtra("field","All");
                intent72.putExtra("num", num);
                if(matchNum.equals("match"))
                    startActivity(intent7);
                else if(matchNum.equals("single"))
                    startActivity(intent72);
                else
                    startActivity(intent73);
                break;
            case R.id.ret:
                this.finish();
                break;
        }
    }
}