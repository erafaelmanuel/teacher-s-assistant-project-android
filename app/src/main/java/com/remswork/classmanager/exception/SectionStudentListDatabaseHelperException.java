package com.remswork.classmanager.exception;

import com.remswork.classmanager.helper.dao.SectionStudentListDatabaseHelper;

/**
 * Created by Rem-sama on 7/18/2017.
 */

public class SectionStudentListDatabaseHelperException extends Exception {

    public SectionStudentListDatabaseHelperException(){
        super();
    }

    public SectionStudentListDatabaseHelperException(final String message){
        super(message);
    }
}
