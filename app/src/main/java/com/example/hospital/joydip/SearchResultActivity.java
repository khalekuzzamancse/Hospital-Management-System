package com.example.hospital.joydip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.hospital.joydip.firebasetemplate.CallbackDomainList;
import com.example.hospital.joydip.firebasetemplate.DomainUserInfo;
import com.example.hospital.joydip.firebasetemplate.UserInfo;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recycler;
    SearchView search;
    List<DomainUserInfo> list;
    public static String from = "Not Main";
    SearchResultAdapter adapter;
    CircularProgressIndicator progressIndicator;
    CallbackDomainList callback = List -> {
        list = List;
        //  progressIndicator.setVisibility(View.INVISIBLE);
        //  Log.i("ReceivedData-AllUserInfo", String.valueOf(List));
        progressIndicator.setVisibility(View.GONE);
        if (List.isEmpty())
            showSnackbar();
        updateAdapter(List);


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        initialize();
        // setToolbar();
        UserInfo db = new UserInfo();
        db.getDoctors(callback);
        progressIndicator.setVisibility(View.VISIBLE);

        search.clearFocus();

        if (from.equals("Main")) {
            search.setVisibility(View.GONE);
            from = "Not Main";
        } else {
            search.setVisibility(View.VISIBLE);
            from = "Not Main";
        }
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


//

    }

    private void filterList(String newText) {
        List<DomainUserInfo> L = new ArrayList<>();
        //L = list;
        for (DomainUserInfo doctorName : list) {
            if (doctorName.name.toLowerCase().contains(newText.toLowerCase())) {
                L.add(doctorName);
            }


        }
        if (L.isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(search, "Not Found", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
            snackbar.show();
        } else {
            adapter.setFilterList(L);
        }
    }

    void updateAdapter(List<DomainUserInfo> List) {
        adapter = new SearchResultAdapter(this, List);
        recycler.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recycler.setAdapter(adapter);
    }

    void showSnackbar() {
        Snackbar snackbar = Snackbar
                .make(recycler, "Not Found", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        recycler = findViewById(R.id.recycler);
        progressIndicator = findViewById(R.id.progrssbar);
        search = findViewById(R.id.searchView);

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User List");
    }


}


