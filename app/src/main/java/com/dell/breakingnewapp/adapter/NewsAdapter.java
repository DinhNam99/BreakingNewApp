package com.dell.breakingnewapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.model.News;
import com.dell.breakingnewapp.view.ViewNewsActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    List<News> newsList;
    Context context;
    public static final int TYPE_0 = 0;
    public static final int TYPE_1 = 1;

    public NewsAdapter(Context context, List<News> newsList){
        this.context = context;
        this.newsList = newsList;

    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.e("POS",getItemId(i)+"");
        if(i == TYPE_0) {
           View view = LayoutInflater.from(context).inflate(R.layout.item_0, viewGroup, false);
            return new NewsHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
            return new NewsHolder(view);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, final int i) {
        final News news = newsList.get(i);
        newsHolder.title.setText(news.getTitle());
        newsHolder.desc.setText(news.getDescription());

        if(TimeToNow(news.getPuDate())==0) {
            newsHolder.timedate.setText("Today - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(TimeToNow(news.getPuDate()) == 1) {
            newsHolder.timedate.setText("Yesterday - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(TimeToNow(news.getPuDate())<=7 && TimeToNow(news.getPuDate()) > 1){
            newsHolder.timedate.setText(formaTimeDate(news.getPuDate(), "EEEE")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(TimeToNow(news.getPuDate())>7){
            newsHolder.timedate.setText(formaTimeDate(news.getPuDate(), "dd/MM/yyyy")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        Picasso.with(context).load(news.getImage()).into(newsHolder.imageItem);

        newsHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ViewNewsActivity.class);
                intent.putExtra("Link",news.getLink());
                context.startActivity(intent);
            }
        });

        newsHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, news.getLink());

                context.startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
    }

    public static String formaTimeDate(String time,String format){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String value = null;
        try {
            Date date = formatter.parse(time);
            value = new SimpleDateFormat(format).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int TimeToNow(String time){
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        java.util.Date date = null;
        try {
            date = sdf.parse(time);
            cal1.setTime(date);

            cal2.setTime(cal2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = daysBetween(cal2, cal1);
        return result;
    }

    public static int daysBetween(Calendar day1, Calendar day2){
        Calendar dayOne = (Calendar) day1.clone(),
                dayTwo = (Calendar) day2.clone();

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder{

        ImageView imageItem,share;
        TextView title, desc, timedate;
        RelativeLayout layout;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item);
            imageItem = itemView.findViewById(R.id.imageItem);
            title = itemView.findViewById(R.id.tvTitleItem);
            desc = itemView.findViewById(R.id.tvDescItem);
            timedate = itemView.findViewById(R.id.tvTimeDateItem);
            share = itemView.findViewById(R.id.shareItem);
        }
    }

}
