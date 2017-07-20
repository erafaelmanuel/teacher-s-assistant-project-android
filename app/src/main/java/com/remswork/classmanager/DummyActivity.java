package com.remswork.classmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.remswork.classmanager.helper.dao.ScheduleDatabaseHelper;
import com.remswork.classmanager.helper.dao.SectionDatabaseHelper;
import com.remswork.classmanager.helper.dao.StudentDatabaseHelper;
import com.remswork.classmanager.helper.dao.SubjectDatabaseHelper;
import com.remswork.classmanager.helper.dao.TeacherDatabaseHelper;
import com.remswork.classmanager.helper.service.SectionService;
import com.remswork.classmanager.helper.service.SectionStudentListService;
import com.remswork.classmanager.helper.service.StudentService;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.helper.service.impl.SectionServiceImpl;
import com.remswork.classmanager.helper.service.impl.SectionStudentListServiceImpl;
import com.remswork.classmanager.helper.service.impl.StudentServiceImpl;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;
import com.remswork.classmanager.model.Schedule;
import com.remswork.classmanager.model.Section;
import com.remswork.classmanager.model.Student;
import com.remswork.classmanager.model.Subject;
import com.remswork.classmanager.model.Teacher;

/**
 * Created by Rafael on 7/4/2017.
 */

public class DummyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        toolbar.setTitle("Dummy You");
        //setSupportActionBar(toolbar);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            toolbar.setElevation(10f);

//        toolbar.inflateMenu(R.menu.menu_main);
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                Toast.makeText(DummyActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onClick(View view) {
        //SubjectDatabaseHelper subjectDatabaseHelper = new SubjectDatabaseHelper(this);
        //subjectDatabaseHelper.upgradeTable(true);
        //subjectDatabaseHelper.deleteSubjectById(1);
      // Toast.makeText(this, subjectDatabaseHelper.addSubject(new Subject(1 , "name","code","desc", 3, 1)) + "", Toast.LENGTH_LONG)
       //Toast.makeText(this, subjectDatabaseHelper.getAllSubject().size() + "", Toast.LENGTH_LONG)
        StudentDatabaseHelper helper  = new StudentDatabaseHelper(this);
        //helper.upgradeTable(true);

        //helper.updateStudentById(1, new Student(1, "aris","beta","coconut", 1, "m", 1,-1));
        //helper.deleteStudentById(1);
        //Toast.makeText(this, helper.addStudent(new Student(1, "a","b","c", 1, "m", 1,-1)) + "", Toast.LENGTH_LONG)
//        Toast.makeText(this, helper.getStudentsByLastName("beta").size() + "", Toast.LENGTH_LONG)

//        TeacherDatabaseHelper helper1 = new TeacherDatabaseHelper(this);
//        int count = helper.addStudents(new Student(1, "abaka","b","c", 1, "m", 1,-1), new Student(2, "verle","b","c", 1, "m", 1,-1),
//                new Student(3, "verle","b","c", 1, "m", 1,-1), new Student(4, "verle","b","c", 1, "m", 1,-1),
//                new Student(5, "verle","b","c", 1, "m", 1,-1),
//                new Student(6, "verle","b","c", 1, "m", 1,-1),
//                        new Student(7, "verle","b","c", 1, "m", 1,-1));

//        for(int ctr = 0 ; ctr < helper.getAllStudent().size() ; ctr++){
//            helper.deleteStudentById(ctr+1);
//        }
//        Toast.makeText(this, helper1.getTeacherByEmail("erafaelmanuel@gmail.com") + "", Toast.LENGTH_LONG)

//        StudentService service = new StudentServiceImpl(this);
//        service.addStudent(new Student(2, "no one","beta","coconut", 1, "m", 1,-1));
//        Toast.makeText(this, service.getAllStudent().size() + "", Toast.LENGTH_LONG)

        //SectionDatabaseHelper sectionDatabaseHelper = new SectionDatabaseHelper(this);
        //sectionDatabaseHelper.upgradeTable(true);
//        SectionStudentListService sectionStudentListService = new SectionStudentListServiceImpl(this);
//        StudentService studentService = new StudentServiceImpl(this);
//        SectionService sectionService = new SectionServiceImpl(this);

        //Toast.makeText(this, sectionDatabaseHelper.addSection(new Section(1,"name",1995, "no")) + "", Toast.LENGTH_LONG);
        //Toast.makeText(this, sectionDatabaseHelper.getSectionById(1) + "", Toast.LENGTH_LONG)
        //Toast.makeText(this, sectionDatabaseHelper.updateSectionById(1, new Section(1,"rafael",1996, "no")) + "", Toast.LENGTH_LONG)
//        Toast.makeText(this, sectionDatabaseHelper.deleteSectionById(1) + "", Toast.LENGTH_LONG)
        //sectionStudentListService.addStudentId(1, 2);
        //sectionStudentListService.deleteAllStudentFromListBySectionId(1);
        //Toast.makeText(this, sectionService.getSectionById(1) + "", Toast.LENGTH_LONG)
        //Toast.makeText(this, studentService.getAllStudent().get(0) + "", Toast.LENGTH_LONG)
//        TeacherDatabaseHelper teacherDatabaseHelper = new TeacherDatabaseHelper(this);
//        teacherDatabaseHelper.onUpgrade(teacherDatabaseHelper.getWritableDatabase(),
//                teacherDatabaseHelper.VERSION-1, teacherDatabaseHelper.VERSION);
//        TeacherService teacherService = new TeacherServiceImpl(this);
//        teacherService.addTeacher(new Teacher(3, "r","m","e","p"));
        //teacherService.getTeacherAuthenticate("e","m");
//        Toast.makeText(this, teacherService.getListOfTeacher().size() + "", Toast.LENGTH_LONG)
//        helper.deleteStudentById(5);
//        helper.deleteStudentById(6);
//        helper.deleteStudentById(7);
//        Toast.makeText(this, helper.getAllStudent().size() + "", Toast.LENGTH_LONG)
//        .show();

        ScheduleDatabaseHelper scheduleDatabaseHelper = new ScheduleDatabaseHelper(this);
        scheduleDatabaseHelper.addSchedule(null);
        //saveUserDetail("", "");
        //goToNextActiviy(null, LoginActivity.class);


    }

    public void saveUserDetail(final String username, final String password){
        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cemail", username);
        editor.putString("cpassword", password);
        editor.apply();

        //Log Information
        //Log.i(TAG, "User references saved successfully");
    }

    public void goToNextActiviy(Teacher intentExtra, Class clazz){
        Intent intent = new Intent(this, clazz);
        intent.putExtra("teacher", intentExtra);
        startActivity(intent);
    }

}


