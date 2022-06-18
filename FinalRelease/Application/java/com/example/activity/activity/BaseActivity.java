package com.example.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.message.AcceptMessage;
import com.example.activity.message.LeaveMessage;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity{
    int id;
    private BaseActivity oContext;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        id = getLayoutId();
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();
        ButterKnife.bind(this);
        getPreIntent();
        initView();
        initData();
        initListener();
        Thread listen_invite = new Thread(){
            @Override
            public void run()
            {
                while(true)
                {
                    HashMap<String, Integer> in;
                    in = ((MyApplication)getApplication()).getInvitors();
                    if(in.isEmpty())
                        continue;
                    String invitor = null;
                    int invitor_id = 0;
                    for(Map.Entry<String, Integer> entry : in.entrySet())
                    {
                        invitor = entry.getKey();
                        invitor_id = entry.getValue();
                    }
                    Looper.prepare();
                    int finalInvitor_id = invitor_id;
                    String finalInvitor = invitor;
                    android.app.AlertDialog textTips = new AlertDialog.Builder(BaseActivity.this)
                            .setMessage(invitor+" 邀请你同台竞技")
                            .setPositiveButton("接受邀请", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MyApplication)getApplication()).sendMessage(new AcceptMessage(finalInvitor_id, ((MyApplication)getApplication()).getId()));
                                    String field = ((MyApplication)getApplication()).getRoomField(finalInvitor_id);
                                    ((MyApplication)getApplication()).removeInvitor(finalInvitor);
                                    Intent intent = new Intent(BaseActivity.this, RoomActivity.class);
                                    intent.putExtra("num", "invite");
                                    intent.putExtra("field", field);
                                    ((MyApplication)getApplication()).setPlaytype("invited");
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("不接受", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MyApplication)getApplication()).removeInvitor(finalInvitor);
                                }
                            })
                            .show();
                    Looper.loop();
                    continue;
                }

            }
        };
        if(id != R.layout.activity_wait && id != R.layout.activity_answer && id != R.layout.activity_field_choose && id != R.layout.activity_room)
            listen_invite.start();
    }

    public void addActivity() {
        ((MyApplication)getApplication()).addActivity_(oContext);// 调用myApplication的添加Activity方法
    }


    public void removeActivity() {
        ((MyApplication)getApplication()).removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }


    public void removeALLActivity() {
        ((MyApplication)getApplication()).removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (id != R.layout.activity_wait && id != R.layout.activity_answer) {
                android.app.AlertDialog textTips = new AlertDialog.Builder(BaseActivity.this)
                        .setTitle("您要退出游戏吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MyApplication)getApplication()).sendMessage(new LeaveMessage(((MyApplication)getApplication()).getId()));
                                removeALLActivity();
                            }
                        })
                        .setNegativeButton("我手滑了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            } else if (id == R.layout.activity_answer) {
                android.app.AlertDialog textTips = new AlertDialog.Builder(BaseActivity.this)
                        .setMessage("游戏过程请勿退出")
                        .show();
            } else if (id == R.layout.activity_wait) {
                android.app.AlertDialog textTips = new AlertDialog.Builder(BaseActivity.this)
                        .setMessage("请先取消匹配")
                        .show();
            }
            return true;
        }
        return true;
        //return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }



    abstract int getLayoutId();

    void getPreIntent(){}

    void initView(){}

    void initData(){}

    void initListener(){}
}

