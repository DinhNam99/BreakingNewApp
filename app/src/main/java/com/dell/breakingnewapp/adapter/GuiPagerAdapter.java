package com.dell.breakingnewapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dell.breakingnewapp.R;

public class GuiPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ImageView cmt1, cmt2, cmt3, radio, listening, pos, like, hd, look;

    public GuiPagerAdapter(Context context){
        this.context = context;
    }

    private int [] imageGui={
            R.drawable.vietnam,
            R.drawable.worldwide,
            R.drawable.news2,
            R.drawable.boy
    };
    private String[] DescGui={
      "Nơi bạn cập nhập mọi tin tức trong nước đáng chú ý",
              "Những sự kiện nổi bật nhất thế giới diễn ra trong 24h",
              "Và những tin tức giải trí đặc sắc",
              "Hãy khám phá ngay nào !!!"
    };
    @Override
    public int getCount() {
        return imageGui.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout, container, false);
        final ImageView igGui = view.findViewById(R.id.igGui);
        TextView tvGui = view.findViewById(R.id.tvGui);
        igGui.setImageResource(imageGui[position]);
        tvGui.setText(DescGui[position]);


        //view groud
        cmt1 = view.findViewById(R.id.cmt1);
        cmt2 = view.findViewById(R.id.cmt2);
        cmt3 = view.findViewById(R.id.cmt3);
        radio = view.findViewById(R.id.radio);
        listening = view.findViewById(R.id.listening);
        pos = view.findViewById(R.id.position);
        like = view.findViewById(R.id.like);
        hd = view.findViewById(R.id.hd);
        look = view.findViewById(R.id.look);
        changeAnimation(position);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object );
    }
    private void changeAnimation(int position){
        if(position == 0){
            final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.zoomin);
            final Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.zoomin);
            final Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.zoomin);
            anim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    anim1.startNow();
                    anim2.cancel();
                    anim3.cancel();
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    anim2.startNow();

                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            anim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    anim2.startNow();
                    anim1.cancel();
                    anim3.cancel();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    anim3.startNow();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            anim3.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    anim3.startNow();
                    anim1.cancel();
                    anim2.cancel();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    anim1.startNow();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            cmt1.setAnimation(anim1);
            cmt2.setAnimation(anim2);
            cmt3.setAnimation(anim3);
            cmt1.setVisibility(View.VISIBLE);
            cmt2.setVisibility(View.VISIBLE);
            cmt3.setVisibility(View.VISIBLE);

            radio.setVisibility(View.INVISIBLE);
            listening.setVisibility(View.INVISIBLE);
            pos.setVisibility(View.INVISIBLE);

            like.setVisibility(View.INVISIBLE);
            hd.setVisibility(View.INVISIBLE);

            look.setVisibility(View.INVISIBLE);
        }else if(position == 1){
            final Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.inout);
            final Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.fadeinout);
            final Animation anim4 = AnimationUtils.loadAnimation(context, R.anim.slidedownup);
            cmt1.setVisibility(View.INVISIBLE);
            cmt2.setVisibility(View.INVISIBLE);
            cmt3.setVisibility(View.INVISIBLE);

            radio.setVisibility(View.VISIBLE);
            radio.setAnimation(anim2);
            listening.setVisibility(View.VISIBLE);
            listening.setAnimation(anim4);
            pos.setVisibility(View.VISIBLE);
            pos.setAnimation(anim3);

            like.setVisibility(View.INVISIBLE);
            hd.setVisibility(View.INVISIBLE);

            look.setVisibility(View.INVISIBLE);
        }else if(position == 2){
            final Animation anim5 = AnimationUtils.loadAnimation(context, R.anim.rotate);
            final Animation anim6 = AnimationUtils.loadAnimation(context, R.anim.rigth_left);
            cmt1.setVisibility(View.INVISIBLE);
            cmt2.setVisibility(View.INVISIBLE);
            cmt3.setVisibility(View.INVISIBLE);

            radio.setVisibility(View.INVISIBLE);
            listening.setVisibility(View.INVISIBLE);
            pos.setVisibility(View.INVISIBLE);

            like.setVisibility(View.VISIBLE);
            like.setAnimation(anim6);
            hd.setVisibility(View.VISIBLE);
            hd.setAnimation(anim5);

            look.setVisibility(View.INVISIBLE);
        }else if(position == 3) {
            final Animation anim7 = AnimationUtils.loadAnimation(context, R.anim.inout);
            cmt1.setVisibility(View.INVISIBLE);
            cmt2.setVisibility(View.INVISIBLE);
            cmt3.setVisibility(View.INVISIBLE);

            radio.setVisibility(View.INVISIBLE);
            listening.setVisibility(View.INVISIBLE);
            pos.setVisibility(View.INVISIBLE);

            like.setVisibility(View.INVISIBLE);
            hd.setVisibility(View.INVISIBLE);

            look.setVisibility(View.VISIBLE);
            look.setAnimation(anim7);
        }
    }
}
