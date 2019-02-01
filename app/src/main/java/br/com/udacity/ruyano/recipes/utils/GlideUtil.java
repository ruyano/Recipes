package br.com.udacity.ruyano.recipes.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.udacity.ruyano.recipes.R;

class GlideUtil {

    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView)
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_cooking)
                                .centerCrop())
                        .into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
        }

    }

}

