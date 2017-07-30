package com.remswork.classmanager.fragment.slidebar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.remswork.classmanager.R;
import com.remswork.classmanager.adapter.ScheduleListViewAdapter;
import com.remswork.classmanager.helper.service.ScheduleService;
import com.remswork.classmanager.helper.service.impl.ScheduleServiceImpl;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.List;

/**
 * Created by Verlie on 7/22/2017.
 */

@Deprecated
public class SlideBarScheduleFragment extends Fragment{

    private Context context;
    private ListView scheduleListView;
    private ScheduleListViewAdapter scheduleListViewAdapter;
    private List<Schedule> schedules;
    private ScheduleService scheduleService;
    private View customView;

    public SlideBarScheduleFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        customView = inflater.inflate(R.layout.fragment_slidebar_schedule, container, false);
        init();
        return customView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TESTTAG", "onCreate");


    }

    public void init(){
        scheduleService = new ScheduleServiceImpl(context);
        schedules = scheduleService.getAllSchedule();

        scheduleListView = (ListView) customView.findViewById(R.id.fragment_slidebar_listview);
        scheduleListViewAdapter = new ScheduleListViewAdapter(context, schedules);
        scheduleListView.setAdapter(scheduleListViewAdapter);
    }
}
