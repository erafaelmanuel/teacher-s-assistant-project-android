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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verlie on 7/22/2017.
 */

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

        Log.i("TESTTAG", "onCreateView");
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
        schedules = new ArrayList<Schedule>();
        //schedules = scheduleService.getAllSchedule();

        Schedule schedule1 = new Schedule(1,"Friday","9:00 PM",120,"Room CS 101",1);
        Schedule schedule2 = new Schedule(2,"Tuesday","cat",2,"",1);
//        Schedule schedule3 = new Schedule(1,"Monday","dog",2,"",1);
//        Schedule schedule8 = new Schedule(2,"Thuesday","cat",2,"",1);
//        Schedule schedule7 = new Schedule(1,"Monday","dog",2,"",1);
//        Schedule schedule4 = new Schedule(2,"Thuesday","cat",2,"",1);
//        Schedule schedule5 = new Schedule(1,"Monday","dog",2,"",1);
//        Schedule schedule6 = new Schedule(2,"Thuesday","cat",2,"",1);
//        Schedule schedule9 = new Schedule(2,"Thuesday","cat",2,"",1);
//        Schedule schedule10 = new Schedule(1,"Monday","dog",2,"",1);
//        Schedule schedule11 = new Schedule(2,"Thuesday","cat",2,"",1);
        schedules.add(schedule1);
        schedules.add(schedule2);
//        schedules.add(schedule3);
//        schedules.add(schedule4);
//        schedules.add(schedule5);
//        schedules.add(schedule6);
//        schedules.add(schedule7);
//        schedules.add(schedule8);
//        schedules.add(schedule9);
//        schedules.add(schedule10);
//        schedules.add(schedule11);


        scheduleListView = (ListView) customView.findViewById(R.id.fragment_slidebar_listview);
        scheduleListViewAdapter = new ScheduleListViewAdapter(context, schedules);
        scheduleListView.setAdapter(scheduleListViewAdapter);
    }
}
