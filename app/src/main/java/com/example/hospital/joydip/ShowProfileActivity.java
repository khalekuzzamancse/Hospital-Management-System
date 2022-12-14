package com.example.hospital.joydip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hospital.joydip.firebasetemplate.CallbackUserProfile;
import com.example.hospital.joydip.firebasetemplate.DomainUserInfo;
import com.example.hospital.joydip.firebasetemplate.FirebaseAuthCustom;

import java.util.Objects;

public class ShowProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView nameTV, emailTV, phoneTV, availableTimeTV, specializationTV, deptTV;
    DomainUserInfo userInfo;
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            userInfo = profile;
            setProfile(profile);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_profile_activity);
        initialize();
        setToolbar();

        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);


    }


    private void setProfile(DomainUserInfo info) {
        nameTV.setText("Name :" + info.name);
        emailTV.setText("Email :" + info.email);
        phoneTV.setText("Phone No :" + info.phoneNo);
        if (info.userType.equals("Doctor")) {
            deptTV.setText("Dept :" + info.dept);
            availableTimeTV.setText("Available Time : " + info.availableTime);
            specializationTV.setText("Specialization :" + info.specialization);
        }
        else
        {
            deptTV.setVisibility(View.GONE);
            availableTimeTV.setVisibility(View.GONE);
            specializationTV.setVisibility(View.GONE);
        }

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    private void initialize() {
        userInfo = new DomainUserInfo();
        toolbar = findViewById(R.id.ShowProfileToolbar);
        nameTV = findViewById(R.id.name_TV);
        emailTV = findViewById(R.id.email_TV);
        phoneTV = findViewById(R.id.phone_TV);
        deptTV = findViewById(R.id.dept_TV);
        availableTimeTV = findViewById(R.id.available_time_tv);
        specializationTV = findViewById(R.id.specialization_tv);
    }

    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Activity_ProifleMenuEditProfile)
            startActivity(new Intent(this, EditProfileActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
