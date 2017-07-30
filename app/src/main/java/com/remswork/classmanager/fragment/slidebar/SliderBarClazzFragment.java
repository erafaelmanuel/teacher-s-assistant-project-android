package com.remswork.classmanager.fragment.slidebar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.remswork.classmanager.R;
import com.remswork.classmanager.adapter.ClazzRecyclerViewAdapter;
import com.remswork.classmanager.helper.dao.ClazzDatabaseHelper;
import com.remswork.classmanager.model.clazz.Clazz;

import java.util.List;

/**
 * Created by Verlie on 7/22/2017.
 */

public class SliderBarClazzFragment extends Fragment {

    private Context context;
    private RecyclerView clazzRecyclerView;
    private ClazzRecyclerViewAdapter clazzRecyclerViewAdapter;
    private List<Clazz> clazzes;
    private FloatingActionButton floatingActionButton;
    private ClazzDatabaseHelper clazzDatabaseHelper;
    private SliderBarClazzFragmentListener sliderBarClazzFragmentListener;

    public SliderBarClazzFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View customView = inflater.inflate(R.layout.fragment_slidebar_clazz, container, false);
        clazzDatabaseHelper = new ClazzDatabaseHelper(context);

        clazzRecyclerView = (RecyclerView)
                customView.findViewById(R.id.fragment_slidebar_recyclerview_clazz);
        floatingActionButton = (FloatingActionButton)
                customView.findViewById(R.id.fragment_slidebar_recyclerview_clazz_fab);

        clazzes = clazzDatabaseHelper.getAllClazz();

        //Set the adapter view list
        clazzRecyclerViewAdapter = new ClazzRecyclerViewAdapter(context, clazzes);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        clazzRecyclerView.setLayoutManager(layoutManager);
        clazzRecyclerView.setItemAnimator(new DefaultItemAnimator());
        clazzRecyclerView.setAdapter(clazzRecyclerViewAdapter);

        floatingActionButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                sliderBarClazzFragmentListener.addClass();
            }
        });

        return customView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        sliderBarClazzFragmentListener = (SliderBarClazzFragmentListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView(true);
    }

    //isAlways recommend to false
    public void refreshRecyclerView(boolean isAlways){
        if(clazzRecyclerViewAdapter != null){
            if(clazzDatabaseHelper.getAllClazz().size() != clazzRecyclerViewAdapter.getItemCount()
                    || isAlways){
                List<Clazz> clazzList = clazzDatabaseHelper.getAllClazz();
                clazzRecyclerViewAdapter = new ClazzRecyclerViewAdapter(context, clazzList);
                clazzRecyclerView.setAdapter(clazzRecyclerViewAdapter);
            }
        }
    }

    public interface SliderBarClazzFragmentListener{
        public void addClass();
    }
}
