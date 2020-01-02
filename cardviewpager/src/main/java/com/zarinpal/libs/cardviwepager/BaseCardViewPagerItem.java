package com.zarinpal.libs.cardviwepager;

import androidx.annotation.LayoutRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Card View ViewPager Item Created by farshid roohi on 12/12/17.
 */

public abstract class BaseCardViewPagerItem<T> extends PagerAdapter implements CardElevationMax {

    private List<CardView> views;
    private List<T> models;
    private float baseElevation;

    @LayoutRes
    public abstract int getLayout();

    public abstract void bindView(View view, T item);

    public BaseCardViewPagerItem() {
        this.views = new ArrayList<>();
        this.models = new ArrayList<>();
    }

    public void addCardItem(T item) {
        this.views.add(null);
        this.models.add(item);
    }

    public T getItem(int position) {
        return this.models.get(position);
    }

    public float getBaseElevation() {
        return this.baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return this.views.get(position);
    }

    @Override
    public int getCount() {
        return this.models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(getLayout(), container, false);
        container.addView(rootView);
        CardView cardView = getCardView((ViewGroup) rootView);

        if (cardView != null) {
            cardView.setCardElevation(1);
            this.bindView(rootView, this.models.get(position));
            if (this.baseElevation == 0) {
                this.baseElevation = cardView.getCardElevation();
            }
            cardView.setMaxCardElevation(this.baseElevation * MAX_ELEVATION_FACTOR);
            this.views.set(position, cardView);
        }
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        this.views.set(position, null);
    }

    public void setElevation(float value) {
        this.baseElevation = value;
    }

    private CardView getCardView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof CardView) {
                return (CardView) child;
            }
            if (child instanceof ViewGroup) {
                return getCardView((ViewGroup) child);
            }
        }
        return null;
    }
}
