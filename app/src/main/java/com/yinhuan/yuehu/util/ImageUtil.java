package com.yinhuan.yuehu.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yinhuan.yuehu.R;


public class ImageUtil {

    private static ImageUtil instance;


    private ImageUtil() {
    }

    public static ImageUtil getInstance() {
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void loadGif(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(imageView);
    }

}
