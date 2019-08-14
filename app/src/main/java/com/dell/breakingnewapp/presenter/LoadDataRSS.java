package com.dell.breakingnewapp.presenter;

import com.dell.breakingnewapp.model.News;

import java.util.List;

public interface LoadDataRSS {
    void parseRSS(List<News> newsList);
    void loadFailed(String str);
}
