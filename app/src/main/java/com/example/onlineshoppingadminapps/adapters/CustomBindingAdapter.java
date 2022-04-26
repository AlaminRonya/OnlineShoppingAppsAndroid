package com.example.onlineshoppingadminapps.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class CustomBindingAdapter {
    @BindingAdapter(value = "app:setIcon")
    public static void setIcon(ImageView imageView, int icon ){
        imageView.setImageResource(icon);
    }
}
