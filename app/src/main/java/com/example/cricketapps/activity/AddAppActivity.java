package com.example.cricketapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cricketapps.AppModel;
import com.example.cricketapps.AppsHelper;
import com.example.cricketapps.BannerCategoryHelper;
import com.example.cricketapps.BannerModel;
import com.example.cricketapps.R;
import com.example.cricketapps.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.Timestamp;

import java.util.Date;

public class AddAppActivity extends AppCompatActivity {

    EditText etAppName, etAppBundleName;
    Button btnAddApp;
    CircularProgressIndicator loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);

        etAppName = findViewById(R.id.etAppName);
        etAppBundleName = findViewById(R.id.etAppBundleName);
        btnAddApp = findViewById(R.id.btnAddApp);
        loadingBar = findViewById(R.id.loadingBar);

        btnAddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAppName.getText().toString().trim().equals("")) {
                    etAppName.setError("Please Enter App Name");
                    etAppName.setFocusable(true);
                }if (etAppBundleName.getText().toString().trim().equals("")) {
                    etAppBundleName.setError("Please Enter App Bundle Name");
                    etAppBundleName.setFocusable(true);
                }
                else
                {
                    uploadAppData();
                }
            }
        });
    }


    public void uploadAppData() {
        loadingBar.setVisibility(View.VISIBLE);
        Date date = new Date();
        Timestamp ts = new Timestamp(date);

        AppModel  appModel = new AppModel(
                    etAppName.getText().toString() + "",
                etAppBundleName.getText().toString() + "",
                    etAppName.getText().toString().trim().replace(" ","").toLowerCase() + "",
                    ts + "",
                    Utils.getISOAbsoluteDate() + ""
            );


        AppsHelper.createAppModel(appModel).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(AddAppActivity.this, "Try Again!", Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddAppActivity.this, "App Added Sucessfully", Toast.LENGTH_SHORT).show();

                etAppName.setText("");
                etAppBundleName.setText("");

                loadingBar.setVisibility(View.GONE);
                finish();
            }
        });
    }
}