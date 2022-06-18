package com.example.activity.activity;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import com.example.activity.R;
import com.example.activity.bean.QuestBean;
import com.example.activity.constants.config;
import com.example.activity.fragment.AnswerFragment;
import com.example.activity.utils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class AddquestActivity extends BaseActivity {
    private String model = "";
    private String field = "";
    private String answer = "";
    EditText []option = new EditText[4];
    Button []fields = new Button[6];
    Button []models = new Button[3];
    Drawable drawable;

    @Override
    int getLayoutId()
    {
        return R.layout.activity_addquest;
    }

    @Override
    public void getPreIntent()
    {
        option[0] = (EditText) findViewById(R.id.add_optionA);
        option[1] = (EditText) findViewById(R.id.add_optionB);
        option[2] = (EditText) findViewById(R.id.add_optionC);
        option[3] = (EditText) findViewById(R.id.add_optionD);
        fields[0] = (Button) findViewById(R.id.add_choose1);
        fields[1] = (Button) findViewById(R.id.add_choose2);
        fields[2] = (Button) findViewById(R.id.add_choose3);
        fields[3] = (Button) findViewById(R.id.add_choose4);
        fields[4] = (Button) findViewById(R.id.add_choose5);
        fields[5] = (Button) findViewById(R.id.add_choose6);
        models[0] = (Button) findViewById(R.id.add_justice);
        models[1] = (Button) findViewById(R.id.add_choose);
        models[2] = (Button) findViewById(R.id.add_mult);
    }

    @OnClick({R.id.add_back, R.id.add_submit, R.id.add_justice, R.id.add_choose, R.id.add_mult,
            R.id.add_choose1, R.id.add_choose2, R.id.add_choose3, R.id.add_choose4, R.id.add_choose5, R.id.add_choose6})
    public void onViewClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.add_back:
                this.finish();
                break;
            case R.id.add_submit:
                submit();
                break;
            case R.id.add_justice:
                model = "judge";
                clearButton2();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                models[0].setBackground(drawable);
                break;
            case R.id.add_choose:
                model = "single";
                clearButton2();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                models[1].setBackground(drawable);
                break;
            case R.id.add_mult:
                model = "Mult";
                clearButton2();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                models[2].setBackground(drawable);
                break;
            case R.id.add_choose1:
                field = "Literature";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[0].setBackground(drawable);
                break;
            case R.id.add_choose2:
                field = "History";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[1].setBackground(drawable);
                break;
            case R.id.add_choose3:
                field = "Films";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[2].setBackground(drawable);
                break;
            case R.id.add_choose4:
                field = "Art";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[3].setBackground(drawable);
                break;
            case R.id.add_choose5:
                field = "Geography";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[4].setBackground(drawable);
                break;
            case R.id.add_choose6:
                field = "Science";
                clearButton1();
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                fields[5].setBackground(drawable);
                break;
        }
    }



    public void submit()
    {
        EditText title = (EditText) findViewById(R.id.add_title);
        EditText answer = (EditText) findViewById(R.id.add_answer);
        if(model.equals(""))
        {
            Toast.makeText(AddquestActivity.this, "题型未选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(field.equals(""))
        {
            Toast.makeText(AddquestActivity.this, "类型未选择！", Toast.LENGTH_SHORT).show();
            return;
        }
        if(title.getText().toString().equals(""))
        {
            Toast.makeText(AddquestActivity.this, "题目不能为空！", Toast.LENGTH_SHORT).show();
        }
        for(int i = 0; i < 4; i++)
        {
            if(option[i].getText().toString().equals(""))
            {
                if(i == 2 && model.equals("judge"))
                    continue;
                if(i == 3 && model.equals("judge"))
                    continue;
                Toast.makeText(AddquestActivity.this, "选项不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(answer.getText().toString().equals(""))
        {
            Toast.makeText(AddquestActivity.this, "正确选项不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String an = answer.getText().toString();
        for(int i = 0; i < an.length(); i++)
        {
            if(!model.equals("Mult") && i > 1)
            {
                Toast.makeText(AddquestActivity.this, "非多选题只能有1个正确选项", Toast.LENGTH_SHORT).show();
                return;
            }
            if(i >= 4)
            {
                Toast.makeText(AddquestActivity.this, "正确选项最多四个", Toast.LENGTH_SHORT).show();
                return;
            }
            if(an.charAt(i) != 'A' && an.charAt(i) != 'B' &&an.charAt(i) != 'C' &&an.charAt(i) != 'D')
            {
                Toast.makeText(AddquestActivity.this, "正确选项格式错误，应该由ABCD组成，中间无空格，例如：AB", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("title",title.getText().toString());
        params.put("field",field);
        params.put("optionA", option[0].getText().toString());
        params.put("optionB", option[1].getText().toString());
        params.put("optionC", option[2].getText().toString());
        params.put("optionD", option[3].getText().toString());
        params.put("answer", an);
        params.put("type",model);
        client.get(config.URL_ADD_QUEST,params, new JsonHttpResponseHandler() {
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
        Intent intent = new Intent(AddquestActivity.this, MyActivity.class);
        startActivity(intent);
    }

    public void clearButton1()
    {
        drawable = getResources().getDrawable(R.drawable.shape_normal);
        for(int i = 0; i < 6; i++)
            fields[i].setBackground(drawable);
    }

    public void clearButton2()
    {
        drawable = getResources().getDrawable(R.drawable.shape_normal);
        for(int i = 0; i < 3; i++)
            models[i].setBackground(drawable);
    }
}