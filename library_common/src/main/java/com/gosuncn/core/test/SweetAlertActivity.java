/*
package com.gosuncn.core.test;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gosuncn.core.R;
import com.gosuncn.core.base.BaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertActivity extends BaseActivity implements View.OnClickListener {

    private Button showBtn;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet_alert);
        initView();
    }

    private void initView() {
        showBtn = (Button) findViewById(R.id.btn_sweetalert_showdialog);
        showBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sweetalert_showdialog:
                SweetAlertDialog sweetAlertDialog = null;
                switch (i) {
                    case SweetAlertDialog.ERROR_TYPE:
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong!")
                                .show();
                        break;
                    case SweetAlertDialog.SUCCESS_TYPE:
                        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.setTitleText("Good job!");
                        sweetAlertDialog.setContentText("You clicked the button!");
                        sweetAlertDialog.show();
                        break;
                    case SweetAlertDialog.WARNING_TYPE:
                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText("Won't be able to recover this file!")
                                .setCancelText("No,cancel plx!")
                                .setConfirmText("Yes,delete it!")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance, keep widget user state, reset them if you need
                                        sDialog.setTitleText("Cancelled!")
                                                .setContentText("Your imaginary file is safe :)")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setCancelClickListener(null)
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                        // or you can new a SweetAlertDialog to show
                       */
/* sDialog.dismiss();
                        new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Cancelled!")
                                .setContentText("Your imaginary file is safe :)")
                                .setConfirmText("OK")
                                .show();*//*

                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.setTitleText("Deleted!")
                                                .setContentText("Your imaginary file has been deleted!")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setCancelClickListener(null)
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                })
                                .show();
                        break;
                    case SweetAlertDialog.CUSTOM_IMAGE_TYPE:
                        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setTitleText("Sweet!")
                                .setContentText("Here's a custom image.")
                                .setCustomImage(R.drawable.ic_launcher)
                                .show();
                        break;
                    case SweetAlertDialog.PROGRESS_TYPE:
                        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                                .setTitleText("Loading");
                        pDialog.show();
                        pDialog.setCancelable(true);
                        new CountDownTimer(800 * 7, 800) {
                            public void onTick(long millisUntilFinished) {
                                // you can change the progress bar color by ProgressHelper every 800 millis
                                i++;
                                switch (i) {
                                    case 0:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                        break;
                                    case 1:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                        break;
                                    case 2:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                        break;
                                    case 3:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                        break;
                                    case 4:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                        break;
                                    case 5:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                        break;
                                    case 6:
                                        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                        break;
                                }
                            }

                            public void onFinish() {
                                i = -1;
                                pDialog.setTitleText("Success!")
                                        .setConfirmText("OK")
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        }.start();
                        break;
                }

                i++;
                i = i % 6;
                break;
        }

    }
}
*/
