package com.github.pl4gue.homeworkmanager_android.mvp.presenter;

import android.util.Log;
import android.widget.LinearLayout;

import com.github.pl4gue.homeworkmanager_android.entity.ClassEntity;
import com.github.pl4gue.homeworkmanager_android.entity.HomeWorkEntry;
import com.github.pl4gue.homeworkmanager_android.entity.HomeworkEntity;
import com.github.pl4gue.homeworkmanager_android.entity.UserEntity;
import com.github.pl4gue.homeworkmanager_android.mvp.view.GetHomeworkView;
import com.github.pl4gue.homeworkmanager_android.mvp.view.fragments.GetHomeworkFragment;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.github.pl4gue.homeworkmanager_android.GSheetConstants.KEY_HOMEWORK;
import static com.github.pl4gue.homeworkmanager_android.GSheetConstants.KEY_HOMEWORK_COMMENTS;
import static com.github.pl4gue.homeworkmanager_android.GSheetConstants.KEY_HOMEWORK_DUE;
import static com.github.pl4gue.homeworkmanager_android.GSheetConstants.KEY_HOMEWORK_ENTRY;
import static com.github.pl4gue.homeworkmanager_android.GSheetConstants.KEY_HOMEWORK_SUBJECT;


/**
 * @author David Wu (david10608@gmail.com)
 *         Created on 14.10.17.
 */

public class GetHomeworkPresenter implements Presenter {
    private GoogleAccountCredential mCredential;

    private GetHomeworkView mView;
    private GetHomeworkFragment context;

    private DatabaseReference mHeadDatabaseReference = FirebaseDatabase.getInstance().getReference().child("head");
    private DatabaseReference mUserDatabaseReference = mHeadDatabaseReference.child("users");
    private DatabaseReference mClassesDatabaseReference = mHeadDatabaseReference.child("classes");


    private ArrayList<UserEntity> userEntityArrayList = new ArrayList<>();
    private ArrayList<ClassEntity> classEntityArrayList = new ArrayList<>();

    private LinearLayout mHomeworkLinearLayout;

    public void initialize(GetHomeworkFragment context, LinearLayout mHomeworkLinearLayout) {
        this.context = context;
        this.mHomeworkLinearLayout = mHomeworkLinearLayout;

        mUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String[] classes = userSnapshot.child("classList").getValue().toString().trim().split(",");
                    userEntityArrayList.add(new UserEntity(
                            userSnapshot.child("id").getValue().toString(),
                            userSnapshot.child("name").getValue().toString(),
                            userSnapshot.child("role").getValue().toString(),
                            Arrays.asList(classes),
                            userSnapshot.child("password").getValue().toString()
                    ));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        mClassesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot classSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot homeworkSnapshot = classSnapshot.child("homework");
                    HomeworkEntity homeworkEntity = new HomeworkEntity(
                            homeworkSnapshot.child("subject").getValue().toString(),
                            homeworkSnapshot.child("entrydate").getValue().toString(),
                            homeworkSnapshot.child("duedate").getValue().toString(),
                            homeworkSnapshot.child("content").getValue().toString()
                    );

                    classEntityArrayList.add(new ClassEntity(
                            homeworkEntity,
                            classSnapshot.child("id").getValue().toString(),
                            classSnapshot.child("name").getValue().toString()
                    ));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    public void update() {

    }

    private void showNext(List<HomeWorkEntry> homeWorkEntryList) {
        mView.updateDatabase(homeWorkEntryList);
    }

    private void newEntry(List<HashMap<Integer, String>> results, String entrydate, String subject, String homework, String duedate, String comments) {
        if (homework != null && !homework.equals("")) {
            HashMap<Integer, String> temp = new HashMap<>();
            temp.put(KEY_HOMEWORK_ENTRY, entrydate);
            temp.put(KEY_HOMEWORK_SUBJECT, subject);
            temp.put(KEY_HOMEWORK, homework);
            temp.put(KEY_HOMEWORK_DUE, duedate);
            temp.put(KEY_HOMEWORK_COMMENTS, comments);
            results.add(temp);
        }
    }

    private void showHomework(List<HashMap<Integer, String>> list) {
        List<HomeWorkEntry> temp = new ArrayList<>();
        for (HashMap<Integer, String> homeworkEntryList : list) {
            HomeWorkEntry entry = new HomeWorkEntry(
                    homeworkEntryList.get(KEY_HOMEWORK_ENTRY)
                    , homeworkEntryList.get(KEY_HOMEWORK_SUBJECT)
                    , homeworkEntryList.get(KEY_HOMEWORK)
                    , homeworkEntryList.get(KEY_HOMEWORK_DUE)
                    , homeworkEntryList.get(KEY_HOMEWORK_COMMENTS));
            temp.add(entry);
        }
        showNext(temp);
    }

}
