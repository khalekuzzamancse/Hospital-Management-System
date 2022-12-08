package com.example.hospital.joydip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.ProgressBar;

import com.example.hospital.joydip.firebasetemplate.CallbackResult;
import com.example.hospital.joydip.firebasetemplate.FirebaseAuthCustom;
import com.example.hospital.joydip.firebasetemplate.FormFillUpInfo;
import com.example.hospital.joydip.firebasetemplate.WritingDocument;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ProgressBar p;
    EditText nameET, emailET, phoneET, passwordET;
    LinearLayout doctorInfoContainer;
    String userType = "";
    AutoCompleteTextView registrationAsACTV, deptACTV, availableACTV, specializationACTV;
    ArrayAdapter<String> registrationAsAdapter,deptAdapter, specializationAdapter,availableTimeAdapter;
    Toolbar toolbar;
    Button submitBTN, cancelBTN;
    FormFillUpInfo fillUpInfo;
    String nameStr, phoneStr, emailStr, availableTimeStr, deptStr, passwordStr, specializationStr;

    private CallbackResult result = response -> {
        // progressBar.setVisibility(View.INVISIBLE);
        if (response)
            showSnackBar("Registration Successful");
        else
            showSnackBar("Failed");

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initialize();
       // setToolbar();
        setUserType();
        setDept();
        setAvailableTime();
        setSpecialization();



        registrationAsACTV.setOnItemClickListener((parent, view, position, id) -> {
            userType = parent.getItemAtPosition(position).toString();
            if (userType.equals("Doctor")) {
                doctorInfoContainer.setVisibility(View.VISIBLE);
            } else {
                doctorInfoContainer.setVisibility(View.GONE);
            }

        });
        submitBTN.setOnClickListener(view -> {
            if (userType.equals("Patient"))
                clearTeacherInfo();
            doRegistration();
        });


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
        nameET = findViewById(R.id.name_ET);
        phoneET = findViewById(R.id.phone_ET);
        passwordET = findViewById(R.id.password_ET);
        emailET = findViewById(R.id.email_ET);
        availableACTV = findViewById(R.id.available_time_ACTV);
        specializationACTV = findViewById(R.id.spectialization_ACTV);
        registrationAsACTV = findViewById(R.id.registerAs_ACTV);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        submitBTN = findViewById(R.id.submit_BTN);
        cancelBTN = findViewById(R.id.cancel_BTN);
        deptACTV = findViewById(R.id.dept_ACTV);
        doctorInfoContainer = findViewById(R.id.teacherInfoContainer);
        fillUpInfo = new FormFillUpInfo();

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");
    }

    private void setDept() {
        String[] list = {"Dentistry", "Surgery"};
        deptAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        deptACTV.setAdapter(deptAdapter);
    }

    private void setUserType() {
        String[] list = {"Doctor", "Patient"};
        registrationAsAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        registrationAsACTV.setAdapter(registrationAsAdapter);
    }

    private void setAvailableTime() {
        String[] list = {"7 AM-9 AM", "9 AM-11 AM","11 AM-1 PM","2 PM-4 PM","4 PM-6 PM"};
       availableTimeAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        availableACTV.setAdapter(availableTimeAdapter);
    }
    private void setSpecialization() {
        String[] list = {"General","Periodontist"};
        specializationAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
       specializationACTV.setAdapter(specializationAdapter);
    }


    private void doRegistration() {
        //order of the calling function is important
        getUserInfo();
        HashMap<String, Object> data = makeMap();
      new WritingDocument().updateDocument(emailStr,data,result);
     new FirebaseAuthCustom().registerUser(emailStr,passwordStr);
    }

    private HashMap<String, Object> makeMap() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", nameStr);
        data.put("phoneNo", phoneStr);
        data.put("email", emailStr);
        data.put("userType", userType);
        data.put("availableTime", availableTimeStr);
        data.put("specialization", specializationStr);
        data.put("dept", deptStr);
        data.put("password", passwordStr);
        return data;
    }

    private void getUserInfo() {
        nameStr = nameET.getText().toString();
        phoneStr = phoneET.getText().toString();
        emailStr = emailET.getText().toString();
        passwordStr = passwordET.getText().toString();
        if (userType.equals("Doctor")) {
            availableTimeStr = availableACTV.getText().toString();
            deptStr = deptACTV.getText().toString();
            specializationStr = specializationACTV.getText().toString();
        }

    }

    private void clearTeacherInfo() {
        availableTimeStr = "";
        specializationStr = "";
        deptStr = "";
    }
    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(submitBTN, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }


}