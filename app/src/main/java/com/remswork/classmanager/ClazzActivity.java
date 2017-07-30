package com.remswork.classmanager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.remswork.classmanager.fragment.ClazzAddFragment;
import com.remswork.classmanager.fragment.ClazzAddFragment2;
import com.remswork.classmanager.fragment.ClazzViewFragment;

import static android.R.attr.type;

/**
 * Created by Rem-sama on 7/26/2017.
 */

public class ClazzActivity extends AppCompatActivity {


    public static final int VIEW = 65126124;
    public static final int ADD = 12542511;
    public static final int UPDATE = 9199173;
    private Bundle clazzInfoBundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_clazz_custom_toolbar);
        setSupportActionBar(toolbar);

        //Set fragment
        clazzInfoBundle = getIntent().getExtras();
        setFragment(clazzInfoBundle.getInt("typeRequest"));

    }

    public void setFragment(final int typeRequest){
        if(typeRequest == VIEW){
            ClazzViewFragment fragment = new ClazzViewFragment();
            fragment.setArguments(clazzInfoBundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_clazz_fragment_layout, fragment);
            fragmentTransaction.commit();
        }else if(typeRequest == ADD){
            ClazzAddFragment2 fragment = new ClazzAddFragment2();
            fragment.setArguments(clazzInfoBundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_clazz_fragment_layout, fragment);
            fragmentTransaction.commit();
        }
    }
}
