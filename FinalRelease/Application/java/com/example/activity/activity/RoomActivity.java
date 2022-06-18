package com.example.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.bean.User;
import com.example.activity.bean.UserBean;
import com.example.activity.constants.config;
import com.example.activity.message.AcceptMessage;
import com.example.activity.message.CompeteMessage;
import com.example.activity.message.DeleteRoomMessage;
import com.example.activity.message.InviteMessage;
import com.example.activity.message.MatchMessage;
import com.example.activity.message.RoomStartMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class RoomActivity extends BaseActivity implements Chronometer.OnChronometerTickListener{
    private String field;
    private String num;
    private List<UserBean> onlineFriends=new ArrayList<>();
    private ListView friendInviteListView;
    private TextView[] name;
    private ImageView[] player;
    private String playertype;
    private String[] playername;
    private int owner_id;
    private int sec = 0;
    private boolean if_removed = false;
    private int[] icons={R.drawable.touxiang1,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.touxiang5,R.drawable.touxiang6};

    @BindView(R.id._chro_room)
    Chronometer chronometer;

    @Override
    int getLayoutId()
    {
        return R.layout.activity_room;
    }

    @Override
    void getPreIntent()
    {
        name = new TextView[4];
        player = new ImageView[4];
        playertype = ((MyApplication)getApplication()).getPlaytype();
        field = getIntent().getExtras().get("field").toString().trim();
        num = getIntent().getExtras().get("num").toString().trim();
        name[0] = (TextView) findViewById(R.id.room_name1);
        name[1] = (TextView) findViewById(R.id.room_name2);
        name[2] = (TextView) findViewById(R.id.room_name3);
        name[3] = (TextView) findViewById(R.id.room_name4);
        player[0] = (ImageView) findViewById(R.id.room_player1);
        player[1] = (ImageView) findViewById(R.id.room_player2);
        player[2] = (ImageView) findViewById(R.id.room_player3);
        player[3] = (ImageView) findViewById(R.id.room_player4);
        playername = new String[1];
        playername[0] = ((MyApplication)getApplication()).getNickname();
        refresh(1);
    }


    public void refresh(int num)
    {
        switch (num)
        {
            case 1:
                name[0].setVisibility(View.VISIBLE);
                name[0].setText(playername[0]);
                name[1].setVisibility(View.INVISIBLE);
                name[2].setVisibility(View.INVISIBLE);
                name[3].setVisibility(View.INVISIBLE);
                player[0].setVisibility(View.VISIBLE);
                player[1].setVisibility(View.INVISIBLE);
                player[2].setVisibility(View.INVISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 2:
                name[0].setVisibility(View.VISIBLE);
                name[1].setVisibility(View.VISIBLE);
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setVisibility(View.INVISIBLE);
                name[3].setVisibility(View.INVISIBLE);
                player[0].setVisibility(View.VISIBLE);
                player[1].setVisibility(View.VISIBLE);
                player[2].setVisibility(View.INVISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 3:
                name[0].setVisibility(View.VISIBLE);
                name[1].setVisibility(View.VISIBLE);
                name[2].setVisibility(View.VISIBLE);
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setText(playername[2]);
                name[3].setVisibility(View.INVISIBLE);
                player[0].setVisibility(View.VISIBLE);
                player[1].setVisibility(View.VISIBLE);
                player[2].setVisibility(View.VISIBLE);
                player[3].setVisibility(View.INVISIBLE);
                break;
            case 4:
                name[0].setVisibility(View.VISIBLE);
                name[1].setVisibility(View.VISIBLE);
                name[2].setVisibility(View.VISIBLE);
                name[3].setVisibility(View.VISIBLE);
                name[0].setText(playername[0]);
                name[1].setText(playername[1]);
                name[2].setText(playername[2]);
                name[3].setText(playername[3]);
                player[0].setVisibility(View.VISIBLE);
                player[1].setVisibility(View.VISIBLE);
                player[2].setVisibility(View.VISIBLE);
                player[3].setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.room_ret,R.id.btn_start_fvf})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.room_ret:
                if(playertype.equals("owner"))
                    ((MyApplication)getApplication()).sendMessage(new DeleteRoomMessage(((MyApplication)getApplication()).getId(), 1, "yongbudao"));
                else
                    ((MyApplication)getApplication()).sendMessage(new DeleteRoomMessage(owner_id, 0, ((MyApplication)getApplication()).getNickname()));
                Intent intent = new Intent(RoomActivity.this, CreateRoomActivity.class);
                ((MyApplication)getApplication()).resetClient();
                startActivity(intent);
                break;
            case R.id.btn_start_fvf:
                if(playertype.equals("owner"))
                {
                    if(playername.length < 2)
                        Toast.makeText(RoomActivity.this, "人数不满足开始条件（至少两人，不然你为什么不去练习模式呢）", Toast.LENGTH_SHORT).show();
                    else
                        ((MyApplication)getApplication()).sendMessage(new RoomStartMessage(owner_id));
                }
                break;
        }
    }

    @Override
    void initView(){
        friendInviteListView = (ListView) findViewById(R.id.list_invitefriend);
        InviteFriendAdapter invitefriendAdapter=new InviteFriendAdapter();
        friendInviteListView.setAdapter(invitefriendAdapter);
        setChronometer();
    }

    class InviteFriendAdapter extends BaseAdapter {
        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return onlineFriends.size();
        }
        //得到Item代表的对象
        @Override
        public Object getItem(int position) {
            //返回ListView Item条目代表的对象
            return onlineFriends.get(position);
        }
        //得到Item的id
        @Override
        public long getItemId(int position) {
            //返回ListView Item的id
            return position;
        }

        //得到Item的View视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderInvite holder = null;
            //使用了懒汉模式
            if(convertView == null){
                //将list_item.xml文件找出来并转换成View对象
                convertView  = View.inflate(RoomActivity.this, R.layout.showinvitefriends, null);
                //找到list_item.xml中创建的TextView
                holder = new ViewHolderInvite();
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
                holder.rank = (TextView) convertView.findViewById(R.id.rank);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_user1);
                holder.invite=(Button) convertView.findViewById(R.id.invite);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolderInvite) convertView.getTag();
            }
            holder.nickname.setText(onlineFriends.get(position).getNickname());
            holder.rank.setText(""+onlineFriends.get(position).getGame_score());
            holder.iv.setBackgroundResource(icons[onlineFriends.get(position).getImage_id()]);
            setOnClick(holder.invite,onlineFriends.get(position).getUserid(),position);
            return convertView;
        }
    }

    static class ViewHolderInvite{
        TextView nickname;
        TextView rank;
        ImageView iv;
        Button invite;
    }

    void setOnClick(final View view, final int id,final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user1id=((MyApplication)getApplication()).getId();
                if(playertype.equals("owner"))
                    ((MyApplication)getApplication()).sendMessage(new InviteMessage(user1id, id, field));
                else
                    ((MyApplication)getApplication()).sendMessage(new InviteMessage(owner_id, id, field));
            }
        });
    }

    private void setChronometer() {
        chronometer.start();
        chronometer.setOnChronometerTickListener(this);
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        if(if_removed)
            return;
        sec ++;
        playername = ((MyApplication)getApplication()).getPlayername();
        refresh(playername.length);
        if(playertype.equals("invited") && sec > 2)
        {
            owner_id = Integer.parseInt(((MyApplication)getApplication()).caonima());
            if(owner_id == 0)
            {
                if_removed = true;
                android.app.AlertDialog textTips;
                textTips = new AlertDialog.Builder(RoomActivity.this)
                        .setMessage("房间已注销")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RoomActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
                textTips.setCancelable(false);
            }
            Button bt = (Button) findViewById(R.id.btn_start_fvf);
            bt.setText("等待房主");
        }
        else
            owner_id = ((MyApplication)getApplication()).getId();
        if (sec == 1 || !((MyApplication) getApplication()).ifMatched()) {
            return;
        }
        Intent intent1 = new Intent(RoomActivity.this, AnswerActivity.class);
        intent1.putExtra("field", field);
        intent1.putExtra("num", playername.length+"");
        ((MyApplication)getApplication()).setPlaytype("match");
        startActivity(intent1);
    }

    @Override
    void initData(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        int id=((MyApplication)getApplication()).getId();
        params.put("id",id);
        //获取在线列表
        client.get(config.URL_GET_FRIENDS,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();++i){
                    try{
                        JSONObject obj = response.getJSONObject(i);
                        onlineFriends.add(new UserBean(
                                obj.getInt("id"),
                                obj.getString("username"),
                                obj.getString("password"),
                                obj.getString("nickname"),
                                obj.getString("image_id"),
                                obj.getInt("game_score")
                        ));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                initView();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(RoomActivity.this, "sbl", Toast.LENGTH_SHORT).show();
            }
        });
    }

}