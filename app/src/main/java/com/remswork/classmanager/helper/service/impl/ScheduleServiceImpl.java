package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.ScheduleDatabaseHelper;
import com.remswork.classmanager.helper.service.ScheduleService;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.List;

/**
 * Created by Rafael on 7/21/2017.
 */

public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleDatabaseHelper scheduleDatabaseHelper;

    public ScheduleServiceImpl(Context context){
          scheduleDatabaseHelper = new ScheduleDatabaseHelper(context);
    }

    @Override
    public boolean addSchedule(Schedule schedule) {
        return scheduleDatabaseHelper.addSchedule(schedule);
    }

    @Override
    public int addSchedule(List<Schedule> listOfSchedule) {
        return scheduleDatabaseHelper.addSchedule(listOfSchedule);
    }

    @Override
    public int addSchedule(Schedule... schedules) {
        return scheduleDatabaseHelper.addSchedule(schedules);
    }

    @Override
    public Schedule getScheduleById(int id) {
        return scheduleDatabaseHelper.getScheduleById(id);
    }

    @Override
    public List<Schedule> getScheduleByClazzId(int classId) {
        return scheduleDatabaseHelper.getScheduleByClazzId(classId);
    }

    @Override
    public List<Schedule> getScheduleByTeacherId(int teacherId) {
        return scheduleDatabaseHelper.getScheduleByTeacherId(teacherId);
    }

    @Override
    public List<Schedule> getAllSchedule() {
        return scheduleDatabaseHelper.getAllSchedule();
    }

    @Override
    public boolean updateScheduleById(int id, Schedule newSchedule) {
        return scheduleDatabaseHelper.updateScheduleById(id, newSchedule);
    }

    @Override
    public int updateScheduleByClassId(int classId, Schedule newSchedule) {
        return scheduleDatabaseHelper.updateScheduleByClassId(classId, newSchedule);
    }

    @Override
    public boolean deleteScheduleById(int id) {
        return scheduleDatabaseHelper.deleteScheduleById(id);
    }

    @Override
    public int deleteScheduleByClassId(int classId) {
        return scheduleDatabaseHelper.deleteScheduleByClassId(classId);
    }
}
