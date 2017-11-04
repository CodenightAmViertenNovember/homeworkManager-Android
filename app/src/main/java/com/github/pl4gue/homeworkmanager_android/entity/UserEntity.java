package com.github.pl4gue.homeworkmanager_android.entity;

/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 04.11.17.
 */

import java.util.ArrayList;
import java.util.List;

public class UserEntity {
    private List<Integer> classList;
    private int id;
    private String name;
    private String password;
    private int role;

    public UserEntity(String id, String name, String role, List<String> classList, String password) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.role = Integer.parseInt(role);
        this.password = password;

        this.classList = new ArrayList<Integer>();
        for (String classItem : classList) {
            this.classList.add(Integer.parseInt(classItem));
        }
    }

    public List<Integer> getClassList() {
        return this.classList;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public int getRole() {
        return this.role;
    }

    public void setClassList(List<Integer> classList) {
        this.classList = classList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }
}