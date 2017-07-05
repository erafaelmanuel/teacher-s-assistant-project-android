package com.remswork.classmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.remswork.classmanager.fragment.LoginFragment;
import com.remswork.classmanager.helper.dao.DatabaseHelper;
import com.remswork.classmanager.helper.dao.TeacherDatabaseHelper;
import com.remswork.classmanager.model.Teacher;

import java.util.HashMap;

/**
 * Created by Rafael on 7/4/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener{

    private static final String TAG = "ClassManager";
    private DatabaseHelper databaseHelper = new TeacherDatabaseHelper(this);
    private int teacherId;

    @Override
    public void loginCommand(String username, String password) {
        if((boolean) ((TeacherDatabaseHelper) databaseHelper)
                .getTeacherAuthenticate(username, password).get("isSuccess")) {
            saveUserDetail(username, password);
            goToNextActiviy((Teacher) ((TeacherDatabaseHelper) databaseHelper)
                    .getTeacherAuthenticate(username, password).get("getTeacher"));
        }else
            Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_LONG).show();
    }

    /**
     *  Initializing the whole context
     * @param savedInstanceState this is just a house keeping staff
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "onCreate of LoginActivity.class");

    }

    /**
     * Every resume of the activity it load and check the USER_LOGIN_PREFERENCE, whether or not
     * there was an existing credentials to use
     */
    @Override
    protected void onResume() {
        super.onResume();
        if((boolean) loadUserDetail().get("isSuccess")){
            Log.i(TAG, "User preferences loaded successfully");
            goToNextActiviy((Teacher) loadUserDetail().get("getTeacher"));
        }
        Log.i(TAG, "onResume of LoginActivity.class");
    }

    /**
     *
     * @return -1 if no teacher is found. otherwise it was id of teacher
     */
    public HashMap loadUserDetail(){

        HashMap map = new HashMap();
        map.put("isSuccess", false);

        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        String cusername = sharedPreferences.getString("cusername", "");
        String cpassword = sharedPreferences.getString("cpassword", "");

        //Log Information
        Log.i(TAG, "Username : " + cusername);
        Log.i(TAG, "Password : " + cpassword);
        Log.i(TAG, "User preferences loaded successfully");

        if (cusername != null && !cusername.equals("")) {
            if (cpassword != null && !cpassword.equals("")) {
                HashMap result = ((TeacherDatabaseHelper) databaseHelper)
                        .getTeacherAuthenticate(cusername, cpassword);
                if ((boolean) result.get("isSuccess"))
                    //return ((int) ((Teacher) result.get("getTeacher")).getId());
                    return result;
            }
            return map;
        }
        return map;
    }

    /**
     * to save a credential to SharedPreferences (cmUserLogin)
     *
     * @param username saved with a key 'cusername'
     * @param password saved with a key 'cpassword'
     */
    public void saveUserDetail(final String username, final String password){
        SharedPreferences sharedPreferences = getSharedPreferences("cmUserLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cusername", username);
        editor.putString("cpassword", password);
        editor.apply();

        //Log Information
        Log.i(TAG, "User references saved successfully");
    }

    public void goToNextActiviy(Teacher intentExtra){
        Intent intent = new Intent(this, DummyActivity.class);
        intent.putExtra("teacher", intentExtra);
        startActivity(intent);
    }
}
