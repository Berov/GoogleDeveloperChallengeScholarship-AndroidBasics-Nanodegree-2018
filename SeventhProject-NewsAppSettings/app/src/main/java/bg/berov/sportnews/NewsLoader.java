package bg.berov.sportnews;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import bg.berov.sportnews.model.NewsItem;
import bg.berov.sportnews.model.Util;


public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    private String url;
    private ArrayList<NewsItem> news;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<NewsItem> loadInBackground() {
        if (news == null) {
            news = Util.getNews(url, getContext());
        }

        return news;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}


