package com.example.hospital.joydip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.hospital.joydip.firebasetemplate.CallbackResult;
import com.example.hospital.joydip.firebasetemplate.CallbackUserProfile;
import com.example.hospital.joydip.firebasetemplate.DomainUserInfo;
import com.example.hospital.joydip.firebasetemplate.FirebaseAuthCustom;
import com.example.hospital.joydip.firebasetemplate.FormFillUpInfo;
import com.example.hospital.joydip.firebasetemplate.WritingDocument;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    AutoCompleteTextView deptACTV, availableTimeACTV, specializationACTV;
    Button submitBTN, cancelBTN;
    EditText nameET, phoneET;
    Toolbar toolbar;
    DomainUserInfo info;
    ArrayAdapter<String> availableTimeAdapter, specializationAdapter, deptAdapter;
    FormFillUpInfo fillUpInfo;
    LinearLayout container;


    //after reading the data we will update the view ....
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            info = profile;
            if (!info.userType.equals("Doctor"))
                container.setVisibility(View.GONE);
            nameET.setText(info.name);
            phoneET.setText(info.phoneNo);
            if (!info.specialization.isEmpty())
                specializationACTV.setText(info.specialization, false);
            if (!info.availableTime.isEmpty())
                availableTimeACTV.setText(info.availableTime, false);
            if (!info.dept.isEmpty())
                deptACTV.setText(info.dept, false);

            //if the user is a donor then it have age other wise age is null
            // because non donor use not store the age information
//            if(info.=null)
//                ageET.setText(info.Age);

        }
    };

    //
    private CallbackResult callbackResult = new CallbackResult() {

        @Override
        public void isSuccess(boolean response) {
            //  progressBar.setVisibility(View.INVISIBLE);
            if (response)
                showSnackBar("Updated Successfully");
            else
                showSnackBar("Failed");
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//            Intent i = new Intent(Become_Donor_Activity.this, MainActivity.class);
//            startActivity(i);
        }
    };
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        initialize();
        initialize();
        // setToolbar();
        setDept();
        setAvailableTime();
        setSpecialization();


        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);


        submitBTN.setOnClickListener(view -> {
            updateInfo();

        });
        cancelBTN.setOnClickListener(view -> {
            finish();

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    private void initialize() {
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        info = new DomainUserInfo();
        fillUpInfo = new FormFillUpInfo();
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phone_noET);
        cancelBTN = findViewById(R.id.cancelBTN);
        availableTimeACTV = findViewById(R.id.available_time_ACTV);
        submitBTN = findViewById(R.id.submitBTN);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        //  progressBar = findViewById(R.id.progressBar);
        deptACTV = findViewById(R.id.dept_ACTV);
        specializationACTV = findViewById(R.id.specialization_ACTV);
        container = findViewById(R.id.teacher_info_container);

    }


    private void setDept() {
        String[] list = {"Dentistry", "Surgery"};
        deptAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        deptACTV.setAdapter(deptAdapter);
    }

    private void setAvailableTime() {
        String[] list = {"7 AM-9 AM", "9 AM-11 AM", "11 AM-1 PM", "2 PM-4 PM", "4 PM-6 PM"};
        availableTimeAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        availableTimeACTV.setAdapter(availableTimeAdapter);

    }

    private void setSpecialization() {
        String[] list = {"General", "Periodontist"};
        specializationAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        specializationACTV.setAdapter(specializationAdapter);
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");
    }

    private void updateInfo() {
        HashMap<String, Object> data = new HashMap<>();
        String name = nameET.getText().toString().trim();
        data.put("name", name);
        String phone = phoneET.getText().toString().trim();
        data.put("phoneNo", phone);
        String availableTimeStr = availableTimeACTV.getText().toString().trim();
        data.put("availableTime", availableTimeStr);
        String deptStr = deptACTV.getText().toString().trim();
        data.put("dept", deptStr);
        String spec=specializationACTV.getText().toString().trim();
        data.put("specialization",spec);
        WritingDocument document = new WritingDocument();
        document.updateDocument(info.email, data, callbackResult);
        Log.i("DataGot", String.valueOf(data));
    }

    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(submitBTN, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

    ////replace the back button with navigationUp because
    //1.Main activity read the data from the database
    //2.based on user data some menu item will be hide
    //3.if we use navigation up then main activity will be recreated
    //4.as a result we got the updated data
    //5.according to updated data menu item list will be updated
    @Override
    public void onBackPressed() {
        onNavigateUp();
    }


}