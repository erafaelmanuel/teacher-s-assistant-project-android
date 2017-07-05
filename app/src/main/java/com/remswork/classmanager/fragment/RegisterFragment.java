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
import android.widget.TextView;

import com.remswork.classmanager.R;

import java.util.HashMap;

/**
 * Created by Rafael on 7/5/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText textFirstName;
    private EditText textLastName;
    private EditText textEmail;
    private EditText textPassword;
    private Button buttonRegister;
    private RegisterFragmentListener registerFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_register, container);
        textFirstName = (EditText) customView.findViewById(R.id.fragment_register_text_firstname);
        textLastName = (EditText) customView.findViewById(R.id.fragment_register_text_lastname);
        textEmail = (EditText) customView.findViewById(R.id.fragment_register_text_email);
        textPassword = (EditText) customView.findViewById(R.id.fragment_register_text_password);
        buttonRegister = (Button) customView.findViewById(R.id.fragment_register_button_register);
        buttonRegister.setOnClickListener(this);
        return customView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity)
           registerFragmentListener = (RegisterFragmentListener) context;
        else
           throw new ClassCastException(getActivity().toString());
    }

    @Override
    public void onClick(View v) {
        HashMap attribute = new HashMap();
        attribute.put("firstname", textFirstName.getText().toString());
        attribute.put("lastname", textLastName.getText().toString());
        attribute.put("email", textEmail.getText().toString());
        attribute.put("password", textPassword.getText().toString());

        registerFragmentListener.registerCommand(attribute);
    }

    public interface RegisterFragmentListener{
        public void registerCommand(HashMap attribute);
    }
}
