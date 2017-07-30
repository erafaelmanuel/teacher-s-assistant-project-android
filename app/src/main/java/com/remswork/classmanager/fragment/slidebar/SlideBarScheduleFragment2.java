package com.remswork.classmanager.fragment.slidebar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.remswork.classmanager.R;
import com.remswork.classmanager.adapter.ClazzRecyclerViewAdapter;
import com.remswork.classmanager.adapter.ScheduleListViewAdapter;
import com.remswork.classmanager.adapter.ScheduleRecyclerViewAdapter;
import com.remswork.classmanager.helper.service.ScheduleService;
import com.remswork.classmanager.helper.service.impl.ScheduleServiceImpl;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 7/23/2017.
 */

public class SlideBarScheduleFragment2 extends Fragment{

    private Context context;
    private RecyclerView scheduleRecyclerView;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    private List<Schedule> schedules;
    private ScheduleService scheduleService;
    private View customView;

    public SlideBarScheduleFragment2(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        customView = inflater.inflate(R.layout.fragment_slidebar_schedule2, container, false);
        init();
        return customView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void init(){
        scheduleService = new ScheduleServiceImpl(context);
//        schedules = scheduleService.getAllSchedule();
//            schedules = new ArrayList<>();
        schedules = scheduleService.getScheduleByTeacherId(
                new TeacherServiceImpl(getActivity()).getListOfTeacher().get(0).getId());
        scheduleRecyclerView = (RecyclerView)
                customView.findViewById(R.id.fragment_slidebar_recyclerview_schedule);
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(context, schedules);
        scheduleRecyclerView.setAdapter(scheduleRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        scheduleRecyclerView.setLayoutManager(layoutManager);
        scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView(true);
    }

    //isAlways recommend to false
    public void refreshRecyclerView(boolean isAlways){
        if(scheduleRecyclerViewAdapter != null){
            if(scheduleService.getAllSchedule().size() != scheduleRecyclerViewAdapter.getItemCount()
                    || isAlways){
                List<Schedule> scheduleList = scheduleService.getScheduleByTeacherId(
                        new TeacherServiceImpl(getActivity()).getListOfTeacher().get(0).getId());
                scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(context,
                        scheduleList);
                scheduleRecyclerView.setAdapter(scheduleRecyclerViewAdapter);
            }
        }
    }
}
