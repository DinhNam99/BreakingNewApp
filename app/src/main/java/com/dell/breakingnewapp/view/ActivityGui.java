package com.dell.breakingnewapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.adapter.GuiPagerAdapter;

public class ActivityGui extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    GuiPagerAdapter adapter;
    TextView [] textViews;
    int current = 0;
    Button btnGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_gui);
        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.linearGui);
        btnGo = findViewById(R.id.btnGo);
        if(restrorePrefsData()){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGui.this,MainActivity.class);
                startActivity(intent);
                savePrefsData();
                finish();
            }
        });

        adapter = new GuiPagerAdapter(ActivityGui.this);
        viewPager.setAdapter(adapter);
        addChange(0);
        viewPager.addOnPageChangeListener(viewListenr);

    }

    public void savePrefsData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isTutorial",true);
        editor.commit();
    }
    private boolean restrorePrefsData(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isTutorial = sharedPreferences.getBoolean("isTutorial",false);
        return isTutorial;
    }
    public void addChange(int pos){
        textViews = new TextView[4];
        linearLayout.removeAllViews();
        for(int i = 0; i<textViews.length; i++){
            textViews[i] = new TextView(this);
            textViews[i].setText(Html.fromHtml("&#8226"));
            textViews[i].setTextSize(35);
            textViews[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            linearLayout.addView(textViews[i]);
        }
        if(textViews.length>0){
            textViews[pos].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListenr = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageSelected(int i) {
            addChange(i);
            current = i;

            if(i == textViews.length-1){
                btnGo.setVisibility(View.VISIBLE);

            }else{
                btnGo.setVisibility(View.INVISIBLE);
            }
        }
    };
}
