package com.remswork.classmanager.fragment.slidebar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remswork.classmanager.R;

/**
 * Created by Verlie on 7/22/2017.
 */

public class ClazzFragment extends Fragment {

    public ClazzFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slidebar_clazz, container, false);
    }
}
