package com.github.pl4gue.homeworkmanager_android.mvp.view;


import com.github.pl4gue.homeworkmanager_android.entity.HomeWorkEntry;

import java.util.List;

/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 14.10.17.
 */

public interface GetHomeworkView extends View {
    void displayLoadingScreen();

    void hideLoadingScreen();

    void updateDatabase(List<HomeWorkEntry> homeWorkEntryList);

    void fetchDataError();

}
