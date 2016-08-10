package com.gosuncn.test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.flipboard.bottomsheet.commons.MenuSheetView;

import java.util.Collections;

public class BottomSheetActivity extends AppCompatActivity {


    BottomSheetLayout bottomSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);


    }

    public void showBottomSheet(View v){
        bottomSheet.showWithSheetView(LayoutInflater.from(this).inflate(R.layout.content_scrolling, bottomSheet, false));

    }

    public void showBottomSheetMenu(View v){
        MenuSheetView menuSheetView =
                new MenuSheetView(this, MenuSheetView.MenuType.LIST, "Create...", new MenuSheetView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(BottomSheetActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        if (bottomSheet.isSheetShowing()) {
                            bottomSheet.dismissSheet();
                        }
                        return true;
                    }
                });
        menuSheetView.inflateMenu(R.menu.menu_main);
        bottomSheet.showWithSheetView(menuSheetView);
    }

    public void showBottomSheetPickerIntent(View v){
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "ddd");
        shareIntent.setType("text/plain");
        IntentPickerSheetView intentPickerSheetView=new IntentPickerSheetView(this, shareIntent, "intent", new IntentPickerSheetView.OnIntentPickedListener() {
            @Override
            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                bottomSheet.dismissSheet();
                startActivity(activityInfo.getConcreteIntent(shareIntent));
            }
        });

        Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
        IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "Custom mix-in", this, TabActivity.class);//最后一个参数表示当Intent无效时默认跳转的Activity
        intentPickerSheetView.setMixins(Collections.singletonList(customInfo));
        bottomSheet.showWithSheetView(intentPickerSheetView);
    }


}
