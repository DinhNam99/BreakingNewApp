package com.dell.breakingnewapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dell.breakingnewapp.R;

public class LoadingActivity extends AppCompatActivity {

    private ImageView ivNews;
    private Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_loading);

        //axa
        ivNews = findViewById(R.id.news);
        Glide.with(this)
                .load(R.drawable.news)
                .apply(new RequestOptions().fitCenter())
                .into(ivNews);
        startAnimation();
    }


    private void startAnimation(){
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.screen_anim);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);

        rotate.reset();
        translate.reset();

        ivNews.setAnimation(rotate);

        mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                Intent intent = new Intent(LoadingActivity.this, ActivityGui.class);
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        LoadingActivity.this.finish();
                    }

            }
        };
        mThread.start();
    }
}
