package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.remswork.classmanager.adapter.ClazzRecyclerViewAdapter;
import com.remswork.classmanager.adapter.SlideBarViewPagerAdapter;
import com.remswork.classmanager.adapter.SubjectRecyclerViewAdapter;
import com.remswork.classmanager.fragment.slidebar.SlideBarScheduleFragment2;
import com.remswork.classmanager.fragment.slidebar.SlideBarSubjectFragment;
import com.remswork.classmanager.fragment.slidebar.SliderBarClazzFragment;
import com.remswork.classmanager.fragment.slidebar.StudentFragment;
import com.remswork.classmanager.helper.dao.ClazzDatabaseHelper;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.model.clazz.Clazz;
import com.remswork.classmanager.model.clazz.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        SubjectRecyclerViewAdapter.SubjectRecyclerViewAdapterListener,
        SlideBarSubjectFragment.SlideBarSubjectFragmentListener, ClazzRecyclerViewAdapter
        .ClazzRecyclerViewAdapterListener, SliderBarClazzFragment.SliderBarClazzFragmentListener{

    private Toolbar customToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SubjectService subjectService;
    private ClazzDatabaseHelper clazzDatabaseHelper;

    public void initializeWidget(){
        customToolbar = (Toolbar) findViewById(R.id.activity_main_custom_toolbar);
        customToolbar.setTitle("Teacher's assistant APP");

        viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.activity_main_tab);

        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subjectService = new SubjectServiceImpl(this);
        clazzDatabaseHelper = new ClazzDatabaseHelper(this);
        initializeWidget();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_logout :
                clickLogOut();
                break;
            case android.R.id.home :
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }

    public void clickLogOut(){
        saveTeacherSharedPreference("","");
        activityStart(null, LoginActivity.class);
    }

    @Deprecated
    public void activityStart(final Subject subject, Class activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("subject", subject);
        intent.putExtra("typeRequest", 0);
        startActivity(intent);
    }

    public void activityStart(final Subject subject, final int typeRequest){
        Intent intent = new Intent(this, SubjectActivity.class);
        intent.putExtra("subject", subject);
        intent.putExtra("typeRequest", typeRequest);
        startActivity(intent);
    }

    public void activityStart(final Clazz clazz, final int typeRequest){
        Intent intent = new Intent(this, ClazzActivity.class);
        if(clazz != null) {
            intent.putExtra("subject", clazz.getSubject());
            intent.putExtra("section", clazz.getSection());
            intent.putParcelableArrayListExtra("scheduleList", (ArrayList<? extends Parcelable>)
                    clazz.getListOfSchedule());
        }
        intent.putExtra("typeRequest", typeRequest);
        startActivity(intent);
    }

    public void saveTeacherSharedPreference(String email, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cemail", email);
        editor.putString("cpassword", password);
        editor.apply();
    }

    private void setupViewPager(ViewPager viewPager) {
        SlideBarViewPagerAdapter slideBarViewPagerAdapter = new SlideBarViewPagerAdapter(
                getSupportFragmentManager());
        slideBarViewPagerAdapter.addFrag(new SlideBarScheduleFragment2(), "Schedule");
        slideBarViewPagerAdapter.addFrag(new SliderBarClazzFragment(), "Class");
        slideBarViewPagerAdapter.addFrag(new SlideBarSubjectFragment(), "Subject");
        slideBarViewPagerAdapter.addFrag(new StudentFragment(), "Student");
        viewPager.setAdapter(slideBarViewPagerAdapter);
    }

    @Override
    public void openSubject(Subject subject) {
        activityStart(subject, SubjectActivity.VIEW);
    }

    @Override
    public void deleteSubject(int id) {
        subjectService.deleteSubjectById(id);
    }

    @Override
    public void openSubject() {
        activityStart((Subject) null, SubjectActivity.ADD);
    }

    @Override
    public void updateSubject(int id, Subject newSubject) {
        activityStart(newSubject, SubjectActivity.UPDATE);
    }

    @Override
    public void viewClazz(Clazz clazz) {
        activityStart(clazz, ClazzActivity.VIEW);
    }

    @Override
    public void deleteClazz(int clazzId) {
        clazzDatabaseHelper.deleteClazzById(clazzId);
    }

    @Override
    public void addClass() {
        activityStart((Clazz) null, ClazzActivity.ADD);
    }
}
