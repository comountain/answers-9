package com.example.activity.bean;

/*import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;*/


public class QuestBean {

    private int id;

    private String q_type;

    private String title;// 问题
    private String optionA;// 选项A
    private String optionB;// 选项B
    private String optionC;// 选项C
    private String optionD;// 选项D
    private String answer;// 正确答案
    private String field;
    private String myanswer;// 我的答案

    public QuestBean(int id, String q_type, String title, String optionA,
                     String optionB, String optionC, String optionD, String answer, String field) {
        this.id = id;
        this.q_type = q_type;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.field = field;
    }


    public QuestBean(int id, String q_type, String title, String optionA,
                     String optionB, String optionC, String optionD,
                     String answer, String field, String myanswer) {
        this.id = id;
        this.q_type = q_type;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.myanswer = myanswer;
        this.field = field;
    }


    public QuestBean() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQ_type() {
        return this.q_type;
    }

    public void setQ_type(String q_type) {
        this.q_type = q_type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return this.optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return this.optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return this.optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return this.optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }


    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getField() {return this.field;}

    public void setField(String field) {this.field = field;}

    public String getMyanswer() {
        return this.myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }


}
