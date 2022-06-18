package com.example.activity.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MyApplication;
import com.example.activity.R;
import com.example.activity.bean.User;
import com.example.activity.bean.UserBean;
import com.example.activity.constants.config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class FriendActivity extends BaseActivity {

    private List<UserBean> friends=new ArrayList<>();
    private List<UserBean> friendsRequest=new ArrayList<>();
    private ListView friendsListView;
    private ListView friendsRequestListView;
    private EditText editText;
    private int[] icons={R.drawable.touxiang1,R.drawable.touxiang2,R.drawable.touxiang3,R.drawable.touxiang4,R.drawable.touxiang5,R.drawable.touxiang6};
    @Override
    int getLayoutId()
    {
        return R.layout.activity_friend;
    }

    @OnClick({R.id.btn_add_friend, R.id.mine_to_home, R.id.mine_to_main, R.id.mine_to_mine, R.id.mine_to_mess})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_add_friend:
                addFriend();
                break;
            case R.id.mine_to_main:
                Intent intent2 = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.mine_to_home:
                Intent intent3 = new Intent(FriendActivity.this, CreateRoomActivity.class);
                startActivity(intent3);
                break;
            case R.id.mine_to_mine:
                break;
            case R.id.mine_to_mess:
                Intent intent5 = new Intent(FriendActivity.this,MyActivity.class);
                startActivity(intent5);
                break;

        }
    }



    @Override
    void initData(){
        friends=new ArrayList<>();
        friendsRequest=new ArrayList<>();
        String url = config.HOST;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        int id=((MyApplication)getApplication()).getId();
        params.put("id",id);

        //获取好友列表
        client.get(config.URL_GET_FRIENDS,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();++i){
                    try{
                        JSONObject obj = response.getJSONObject(i);
                        friends.add(new UserBean(
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
                Toast.makeText(FriendActivity.this, "sbl", Toast.LENGTH_SHORT).show();
            }
        });

        //获取申请列表
        client.get(config.URL_GET_FRIENDS_REQUEST,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                super.onSuccess(statusCode, headers, response);
                System.out.println("response.length is"+response.length());
                for(int i=0;i<response.length();++i){
                    try{
                        JSONObject obj = response.getJSONObject(i);
                        friendsRequest.add(new UserBean(
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
                Toast.makeText(FriendActivity.this, "sbl", Toast.LENGTH_SHORT).show();
            }
        });

//        System.out.println("my debug");
//        System.out.println(friends.size());
//        System.out.println(friendsRequest.size());

//        this.initView();
    }

    @Override
    void initView(){
        friendsListView = (ListView) findViewById(R.id.list_myfirends);
        FriendsAdapter friendsAdapter=new FriendsAdapter();
        friendsListView.setAdapter(friendsAdapter);

        friendsRequestListView=(ListView) findViewById(R.id.list_friendsrequest);
        FriendsRequestAdapter friendsRequestAdapter=new FriendsRequestAdapter();
        friendsRequestListView.setAdapter(friendsRequestAdapter);

    }

    class FriendsAdapter extends BaseAdapter{
        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return friends.size();
        }
        //得到Item代表的对象
        @Override
        public Object getItem(int position) {
            //返回ListView Item条目代表的对象
            return friends.get(position);
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
            ViewHolder holder = null;
            //使用了懒汉模式
            if(convertView == null){
                //将list_item.xml文件找出来并转换成View对象
                convertView  = View.inflate(FriendActivity.this, R.layout.showfriends, null);
                //找到list_item.xml中创建的TextView
                holder = new ViewHolder();
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
                holder.rank = (TextView) convertView.findViewById(R.id.rank);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_user1);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nickname.setText(friends.get(position).getNickname());
            holder.rank.setText(""+friends.get(position).getGame_score());
            holder.iv.setBackgroundResource(icons[friends.get(position).getImage_id()]);
            return convertView;
        }
    }
    static class ViewHolder{
        TextView nickname;
        TextView rank;
        ImageView iv;
    }

    class FriendsRequestAdapter extends BaseAdapter{
        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return friendsRequest.size();
        }
        //得到Item代表的对象
        @Override
        public Object getItem(int position) {
            //返回ListView Item条目代表的对象
            return friendsRequest.get(position);
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
            ViewHolderRequest holder = null;
            //使用了懒汉模式
            if(convertView == null){
                //将list_item.xml文件找出来并转换成View对象
                convertView  = View.inflate(FriendActivity.this, R.layout.showfriendsrequest, null);
                //找到list_item.xml中创建的TextView
                holder = new ViewHolderRequest();
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
                holder.rank = (TextView) convertView.findViewById(R.id.rank);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_user1);
                holder.accept=(Button) convertView.findViewById(R.id.accept);
                holder.refuse=(Button) convertView.findViewById(R.id.refuse);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolderRequest) convertView.getTag();
            }
            holder.nickname.setText(friendsRequest.get(position).getNickname());
            holder.rank.setText(""+friendsRequest.get(position).getGame_score());
            holder.iv.setBackgroundResource(icons[friendsRequest.get(position).getImage_id()]);
            setOnClick(holder.accept,1,friendsRequest.get(position).getUserid());
            setOnClick(holder.refuse,2,friendsRequest.get(position).getUserid());
            return convertView;
        }
    }

    static class ViewHolderRequest{
        TextView nickname;
        TextView rank;
        ImageView iv;
        Button accept;
        Button refuse;
    }

    void setOnClick(final View view, final int aor, final int id) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("------aor----------");
                System.out.println(aor+"   "+id);

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                int user1id=((MyApplication)getApplication()).getId();
                params.put("user1id",user1id);
                params.put("acceptOrRefuse",aor);
                params.put("user2id",id);

                //处理申请
                client.get(config.URL_SOLVE_REQUEST,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        super.onSuccess(statusCode, headers, response);
                        initData();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(FriendActivity.this, "sbl", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    void addFriend() {
        editText=findViewById(R.id.edit_add_friend);
        String friend_account_or_nickname=editText.getText().toString();

        String url = config.HOST;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        int id=((MyApplication)getApplication()).getId();
        params.put("account_or_nickname",friend_account_or_nickname);
        params.put("id",id);

        //申请添加好友
        client.get(config.URL_ADD_FRIEND,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                super.onSuccess(statusCode, headers, response);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(FriendActivity.this, "sbl", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

