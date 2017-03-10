package com.jiangpingwei.cowpea.chongdian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangpingwei.cowpea.R;

/**
 * Created by jiangpingwei on 2017/2/22.
 */

public class ChongdianFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chongdian, container, false);
        return view;
    }
}
