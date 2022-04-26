package com.example.onlineshoppingadminapps.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlineshoppingadminapps.callbacks.OnCategoriesQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnCheckAdminListener;
import com.example.onlineshoppingadminapps.callbacks.OnProductQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnPurchaseQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnSingleProductQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnUserQueryCompleteListener;
import com.example.onlineshoppingadminapps.models.ProductModel;
import com.example.onlineshoppingadminapps.models.PurchaseModel;
import com.example.onlineshoppingadminapps.models.Users;
import com.example.onlineshoppingadminapps.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminRepository {
    private final String TAG = AdminRepository.class.getSimpleName();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addNewProduct(ProductModel productModel, PurchaseModel purchaseModel){
        final DocumentReference pDoc = db.collection(Constants.COLLECTION_PRODUCT).document();
        productModel.setProductId(pDoc.getId());
        final DocumentReference purDoc = db.collection(Constants.COLLECTION_PURCHASE).document();
        purchaseModel.setPurchaseId(purDoc.getId());
        purchaseModel.setProductId(pDoc.getId());

        final WriteBatch writeBatch = db.batch();
        writeBatch.set(pDoc, productModel);
        writeBatch.set(purDoc, purchaseModel);
        writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e(TAG, "saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failed");
            }
        });
    }


    public void getAllCategories(OnCategoriesQueryCompleteListener listener){
        db.collection(Constants.COLLECTION_CATEGORIES).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("DATA","Error "+error.getLocalizedMessage());
                    return;
                }
                final List<String> temp = new ArrayList<>();
                assert value != null;
                for (DocumentSnapshot doc: value.getDocuments()){
                    temp.add(doc.get("name",String.class));
                }
                Log.e("DATA",""+temp.size());
                Collections.sort(temp);
                listener.onCategoryQueryComplete(temp);
            }
        });



    }
    public void getAllProducts(OnProductQueryCompleteListener listener){
        db.collection(Constants.COLLECTION_PRODUCT).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                final List<ProductModel> temp = new ArrayList<>();
                for (DocumentSnapshot doc: value.getDocuments()){
                    temp.add(doc.toObject(ProductModel.class));
                }
                listener.onProductQueryComplete(temp);
            }
        });
    }
    public void getProductByProductId(String productId, OnSingleProductQueryCompleteListener listener) {
        db.collection(Constants.COLLECTION_PRODUCT).document(productId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                assert value != null;
                final ProductModel model = value.toObject(ProductModel.class);
                listener.onSingleProductQueryComplete(model);

            }
        });
    }
    public void getPurchasesByProductId(String productId, OnPurchaseQueryCompleteListener listener) {
        db.collection(Constants.COLLECTION_PURCHASE)
                .whereEqualTo("productId", productId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final List<PurchaseModel> temp = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        temp.add(doc.toObject(PurchaseModel.class));
                    }
                    listener.onPurchaseQueryComplete(temp);
                });
    }


    public void checkAdmin(String uid, OnCheckAdminListener listener) {
        db.collection(Constants.COLLECTION_ADMIN)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot>
                                                   task) {
                        listener.onCheckAdmin(task.getResult().exists());
                    }
                });
    }
    public void getAllUser(OnUserQueryCompleteListener listener){
        db.collection(Constants.COLLECTION_USER).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                final List<Users> temps = new ArrayList<>();
                assert value != null;
                for (DocumentSnapshot doc: value.getDocuments()){
                    temps.add(doc.toObject(Users.class));
                }
                listener.onUserQueryComplete(temps);
//                listener.onProductQueryComplete(temp);
            }
        });
    }

    public void addUser(Users users) {
        final DocumentReference doc = db.collection(Constants.COLLECTION_USER).document(users.getUserId());

        doc.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e(TAG, "User is insert to database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "User is failure to database: "+e.getLocalizedMessage());
            }
        });
//            Log.e(TAG, "addUserToDatabase: "+e.getLocalizedMessage());
    }



}
