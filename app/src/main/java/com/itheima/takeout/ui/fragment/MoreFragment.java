package com.itheima.takeout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.ui.activity.SettleCenterActivity;

/**
 * Created by ywf on 2017/3/24.
 */
public class MoreFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        Button button = (Button) view.findViewById(R.id.btn);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getContext().startActivity(new Intent(getContext(),SettleCenterActivity.class));

//        Toast.makeText(getActivity(), "被点击了", Toast.LENGTH_SHORT).show();
    }
}
