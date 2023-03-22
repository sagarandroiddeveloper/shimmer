package com.facebook.shimmer.config;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class Anner_shimmer {

    public static AdView adView;
    public static void fb_shimmer_Anner(Context context, final LinearLayout adview_lnr) {
        if (adview_lnr != null) {
            adview_lnr.removeAllViews();
        }
        String banner_ads_id = "/6499/example/banner";
        Log.d("Admob_banner_show12", "banner_ads_id = " + banner_ads_id);
        adView = new AdView(context);
        adView.setAdUnitId(banner_ads_id);
        adview_lnr.addView(adView);
        com.google.android.gms.ads.AdSize adSize = getAdSize((Activity) context);
        adView.setAdSize(adSize);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new com.google.android.gms.ads.AdListener() {

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adview_lnr.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                adview_lnr.setVisibility(View.GONE);
                Log.d("banner_show", "onAdFailedToLoad check = " + adError.getCode());

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


    }

    public static com.google.android.gms.ads.AdSize getAdSize(Activity context) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}
