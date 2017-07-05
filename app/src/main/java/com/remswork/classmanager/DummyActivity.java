package com.remswork.classmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.remswork.classmanager.model.Teacher;

/**
 * Created by Rafael24 on 7/4/2017.
 */

public class DummyActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public void onClick(View view) {
        saveUserDetail("", "");
        goToNextActiviy(null, LoginActivity.class);
    }

    public void saveUserDetail(final String username, final String password){
        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cusername", username);
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


