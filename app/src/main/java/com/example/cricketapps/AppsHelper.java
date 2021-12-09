package com.example.cricketapps;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class AppsHelper {

    private static final String COLLECTION_NAME = "Apps";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createAppModel( AppModel category) {
        category.setAppId(category.getAppName().replace(" ", "").toLowerCase());
        return  AppsHelper.getCollection().document(category.getAppName().replace(" ", "").toLowerCase()).set(category);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getBannerInfo(String appName){
        return  AppsHelper.getCollection().document(appName).get();
    }

    public static Task<QuerySnapshot> getProductList() {
        return  AppsHelper.getCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateInvoiceStatus(String uid, int status) {
        return  AppsHelper.getCollection().document(uid).update("status", status);
    }

    public static Task<Void> updateInvoiceFields(String uid, Map<String, Object> map) {
        return  AppsHelper.getCollection().document(uid).update(map);
    }

    // --- DELETE ---

    public static Task<Void> deleteInvoice(String id) {
        return  AppsHelper.getCollection().document(id).delete();
    }
}
