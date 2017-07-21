package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.clazz.Section;

import java.util.List;

/**
 * Created by Rafael on 7/19/2017.
 */

public interface SectionService {

    boolean addSection(Section section);
    Section getSectionById(int id);
    Section getSectionByIdWithoutStudentList(int id);
    List<Section> getAllSection();
    boolean updateSectionById(int id, Section newSection);
    boolean deleteSectionById(int id);
}
