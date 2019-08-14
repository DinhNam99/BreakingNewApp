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
import android.widget.Toast;

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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EntertaimentFragment extends Fragment implements LoadDataRSS {
    RelativeLayout layout;
    String linkRSS = "https://vnexpress.net/rss/giai-tri.rss";
    RecyclerView rcEntertainment;
    NewsAdapter newsAdapter;
    MainPresenter mainPresenter;
    EditText edSearch;
    TextView tvName;
    List<News> newsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);

        searchNews();

        //recycleview
        rcEntertainment = (RecyclerView) view.findViewById(R.id.recycleview);
        rcEntertainment.setHasFixedSize(true);
        rcEntertainment.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvName = view.findViewById(R.id.tvName);
        tvName.setText("Entertainment");

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

                if(s!=null) {
                    for (int i = 0; i < newsList.size(); i++) {

                        final String text = newsList.get(i).getTitle().toLowerCase();
                        if (text.contains(s)) {

                            filteredList.add(newsList.get(i));
                        }
                    }
                }else{
                    Toast.makeText(getContext(),"Please enter query!!!",Toast.LENGTH_SHORT);
                }

                rcEntertainment.setLayoutManager(new LinearLayoutManager(getContext()));
                newsAdapter = new NewsAdapter(getContext(),filteredList);
                rcEntertainment.setAdapter(newsAdapter);
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



    @Override
    public void parseRSS(List<News> newsList) {
        this.newsList = newsList;
        newsAdapter = new NewsAdapter(getContext(),newsList);
        rcEntertainment.setAdapter(newsAdapter);
    }

    @Override
    public void loadFailed(String str) {

    }


}
