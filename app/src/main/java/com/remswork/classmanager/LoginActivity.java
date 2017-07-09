package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.remswork.classmanager.fragment.LoginFragment;
import com.remswork.classmanager.helper.dao.DatabaseHelper;
import com.remswork.classmanager.helper.dao.TeacherDatabaseHelper;
import com.remswork.classmanager.model.Teacher;

import java.util.HashMap;

/**
 * Created by Rafael on 7/4/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginFragment
        .LoginFragmentListener{

    private static final String TAG = "ClassManager";

    private DatabaseHelper databaseHelper = new TeacherDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if((boolean) loadUserDetail().get("isSuccess")){
            activityStart((Teacher) loadUserDetail().get("getTeacher"), MainActivity.class);
        }
    }

    @Override
    public void doLogIn(final String email, final String password) {

        if((boolean) ((TeacherDatabaseHelper) databaseHelper)
                .getTeacherAuthenticate(email, password).get("isSuccess")) {

            saveTeacherSharedPreference(email, password);
            activityStart((Teacher) ((TeacherDatabaseHelper) databaseHelper)
                    .getTeacherAuthenticate(email, password).get("getTeacher"),
                    MainActivity.class);
        }else
            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void doSignUp() {
        activityStart(null, RegisterActivity.class);
    }

    public HashMap loadUserDetail(){

        HashMap map = new HashMap();
        map.put("isSuccess", false);

        SharedPreferences sharedPreferences =
                getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        String cemail = sharedPreferences.getString("cemail", "");
        String cpassword = sharedPreferences.getString("cpassword", "");

        if (cemail != null && !cemail.equals("")) {
            if (cpassword != null && !cpassword.equals("")) {
                HashMap result = ((TeacherDatabaseHelper) databaseHelper)
                        .getTeacherAuthenticate(cemail, cpassword);
                if ((boolean) result.get("isSuccess"))
                    return result;
            }
        }

        return map;
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