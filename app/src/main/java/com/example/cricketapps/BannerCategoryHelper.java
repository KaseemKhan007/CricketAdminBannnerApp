package com.example.cricketapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class BannerCategoryHelper {

    private static final String COLLECTION_NAME = "Banner";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createBannerModel( BannerModel category) {
        category.setBannerId(category.getAppName().replace(" ", "").toLowerCase());
        return  BannerCategoryHelper.getCollection().document(category.getAppName().replace(" ", "").toLowerCase()).set(category);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getBannerInfo(String appName){
        return  BannerCategoryHelper.getCollection().document(appName).get();
    }

    public static Task<QuerySnapshot> getProductList() {
        return  BannerCategoryHelper.getCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateInvoiceStatus(String uid, int status) {
        return  BannerCategoryHelper.getCollection().document(uid).update("status", status);
    }

    public static Task<Void> updateInvoiceFields(String uid, Map<String, Object> map) {
        return  BannerCategoryHelper.getCollection().document(uid).update(map);
    }

    // --- DELETE ---

    public static Task<Void> deleteInvoice(String id) {
        return  BannerCategoryHelper.getCollection().document(id).delete();
    }
}
