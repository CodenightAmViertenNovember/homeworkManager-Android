package com.github.pl4gue.homeworkmanager_android.mvp.view.fragments;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 14.10.17.
 */

public class GetHomeworkFragment extends Fragment implements GetHomeworkView {

    GoogleAccountCredential mCredential;

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
        mGetHomeworkPresenter.initialize(this,mHomeworkLinearLayout);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mHomeworkListRecyclerView.setLayoutManager(mLayoutManager);
        mHomeworkListRecyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider)));
        mAdapter = new GetHomework_RecyclerViewAdapter(mHomeworkList, this);
        mHomeworkListRecyclerView.setAdapter(mAdapter);

        mGetHomeworkPresenter.update();
    }

    @Override
    public void displayLoadingScreen() {
        DialogManagers.ProgressDialogManager.startProgressDialog(getString(R.string.loading));
    }

    @Override
    public void hideLoadingScreen() {
        DialogManagers.ProgressDialogManager.stopProgressDialog();
    }

    @Override
    public void updateGSheetsResult(List<HomeWorkEntry> homeWorkEntryList) {
        mHomeworkList = homeWorkEntryList;
        mAdapter = new GetHomework_RecyclerViewAdapter(mHomeworkList, this);
        mHomeworkListRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void fetchDataError() {
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    showError(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.", this);
                } else {
                    mGetHomeworkPresenter.update();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        mGetHomeworkPresenter.update();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    mGetHomeworkPresenter.update();
                }
                break;
            default:
                showError("Unknown Error", this);
        }
    }
}
