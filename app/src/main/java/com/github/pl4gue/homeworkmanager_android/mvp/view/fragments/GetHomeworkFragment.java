package com.github.pl4gue.homeworkmanager_android.mvp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.pl4gue.homeworkmanager_android.R;
import com.github.pl4gue.homeworkmanager_android.adapters.GetHomework_RecyclerViewAdapter;
import com.github.pl4gue.homeworkmanager_android.entity.HomeWorkEntry;
import com.github.pl4gue.homeworkmanager_android.mvp.presenter.GetHomeworkPresenter;
import com.github.pl4gue.homeworkmanager_android.mvp.view.GetHomeworkView;
import com.github.pl4gue.homeworkmanager_android.mvp.view.activity.BaseActivity;
import com.github.pl4gue.homeworkmanager_android.mvp.view.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 14.10.17.
 */

public class GetHomeworkFragment extends Fragment implements GetHomeworkView {

    BaseActivity activity;

    GetHomeworkPresenter mGetHomeworkPresenter;
    GetHomework_RecyclerViewAdapter mAdapter;

    private List<HomeWorkEntry> mHomeworkList;

    @BindView(R.id.getHomeworkRecyclerView)
    RecyclerView mHomeworkListRecyclerView;
    @BindView(R.id.getHomeworkLinearLayout)
    LinearLayout mHomeworkLinearLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_show_homework, container, false);
        ButterKnife.bind(this, root);
        mGetHomeworkPresenter = new GetHomeworkPresenter();

        BaseActivity.DialogManagers.ProgressDialogManager.setUpProgressDialog(activity);
        mGetHomeworkPresenter.initialize(this, mHomeworkLinearLayout);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mHomeworkListRecyclerView.setLayoutManager(mLayoutManager);
        mHomeworkListRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.divider)));
        mAdapter = new GetHomework_RecyclerViewAdapter(mHomeworkList, activity);
        mHomeworkListRecyclerView.setAdapter(mAdapter);

        mGetHomeworkPresenter.update();

        return root;
    }

    @Override
    public void displayLoadingScreen() {
        BaseActivity.DialogManagers.ProgressDialogManager.startProgressDialog(getString(R.string.loading));
    }

    @Override
    public void hideLoadingScreen() {
        BaseActivity.DialogManagers.ProgressDialogManager.stopProgressDialog();
    }

    @Override
    public void updateDatabase(List<HomeWorkEntry> homeWorkEntryList) {
        mHomeworkList = homeWorkEntryList;
        mAdapter = new GetHomework_RecyclerViewAdapter(mHomeworkList, activity);
        mHomeworkListRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void fetchDataError() {
    }

}
