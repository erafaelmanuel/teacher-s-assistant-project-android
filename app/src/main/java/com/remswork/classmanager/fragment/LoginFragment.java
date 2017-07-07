package com.remswork.classmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.remswork.classmanager.R;

/**
 * Created by Rafael on 7/4/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText textEmail;
    private EditText textPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private LoginFragmentListener loginFragmentListener;

    public void initializeWidget(View customView){
        textEmail = (EditText) customView.findViewById(R.id.fragment_login_text_email);
        textPassword = (EditText) customView.findViewById(R.id.fragment_login_text_password);
        buttonLogin = (Button) customView.findViewById(R.id.fragment_login_button_login);
        buttonSignUp = (Button) customView.findViewById(R.id.fragment_login_button_signup);
        textInputLayoutEmail = (TextInputLayout) customView.findViewById(R.id.fragment_login_email_layout);
        textInputLayoutPassword = (TextInputLayout) customView.findViewById(R.id.fragment_login_password_layout);

        buttonLogin.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeWidget(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity)
            loginFragmentListener = (LoginFragmentListener) context;
        else
            throw new ClassCastException(getActivity().toString());
    }

    public boolean isWidgetInputIsValid(){
        boolean isValid = true;
        if(textEmail.getText().toString().trim().equals("")){
            textInputLayoutEmail.setError("Please enter your email name");
            isValid = false;
        }else {
            textInputLayoutEmail.setErrorEnabled(false);
        }
        if(textPassword.getText().toString().trim().equals("")){
            textInputLayoutPassword.setError("Please enter your password name");
            isValid = false;
        }else {
            textInputLayoutPassword.setErrorEnabled(false);
        }
        return isValid;
    }

    public void logInClick(){
        if(isWidgetInputIsValid()) {
            loginFragmentListener.doLogIn(textEmail.getText().toString().trim(),
                    textPassword.getText().toString().trim());
        }
    }

    public void signUpClick(){
        loginFragmentListener.doSignUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_login_button_login :
                logInClick();
                break;
            case R.id.fragment_login_button_signup :
                signUpClick();
                break;
            default:
                break;
        }
    }

    public interface LoginFragmentListener{
        public void doLogIn(String email, String password);
        public void doSignUp();
    }
}
