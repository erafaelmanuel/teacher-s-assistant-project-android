package com.remswork.classmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private LoginFragmentListener loginFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        textEmail = (EditText) view.findViewById(R.id.fragment_login_text_email);
        textPassword = (EditText) view.findViewById(R.id.fragment_login_text_password);
        buttonLogin = (Button) view.findViewById(R.id.fragment_login_button_login);
        buttonLogin.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        loginFragmentListener.loginCommand(textEmail.getText().toString().trim(),
                textPassword.getText().toString().trim());
    }

    public interface LoginFragmentListener{
        public void loginCommand(String email, String password);
    }
}
