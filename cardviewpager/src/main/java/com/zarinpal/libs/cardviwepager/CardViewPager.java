package com.zarinpal.libs.cardviwepager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shuhart.bubblepagerindicator.BubblePageIndicator;

/**
 * Card ViewPager Created by farshid roohi on 12/13/17.
 */

public class CardViewPager extends LinearLayout {

    private ViewPager             viewPager;
    private ShadowTransformer     cardShadowTransformer;
    private BaseCardViewPagerItem viewPagerAdapter;

    private boolean visibilityIndicator;
    private Integer dotSize = 15;

    private Integer paddingLeft   = 20;
    private Integer paddingTop    = 0;
    private Integer paddingRight  = 20;
    private Integer paddingBottom = 0;

    private Integer selectIndicatorColor;
    private Integer unSelectIndicatorColor;

    private BubblePageIndicator indicator;

    public CardViewPager(Context context) {
        super(context);
    }

    public CardViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CardViewPager);

        // attrs indicator
        this.dotSize = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_indicatorSize,
                Utilities.dpToPx(getContext(), this.dotSize));
        this.visibilityIndicator = typedArray.getBoolean(R.styleable.CardViewPager_visibleIndicator, true);
        // attrs viewPager
        this.paddingLeft = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleLeftPadding, this.paddingLeft);
        this.paddingTop = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleTopPadding, this.paddingTop);
        this.paddingRight = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleRightPadding, this.paddingRight);
        this.paddingBottom = typedArray.getDimensionPixelSize(R.styleable.CardViewPager_visibleBottomPadding, this.paddingBottom);

        this.selectIndicatorColor = typedArray.getColor(R.styleable.CardViewPager_selectIndicatorColor,
                ContextCompat.getColor(getContext(), R.color.select_indicator));
        this.unSelectIndicatorColor = typedArray.getColor(R.styleable.CardViewPager_unSelectIndicatorColor,
                ContextCompat.getColor(getContext(), R.color.un_select_indicator));

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

        this.indicator = view.findViewById(R.id.indicator);


        // set view attrs

        this.indicator.setFillColor(this.selectIndicatorColor);
        this.indicator.setPageColor(this.unSelectIndicatorColor);
        this.indicator.setRadius(this.dotSize);
        this.indicator.setVisibility(this.visibilityIndicator ? VISIBLE : GONE);
        this.viewPager.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);

    }

    public void setAdapter(BaseCardViewPagerItem adapter) {
        if (this.viewPager == null) {
            return;
        }
        this.viewPagerAdapter = adapter;
        this.viewPager.setAdapter(this.viewPagerAdapter);
        this.indicator.setViewPager(this.viewPager);
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

    public ViewPager getViewPager() {
        return viewPager;
    }
}
