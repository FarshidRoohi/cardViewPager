# Custom Toast

![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)
- Add library gradle : 

```Gradle
    compile 'com.zarinpal:cardviewpager:0.3'
        
```
### Sample Use
```XML

    <com.zarinpal.libs.cardviwepager.CardViewPager
        android:id="@+id/card_view_pager"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_gravity="center"
        app:indicatorGapSize="6dp"
        app:indicatorScrollSpeed="500"
        app:indicatorSize="10dp"
        app:indicatorVisibleItems="5"
        app:selectIndicatorResource="@drawable/my_indicator"
        app:visibleBottomPadding="5dp"
        app:visibleIndicator="true"
        app:visibleLeftPadding="30dp"
        app:visibleRightPadding="30dp"
        app:visibleTopPadding="5dp" />

```

###### sample : 

<img src="https://raw.githubusercontent.com/FarshidRoohi/cardViewPager/master/image/sample_image.png" alt="screen show" width="270px" height="500px">