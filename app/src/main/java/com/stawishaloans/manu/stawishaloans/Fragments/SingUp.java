package com.stawishaloans.manu.stawishaloans.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stawishaloans.manu.stawishaloans.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUp extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sing_up, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

}
