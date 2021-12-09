package com.example.cricketapps.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cricketapps.AppModel;
import com.example.cricketapps.AppsHelper;
import com.example.cricketapps.BannerCategoryHelper;
import com.example.cricketapps.BannerModel;
import com.example.cricketapps.R;
import com.example.cricketapps.Utils;
import com.example.cricketapps.adapter.AppNameAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBannerActivity extends AppCompatActivity {

    /*rules_version = '2';
    service cloud.firestore {
      match /databases/{database}/documents {
        match /{document=**} {
          allow read, write: if true;
        }
      }
    }
    */

    CircularProgressIndicator loadingBar;
    TextView toolbar_title;
    EditText etBannerName,  etPhoneNumber;
    ImageView ivBack, ivSmallBanner, ivLargeBanner;
    Button btnUploadData;
    ArrayList<AppModel> appModelArrayList;
    Spinner spinnerAppName;
    AppNameAdapter appNameAdapter;
    String appName = "";

    private void initView() {
        etBannerName = findViewById(R.id.etBannerName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        ivBack = findViewById(R.id.ivBack);
        ivSmallBanner = findViewById(R.id.ivSmallBanner);
        ivLargeBanner = findViewById(R.id.ivLargeBanner);

        spinnerAppName = findViewById(R.id.spinnerAppName);


        loadingBar = findViewById(R.id.loadingBar);
        btnUploadData = findViewById(R.id.btnUploadData);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Banner");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);

        initView();

        appModelArrayList = new ArrayList<>();
        getAllApps();

         appNameAdapter = new AppNameAdapter(AddBannerActivity.this,
                R.layout.item_app_name, R.id.title, appModelArrayList);
        spinnerAppName.setAdapter(appNameAdapter);

        spinnerAppName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                appName = appModelArrayList.get(position).getAppName();
                Toast.makeText(AddBannerActivity.this, appName+".", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etBannerName.getText().toString().trim().equals("")) {
                    etBannerName.setError("Please Enter Banner Name");
                    etBannerName.setFocusable(true);
                }
                else if (appName.equals("")) {
                    Toast.makeText(AddBannerActivity.this, "PLease select appName", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etPhoneNumber.getText().toString().trim().equals("")) {
                    etPhoneNumber.setError("Please Enter Phone No.");
                    etPhoneNumber.setFocusable(true);
                } else if (imageSmallUri == null) {
                    Toast.makeText(AddBannerActivity.this, "Please select Banner Images", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setVisibility(View.VISIBLE);
                    uploadImage();
                }
            }
        });

        ivSmallBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, Utils.REQUEST_CODE_SMALL_IMAGE_PICKER);
            }
        });

        ivLargeBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, Utils.REQUEST_CODE_BIG_IMAGE_PICKER);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        Map<String, Object> data = new HashMap<>();
//        data.put("task_name", "123321kk");
//        myDB.collection("tasks")
//                .add(data)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(AddBannerActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
//                     //   toastResult("Data added successfully");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(AddBannerActivity.this, "Error while adding the data : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                     //   toastResult("Error while adding the data : " + e.getMessage());
//                    }
//                });


//        Map<String, Object> data = new HashMap<>();
//        data.put("data", "123qwe");
//        myDB.collection("myData").document("1").update(data)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.e(Utils.getTag()+" " , "Data updated successfully ---");
//
//                        Toast.makeText(AddBannerActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        Log.e(Utils.getTag()+" " , "Data update Completed ---");
//                        Toast.makeText(AddBannerActivity.this, "Data update Completed", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(Utils.getTag()+" " , "Error while updating the data : " + e.getMessage());
//                        Toast.makeText(AddBannerActivity.this, "Error while updating the data : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private Uri imageSmallUri = null;
    private Uri imageBigUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utils.REQUEST_CODE_SMALL_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            imageSmallUri = data.getData();
            ivSmallBanner.setImageURI(imageSmallUri);
        } else if (requestCode == Utils.REQUEST_CODE_BIG_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {

            imageBigUri = data.getData();
            ivLargeBanner.setImageURI(imageBigUri);
        }
    }


    //getting file extension
    private String getFileExtension(Uri muri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(muri));
    }

    public void uploadBannerData() {
        Date date = new Date();
        Timestamp ts = new Timestamp(date);
        BannerModel bannerModel;

        if (imageBigUri != null) {
            bannerModel = new BannerModel(
                    etBannerName.getText().toString() + "",
                    appName + "",
                    etPhoneNumber.getText().toString() + "",
                    "https:wa.me/"+etPhoneNumber.getText().toString(),
                    imageSmallUri + "",
                    imageBigUri + "",
                    etBannerName.getText().toString() + "",
                    ts + "",
                    Utils.getISOAbsoluteDate() + ""
            );
        } else {
            bannerModel = new BannerModel(
                    etBannerName.getText().toString() + "",
                    appName + "",
                    etPhoneNumber.getText().toString() + "",
                    "https:wa.me/"+etPhoneNumber.getText().toString(),
                    imageSmallUri + "",
                    "",
                    etBannerName.getText().toString() + "",
                    ts + "",
                    Utils.getISOAbsoluteDate() + ""
            );
        }

        BannerCategoryHelper.createBannerModel(bannerModel).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(AddBannerActivity.this, "Try Again!", Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddBannerActivity.this, "Category Added Sucessfully", Toast.LENGTH_SHORT).show();
                etBannerName.setText("");
                appName = "";
                etPhoneNumber.setText("");
                imageBigUri = null;
                imageSmallUri = null;
                loadingBar.setVisibility(View.GONE);
                finish();
            }
        });
    }

    public void uploadImage() {

        if (imageSmallUri != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "_small" + "." + getFileExtension(imageSmallUri));
            Log.e(Utils.getTag() + " ", " ref=======" + ref);

            ref.putFile(imageSmallUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri1) {
                            imageSmallUri = uri1;
                            Log.e(Utils.getTag(), "imageUriSmall=======" + imageSmallUri);

                            if (imageBigUri != null) {
                                StorageReference ref2 = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "_large" + "." + getFileExtension(imageBigUri));
                                Log.e(Utils.getTag() + " ", "ref2=======" + ref2);

                                ref2.putFile(imageBigUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri3) {
                                                imageBigUri = uri3;
                                                Log.e(Utils.getTag(), " 1. imageSmallUri =======" + imageSmallUri);
                                                Log.e(Utils.getTag(), " 2. imageBigUri =======" + imageBigUri);

                                                {
                                                    uploadBannerData();
                                                }
                                            }
                                        });
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                        Log.i(Utils.getTag() + " ", " getTotalByteCount =" + snapshot.getTotalByteCount());

                                        double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                        //System.out.println("Upload is " + progress + "% done");
                                        int currentprogress = (int) progress;

                                        Log.i(Utils.getTag() + " ", " currentprogress =" + currentprogress + " % done");

                                        if (currentprogress < 100) {

                                            // dialog_UplodingProgress.dismiss();
                                            //  Toast.makeText(AddWallpaperActivity.this, "currentprogress ==" + currentprogress, Toast.LENGTH_SHORT).show();
                                            //            showUploadedDialog();
                                        }
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingBar.setVisibility(View.GONE);
                                                Toast.makeText(AddBannerActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
                                                //  Utils.hideProgressBar();
                                                // progressBar.setVisibility(View.INVISIBLE);
                                            }
                                        });
                            } else {
                                Log.e(Utils.getTag(), " ONLY SMALL URI =======" + imageSmallUri);

                                {
                                    uploadBannerData();
                                }
                            }
                        }
                    });
                }
            });
        } else {
            loadingBar.setVisibility(View.GONE);
            Toast.makeText(this, "Please select Small Image", Toast.LENGTH_SHORT).show();
        }
    }


    private void getAllApps() {

        CollectionReference reference = AppsHelper.getCollection();

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

                            List<AppModel> types = queryDocumentSnapshots.toObjects(AppModel.class);

                            Log.e(Utils.getTag() + " ", " List<BannerModel.size() " + types.size());


                            // Add all to your list
                            appModelArrayList.clear();
                            appModelArrayList.addAll(types);
                            appNameAdapter.notifyDataSetChanged();

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