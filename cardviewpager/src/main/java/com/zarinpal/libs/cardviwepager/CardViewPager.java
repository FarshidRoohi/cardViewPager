package com.zarinpal.libs.cardviwepager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Card ViewPager Created by farshid roohi on 12/13/17.
 */

public class CardViewPager extends LinearLayout {

    private ViewPager viewPager;
    private ScrollViewPagerIndicator viewPagerIndicator;
    private ShadowTransformer cardShadowTransformer;
    private BaseCardViewPagerItem viewPagerAdapter;

    private boolean visibilityIndicator;
    private Integer dotSize = 15;
    private Integer gapSize = 5;
    private Integer scrollSpeed = 400;
    private Integer visibleItems = 0;

    private Integer paddingLeft = 20;
    private Integer paddingTop = 0;
    private Integer paddingRight = 20;
    private Integer paddingBottom = 0;

    private Drawable drawableSelectIndicator;
    private Drawable drawableUnSelectIndicator;
    private Drawable drawableTransparentIndicator;

    public CardViewPager(Context context) {
        super(context);
    }

    public CardViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CardViewPager);

        // attrs indicator
        this.dotSize = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_indicatorSize,
                Utilities.dpToPx(getContext(), this.dotSize));
        this.gapSize = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_indicatorGapSize,
                Utilities.dpToPx(getContext(), this.gapSize));
        this.scrollSpeed = typedArray.getInteger(R.styleable.CardViewPager_indicatorScrollSpeed, this.scrollSpeed);
        this.visibleItems = typedArray.getInteger(R.styleable.CardViewPager_indicatorVisibleItems, 0);
        this.visibilityIndicator = typedArray.getBoolean(R.styleable.CardViewPager_visibleIndicator, true);
        // attrs viewPager
        this.paddingLeft = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleLeftPadding, this.paddingLeft);
        this.paddingTop = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleTopPadding, this.paddingTop);
        this.paddingRight = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleRightPadding, this.paddingRight);
        this.paddingBottom = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleBottomPadding, this.paddingBottom);

        this.drawableSelectIndicator = typedArray.getDrawable(R.styleable.CardViewPager_selectIndicatorResource);
        this.drawableUnSelectIndicator = typedArray.getDrawable(R.styleable.CardViewPager_unSelectIndicatorResource);
        this.drawableTransparentIndicator = typedArray.getDrawable(R.styleable.CardViewPager_transparentIndicatorResource);

        if (this.drawableSelectIndicator == null) {
            this.drawableSelectIndicator = ContextCompat.getDrawable(getContext(), R.drawable.circle_selected_indicator);
        }
        if (this.drawableUnSelectIndicator == null) {
            this.drawableUnSelectIndicator = ContextCompat.getDrawable(getContext(), R.drawable.circle_unselected_indicator);
        }
        if (this.drawableTransparentIndicator == null) {
            this.drawableTransparentIndicator = ContextCompat.getDrawable(getContext(), R.drawable.circle_transparent_indicator);
        }


        typedArray.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initializeView();
    }

    private void initializeView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_view_pager, this);
        this.viewPager = view.findViewById(R.id.view_pager);
        this.viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);

        // set view attrs
        this.viewPagerIndicator.setVisibility(this.visibilityIndicator ? VISIBLE : GONE);
        this.viewPagerIndicator.initAttrbs(this.dotSize, this.gapSize, this.scrollSpeed, this.visibleItems);
        this.viewPagerIndicator.setDrawableResource(this.drawableSelectIndicator,this.drawableUnSelectIndicator,this.drawableTransparentIndicator);
        this.viewPager.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);

    }

    public void setAdapter(BaseCardViewPagerItem adapter) {
        if (this.viewPager == null) {
            return;
        }
        this.viewPagerAdapter = adapter;
        this.viewPager.setAdapter(this.viewPagerAdapter);
        this.viewPagerIndicator.attachViewPager(this.viewPager);
    }

    public void isShowShadowTransformer(boolean flag) {
        if (this.viewPager == null && this.viewPagerAdapter == null) {
            return;
        }
        if (!flag) {
            return;
        }
        this.cardShadowTransformer = new ShadowTransformer(this.viewPager, this.viewPagerAdapter);
        this.cardShadowTransformer.enableScaling(false);
        this.viewPager.setPageTransformer(false, this.cardShadowTransformer);
    }

    public void enableScaling(boolean flag) {
        if (this.cardShadowTransformer == null) {
            return;
        }
        this.cardShadowTransformer.enableScaling(flag);
    }
}
