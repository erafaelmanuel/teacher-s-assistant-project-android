package com.remswork.classmanager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.remswork.classmanager.R;
import com.remswork.classmanager.model.clazz.Schedule;
import com.remswork.classmanager.model.clazz.Section;
import com.remswork.classmanager.model.clazz.Subject;

import java.util.List;
import java.util.Locale;

/**
 * Created by Rem-sama on 7/27/2017.
 */

public class ClazzViewFragment extends Fragment {

    private TextView textSubjectName;
    private TextView textSubjectDesc;
    private TextView textSectionDetail;
    private TextView textSectionDep;
    private TextView textTermType;
    private TextView textTermTypeCur;
    private Bundle clazzInfoBundle;
    private Subject subject;
    private Section section;
    private List<Schedule> scheduleList;
    private LinearLayout scheduleContainerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View customView = inflater.inflate(
                R.layout.activity_clazz_fragment_view, container, false);

        clazzInfoBundle = getArguments();
        subject = clazzInfoBundle.getParcelable("subject");
        section = clazzInfoBundle.getParcelable("section");
        scheduleList = clazzInfoBundle.getParcelableArrayList("scheduleList");
        textSubjectName = (TextView) customView.findViewById(R.id.a_class_f_view_subject_name);
        textSubjectDesc = (TextView) customView.findViewById(R.id.a_class_f_view_subject_desc);
        textSectionDetail = (TextView) customView.findViewById(R.id.a_class_f_view_section_detail);
        textSectionDep = (TextView) customView.findViewById(R.id.a_class_f_view_section_dep);
        scheduleContainerLayout = (LinearLayout) customView.findViewById(
                R.id.a_class_f_view_schedule_container);

        setView();
        return customView;
    }

    public void setView(){
        if(scheduleList.size() == 0)
            scheduleContainerLayout.addView(getScheduleView("No schedule"));
        else {
            for (Schedule schedule : scheduleList)
                scheduleContainerLayout.addView(
                        getScheduleView(""+ schedule.getDay() +" / "+ schedule.getRoom() +" / " +
                                getToDate(schedule.getTime(), schedule.getHour())));
        }
        if(subject != null) {
            textSubjectName.setText(subject.getName());
            textSubjectDesc.setText(subject.getDesc());
        }else{
            textSubjectName.setText("No subject");
            textSubjectDesc.setText("No description");
        }if(section != null) {
            textSectionDetail.setText(String.format(Locale.ENGLISH, "%s-%d%s", section.getDepartment(),
                    section.getYear(), section.getSectionName()));
            textSectionDep.setText(section.getDepartment());
        }else{
            textSectionDetail.setText("No Section");
            textSectionDep.setText("No Department");
        }
    }

    public CardView getScheduleView(final String messageToDisplay){

        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 0);
        CardView cardView = new CardView(getActivity());
        cardView.setRadius(5);
        cardView.setMaxCardElevation(9);
        cardView.setCardElevation(5);
        cardView.setLayoutParams(params);
        cardView.setUseCompatPadding(true);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, getPixel(40)
        );
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.setBackgroundResource(R.color.colorTweeterBlue);
        TableRow.LayoutParams textParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TextView textView = new TextView(getActivity());
        textView.setText(messageToDisplay);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setGravity(Gravity.CENTER);

        layout.addView(textView, layoutParams);
        cardView.addView(layout);

        return cardView;
    }

    public int getPixel(final int value){
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return  (int) (value * scale + 0.5f);
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
