package com.remswork.classmanager.fragment.slidebar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remswork.classmanager.R;
import com.remswork.classmanager.adapter.SubjectRecyclerViewAdapter;
import com.remswork.classmanager.helper.service.SubjectService;
import com.remswork.classmanager.helper.service.impl.SubjectServiceImpl;
import com.remswork.classmanager.model.clazz.Subject;

import java.util.List;

/**
 * Created by Verlie on 7/22/2017.
 */

public class SlideBarSubjectFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private RecyclerView recyclerView;
    private SubjectRecyclerViewAdapter subjectRecyclerViewAdapter;
    private SubjectService subjectService;
    private CardView buttonAdd;
    private SlideBarSubjectFragmentListener slideBarSubjectFragmentListener;

    public SlideBarSubjectFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View customView = inflater.inflate(R.layout.fragment_slidebar_subject, container, false);
        subjectService = new SubjectServiceImpl(context);
        List<Subject> subjects = subjectService.getAllSubject();

        subjectRecyclerViewAdapter = new SubjectRecyclerViewAdapter(context, subjects);
        buttonAdd = (CardView) customView.findViewById(R.id.fragment_slidebar_subject_add_subject);
        recyclerView = (RecyclerView) customView.findViewById(
                R.id.fragment_slidebar_recyclerview_subject);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subjectRecyclerViewAdapter);

        buttonAdd.setOnClickListener(this);
        return customView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if(context instanceof SlideBarSubjectFragmentListener)
            slideBarSubjectFragmentListener = (SlideBarSubjectFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView(true);
    }

    //isAlways recommend to false
    public void refreshRecyclerView(boolean isAlways){
        if(subjectRecyclerViewAdapter != null){
            if(subjectService.getAllSubject().size() != subjectRecyclerViewAdapter.getItemCount()
                    || isAlways){
                List<Subject> subjects = subjectService.getAllSubject();
                subjectRecyclerViewAdapter = new SubjectRecyclerViewAdapter(context, subjects);
                recyclerView.setAdapter(subjectRecyclerViewAdapter);
            }
        }
    }

    @Override
    public void onClick(View v) {
        slideBarSubjectFragmentListener.openSubject();
    }

    public interface SlideBarSubjectFragmentListener{
        void openSubject();
    }
}
