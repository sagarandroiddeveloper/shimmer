package com.facebook.shimmer.config;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class Pp_Open_shimmer {

    public static void fb_shimmer_Pp_Open_shimmer(Activity activity){
        loadAd(activity);
    }

    public static final String LOG_TAG = "AppOpenAdManager";
    public static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";

    public static AppOpenAd appOpenAd = null;
    public static boolean isLoadingAd = false;
    public static boolean isShowingAd = false;
    public static void loadAd(final Activity activity) {
        // Do not load ad if there is an unused ad or one is already loading.

        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        Global_dialog.showProgressDialog(activity);

        isLoadingAd = true;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                activity, AD_UNIT_ID, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        // Called when an app open ad has loaded.
                        Global_dialog.hideProgressDialog();
                        Log.d(LOG_TAG, "Ad was loaded.");
                        appOpenAd = ad;
                        isLoadingAd = false;
                        showAdIfAvailable(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Called when an app open ad has failed to load.
                        Global_dialog.hideProgressDialog();
                        Log.d(LOG_TAG, loadAdError.getMessage());
                        isLoadingAd = false;
                    }
                });
    }

    public static boolean isAdAvailable() {
        return appOpenAd != null;
    }


    public static void showAdIfAvailable(
            @NonNull final Activity activity){
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return;
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
           // loadAd(activity);
            return;
        }

        appOpenAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                // Set the reference to null so isAdAvailable() returns false.
                Log.d(LOG_TAG, "Ad dismissed fullscreen content.");
                appOpenAd = null;
                isShowingAd = false;

              //  loadAd(activity);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                // Set the reference to null so isAdAvailable() returns false.
                Log.d(LOG_TAG, adError.getMessage());
                appOpenAd = null;
                isShowingAd = false;

               // loadAd(activity);
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                Log.d(LOG_TAG, "Ad showed fullscreen content.");
            }
        });
        isShowingAd = true;
        appOpenAd.show(activity);
    }


}

