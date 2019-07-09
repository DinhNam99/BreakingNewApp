package com.dell.breakingnewapp.presenter;

import com.dell.breakingnewapp.model.News;

import java.util.List;

public interface LoadDataRSS {
    void parseRSSForMain(News news);
    void parseRSS(List<News> newsList);
    void searchSusscess(List<News> newsList);
    void loadFailed(String str);
}
