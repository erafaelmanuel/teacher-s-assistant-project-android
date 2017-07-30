package com.remswork.classmanager.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remswork.classmanager.R;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.model.clazz.Schedule;
import com.remswork.classmanager.model.clazz.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rem-sama on 7/27/2017.
 */

@Deprecated
public class ClazzAddFragment extends Fragment implements View.OnClickListener {

    private Spinner mSubjectSpinner;
    private SubjectService subjectService;
    private ImageView mAddSchedule;
    private TimePicker mTimePicker;
    private Spinner mDaySpinner;
    private Button mConfirmButton;
    private Button mCancelButton;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mScheduleDialog;
    private EditText mDurationText;
    private EditText mRoomText;
    private List<Schedule> schedules;
    private LinearLayout mScheduleLayout;
    private List<ViewHolder> mViewHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        schedules = new ArrayList<>();
        subjectService = new SubjectServiceImpl(getActivity());
        mViewHolder = new ArrayList<>();

        View mView = inflater.inflate(R.layout.activity_clazz_fragment_add, container, false);
        mScheduleLayout = (LinearLayout) mView.findViewById(R.id.a_class_f_add_schedule_container);
        mAddSchedule = (ImageView) mView.findViewById(R.id.a_class_f_view_schedule_add);
        mAddSchedule.setOnClickListener(this);
        mSubjectSpinner = (Spinner) mView.findViewById(R.id.a_class_f_view_subject_list);
        setSpinner();
        mScheduleDialog = getScheduleDialog();
        return mView;
    }

    public void setSpinner() {
        List<Subject> subjectList = subjectService.getAllSubject();
        String[] arraySubject = new String[subjectList.size()];

        int counter = 0;
        for (Subject subject : subjectList) {
            arraySubject[counter] = stringFormat(subject.getName()) + " (" +
                    (!subject.getCode().equals("") ? subject.getCode() : "no code") + ")";
            counter++;
        }
        ArrayAdapter subjectArrayAdapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, arraySubject);
        mSubjectSpinner.setAdapter(subjectArrayAdapter);
        mSubjectSpinner.setVisibility(View.VISIBLE);
    }

    public String stringFormat(final String word) {
        if (word.toCharArray().length <= 40)
            return word;
        else
            return word.substring(0, 40) + "...";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_class_f_view_schedule_add:
                mScheduleDialog.show();
                break;
            case R.id.a_class_f_d_btn_cancel:
                mScheduleDialog.cancel();
                break;
            case R.id.a_class_f_d_btn_confirm:
                String day = mDaySpinner.getSelectedItem().toString();
                String time = getHour() + ":" + getMinute();
                int numberOfHour = mDurationText.getText().toString().equals("") ? 0 : Integer
                        .parseInt(mDurationText.getText().toString());
                String room = mRoomText.getText().toString();

                Schedule currentSchedule = new Schedule();
                currentSchedule.setDay(day);
                currentSchedule.setTime(time);
                currentSchedule.setHour(numberOfHour);
                currentSchedule.setRoom(room);

                schedules.add(currentSchedule);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.setView(currentSchedule, schedules.size() - 1);
                mViewHolder.add(viewHolder);
                notifyChange();
                mScheduleDialog.cancel();
                break;
            case R.id.a_class_f_add_schedule_card_remove:
                break;
        }
    }

    public void notifyChange() {
        mScheduleLayout.removeAllViews();
        int counter = 0;
        for (ViewHolder v : mViewHolder) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, getPixel(20), 0, getPixel(20));
            mScheduleLayout.addView(v.getView(), params);
            v.renewPosition(counter);
            counter ++;
        }

    }

    public int getPixel(final int value){
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return  (int) (value * scale + 0.5f);
    }

    public AlertDialog getScheduleDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = mLayoutInflater.inflate(R.layout.activity_clazz_schedule_add_dialog, null);
        mTimePicker = (TimePicker) view.findViewById(R.id.a_class_f_d_add_timepicker);
        mDaySpinner = (Spinner) view.findViewById(R.id.a_class_f_d_add_spinnerDay);
        mConfirmButton = (Button) view.findViewById(R.id.a_class_f_d_btn_confirm);
        mCancelButton = (Button) view.findViewById(R.id.a_class_f_d_btn_cancel);
        mDurationText = (EditText) view.findViewById(R.id.a_class_f_d_text_duration);
        mRoomText = (EditText) view.findViewById(R.id.a_class_f_d_text_room);
        String days[] = new String[7];
        days[0] = "Sunday";
        days[1] = "Monday";
        days[2] = "Tuesday";
        days[3] = "Wednesday";
        days[4] = "Thursday";
        days[5] = "Friday";
        days[6] = "Saturday";
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, days);

        mDaySpinner.setAdapter(arrayAdapter);
        mCancelButton.setOnClickListener(this);
        mConfirmButton.setOnClickListener(this);
        mBuilder.setView(view);
        return mBuilder.create();
    }

    public int getHour() {
        if (Build.VERSION.SDK_INT >= 23)
            return mTimePicker.getHour();
        else
            return mTimePicker.getCurrentHour();
    }

    public int getMinute() {
        if (Build.VERSION.SDK_INT >= 23)
            return mTimePicker.getMinute();
        else
            return mTimePicker.getCurrentMinute();
    }

    public String getToDate(final String time, final int numberOfHour) {
        try {
            Toast.makeText(getActivity(), time + "", Toast.LENGTH_LONG).show();
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1].split(" ")[0].trim());
            String meridiem = (time.split(":")[1].split(" ").length > 1 ?
                    time.split(":")[1].split(" ")[1] : null);

            minute += numberOfHour;
            hour += (int) (minute >= 60 ? minute / 60 : 0);
            minute %= 60;

            if (meridiem != null) {
                if (meridiem.equalsIgnoreCase("PM"))
                    hour += 12;
            }
            return String.format(Locale.ENGLISH, "%s:%02d %s", (hour <= 12 ? hour : hour % 12 == 0
                            ? 12 : hour % 12),
                    minute, (hour > 11 && hour < 24 ? "PM" : "AM"));
        } catch (NumberFormatException e) {
            return "Time not set";
        }
    }


    public class ViewHolder implements View.OnClickListener {

        private View view;
        private CardView cardView;
        private TextView textView;
        private ImageView imageView;
        private Schedule mSchedule;
        private int mPosition;

        public ViewHolder() {
            view = mLayoutInflater.inflate(R.layout.activity_clazz_fragment_schedule_item, null);
            cardView = (CardView) view.findViewById(R.id.a_class_f_add_schedule_card);
            textView = (TextView) view.findViewById(R.id.a_class_f_add_schedule_card_text);
            imageView = (ImageView) view.findViewById(
                    R.id.a_class_f_add_schedule_card_remove);

        }

        public void setView(final Schedule schedule, final int position) {
            mSchedule = schedule;
            mPosition = position;
            String message = schedule.getDay() + " / " + "Room CS101" + " / " + schedule.getTime() +
                    " - " + getToDate(schedule.getTime(), schedule.getHour());
            textView.setText(message);
            imageView.setOnClickListener(this);
        }

        public CardView getView() {
            return cardView;
        }

        public void deleteItem() {
            schedules.remove(mPosition);
            mViewHolder.remove(mPosition);
            notifyChange();
        }

        public void renewPosition(final int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            deleteItem();
        }
    }
}
