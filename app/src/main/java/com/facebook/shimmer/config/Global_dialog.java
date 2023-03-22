package com.facebook.shimmer.config;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.shimmer.R;

public class Global_dialog {

    static Dialog dialog_simple;
    public static void showProgressDialog(Activity context) {
        try {
            try {
                if (dialog_simple != null && dialog_simple.isShowing()) {
                    dialog_simple.dismiss();
                }
            } catch (NullPointerException n) {
                n.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog_simple = new Dialog(context);
            dialog_simple.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LayoutInflater inflater = (LayoutInflater) dialog_simple.getLayoutInflater();
            View customView = inflater.inflate(R.layout.custom_progressbar, null);
            dialog_simple.setContentView(customView);
            dialog_simple.setCancelable(false);
            dialog_simple.setCanceledOnTouchOutside(false);
            dialog_simple.show();
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideProgressDialog() {
        try {
            if (dialog_simple != null && dialog_simple.isShowing()) {
                dialog_simple.dismiss();
            }
        } catch (NullPointerException n) {
            n.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
