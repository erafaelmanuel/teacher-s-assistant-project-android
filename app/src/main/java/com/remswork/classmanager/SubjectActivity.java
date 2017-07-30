package com.remswork.classmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.remswork.classmanager.fragment.SubjectAddFragment;
import com.remswork.classmanager.fragment.SubjectUpdateFragment;
import com.remswork.classmanager.fragment.SubjectViewFragment;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.model.clazz.Subject;

import static android.R.attr.fragment;
import static android.R.attr.id;

/**
 * Created by Rafael on 7/25/2017.
 */

public class SubjectActivity extends AppCompatActivity implements SubjectAddFragment
        .SubjectAddFragmentListener, SubjectUpdateFragment.SubjectUpdateFragmentListener{

    public static final int VIEW = 1234;
    public static final int ADD = 2143;
    public static final int UPDATE = 4321;

    private Subject subject;
    private SubjectService subjectService;
    private Bundle bundle;
    private int typeRequest;
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        layout = (LinearLayout) findViewById(R.id.fragment_slidebar_subject_add_layout);
        //Extras
        bundle = getIntent().getExtras();
        subjectService = new SubjectServiceImpl(this);
        subject = (Subject) bundle.getParcelable("subject");
        typeRequest = (int) bundle.getInt("typeRequest");

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_subject_custom_toolbar);
        toolbar.setTitle((subject!=null?subject.getName():"Teacher's assistant APP"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Extras
        Bundle bundle = getIntent().getExtras();

        //Request
        if(typeRequest == SubjectActivity.VIEW) {
            SubjectViewFragment fragment = new SubjectViewFragment();
            fragment.setArguments(bundle);
            setFragment(fragment);
        }else if(typeRequest == SubjectActivity.ADD){
            SubjectAddFragment fragment = new SubjectAddFragment();
            setFragment(fragment);
        }else if(typeRequest == SubjectActivity.UPDATE){
            SubjectUpdateFragment fragment = new SubjectUpdateFragment();
            fragment.setArguments(bundle);
            setFragment(fragment);
        }

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.activity_subject_layout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                break;
            default:
                Toast.makeText(this,"You select somthing",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void saveSubject(Subject subject){
        subjectService.addSubject(subject);
        Toast.makeText(this,"You've successfuly add subject",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void updateSubject(int subjectId, Subject newSubject) {
        subjectService.updateSubjectById(subjectId, newSubject);
        Toast.makeText(this,"You've successfuly update subject",Toast.LENGTH_LONG).show();
        finish();
    }
}
