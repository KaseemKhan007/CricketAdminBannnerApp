package com.example.cricketapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cricketapps.BannerCategoryHelper;
import com.example.cricketapps.BannerModel;
import com.example.cricketapps.R;
import com.example.cricketapps.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.DocumentSnapshot;

public class GetBannerInfoActivity extends AppCompatActivity {

    Button btnGetBannerInfo;
    EditText etAppName;
    TextView tvAppName, tvBannerName, tvPhoneNumber;
    ImageView ivSmallBanner, ivLargeBanner;
    TextView  toolbar_title;
    ImageView ivBack;

    CircularProgressIndicator loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_banner_info);

        ivBack = findViewById(R.id.ivBack);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Get Banner Info");
        etAppName = findViewById(R.id.etAppName);
        btnGetBannerInfo = findViewById(R.id.btnGetBannerInfo);
        tvAppName = findViewById(R.id.tvAppName);
        tvBannerName = findViewById(R.id.tvBannerName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        ivSmallBanner = findViewById(R.id.ivSmallBanner);
        ivLargeBanner = findViewById(R.id.ivLargeBanner);
        loadingBar = findViewById(R.id.loadingBar);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnGetBannerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etAppName.getText().toString().trim().equals(""))
                {
                    loadingBar.setVisibility(View.VISIBLE);
                    BannerCategoryHelper.getBannerInfo(etAppName.getText().toString().replace(" ", "").toLowerCase()+"")
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingBar.setVisibility(View.GONE);
                                    e.printStackTrace();
                                    Log.e(Utils.getTag() + " ", " onFailure e.getMessage =" + e.getMessage());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    BannerModel bannerModel = documentSnapshot.toObject(BannerModel.class);
                                    if (bannerModel != null)
                                    {

                                        Log.e(Utils.getTag()+" ", " getAppName ="+ bannerModel.getAppName());
                                        Log.e(Utils.getTag()+" ", " getBannerId ="+ bannerModel.getBannerId());
                                        Log.e(Utils.getTag()+" ", " getBannerName ="+ bannerModel.getBannerName());
                                        Log.e(Utils.getTag()+" ", " getPhoneNumber ="+ bannerModel.getPhoneNumber());
                                        Log.e(Utils.getTag()+" ", " getSmallBanner ="+ bannerModel.getSmallBanner());
                                        Log.e(Utils.getTag()+" ", " getBigBanner ="+ bannerModel.getBigBanner());

                                        tvAppName.setText(bannerModel.getAppName()+"");
                                        tvBannerName.setText(bannerModel.getBannerName()+"");
                                        tvPhoneNumber.setText(bannerModel.getPhoneNumber()+"");

                                        Glide.with(GetBannerInfoActivity.this)
                                                .load(bannerModel.getSmallBanner())
                                                .placeholder(R.drawable.ic_launcher_background)
                                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                                .error(R.drawable.ic_launcher_background)
                                                .into(ivSmallBanner);

                                        Glide.with(GetBannerInfoActivity.this)
                                                .load(bannerModel.getBigBanner())
                                                .placeholder(R.drawable.ic_launcher_background)
                                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                                                .error(R.drawable.ic_launcher_background)
                                                .into(ivLargeBanner);

                                        loadingBar.setVisibility(View.GONE);

                                    }else {
                                        loadingBar.setVisibility(View.GONE);
                                        Toast.makeText(getBaseContext(), "Auth Required!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else
                    {
                        Toast.makeText(GetBannerInfoActivity.this, "Please Enter App Name", Toast.LENGTH_SHORT).show();
                    }
           }
        });
    }
}