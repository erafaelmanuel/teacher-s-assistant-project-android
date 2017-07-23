package com.remswork.classmanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verlie on 7/22/2017.
 */

public class SlideBarViewPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> listOfFragmet = new ArrayList<Fragment>();
    private final List<String> listOfTitle = new ArrayList<String>();

    public SlideBarViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragmet.get(position);
    }

    @Override
    public int getCount() {
        return listOfFragmet.size();
    }

    public void addFrag(Fragment fragment, String title) {
        listOfFragmet.add(fragment);
        listOfTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listOfTitle.get(position);
    }



}
