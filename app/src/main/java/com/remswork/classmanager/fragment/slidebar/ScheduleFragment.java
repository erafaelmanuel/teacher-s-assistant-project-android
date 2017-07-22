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

public class ScheduleFragment extends Fragment{

    public ScheduleFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slidebar_schedule, container, false);
    }
}
