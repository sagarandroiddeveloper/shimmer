package com.facebook.shimmer.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class Ds_Controller extends Application {
    private int numStarted = 0;
    public static boolean fast_start = false;

    @Override
    public void onCreate() {
        super.onCreate();
        SetAppOpenads();
    }

    private void SetAppOpenads() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                ///////Log.d("App_Controller", "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                ///////Log.d("App_Controller", "onActivityStarted");

                if (numStarted == 0) {
                    if (fast_start) {
                        showAdIfAvailable(activity);
                    } else {
                        fast_start = true;
                    }
                }
                numStarted++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ///////Log.d("App_Controller", "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                ///////Log.d("App_Controller", "onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                ///////Log.d("App_Controller", "onActivityStopped");
                numStarted--;
                if (numStarted == 0) {

                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                ///////Log.d("App_Controller", "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ///////Log.d("App_Controller", "onActivityDestroyed");
            }
        });
    }

    private static final String LOG_TAG = "AppOpenAdManager";
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";

    private AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    private boolean isShowingAd = false;

    private boolean isAdAvailable() {
        return appOpenAd != null;
    }

    public void loadAd(Context context) {
        // Do not load ad if there is an unused ad or one is already loading.
        if (isLoadingAd || isAdAvailable()) {
            return;
        }

        isLoadingAd = true;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context, AD_UNIT_ID, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        // Called when an app open ad has loaded.
                        Log.d(LOG_TAG, "Ad was loaded.");
                        appOpenAd = ad;
                        isLoadingAd = false;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Called when an app open ad has failed to load.
                        Log.d(LOG_TAG, loadAdError.getMessage());
                        isLoadingAd = false;
                    }
                });
    }


    public void showAdIfAvailable(@NonNull final Activity activity) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(LOG_TAG, "The app open ad is already showing.");
            return;
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (!isAdAvailable()) {
            Log.d(LOG_TAG, "The app open ad is not ready yet.");
            loadAd(activity);
            return;
        }

        appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                // Set the reference to null so isAdAvailable() returns false.
                Log.d(LOG_TAG, "Ad dismissed fullscreen content.");
                appOpenAd = null;
                isShowingAd = false;

                loadAd(activity);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when fullscreen content failed to show.
                // Set the reference to null so isAdAvailable() returns false.
                Log.d(LOG_TAG, adError.getMessage());
                appOpenAd = null;
                isShowingAd = false;

                loadAd(activity);
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
