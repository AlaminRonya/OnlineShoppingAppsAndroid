package com.example.onlineshoppingadminapps.callbacks;

import com.example.onlineshoppingadminapps.models.ProductModel;


import java.util.List;

public interface OnProductQueryCompleteListener {
    void onProductQueryComplete(List<ProductModel> models);
}
