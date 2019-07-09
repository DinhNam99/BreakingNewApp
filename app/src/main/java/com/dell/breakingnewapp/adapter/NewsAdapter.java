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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    List<News> newsList;
    Context context;

    public NewsAdapter(Context context, List<News> newsList){
        this.context = context;
        this.newsList = newsList;

    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        return new NewsHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, final int i) {
        final News news = newsList.get(i);
        newsHolder.title.setText(news.getTitle());
        newsHolder.desc.setText(news.getDescription());

        //time
        Calendar calendar = Calendar.getInstance();
        int now = Integer.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        if(getDayOfYear(news.getPuDate())-now == 0) {
            newsHolder.timedate.setText("Today - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate()) == 1) {
            newsHolder.timedate.setText("Yesterday - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate())<=7 && now-getDayOfYear(news.getPuDate()) > 1){
            newsHolder.timedate.setText(formaTimeDate(news.getPuDate(), "EEEE")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate())>7){
            newsHolder.timedate.setText(formaTimeDate(news.getPuDate(), "dd/MM/yyyy")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }

        Glide.with(context)
                .load(news.getImage())
                .apply(new RequestOptions().fitCenter())
                .into(newsHolder.imageItem);

        newsHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TIME",formaTimeDate(news.getPuDate(), "EEEE")+"");
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
        java.util.Date date=null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String value = new java.text.SimpleDateFormat(format).
                format(date);
        return value;
    }
    public static int getDayOfYear(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        java.util.Date date=null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int value = Integer.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        return value;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void filterList(List<News> newsFilter){
       this.newsList = newsFilter;
        notifyDataSetChanged();
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
