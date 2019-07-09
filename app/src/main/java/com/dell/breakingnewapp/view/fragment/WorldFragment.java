package com.dell.breakingnewapp.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.adapter.NewsAdapter;
import com.dell.breakingnewapp.adapter.ViewPaperAdapter;
import com.dell.breakingnewapp.model.News;
import com.dell.breakingnewapp.presenter.LoadDataRSS;
import com.dell.breakingnewapp.presenter.MainPresenter;
import com.dell.breakingnewapp.view.ViewNewsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorldFragment extends Fragment implements LoadDataRSS {
    RelativeLayout layout;
    String linkRSS = "https://vnexpress.net/rss/the-gioi.rss";
    RecyclerView rcWorld;
    NewsAdapter newsAdapter;
    MainPresenter mainPresenter;
    EditText edSearch;
    List<News> newsList;

    ImageView imageM,shareWM;
    TextView tvTitleM, tvDescM, tvPubDataM;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_world,container,false);
        //nen
        ImageView imageView = view.findViewById(R.id.ivNen);
        Glide.with(this)
                .load(R.drawable.news)
                .apply(new RequestOptions().fitCenter())
                .into(imageView);

        layout = view.findViewById(R.id.world);

        //display title desc, pubdat main
        //nen
        imageM = view.findViewById(R.id.ivNen);
        tvTitleM = view.findViewById(R.id.tvTitleWM);
        tvDescM = view.findViewById(R.id.tvDescWM);
        tvPubDataM = view.findViewById(R.id.tvTimeDateWM);
        shareWM = view.findViewById(R.id.shareWM);
        searchNews();

        //recycleview
        rcWorld = (RecyclerView) view.findViewById(R.id.rcWorld);
        rcWorld.setHasFixedSize(true);
        rcWorld.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    public void searchNews(){
        edSearch = getActivity().findViewById(R.id.edSearch);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();

                final List<News> filteredList = new ArrayList<>();

                for (int i = 0; i < newsList.size(); i++) {

                    final String text = newsList.get(i).getTitle().toLowerCase();
                    if (text.contains(s)) {

                        filteredList.add(newsList.get(i));
                    }
                }

                rcWorld.setLayoutManager(new LinearLayoutManager(getContext()));
                newsAdapter = new NewsAdapter(getContext(),filteredList);
                rcWorld.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();  // data set changed
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = new MainPresenter(this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.execute(linkRSS);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mainPresenter = new MainPresenter(this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.execute(linkRSS);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void parseRSSForMain(final News news) {
        Glide.with(this)
                .load(news.getImage())
                .apply(new RequestOptions().fitCenter())
                .into(imageM);
        tvTitleM.setText(news.getTitle()+"");
        tvDescM.setText(news.getDescription()+"");


        //time
        Calendar calendar = Calendar.getInstance();
        int now = Integer.valueOf(calendar.get(Calendar.DAY_OF_YEAR));

        if(getDayOfYear(news.getPuDate())-now == 0) {
            tvPubDataM.setText("Today - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate()) == 1) {
            tvPubDataM.setText("Yesterday - " + formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate())<=7 && now-getDayOfYear(news.getPuDate()) > 1){
            tvPubDataM.setText(formaTimeDate(news.getPuDate(), "EEEE")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }
        if(now-getDayOfYear(news.getPuDate())>7){
            tvPubDataM.setText(formaTimeDate(news.getPuDate(), "dd/MM/yyyy")+" - "+formaTimeDate(news.getPuDate(), "HH:mm:ss a"));
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewNewsActivity.class);
                intent.putExtra("Link",news.getLink());
                startActivity(intent);
            }
        });

        shareWM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, news.getLink());

                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });
    }

    @Override
    public void parseRSS(List<News> newsList) {
        this.newsList = newsList;
        newsAdapter = new NewsAdapter(getContext(),newsList);
        rcWorld.setAdapter(newsAdapter);
    }

    @Override
    public void searchSusscess(List<News> newsList) {

    }

    @Override
    public void loadFailed(String str) {

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
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int value = Integer.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        return value;
    }
}
