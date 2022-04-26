package com.example.onlineshoppingadminapps.callbacks;

import com.example.onlineshoppingadminapps.models.PurchaseModel;


import java.util.List;

public interface OnPurchaseQueryCompleteListener {
    void onPurchaseQueryComplete(List<PurchaseModel> temp);
}
