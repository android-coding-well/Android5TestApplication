package com.gosuncn.test;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gosuncn.test.widget.viewexplosion.AnimatorStatusInterface;
import com.gosuncn.test.widget.viewexplosion.ExplosionField;
import com.gosuncn.test.widget.viewexplosion.factory.ExplodeParticleFactory;
import com.gosuncn.test.widget.viewexplosion.factory.FallingParticleFactory;
import com.gosuncn.test.widget.viewexplosion.factory.FlyawayFactory;
import com.gosuncn.test.widget.viewexplosion.factory.VerticalAscentFactory;

public class ExplosionActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explosion);
        RelativeLayout v1 = (RelativeLayout) findViewById(R.id.rl_all);
        final TextView v2 = (TextView) findViewById(R.id.tv_1);
        ImageView v3 = (ImageView) findViewById(R.id.iv_1);
        Button v4 = (Button) findViewById(R.id.btn_1);
        ExplosionField explosionField = new ExplosionField(this,new FallingParticleFactory());//向下破碎
        ExplosionField explosionField1 = new ExplosionField(this,new FlyawayFactory());//被风吹走
        ExplosionField explosionField2 = new ExplosionField(this,new ExplodeParticleFactory());//竖直上升
        ExplosionField explosionField3 = new ExplosionField(this,new VerticalAscentFactory());//分裂
        explosionField.addListener(v1);

        explosionField1.addListener(v2);
        explosionField1.addAnimatorListener(new AnimatorStatusInterface() {
            @Override
            public void animStart(View v,Animator animation) {

            }

            @Override
            public void animEnd(View v,Animator animation) {
                v.setVisibility(View.GONE);
            }
        });
        explosionField2.addListener(v3);
        explosionField3.addListener(v4);
    }
}
