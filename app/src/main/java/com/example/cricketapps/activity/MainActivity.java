package com.example.cricketapps.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cricketapps.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView toolbar_title;
    Button btnAddApp, btnAddBanner, btnGetBannerByAppName, btnGetAllBanner;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();



    }

    void initView()
    {
        btnAddApp = findViewById(R.id.btnAddApp);
        btnAddBanner = findViewById(R.id.btnAddBanner);
        btnGetBannerByAppName = findViewById(R.id.btnGetBannerByAppName);
        btnGetAllBanner = findViewById(R.id.btnGetAllBanner);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Cricket Admin");
        ivBack = findViewById(R.id.ivBack);
        ivBack.setVisibility(View.GONE);
    }

    void initListener()
    {
        btnAddBanner.setOnClickListener(this);
        btnGetBannerByAppName.setOnClickListener(this);
        btnGetAllBanner.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnAddApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAddApp:
                {
                    startActivity(new Intent(MainActivity.this, AddAppActivity.class));
                    break;
                }
            case R.id.btnAddBanner:
                {
                    startActivity(new Intent(MainActivity.this, AddBannerActivity.class));
                    break;
                }
            case R.id.btnGetBannerByAppName:
                {
                    startActivity(new Intent(MainActivity.this, GetBannerInfoActivity.class));
                    break;
                }
            case R.id.btnGetAllBanner:
                {
                    startActivity(new Intent(MainActivity.this, ViewAllBannerActivity.class));
                    break;
                }
        }
    }

  /*  Spinner categorySpinner;
    ProgressBar progressBar;
    ImageView ivSmallBanner,ivLargeBanner;
    Button uploadButton,viewImage;
    private Uri imageUri;
    EditText etBannerName;
    EditText etPhoneNumber;
    EditText etAppName;
    Button btnUploadData;
    String category;
    String flag;
    String bannerId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BannerModel bannerModel;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("images");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        // initializing our object
        // class variable.
        initView();
        bannerModel = new BannerModel();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        String BannerName = etBannerName.getText().toString();
        String AppName = etAppName.getText().toString();
        String PhoneNumber = etPhoneNumber.getText().toString();
        String watsappUrl =" https://wa.me/"+PhoneNumber;

        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData(BannerName,AppName,PhoneNumber,watsappUrl,imageUri);
            }
        });

//
//        spinner();
//        progressBar.setVisibility(View.INVISIBLE);
        ivSmallBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                flag = "small";
                startActivityForResult(gallery,2);
            }
        });
        ivLargeBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                flag = "large";
                startActivityForResult(gallery,2);
            }
        });
//
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (imageUri!=null){
//                    uploadToFirebase(imageUri);
//                }
//                else
//                    Toast.makeText(MainActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void uploadData(String bannerName, String appName, String phoneNumber, String watsappUrl,Uri uri) {

        Log.e("upload data method","");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image  = uri.toString();
//                                progressBar.setVisibility(View.INVISIBLE);
                                bannerId = databaseReference.push().getKey();
                                String bannername= etBannerName.getText().toString();
                                String appname= etAppName.getText().toString();
                                String phonnumber= etPhoneNumber.getText().toString();
                                String watsappUrl =" https://wa.me/"+phonnumber;
                                bannerModel.setBannerName(bannername);
                                bannerModel.setAppName(appname);
                                bannerModel.setPhoneNumber(phonnumber);
                                bannerModel.setWatsappUrl(watsappUrl);
                                bannerModel.setBannerId(bannerId);
                                bannerModel.setSmallBanner(image);
                                bannerModel.setBigBanner(image);



                                databaseReference.child(bannerId).setValue(bannerModel);
                                Toast.makeText(MainActivity.this, "data uploaded", Toast.LENGTH_SHORT).show();
                                Log.e("data uploaded","====0");
                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        progressBar.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            if (flag.equals("small")) {
                ivSmallBanner.setImageURI(imageUri);
            }
            else {
                ivLargeBanner.setImageURI(imageUri);
            }
        }
    }

    private void uploadToFirebase(Uri uri){
        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String image  = uri.toString();
//                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "failed to upload", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
//    getting file extension
    private String getFileExtension(Uri muri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(muri));
    }


//    private void spinner() {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categorySpinner.setAdapter(adapter);
//        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                category = parent.getItemAtPosition(position).toString();
//                Toast.makeText(MainActivity.this, category, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    private void initView() {
//        categorySpinner = findViewById(R.id.category_spinner);
//        progressBar = findViewById(R.id.progressBar);
        ivSmallBanner = findViewById(R.id.ivSmallBanner);
        etBannerName = findViewById(R.id.etBannerName);
        etAppName = findViewById(R.id.etAppName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        ivLargeBanner = findViewById(R.id.ivLargeBanner);
        btnUploadData = findViewById(R.id.btnUploadData);
//        uploadButton = findViewById(R.id.btn_upload);
    }*/
}