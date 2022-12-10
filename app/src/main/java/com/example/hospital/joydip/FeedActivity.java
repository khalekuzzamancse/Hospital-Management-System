package com.example.hospital.joydip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hospital.joydip.firebasetemplate.CallbackDomainList;
import com.example.hospital.joydip.firebasetemplate.CallbackFeedDomainList;
import com.example.hospital.joydip.firebasetemplate.CallbackUserProfile;
import com.example.hospital.joydip.firebasetemplate.DomainFeed;
import com.example.hospital.joydip.firebasetemplate.DomainUserInfo;
import com.example.hospital.joydip.firebasetemplate.FeedInfo;
import com.example.hospital.joydip.firebasetemplate.FirebaseAuthCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FeedActivity extends AppCompatActivity {
    RecyclerView recycler;
    DomainUserInfo info;
    FeedInfo feed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);

        CallbackFeedDomainList callback = new CallbackFeedDomainList() {
            @Override
            public void receivedList(List<DomainFeed> list) {
                Log.i("FETCHED_Fee", String.valueOf(list));
                updateAdapter(list);

            }
        };
        CallbackUserProfile profileCB = new CallbackUserProfile() {
            @Override
            public void getProfile(DomainUserInfo profile) {
                info = profile;
                if(info.userType.equals("Doctor"))
                {
                    feed.getFeedDoctor(callback);
                    FeedAdapter.isPatient=false;
                }

                else
                {
                    feed.getFeedPatient(callback);
                    FeedAdapter.isPatient=true;
                }


            }
        };
        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(profileCB);
        feed= new FeedInfo();
        recycler = findViewById(R.id.recycler);

    }

    void updateAdapter(List<DomainFeed> List) {
        Log.i("ListSize", String.valueOf(List.size()));
        FeedAdapter adapter = new FeedAdapter(this, List);
        recycler.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
        recycler.setAdapter(adapter);
    }
}
