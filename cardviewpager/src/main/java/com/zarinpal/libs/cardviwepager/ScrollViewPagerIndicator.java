package com.zarinpal.libs.cardviwepager;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Custom ViewPager Indicator Created by farshid roohi on 12/10/17.
 */


public class ScrollViewPagerIndicator extends HorizontalScrollView {
    // In dp
    private int DEFAULT_DOT_SIZE = 15;
    private int DEFAULT_GAP_SIZE = 3;
    private int DEFAULT_SCROLL_SPEED = 100;


    private LinearLayout linearLayout;

    private Integer dotSize;
    private Integer gapSize;
    private Integer scrollSpeed;

    private Integer itemMargin;
    private Integer visibleItems;
    private Integer extraItems;

    private int realNumItems = 0;
    private int numItems = 0;

    private int currentCenterLayoutPosition = 0;

    private Drawable drawableSelect;
    private Drawable drawableUnSelect;
    private Drawable drawableTransparent;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

            goTo(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public ScrollViewPagerIndicator(Context context) {
        super(context);
    }

    public ScrollViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet,
                R.styleable.ScrollViewPagerIndicator);

        this.dotSize = typedArray.getDimensionPixelSize(R.styleable.ScrollViewPagerIndicator_dotSize,
                Utilities.dpToPx(getContext(), DEFAULT_DOT_SIZE));
        this.gapSize = typedArray.getDimensionPixelSize(R.styleable.ScrollViewPagerIndicator_gapSize,
                Utilities.dpToPx(getContext(), DEFAULT_GAP_SIZE));
        this.scrollSpeed = typedArray.getInteger(R.styleable.ScrollViewPagerIndicator_scrollSpeed,
                DEFAULT_SCROLL_SPEED);
        this.visibleItems = typedArray.getInteger(R.styleable.ScrollViewPagerIndicator_visibleItems, 0);

        typedArray.recycle();

        this.itemMargin = this.gapSize / 2;

        setHorizontalScrollBarEnabled(false);
    }

    public void initAttrbs(int dotSize, int gapSize, int scrollSpeed, int visibleItems) {
        this.dotSize = dotSize;
        this.gapSize = gapSize;
        this.scrollSpeed = scrollSpeed;
        this.visibleItems = visibleItems;
    }

    public void setDrawableResource(Drawable drawableSelect, Drawable drawableUnSelect, Drawable drawableTransparent) {

        this.drawableSelect = drawableSelect;
        this.drawableUnSelect = drawableUnSelect;
        this.drawableTransparent = drawableTransparent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    public void attachViewPager(final ViewPager viewPager) {
        if (this.dotSize == null)
            this.dotSize = (int) getResources().getDimension(this.DEFAULT_DOT_SIZE);
        if (this.gapSize == null)
            this.gapSize = (int) getResources().getDimension(this.DEFAULT_GAP_SIZE);

        if (viewPager.getAdapter() == null) return;

        viewPager.addOnPageChangeListener(onPageChangeListener);

        measureItems(viewPager);
        measureParentLayout();

        this.linearLayout = new LinearLayout(getContext());
        this.linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        this.linearLayout.setLayoutParams(linearLayoutParams);

        if (getChildCount() > 0) removeAllViewsInLayout();

        addView(this.linearLayout);

        for (int i = 0; i < this.numItems; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());

            LinearLayout.LayoutParams frameLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            frameLayoutParams.width = dotSize;
            frameLayoutParams.height = dotSize;

            Drawable drawable;
            int marginStart, marginEnd;
            if (i < this.extraItems || i > this.realNumItems + 1) {
                drawable = this.drawableTransparent;
            } else if (i == this.realNumItems + 1) {
                drawable = this.drawableSelect;
            } else {
                drawable = this.drawableUnSelect;
            }

            if (i == 0) {
                marginStart = this.itemMargin;
                marginEnd = this.itemMargin * 2;
            } else if (i == this.numItems - 1) {
                marginStart = this.itemMargin * 2;
                marginEnd = this.itemMargin;
            } else {
                marginStart = this.itemMargin;
                marginEnd = this.itemMargin;
            }

            frameLayoutParams.setMargins(marginStart, 0, marginEnd, 0);

            frameLayout.setLayoutParams(frameLayoutParams);
            frameLayout.setBackground(drawable);

            this.linearLayout.addView(frameLayout, 0);
        }
        setAlphas();
    }

    public void goTo(int position) {
        int newCenterLayoutPosition = position + this.extraItems;
        View oldCenterView = this.linearLayout.getChildAt(currentCenterLayoutPosition);

        if (oldCenterView != null) {
            oldCenterView.setBackground(this.drawableUnSelect);
        }

        int positionToScroll = (this.gapSize * position) + (position * this.dotSize);

        ObjectAnimator.ofInt(this, "scrollX", positionToScroll).setDuration(scrollSpeed).start();

        View newCenterView = this.linearLayout.getChildAt(newCenterLayoutPosition);

        if (newCenterView != null) {
            newCenterView.setBackground(this.drawableSelect);

        }

        this.currentCenterLayoutPosition = position + extraItems;

        setAlphas();
    }

    private void measureItems(ViewPager viewPager) {
        this.realNumItems = viewPager.getAdapter().getCount();

        if (this.realNumItems < this.visibleItems) this.visibleItems = this.realNumItems;

        if (this.visibleItems > 5) {
            this.visibleItems = 5;
        } else if (this.visibleItems < 3) {
            this.visibleItems = 3;
        } else {
            if ((this.visibleItems % 2) != 0) this.visibleItems--;
        }

        this.extraItems = this.visibleItems / 2;

        this.numItems = this.realNumItems + this.extraItems * 2;

        this.currentCenterLayoutPosition = this.extraItems;
    }

    private void measureParentLayout() {
        ViewGroup.LayoutParams viewGroupLayoutParams = getLayoutParams();
        viewGroupLayoutParams.width = (this.itemMargin * 2) + (this.visibleItems * (this.dotSize + (this.itemMargin * 2)));
        viewGroupLayoutParams.height = this.dotSize;

        setLayoutParams(viewGroupLayoutParams);
    }

    private void setAlphas() {
        float alpha = 0.1f;
        for (int i = this.extraItems; i > 0; i--) {

            if (i == this.extraItems) {
                View externalLeftView = this.linearLayout.getChildAt(this.currentCenterLayoutPosition - i - 1);
                View externalRightView = this.linearLayout.getChildAt(this.currentCenterLayoutPosition + i + 1);

                if (externalLeftView != null) {
                    externalLeftView.setAlpha(0);
                }

                if (externalRightView != null) {
                    externalRightView.setAlpha(0);
                }
            }

            View leftView = this.linearLayout.getChildAt(this.currentCenterLayoutPosition - i);
            View rightView = this.linearLayout.getChildAt(this.currentCenterLayoutPosition + i);

            float currentAlpha = leftView.getAlpha();

            AlphaAnimation animation = new AlphaAnimation(currentAlpha, alpha);
            animation.setDuration(100);

            leftView.setAlpha(alpha);


            if (rightView != null) {
                rightView.setAlpha(alpha);
            }

            alpha = ((float) 1.0 / extraItems) + alpha;
        }

        View centerView = this.linearLayout.getChildAt(this.currentCenterLayoutPosition);
        centerView.setAlpha(1f);
    }


}