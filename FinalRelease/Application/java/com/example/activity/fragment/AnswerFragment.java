package com.example.activity.fragment;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.activity.MyApplication;
import com.example.activity.R;

import com.example.activity.bean.QuestBean;
import com.example.activity.utils.LogUtils;
import com.example.activity.service.FragmentCallBack;

import java.util.HashMap;

public class AnswerFragment extends BaseFragment {
    public interface MyListener{
        public void sendValue(String value);
    }

    Drawable drawable;
    private MyListener myListener;
    private Button chooseA;
    private Button chooseB;
    private Button chooseC;
    private Button chooseD;
    private TextView tv_title;
    private HashMap<String, Boolean> multihelper = new HashMap<>();
    private String myanswer = "";
    volatile QuestBean questBean = null;
    FragmentCallBack mfragmentCallBack;

    public AnswerFragment(QuestBean questBean)
    {
        this.questBean = questBean;
    }

    @Override
    protected View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_answer, null);
        tv_title = (TextView) view.findViewById(R.id.quest);
        chooseA = (Button) view.findViewById(R.id.chooseA);
        chooseB = (Button) view.findViewById(R.id.chooseB);
        chooseC = (Button) view.findViewById(R.id.chooseC);
        chooseD = (Button) view.findViewById(R.id.chooseD);
        chooseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"Mult".equals(questBean.getQ_type()))
                    check(view);
                else
                    checkmore(view);
            }
        });
        chooseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"Mult".equals(questBean.getQ_type()))
                    check(view);
                else
                    checkmore(view);
            }
        });
        chooseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"Mult".equals(questBean.getQ_type()))
                    check(view);
                else
                    checkmore(view);
            }
        });
        chooseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"Mult".equals(questBean.getQ_type()))
                    check(view);
                else
                    checkmore(view);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ///获取绑定的监听
        if (context instanceof FragmentCallBack) {
            mfragmentCallBack = (FragmentCallBack) context;
        }
    }

    public void check(View v)
    {
        clearButton();
        switch (v.getId())
        {
            case R.id.chooseA:
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                chooseA.setBackground(drawable);
                questBean.setMyanswer("A");
                mfragmentCallBack.sendAnswer("A");
                break;
            case R.id.chooseB:
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                chooseB.setBackground(drawable);
                questBean.setMyanswer("B");
                mfragmentCallBack.sendAnswer("B");
                break;
            case R.id.chooseC:
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                chooseC.setBackground(drawable);
                questBean.setMyanswer("C");
                mfragmentCallBack.sendAnswer("C");
                break;
            case R.id.chooseD:
                drawable = getResources().getDrawable(R.drawable.shape_pressed);
                chooseD.setBackground(drawable);
                questBean.setMyanswer("D");
                mfragmentCallBack.sendAnswer("D");
                break;
        }
    }

    public void checkmore(View v)
    {
        switch (v.getId())
        {
            case R.id.chooseA:
                if(!multihelper.containsKey("A") || !multihelper.get("A"))
                    multihelper.put("A",true);
                else
                    multihelper.put("A", false);
                myanswer = "";
                for(int i = 0; i < 4; i++)
                {
                    switch (i){
                        case 0:
                            if(multihelper.containsKey("A") && multihelper.get("A") == true)
                                myanswer = myanswer + "A";
                            break;
                        case 1:
                            if(multihelper.containsKey("B") && multihelper.get("B") == true)
                                myanswer = myanswer + "B";
                            break;
                        case 2:
                            if(multihelper.containsKey("C") && multihelper.get("C") == true)
                                myanswer = myanswer + "C";
                            break;
                        case 3:
                            if(multihelper.containsKey("D") && multihelper.get("D") == true)
                                myanswer = myanswer + "D";
                    }
                }
                questBean.setMyanswer(myanswer);
                mfragmentCallBack.sendAnswer(myanswer);
                break;
            case R.id.chooseB:
                if(!multihelper.containsKey("B") || !multihelper.get("B"))
                    multihelper.put("B",true);
                else
                    multihelper.put("B", false);
                myanswer = "";
                for(int i = 0; i < 4; i++)
                {
                    switch (i){
                        case 0:
                            if(multihelper.containsKey("A") && multihelper.get("A") == true)
                                myanswer = myanswer + "A";
                            break;
                        case 1:
                            if(multihelper.containsKey("B") && multihelper.get("B") == true)
                                myanswer = myanswer + "B";
                            break;
                        case 2:
                            if(multihelper.containsKey("C") && multihelper.get("C") == true)
                                myanswer = myanswer + "C";
                            break;
                        case 3:
                            if(multihelper.containsKey("D") && multihelper.get("D") == true)
                                myanswer = myanswer + "D";
                    }
                }
                questBean.setMyanswer(myanswer);
                mfragmentCallBack.sendAnswer(myanswer);
                break;
            case R.id.chooseC:
                if(!multihelper.containsKey("C") || !multihelper.get("C"))
                    multihelper.put("C",true);
                else
                    multihelper.put("C", false);
                myanswer = "";
                for(int i = 0; i < 4; i++)
                {
                    switch (i){
                        case 0:
                            if(multihelper.containsKey("A") && multihelper.get("A") == true)
                                myanswer = myanswer + "A";
                            break;
                        case 1:
                            if(multihelper.containsKey("B") && multihelper.get("B") == true)
                                myanswer = myanswer + "B";
                            break;
                        case 2:
                            if(multihelper.containsKey("C") && multihelper.get("C") == true)
                                myanswer = myanswer + "C";
                            break;
                        case 3:
                            if(multihelper.containsKey("D") && multihelper.get("D") == true)
                                myanswer = myanswer + "D";
                    }
                }
                questBean.setMyanswer(myanswer);
                mfragmentCallBack.sendAnswer(myanswer);
                break;
            case R.id.chooseD:
                if(!multihelper.containsKey("D") || !multihelper.get("D"))
                    multihelper.put("D",true);
                else
                    multihelper.put("D", false);
                myanswer = "";
                for(int i = 0; i < 4; i++)
                {
                    switch (i){
                        case 0:
                            if(multihelper.containsKey("A") && multihelper.get("A") == true)
                                myanswer = myanswer + "A";
                            break;
                        case 1:
                            if(multihelper.containsKey("B") && multihelper.get("B") == true)
                                myanswer = myanswer + "B";
                            break;
                        case 2:
                            if(multihelper.containsKey("C") && multihelper.get("C") == true)
                                myanswer = myanswer + "C";
                            break;
                        case 3:
                            if(multihelper.containsKey("D") && multihelper.get("D") == true)
                                myanswer = myanswer + "D";
                    }
                }
                questBean.setMyanswer(myanswer);
                mfragmentCallBack.sendAnswer(myanswer);
        }
        clearButton();
        for(int i = 0; i < 4; i++)
        {
            switch (i){
                case 0:
                    if(multihelper.containsKey("A") && multihelper.get("A") == true)
                    {
                        drawable = getResources().getDrawable(R.drawable.shape_pressed);
                        chooseA.setBackground(drawable);
                    }
                    break;
                case 1:
                    if(multihelper.containsKey("B") && multihelper.get("B") == true)
                    {
                        drawable = getResources().getDrawable(R.drawable.shape_pressed);
                        chooseB.setBackground(drawable);
                    }
                    break;
                case 2:
                    if(multihelper.containsKey("C") && multihelper.get("C") == true)
                    {
                        drawable = getResources().getDrawable(R.drawable.shape_pressed);
                        chooseC.setBackground(drawable);
                    }
                    break;
                case 3:
                    if(multihelper.containsKey("D") && multihelper.get("D") == true)
                    {
                        drawable = getResources().getDrawable(R.drawable.shape_pressed);
                        chooseD.setBackground(drawable);
                    }
            }
        }
    }

    @Override
    public void initData()
    {
        tv_title.setText("" + questBean.getTitle());
        if (questBean == null) {
            LogUtils.e( "initData: questBean==null");
            return;
        }

        if ("single".equals(questBean.getQ_type()) || "Mult".equals(questBean.getQ_type())) {
            chooseA.setText("" + questBean.getOptionA());
            chooseB.setText("" + questBean.getOptionB());
            chooseC.setText("" + questBean.getOptionC());
            chooseD.setText("" + questBean.getOptionD());
        }

        else if ("judge".equals(questBean.getQ_type())) {
            chooseA.setText("对");
            chooseB.setText("错");
            chooseC.setVisibility(View.GONE);
            chooseD.setVisibility(View.GONE);
        }

    }

    public void clearButton()
    {
        drawable = getResources().getDrawable(R.drawable.shape_normal);
        chooseA.setBackground(drawable);
        chooseB.setBackground(drawable);
        chooseC.setBackground(drawable);
        chooseD.setBackground(drawable);
    }



}
