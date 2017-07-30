package com.remswork.classmanager.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.remswork.classmanager.R;
import com.remswork.classmanager.helper.dao.ClazzDatabaseHelper;
import com.remswork.classmanager.model.clazz.Schedule;

import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael on 7/23/2017.
 */

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter
        .ScheduleViewHolder>{

    private Context context;
    private List<Schedule> schedules;
    private LayoutInflater inflater;
    private ClazzDatabaseHelper clazzDatabaseHelper;

    public ScheduleRecyclerViewAdapter(Context context, List<Schedule> schedules){
        this.schedules = schedules;
        this.context = context;
        inflater = LayoutInflater.from(context);
        clazzDatabaseHelper = new ClazzDatabaseHelper(context);
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = inflater.inflate(R.layout.fragment_slidebar_cardview_schedule,
                parent, false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(customView);
        return  scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder scheduleViewHolder, int position) {
        Schedule schedule = schedules.get(position);
        scheduleViewHolder.setView(schedule, position);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    protected class ScheduleViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{

        private ImageView imageView;
        private TextView textScheduleDetail;
        private TextView textSubjectName;
        private CardView cardView;
        private Schedule schedule;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_imageview);
            textScheduleDetail = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_schedule);
            textSubjectName = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_subject);
            cardView = (CardView) itemView.findViewById(R.id.fragment_slidebar_cardview_schedule);
        }

        public void setView(Schedule currentSchedule, int position) {
            this.schedule = currentSchedule;
            imageView.setImageResource(setUpImageResourceForDay(schedule.getDay()));
            textScheduleDetail.setText("1A / " + schedule.getRoom() + " / " + schedule.getTime() +
                    " - " + getToDate(schedule.getTime(), schedule.getHour()));
            textSubjectName.setText(schedule.getSubjectName());
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, schedule.getId() + "", Toast.LENGTH_LONG).show();
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
}