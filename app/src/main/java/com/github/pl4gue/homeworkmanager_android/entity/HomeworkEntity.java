package com.github.pl4gue.homeworkmanager_android.entity;

/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 04.11.17.
 */

import android.content.ContentValues;
import android.nfc.Tag;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeworkEntity {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private String content;
    private Date dueDate;
    private Date entryDate;
    private String subject;

    public HomeworkEntity(String subject, String entryDate, String dueDate, String content) {
        try {
            this.subject = subject;
            this.entryDate = HomeworkEntity.FORMAT.parse(entryDate);
            this.dueDate = HomeworkEntity.FORMAT.parse(dueDate);
            this.content = content;
        } catch (ParseException pe) {
            Log.d(ContentValues.TAG, pe.getMessage());
        }
    }

    public String getContent() {
        return this.content;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
