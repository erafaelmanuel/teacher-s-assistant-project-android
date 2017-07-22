package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.remswork.classmanager.adapter.SlideBarViewPagerAdapter;
import com.remswork.classmanager.fragment.slidebar.ClazzFragment;
import com.remswork.classmanager.fragment.slidebar.ScheduleFragment;
import com.remswork.classmanager.fragment.slidebar.StudentFragment;
import com.remswork.classmanager.fragment.slidebar.SubjectFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar customToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public void initializeWidget(){
        customToolbar = (Toolbar) findViewById(R.id.activity_main_custom_toolbar);
        customToolbar.setTitle("Class Manager");


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(customToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            default:
                break;
        }
        return true;
    }

    public void clickLogOut(){
        saveTeacherSharedPreference("","");
        activityStart(null, LoginActivity.class);
    }

    public void activityStart(HashMap attribute, Class activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("attribute", attribute);
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
        slideBarViewPagerAdapter.addFrag(new ScheduleFragment(), "Schedule");
        slideBarViewPagerAdapter.addFrag(new ClazzFragment(), "Class");
        slideBarViewPagerAdapter.addFrag(new SubjectFragment(), "Subject");
        slideBarViewPagerAdapter.addFrag(new StudentFragment(), "Student");
        viewPager.setAdapter(slideBarViewPagerAdapter);
    }

}
