package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.remswork.classmanager.fragment.RegisterFragment;
import com.remswork.classmanager.model.clazz.Teacher;
import com.remswork.classmanager.helper.service.TeacherService;
import com.remswork.classmanager.helper.service.impl.TeacherServiceImpl;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
        implements RegisterFragment.RegisterFragmentListener{

    private TeacherService teacherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        teacherService = new TeacherServiceImpl(this);
    }

    @Override
    public void doRegister(HashMap attribute) {
        Teacher teacher = new Teacher();
        teacher.setFirstName((String) attribute.get("firstname"));
        teacher.setLastName((String) attribute.get("lastname"));
        teacher.setEmail((String) attribute.get("email"));
        teacher.setPassword((String) attribute.get("password"));

        teacherService.addTeacher(teacher);
        saveTeacherSharedPreference(teacher.getEmail(), teacher.getPassword());
        activityStart(null, LoginActivity.class);
        finish();
    }

    @Override
    public void doCancel() {
        finish();
    }

    public void saveTeacherSharedPreference(final String email, final String password){
        SharedPreferences sharedPreferences =
                getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cemail", email);
        editor.putString("cpassword", password);
        editor.apply();
    }

    public void activityStart(Teacher intentExtra, Class activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("teacher", intentExtra);
        startActivity(intent);
    }
}
