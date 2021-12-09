package com.example.cricketapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cricketapps.BannerCategoryHelper;
import com.example.cricketapps.BannerModel;
import com.example.cricketapps.R;
import com.example.cricketapps.Utils;
import com.example.cricketapps.adapter.BannerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllBannerActivity extends AppCompatActivity {

    TextView  toolbar_title;
    ArrayList<BannerModel> bannerModelArrayList;
    RecyclerView rvBanners;
    BannerAdapter bannerAdapter;
    ImageView ivBack;
    CircularProgressIndicator loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_banner);

        loadingBar = findViewById(R.id.loadingBar);
        loadingBar.setVisibility(View.VISIBLE);
        rvBanners = findViewById(R.id.rvBanners);
        ivBack = findViewById(R.id.ivBack);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("All Banner");

        bannerModelArrayList = new ArrayList<>();

        bannerAdapter = new BannerAdapter(this, bannerModelArrayList);
        //  trendingAdapter.setListener(this);
        rvBanners.setHasFixedSize(true);
        LinearLayoutManager hotLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBanners.setLayoutManager(hotLayoutManager);
        rvBanners.setAdapter(bannerAdapter);

        getAllBanner();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getAllBanner() {

        CollectionReference reference = BannerCategoryHelper.getCollection();

        reference.orderBy("timestamp", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Log.d("145", "onSuccess: LIST EMPTY");

                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.

                            List<BannerModel> types = queryDocumentSnapshots.toObjects(BannerModel.class);

                            Log.e(Utils.getTag() + " ", " List<BannerModel.size() " + types.size());

                            // Add all to your list
                            bannerModelArrayList.clear();
                            bannerModelArrayList.addAll(types);
                            bannerAdapter.notifyDataSetChanged();
                            loadingBar.setVisibility(View.GONE);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        loadingBar.setVisibility(View.GONE);
                        Log.e(Utils.getTag() + " ", " onFailure e.getMessage =" + e.getMessage());
                    }
                });

    }

}