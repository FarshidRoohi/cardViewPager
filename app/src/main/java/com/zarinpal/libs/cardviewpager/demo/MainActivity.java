package com.zarinpal.libs.cardviewpager.demo;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zarinpal.libs.cardviwepager.ShadowTransformer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        Adapter adapter = new Adapter();

        Model item1 = new Model("Title 1", Color.parseColor("#1abc9c"));
        Model item2 = new Model("Title 2", Color.parseColor("#9b59b6"));
        Model item3 = new Model("Title 3", Color.parseColor("#e74c3c"));
        Model item4 = new Model("Title 4", Color.parseColor("#3498db"));
        Model item5 = new Model("Title 5", Color.parseColor("#2c3e50"));

        adapter.addCardItem(item1);
        adapter.addCardItem(item2);
        adapter.addCardItem(item3);
        adapter.addCardItem(item4);
        adapter.addCardItem(item5);

        viewPager.setOffscreenPageLimit(5);

        ShadowTransformer mCardShadowTransformer = new ShadowTransformer(viewPager, adapter);
        mCardShadowTransformer.enableScaling(false);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setAdapter(adapter);
    }
}
