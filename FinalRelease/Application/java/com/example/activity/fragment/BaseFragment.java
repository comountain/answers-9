package com.example.activity.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{
    public FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle bundle)
    {
        super.onCreate(bundle);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =initView();
        return view;
    }

    protected abstract View initView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    public abstract void initData();

    void initListener(){};
}
