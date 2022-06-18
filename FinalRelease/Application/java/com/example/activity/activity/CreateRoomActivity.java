package com.example.activity.activity;

import android.content.Intent;
import android.view.View;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.message.CreateRoomMessage;

import butterknife.OnClick;

public class CreateRoomActivity extends BaseActivity {

    @Override
    int getLayoutId()
    {
        return R.layout.activity_create_room;
    }

    @OnClick({R.id.room_to_home,R.id.create_room, R.id.room_to_main, R.id.room_to_mine, R.id.room_to_mess})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.create_room:
                Intent intent1 = new Intent(CreateRoomActivity.this,FieldChooseActivity.class);
                int id = ((MyApplication)getApplication()).getId();
                ((MyApplication)getApplication()).sendMessage(new CreateRoomMessage(id));
                ((MyApplication)getApplication()).setPlaytype("owner");
                intent1.putExtra("num","invite");
                startActivity(intent1);
                break;
            case R.id.room_to_main:
                Intent intent2 = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.room_to_home:
                break;
            case R.id.room_to_mine:
                Intent intent4 = new Intent(CreateRoomActivity.this, FriendActivity.class);
                startActivity(intent4);
                break;
            case R.id.room_to_mess:
                Intent intent5 = new Intent(CreateRoomActivity.this,MyActivity.class);
                startActivity(intent5);
                break;
        }
    }


}