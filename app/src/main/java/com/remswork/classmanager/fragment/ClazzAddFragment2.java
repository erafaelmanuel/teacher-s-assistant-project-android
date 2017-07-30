package com.remswork.classmanager.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.remswork.classmanager.R;
import com.remswork.classmanager.helper.dao.ClazzDatabaseHelper;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Schedule;
import com.remswork.classmanager.model.clazz.Section;
import com.remswork.classmanager.model.clazz.Subject;
import com.remswork.classmanager.model.clazz.Teacher;
import com.remswork.classmanager.utils.StringFormatter;
import com.remswork.classmanager.utils.TeacherUtil;

import java.util.ArrayList;
import java.util.List;

import static com.remswork.classmanager.utils.StringFormatter.getToDate;

/**
 * Created by Rafael on 7/27/2017.
 */

public class ClazzAddFragment2 extends Fragment implements View.OnClickListener, AdapterView
        .OnItemSelectedListener {

    private Spinner mSubjectSpinner;
    private ImageView mAddSchedule;
    private TimePicker mTimePicker;
    private Spinner mDaySpinner;
    private Button mConfirmButton;
    private Button mCancelButton;
    private LayoutInflater mLayoutInflater;
    private AlertDialog mScheduleDialog;
    private EditText mDurationText;
    private EditText mRoomText;
    private List<Schedule> mSchedules;
    private RecyclerView mScheduleLayout;
    private ClazzAddFragment2Adapter adapter;
    private TextView mHeaderText;
    private EditText mSectionNameText;
    private EditText mYearText;
    private EditText mDeparmentText;
    private TextInputLayout mSectionNameTextLayout;
    private TextInputLayout mYearTextLayout;
    private TextInputLayout mDeparmentTextLayout;
    private FloatingActionButton mAddFAButton;
    private SubjectService subjectService;
    private ClazzDatabaseHelper clazzDatabaseHelper;
    private Subject subject;
    List<Subject> subjectList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        View mView = inflater.inflate(R.layout.activity_clazz_fragment_add2, container, false);

        subjectService = new SubjectServiceImpl(getActivity());
        clazzDatabaseHelper = new ClazzDatabaseHelper(getActivity());

        //Schedule list
        mSchedules = new ArrayList<>();
        mSchedules.add(new Schedule(-1, "", "", 1, "", 1));
        adapter = new ClazzAddFragment2Adapter();

        mSectionNameText = (EditText) mView.findViewById(R.id.a_class_f_add_text_sec_name);
        mYearText = (EditText) mView.findViewById(R.id.a_class_f_add_text_year);
        mDeparmentText = (EditText) mView.findViewById(R.id.a_class_f_add_text_dep);
        mSectionNameTextLayout = (TextInputLayout) mView.findViewById(
                R.id.a_class_f_add_text_sec_name_layout);
        mYearTextLayout = (TextInputLayout) mView.findViewById(
                R.id.a_class_f_add_text_year_layout);
        mDeparmentTextLayout = (TextInputLayout) mView.findViewById(
                R.id.a_class_f_add_text_dep_layout);
        mAddFAButton = (FloatingActionButton) mView.findViewById(R.id.a_class_f_add_btn_add);
        mAddFAButton.setOnClickListener(this);

        mScheduleLayout = (RecyclerView) mView.findViewById(R.id.a_class_f_add_schedule_container);
        mScheduleLayout.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleLayout.setItemAnimator(new DefaultItemAnimator());
        mScheduleLayout.setAdapter(adapter);

        mAddSchedule = (ImageView) mView.findViewById(R.id.a_class_f_view_schedule_add);
        mAddSchedule.setOnClickListener(this);

        mSubjectSpinner = (Spinner) mView.findViewById(R.id.a_class_f_add_subject_list);
        mSubjectSpinner.setAdapter(spinnerAdapter());
        mSubjectSpinner.setOnItemSelectedListener(this);

        mScheduleDialog = getScheduleDialog();
        return mView;
    }

    public ArrayAdapter<String> spinnerAdapter() {
        subjectList = subjectService.getAllSubject();
        String[] arraySubject = new String[subjectList.size()];
        int counter = 0;
        for (Subject subject : subjectList) {
            arraySubject[counter] = StringFormatter.cutLongString(subject.getName(), 40) + " (" +
                    (!subject.getCode().equals("") ? subject.getCode() : "no code") + ")";
            counter++;
        }
        return new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, arraySubject);
    }

    public boolean isInputValid(){
        boolean isVaild = true;
        if(mSectionNameText.getText().toString().trim().equals("")){
            mSectionNameTextLayout.setError("Please enter a section name");
            isVaild = false;
        }else
            mSectionNameTextLayout.setErrorEnabled(false);
        if(mYearText.getText().toString().trim().equals("")){
            mYearTextLayout.setError("Please enter a year");
            isVaild = false;
        }else
            mYearTextLayout.setErrorEnabled(false);
        if(mDeparmentText.getText().toString().trim().equals("")){
            mDeparmentTextLayout.setError("Please enter a department name");
            isVaild = false;
        }else
            mDeparmentTextLayout.setErrorEnabled(false);

        return isVaild;
    }

    public boolean isDialogInputValid(){
        boolean isValid = true;
        if(mDurationText.getText().toString().trim().equals("")){
            mHeaderText.setText("Number of time is required");
            mHeaderText.setTextColor(getResources().getColor(R.color.colorError));
            isValid = false;
        }
        return  isValid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_class_f_view_schedule_add:
                clearDialogInput();
                mScheduleDialog.show();
                break;
            case R.id.a_class_f_d_btn_cancel:
                mScheduleDialog.cancel();
                break;
            case R.id.a_class_f_d_btn_confirm:
                //Delete the no schedule item
                if (isDialogInputValid()){
                    if (mSchedules.get(0).getId() == -1)
                        adapter.deleteItem(0);

                    //Set schedule
                    String day = mDaySpinner.getSelectedItem().toString();
                    String time = getHour() + ":" + getMinute();
                    int numberOfHour = mDurationText.getText().toString().equals("") ? 0 : Integer
                            .parseInt(mDurationText.getText().toString());
                    String room = mRoomText.getText().toString();
                    Schedule schedule = new Schedule(1, day, StringFormatter.getToDate(time, 0),
                            numberOfHour, room, 1);
                    //Add item to adapter
                    adapter.addItem(schedule, adapter.getItemCount());
                    //Close the dialog
                    mScheduleDialog.cancel();
                }
                break;
            case R.id.a_class_f_add_btn_add:
                if(isInputValid()) {
                    //Teacher
                    Teacher teacher = TeacherUtil.getTeacher();

                    //Schedule
                    for (Schedule s : mSchedules) {
                        if (s.getId() == -1) {
                            mSchedules.remove(s);
                        }
                    }
                    //Section
                    Section section = new Section();
                    section.setSectionName(mSectionNameText.getText().toString());
                    section.setYear(mYearText.getText().toString() != "" ? Integer.parseInt(
                            mYearText.getText().toString()) : 0);
                    section.setDepartment(mDeparmentText.getText().toString());

                    //Clazz
                    Clazz clazz = new Clazz();
                    clazz.setSection(section);
                    clazz.setListOfSchedule(mSchedules);
                    clazz.setSubject(subject);
                    clazz.setTeacher(teacher);
                    clazzDatabaseHelper.addClazz(clazz);

                    getActivity().finish();
                }
                break;
        }
    }

    public AlertDialog getScheduleDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View view = mLayoutInflater.inflate(R.layout.activity_clazz_schedule_add_dialog, null);
        mHeaderText = (TextView) view.findViewById(R.id.a_class_f_d_add_text_header);
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

    public void clearDialogInput(){
        mHeaderText.setText("Manage your schedule");
        mHeaderText.setTextColor(getResources().getColor(R.color.colorWhite));
        mDurationText.setText("");
        mRoomText.setText("");
        mDaySpinner.setSelection(0);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = subjectList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        subject = null;
    }

    public class ClazzAddFragment2Adapter extends RecyclerView.Adapter<ClazzAddFragment2Adapter
            .ClazzAddFragment2ViewHolder> {

        @Override
        public ClazzAddFragment2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(
                    R.layout.activity_clazz_fragment_schedule_item, parent, false);
            ClazzAddFragment2ViewHolder viewHolder = new ClazzAddFragment2ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ClazzAddFragment2ViewHolder holder, int position) {
            Schedule schedule = mSchedules.get(position);
            holder.setView(schedule, position);
        }

        @Override
        public int getItemCount() {
            return mSchedules.size();
        }

        public void deleteItem(int position) {
            mSchedules.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeRemoved(position, mSchedules.size());

        }

        public void addItem(Schedule schedule, int position) {
            mSchedules.add(schedule);
            notifyItemInserted(position);
            notifyItemRangeChanged(position, mSchedules.size());
        }


        public class ClazzAddFragment2ViewHolder extends RecyclerView.ViewHolder implements View
                .OnClickListener {

            private CardView cardView;
            private TextView textView;
            private ImageView imageView;
            private Schedule schedule;
            private int position;

            public ClazzAddFragment2ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.a_class_f_add_schedule_card);
                textView = (TextView) itemView.findViewById(R.id.a_class_f_add_schedule_card_text);
                imageView = (ImageView) itemView.findViewById(
                        R.id.a_class_f_add_schedule_card_remove);
                imageView.setOnClickListener(this);
            }

            public void setView(Schedule schedule, int position) {
                this.schedule = schedule;
                this.position = position;
                String message;
                if (schedule.getId() != -1)
                    message = schedule.getDay() + " / " + "Room " + schedule.getRoom() + " / " +
                            schedule.getTime() + " - " + getToDate(schedule.getTime(),
                            schedule.getHour());
                else
                    message = "No schedule";
                textView.setText(message);
            }

            @Override
            public void onClick(View v) {
                deleteItem(position);
                if (mSchedules.size() < 1)
                    mSchedules.add(new Schedule(-1, null, null, -1, null, -1));
            }

        }
    }

}
