package com.example.onlineshoppingadminapps.repositories;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.onlineshoppingadminapps.callbacks.OnCategoriesQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnProductQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnPurchaseQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnSingleProductQueryCompleteListener;
import com.example.onlineshoppingadminapps.models.ProductModel;
import com.example.onlineshoppingadminapps.models.PurchaseModel;
import com.example.onlineshoppingadminapps.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserRepository {

    private final String TAG = UserRepository.class.getSimpleName();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


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

}
