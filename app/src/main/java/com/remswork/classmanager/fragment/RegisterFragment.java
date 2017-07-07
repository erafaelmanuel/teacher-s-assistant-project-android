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
import android.widget.TextView;

import com.remswork.classmanager.R;

import java.util.HashMap;

import static android.R.attr.onClick;

/**
 * Created by Rafael on 7/5/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText textFirstName;
    private EditText textLastName;
    private EditText textEmail;
    private EditText textPassword;
    private Button buttonRegister;
    private Button buttonCancel;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private RegisterFragmentListener registerFragmentListener;

    /**
     *
     * @param customView
     */
    public void initializeWidgets(View customView){
        textFirstName = (EditText) customView.findViewById(R.id.fragment_register_text_firstname);
        textLastName = (EditText) customView.findViewById(R.id.fragment_register_text_lastname);
        textEmail = (EditText) customView.findViewById(R.id.fragment_register_text_email);
        textPassword = (EditText) customView.findViewById(R.id.fragment_register_text_password);
        textInputLayoutFirstName = (TextInputLayout) customView
                .findViewById(R.id.fragment_register_fname_layout);
        textInputLayoutLastName = (TextInputLayout) customView
                .findViewById(R.id.fragment_register_lname_layout);
        textInputLayoutEmail = (TextInputLayout) customView
                .findViewById(R.id.fragment_register_email_layout);
        textInputLayoutPassword = (TextInputLayout) customView
                .findViewById(R.id.fragment_register_password_layout);
        buttonRegister = (Button) customView.findViewById(R.id.fragment_register_button_register);
        buttonCancel = (Button) customView.findViewById(R.id.fragment_register_button_cancel);

        buttonRegister.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    public boolean isWidgetInputValid(){
        boolean isValid = true;
        if(textFirstName.getText().toString().trim().equals("")){
            textInputLayoutFirstName.setError("Please enter your first name");
            isValid = false;
        }else {
            textInputLayoutFirstName.setErrorEnabled(false);
            }
        if(textLastName.getText().toString().trim().equals("")){
            textInputLayoutLastName.setError("Please enter your last name");
            isValid = false;
        }else {
            textInputLayoutLastName.setErrorEnabled(false);
        }
        if(textEmail.getText().toString().trim().equals("")){
            textInputLayoutEmail.setError("Please enter your email name");
            isValid = false;
        }else {
            textInputLayoutEmail.setErrorEnabled(false);
        }
        if(textPassword.getText().toString().trim().equals("")){
            textInputLayoutPassword.setError("Please enter your password name");
            isValid = false;
        }else if(textPassword.getText().toString().trim().length() < 8){
            textInputLayoutPassword.setError("Minimum of 8 character");
            isValid = false;
        }else {
            textInputLayoutPassword.setErrorEnabled(false);
        }


        return isValid;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View customView = inflater.inflate(R.layout.fragment_register, container);
        initializeWidgets(customView);
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

    public void clickRegister() {

        if(isWidgetInputValid()) {
            HashMap attribute = new HashMap();
            attribute.put("firstname", textFirstName.getText().toString().trim());
            attribute.put("lastname", textLastName.getText().toString().trim());
            attribute.put("email", textEmail.getText().toString().trim());
            attribute.put("password", textPassword.getText().toString().trim());
            registerFragmentListener.registerCommand(attribute);
        }
    }

    public void clickCancel(){
        registerFragmentListener.registerCancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_register_button_register :
                clickRegister();
                break;
            case R.id.fragment_register_button_cancel :
                clickCancel();;
                break;
            default:
                break;
        }

    }

    public interface RegisterFragmentListener{
        public void registerCommand(HashMap attribute);
        public void registerCancel();
    }
}
