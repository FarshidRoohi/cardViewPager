package com.zarinpal.libs.cardviewpager.demo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zarinpal.libs.cardviwepager.BaseCardViewPagerItem;

/**
 * adapter Created by farshid roohi on 12/12/17.
 */

public class Adapter extends BaseCardViewPagerItem<Model> {
    @Override
    public int getLayout() {
        return R.layout.item;
    }

    @Override
    public void bindView(View view, Model item) {

        ViewGroup layoutRoot = view.findViewById(R.id.layout_root);
        TextView txtTitle = view.findViewById(R.id.txt_title);

        layoutRoot.setBackgroundColor(item.getBgColor());
        txtTitle.setText(item.getTitle());
    }
}
