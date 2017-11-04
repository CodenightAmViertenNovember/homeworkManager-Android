package com.github.pl4gue.homeworkmanager_android.entity;

/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 04.11.17.
 */

import java.util.List;

public class ClassEntity {
    private List<HomeworkEntity> homework;
    private int id;
    private String name;

    public ClassEntity(List<HomeworkEntity> homework, String id, String name) {
        this.id = Integer.parseInt(id);
        this.homework = homework;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public List<HomeworkEntity> getHomework() {
        return this.homework;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHomework(List<HomeworkEntity> homework) {
        this.homework = homework;
    }

    public void setName(String name) {
        this.name = name;
    }
}
