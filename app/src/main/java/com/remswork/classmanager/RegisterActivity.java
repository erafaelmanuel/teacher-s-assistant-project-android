package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.remswork.classmanager.fragment.RegisterFragment;
import com.remswork.classmanager.helper.dao.TeacherDatabaseHelper;
import com.remswork.classmanager.model.Teacher;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
        implements RegisterFragment.RegisterFragmentListener{

    private TeacherDatabaseHelper teacherDatabaseHelper = new TeacherDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void registerCommand(HashMap attribute) {
        Teacher teacher = new Teacher();
        teacher.setFirstName((String) attribute.get("firstname"));
        teacher.setLastName((String) attribute.get("lastname"));
        teacher.setEmail((String) attribute.get("email"));
        teacher.setPassword((String) attribute.get("password"));

        teacherDatabaseHelper.addTeacher(teacher);
        saveUserDetail(teacher.getEmail(), teacher.getPassword());
        goToNextActiviy(null, LoginActivity.class);
        finish();
    }

    /**
     * to save a credential to SharedPreferences (cmUserLogin)
     *
     * @param email saved with a key 'cemail'
     * @param password saved with a key 'cpassword'
     */
    public void saveUserDetail(final String email, final String password){
        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cemail", email);
        editor.putString("cpassword", password);
        editor.apply();
    }

    /**
     *
     * @param intentExtra
     * @param activityClass
     */
    public void goToNextActiviy(Teacher intentExtra, Class activityClass){
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("teacher", intentExtra);
        startActivity(intent);
    }
}
