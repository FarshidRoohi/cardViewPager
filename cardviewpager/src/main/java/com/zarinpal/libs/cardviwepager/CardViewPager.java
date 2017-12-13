package com.zarinpal.libs.cardviwepager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Card ViewPager Created by farshid roohi on 12/13/17.
 */

public class CardViewPager extends ViewPager {

    private ShadowTransformer cardShadowTransformer;

    public CardViewPager(Context context) {
        super(context);
    }

    public CardViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addShadowTransformer(BaseCardViewPagerItem adapter) {
        this.cardShadowTransformer = new ShadowTransformer(this, adapter);
        this.cardShadowTransformer.enableScaling(false);
        this.setPageTransformer(false, this.cardShadowTransformer);
    }

    public void enableScaling(boolean flag) {
        if (this.cardShadowTransformer == null) {
            return;
        }
        this.cardShadowTransformer.enableScaling(flag);
    }
}
