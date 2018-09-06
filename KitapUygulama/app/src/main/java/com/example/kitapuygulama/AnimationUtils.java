package com.example.kitapuygulama;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Feyyaz on 29.03.2015.
 */
public class AnimationUtils {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true?50:-50, 0);
        animatorTranslateY.setDuration(1000);
        animatorTranslateY.start();

    }
}
