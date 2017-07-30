package com.remswork.classmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.remswork.classmanager.R;
import com.remswork.classmanager.helper.dao.ClazzDatabaseHelper;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael on 7/23/2017.
 */

@Deprecated
public class ScheduleListViewAdapter extends ArrayAdapter<Schedule> {

    private List<Schedule> schedules;
    private ImageView imageView;
    private TextView subjectNameTextView;
    private TextView scheduleTextView;

    private ClazzDatabaseHelper clazzDatabaseHelper;

    public ScheduleListViewAdapter(Context context, List<Schedule> schedules) {
        super(context, R.layout.fragment_slidebar_listview_schedule, schedules);
        this.schedules = schedules;
        clazzDatabaseHelper = new ClazzDatabaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(
                R.layout.fragment_slidebar_listview_schedule, parent, false);
        Schedule schedule = schedules.get(position);

        imageView = (ImageView) customView.findViewById(
                R.id.fragment_slidebar_listview_schedule_imageview);
        subjectNameTextView = (TextView) customView.findViewById(
                R.id.fragment_slidebar_listview_schedule_text_subject);
        scheduleTextView = (TextView) customView.findViewById(
                R.id.fragment_slidebar_listview_schedule_text_schedule);

        imageView.setImageResource(setUpImageResourceForDay(schedule.getDay()));
        subjectNameTextView.setText(
                clazzDatabaseHelper.getClazzById(schedule.getClazzId()).getSubject().getName());

        scheduleTextView.setText("1A / " + schedule.getRoom() + " / " + schedule.getTime() +
                " - " + getToDate(schedule.getTime(), schedule.getHour()));
        return customView;
    }

    public int setUpImageResourceForDay(final String day){
        if(day.equalsIgnoreCase("monday"))
            return R.drawable.logo_day_monday;
        else if(day.equalsIgnoreCase("tuesday"))
            return R.drawable.logo_day_tuesday;
        else if(day.equalsIgnoreCase("wednesday"))
            return R.drawable.logo_day_wednesday;
        else if(day.equalsIgnoreCase("thursday"))
            return R.drawable.logo_day_thursday;
        else if(day.equalsIgnoreCase("friday"))
            return R.drawable.logo_day_friday;
        else if(day.equalsIgnoreCase("saturday"))
            return R.drawable.logo_day_saturday;
        else
            return R.drawable.logo_day_sunday;
    }

    public String getToDate(final String time, final int numberOfHour){

        try {
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1].split(" ")[0].trim());

            minute += numberOfHour;
            hour += (int) (minute >= 60 ? minute / 60 : 0);
            minute %= 60;

            return String.format(Locale.ENGLISH, "%s:%02d %s", (hour <= 12 ? hour : hour % 12),
                    minute, (hour > 12 ? "PM" : "AM"));
        }catch (NumberFormatException e){
            return "Time not set";
        }
    }
}
