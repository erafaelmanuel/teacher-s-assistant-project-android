package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Schedule;

import java.util.List;

/**
 * Created by Rafael on 7/13/2017.
 */

public interface ScheduleService {

    boolean addSchedule(Schedule schedule);
    int addSchedule(List<Schedule> listOfSchedule);
    int addSchedule(Schedule... schedules);
    Schedule getScheduleById(int id);
    List<Schedule> getScheduleByClazzId(int classId);
    List<Schedule> getAllSchedule();
    boolean updateScheduleById(int id, Schedule newSchedule);
    int updateScheduleByClassId(int classId, Schedule newSchedule);
    boolean deleteScheduleById(int id);
    int deleteScheduleByClassId(int classId);

}
