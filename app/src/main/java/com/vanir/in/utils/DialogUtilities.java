package com.vanir.in.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vanir.in.AppClass;
import com.vanir.in.NoInternetRetryInteracter;
import com.vanir.in.R;


import cn.pedant.SweetAlert.SweetAlertDialog;


public class DialogUtilities {

    public static void showProgressDialog(SweetAlertDialog progressDialog) {
        if (progressDialog == null)
            return;
        progressDialog.show();
    }

    public static void dismissProgressDialog(SweetAlertDialog progressDialog) {
        if (progressDialog == null)
            return;
        progressDialog.dismiss();
    }

    public static void dismissProgressDialogWithAnimation(SweetAlertDialog progressDialog) {
        if (progressDialog == null)
            return;
        progressDialog.dismissWithAnimation();
    }

    public static Dialog showProgressBar(){
        Dialog dialog=new Dialog(AppClass.currentAct);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = AppClass.currentAct.getLayoutInflater().inflate(R.layout.progress_bar, null);
        ImageView imgview  = view.findViewById(R.id.imgViewpb);
        Glide.with(AppClass.currentAct).load(R.drawable.progressh).into(imgview);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= WindowManager.LayoutParams.DIM_AMOUNT_CHANGED;
        window.setAttributes(wlp);
        //window.getDecorView().setBackgroundColor(AppClass.currentAct.getResources().getColor(R.color.float_transparent));
        return dialog;
    }

    public static SweetAlertDialog getProgressDialog(Context context) {
        SweetAlertDialog progressDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#C11620"));
        progressDialog.setTitleText("Loading");
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static Dialog getErrorDialog(String errorTitle) {
        /*Snackbar snackbar = Snackbar
                .make(contextView, errorTitle, Snackbar.LENGTH_LONG);
        return snackbar;*/
        String errorMsg = errorTitle;
        if (errorMsg==""){
            errorMsg = "Something Unexpected Happened";
        }
                 SweetAlertDialog errorDialog = new SweetAlertDialog(AppClass.currentAct, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(errorMsg)
                .setConfirmText("OK")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
        return errorDialog;
    }

    public static SweetAlertDialog getInfoDialog(String title) {
        SweetAlertDialog errorDialog = new SweetAlertDialog(AppClass.currentAct, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setConfirmText("OK")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
        return errorDialog;
    }

    public static SweetAlertDialog getRetry(String title) {
        NoInternetRetryInteracter noInternetRetryInteracter = (NoInternetRetryInteracter) AppClass.currentAct;
        SweetAlertDialog errorDialog = new SweetAlertDialog(AppClass.currentAct, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setConfirmText("RETRY")
                .setConfirmClickListener(sDialog ->{sDialog.dismissWithAnimation();
                            noInternetRetryInteracter.retryApiCall();});
        return errorDialog;
    }


    public static SweetAlertDialog getSuccessDialog(String successMessage) {
        SweetAlertDialog errorDialog = new SweetAlertDialog(AppClass.currentAct, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(successMessage)
                .setConfirmText("OK")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation());
        return errorDialog;
    }

}
