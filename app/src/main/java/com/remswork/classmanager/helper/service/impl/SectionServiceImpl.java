package com.remswork.classmanager.helper.service.impl;

import android.content.Context;

import com.remswork.classmanager.helper.dao.SectionDatabaseHelper;
import com.remswork.classmanager.helper.service.SectionService;
import com.remswork.classmanager.model.Section;

import java.util.List;

/**
 * Created by Rafael on 7/19/2017.
 */

public class SectionServiceImpl implements SectionService {

    private SectionDatabaseHelper sectionDatabaseHelper;

    public SectionServiceImpl(Context context){
        sectionDatabaseHelper = new SectionDatabaseHelper(context);
    }

    @Override
    public boolean addSection(Section section) {
        return sectionDatabaseHelper.addSection(section);
    }

    @Override
    public Section getSectionById(int id) {
        return sectionDatabaseHelper.getSectionById(id);
    }

    @Override
    public Section getSectionByIdWithoutStudentList(int id) {
        return sectionDatabaseHelper.getSectionByIdWithoutStudentList(id);
    }

    @Override
    public List<Section> getAllSection() {
        return sectionDatabaseHelper.getAllSection();
    }

    @Override
    public boolean updateSectionById(int id, Section newSection) {
        return sectionDatabaseHelper.updateSectionById(id, newSection);
    }

    @Override
    public boolean deleteSectionById(int id) {
        return sectionDatabaseHelper.deleteSectionById(id);
    }
}
