package com.remswork.classmanager.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.remswork.classmanager.R;
import com.remswork.classmanager.model.clazz.Subject;

/**
 * Created by Rem-sama on 7/25/2017.
 */

public class SubjectViewFragment extends Fragment {

    private View customView;
    private EditText textTitle;
    private EditText textDescription;
    private EditText textCode;
    private EditText textUnit;
    private EditText textCategory;
    private Subject subject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        customView = inflater.inflate(
                R.layout.activity_subject_fragment_view, container, false);
        init();
        return customView;
    }

    public void init(){
        subject = (Subject) getArguments().getParcelable("subject");
        textTitle = (EditText) customView.findViewById(R.id.fragment_subject_view_text_title);
        textDescription = (EditText) customView
                .findViewById(R.id.fragment_subject_view_text_description);
        textCode = (EditText) customView.findViewById(R.id.fragment_subject_view_text_code);
        textUnit = (EditText) customView.findViewById(R.id.fragment_subject_view_text_unit);
        textCategory = (EditText) customView
                .findViewById(R.id.fragment_subject_view_text_category);

        textTitle.setText(subject.getName());
        textDescription.setText(subject.getDesc());
        textCode.setText(subject.getCode());
        textUnit.setText(subject.getUnit() + "");
        textCategory.setText(subject.getCategory());
    }
}
