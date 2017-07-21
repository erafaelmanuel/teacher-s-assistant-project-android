package com.remswork.classmanager.helper.service;

import com.remswork.classmanager.model.grade.Term;

import java.util.List;

/**
 * Created by Rafael on 7/21/2017.
 */

public interface TermService {

    boolean addTerm(Term term);
    int addTerm(List<Term> terms);
    int addTerm(Term... terms);
    Term getTermById(int id);
    Term getTermByCodeAndClazzId(String code, int clazzId);
    List<Term> getTermByClassId(int clazzId);
    boolean updateTermById(int id, Term term);
    boolean deleteTermById(int id);
    int deleteTermByClassId(int clazzId);
}
