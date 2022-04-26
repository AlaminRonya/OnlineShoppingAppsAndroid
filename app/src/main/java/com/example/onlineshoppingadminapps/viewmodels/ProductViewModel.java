package com.example.onlineshoppingadminapps.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshoppingadminapps.callbacks.OnCategoriesQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnProductQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnPurchaseQueryCompleteListener;
import com.example.onlineshoppingadminapps.callbacks.OnUserQueryCompleteListener;
import com.example.onlineshoppingadminapps.models.ProductModel;
import com.example.onlineshoppingadminapps.models.PurchaseModel;
import com.example.onlineshoppingadminapps.models.Users;
import com.example.onlineshoppingadminapps.repositories.AdminRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductViewModel extends ViewModel {
    private final AdminRepository repository = new AdminRepository();

    public MutableLiveData<List<String>> categoryListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> productListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<PurchaseModel>> purchaseListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Users>> usersListLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductModel> productLiveData = new MutableLiveData<>();
    private final List<ProductModel> productModelList = new ArrayList<>();

    public ProductViewModel() {
        getCategories();
        getAllProducts();
    }

    public void addProduct(ProductModel productModel, PurchaseModel purchaseModel) {
        repository.addNewProduct(productModel, purchaseModel);
    }

    public void getCategories(){
        repository.getAllCategories(new OnCategoriesQueryCompleteListener() {
            @Override
            public void onCategoryQueryComplete(List<String> items) {
                categoryListLiveData.postValue(items);
            }
        });
    }

    public void getAllProducts(){
        repository.getAllProducts(new OnProductQueryCompleteListener() {
            @Override
            public void onProductQueryComplete(List<ProductModel> models) {
                productListLiveData.postValue(models);
                productModelList.clear();
                productModelList.addAll(models);
            }
        });
    }

    public void getProductById(String productId) {
        repository.getProductByProductId(productId, productModel -> {
            productLiveData.postValue(productModel);
        });
    }
    public void getPurchaseProductById(String productId) {
        repository.getPurchasesByProductId(productId, new OnPurchaseQueryCompleteListener() {
            @Override
            public void onPurchaseQueryComplete(List<PurchaseModel> temp) {
                purchaseListLiveData.postValue(temp);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getProductByCategory(String category) {
            List<ProductModel> collect = productModelList.stream().filter(productModel -> productModel.getCategory().equals(category))
                    .collect(Collectors.toList());

            productListLiveData.postValue(collect);

    }





    public void getAllUsers(){
        repository.getAllUser(new OnUserQueryCompleteListener() {
            @Override
            public void onUserQueryComplete(List<Users> users) {
                usersListLiveData.postValue(users);
            }
        });
    }

    public LiveData<List<Users>> getUsersListLiveData() {
        return usersListLiveData;
    }
}
