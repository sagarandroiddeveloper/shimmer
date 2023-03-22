package com.facebook.shimmer.config;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;

public class Terstitial_shimmer {
    public static AdManagerInterstitialAd mAdManagerInterstitialAd;

    public static void fb_shimmer_Terstitial_shimmer(final Activity activity) {
        Global_dialog.showProgressDialog(activity);
        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

        AdManagerInterstitialAd.load(activity, "/6499/example/interstitial", adRequest,
                new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                        Global_dialog.hideProgressDialog();
                        // The mAdManagerInterstitialAd reference will be null until
                        // an ad is loaded.
                        mAdManagerInterstitialAd = interstitialAd;
                        Log.d("AdManagerInterstitialAd", "onAdLoaded");
                        if (mAdManagerInterstitialAd != null) {
                            mAdManagerInterstitialAd.show(activity);
                            ShowInterstital(activity);
                        } else {
                            Log.d("AdManagerInterstitialAd", "The interstitial ad wasn't ready yet.");
                        }

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("AdManagerInterstitialAd", "onAdLoaded");
                        Global_dialog.hideProgressDialog();
                        mAdManagerInterstitialAd = null;
                    }
                });
    }

    public static void ShowInterstital(Activity activity) {
        mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("AdManagerInterstitialAd", "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("AdManagerInterstitialAd", "Ad dismissed fullscreen content.");
                mAdManagerInterstitialAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e("AdManagerInterstitialAd", "Ad failed to show fullscreen content.");
                mAdManagerInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("AdManagerInterstitialAd", "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("AdManagerInterstitialAd", "Ad showed fullscreen content.");
            }
        });
    }
}
