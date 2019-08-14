package com.dell.breakingnewapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dell.breakingnewapp.R;
import com.dell.breakingnewapp.adapter.NewsAdapter;
import com.dell.breakingnewapp.model.News;
import com.dell.breakingnewapp.presenter.LoadDataRSS;
import com.dell.breakingnewapp.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class ActivityMore extends AppCompatActivity implements LoadDataRSS {

    RelativeLayout layout;
    String linkRSS, namePage;
    RecyclerView rcHome;
    NewsAdapter newsAdapter;
    MainPresenter mainPresenter;
    TextView tvNamePage;
    List<News> newsList;
    EditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_more);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();
        //load
        Intent intent = getIntent();
        namePage = intent.getStringExtra("NamePage");
        Log.e("NAMEPAGE", namePage);
        tvNamePage.setText(namePage + "");
        linkRSS = intent.getStringExtra("LinkPage");
        mainPresenter = new MainPresenter(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.execute(linkRSS);
            }
        });
    }

    void init() {

        tvNamePage = findViewById(R.id.tvNamePage);
        searchNews();

        //recycleview
        rcHome = findViewById(R.id.rcPage);
        rcHome.setHasFixedSize(true);
        rcHome.setLayoutManager(new LinearLayoutManager(this));


    }

    public void searchNews() {
        edSearch = findViewById(R.id.edSearchPage);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();

                final List<News> filteredList = new ArrayList<>();

                if (s != null) {
                    for (int i = 0; i < newsList.size(); i++) {

                        final String text = newsList.get(i).getTitle().toLowerCase();
                        if (text.contains(s)) {

                            filteredList.add(newsList.get(i));
                        }
                    }
                } else {
                    Toast.makeText(ActivityMore.this, "Please enter query!!!", Toast.LENGTH_SHORT);
                }

                newsAdapter = new NewsAdapter(ActivityMore.this, filteredList);
                rcHome.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();  // data set changed
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void parseRSS(List<News> newsList) {
        this.newsList = newsList;
        Log.e("List", newsList.get(0).getDescription());
        newsAdapter = new NewsAdapter(ActivityMore.this, newsList);
        rcHome.setAdapter(newsAdapter);
    }

    @Override
    public void loadFailed(String str) {

    }

}
