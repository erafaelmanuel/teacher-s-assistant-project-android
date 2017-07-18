package com.remswork.classmanager.service;

import com.remswork.classmanager.model.Clazz;
import com.remswork.classmanager.model.Schedule;

import java.util.List;

/**
 * Created by Rem-sama on 7/13/2017.
 */

public interface ScheduleService {

    boolean addSchedule(Schedule schedule);
    List<Schedule> getSchedulesByClassSectionId(int id);
    List<Schedule> getSchedulesByClassSection(Clazz clazz);
    List<Schedule> getSchedulesByDay(String day);
    List<Schedule> getScheduleToday();
    List<Schedule> getScheduleTomorrow();
    boolean deleteSchedule(Schedule schedule);
}
