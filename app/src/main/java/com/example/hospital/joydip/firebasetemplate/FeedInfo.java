package com.example.hospital.joydip.firebasetemplate;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedInfo {
    FirebaseFirestore db;
    CallbackFeedDomainList callbackCustom;
    List<DomainFeed> list;

    private static final String collectionName = "Feed";

    public FeedInfo() {

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
    }

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    DomainFeed info = new DomainFeed();
                    info =document.toObject(DomainFeed.class);
                    list.add(info);

                     // Log.i("FETCHED_Feed", document.getId() + " => " + document.getData());
                }
               callbackCustom.receivedList(list);
                //making the list empty or null so that existing data will remove

            }
        }
    };

    public void getFeedDoctor(CallbackFeedDomainList callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cRef = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cRef.whereEqualTo("to", new FirebaseAuthCustom().getUserEmail()).get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }
    public void getFeedPatient(CallbackFeedDomainList callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cRef = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cRef.whereEqualTo("from", new FirebaseAuthCustom().getUserEmail()).get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }


}