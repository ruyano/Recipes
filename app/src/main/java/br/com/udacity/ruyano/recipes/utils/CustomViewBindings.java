package br.com.udacity.ruyano.recipes.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class CustomViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindImageViewUrl(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl);

    }

}
