package com.example.activity.constants;

import java.util.List;

public class config {
    //是否开启调试模式
    public static final int maxWrapperSize = 4;

    public static final boolean DEBUG = true;
    //    Log 打印的 tag
    public static final String LogTag = "metis";
    //    主机 host
    public static final String HOST = "http://192.168.0.107:8081/";
    //    登录网址
    public static final String URL_LOGIN = HOST + "LogLet";
    //    账号信息
    public static final String URL_GET_INFO=HOST + "getInfo";
    public static final String URL_UPDATE_INFO=HOST + "updateInfo";
    public static final String URL_UPDATE_SCORE = HOST + "updateScore";
    public static final String URL_UPDATE_MONEY = HOST + "updateMoney";
    public static final String URL_UPDATE_WEAK = HOST + "updateWeak";
    public static final String URL_UPDATE_BRAVE = HOST + "updateBrave";
    public static final String URL_UPDATE_HEAD = HOST + "updateHead";
    public static final String URL_ADD_QUEST = HOST + "addQuestadds";
    //    注册网址
    public static final String URL_SIGNUP = HOST + "RegLet";
    //    获取问题网址
    public static final String URL_GET_QUESTION = HOST + "getQuests";

    //    获取试卷题目网址
    public static final String URL_DEBUG = HOST + "debug";
    public static final String URL_UP_USER_GRADE = HOST + "UpUserGrade";

    //    获取好友信息网址
    public static final String URL_GET_FRIENDS = HOST+"getFriends";

    //    获取好友申请信息网址
    public static final String URL_GET_FRIENDS_REQUEST = HOST + "getFriendsRequest";

    //    添加好友网址
    public static final String URL_ADD_FRIEND = HOST + "addFriend";

    //    处理申请网址
    public static final String URL_SOLVE_REQUEST = HOST + "solveRequest";

    //    发出对战邀请网址
    public static final String URL_INVITE_BATTLE = HOST + "inviteBattle";

    //    获取在线好友
    public static final String URL_GET_ONLINE_FRIENDS = HOST +"getOnlineFriends";

}
