package bg.berov.sportnews;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import bg.berov.sportnews.model.NewsItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class SectionNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private TextView state;
    private ProgressBar progressBar;
    private ListView listView;
    String url;

    public SectionNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_news, container, false);

        Bundle bundle = getArguments();
//        String section = bundle.getString(getString(R.string.section__));
        int position = bundle.getInt(getString(R.string.section_number_));

        String value = null;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());


        switch (position) {
            case 0:
                value = sharedPrefs.getString(getString(R.string.first_key), getString(R.string.sport_1));
                break;
            case 1:
                value = sharedPrefs.getString(getString(R.string.second_key), getString(R.string.sport_2));
                break;
            case 2:
                value = sharedPrefs.getString(getString(R.string.third_key), getString(R.string.sport_3));
                break;
            case 3:
                value = sharedPrefs.getString(getString(R.string.fourth_key), getString(R.string.sport_4));
                break;
        }


        url = getString(R.string.first_part_of_the_url) + sharedPrefs.getString(getString(R.string.items_number_key), getString(R.string.number_of_items)) + getString(R.string.middle_part_of_the_url) + value + getString(R.string.second_part_of_the_url);


        state = (TextView) view.findViewById(R.id.textView_state);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        listView = (ListView) view.findViewById(R.id.listView_news);


        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();

        if (isConnected) {
            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(0, null, this);
        } else {
            state.setVisibility(View.VISIBLE);
            state.setText(R.string.no_connection);
        }


        return view;
    }


    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> news) {


        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);


        if (news.size() == 0) {
            state.setText(R.string.no_news);
            listView.setEmptyView(state);
        } else {
            NewsAdapter newsAdapter = new NewsAdapter(getContext(), news);
            listView.setAdapter(newsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    NewsItem newsItem = (NewsItem) listView.getItemAtPosition(position);
                    String url = newsItem.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        loader.reset();
    }


    //viewHolder for the Atractions adapter
    private static class ViewHolder {
        ImageView thumbnail;
        TextView label, section, author, date;
    }

    private class NewsAdapter extends ArrayAdapter<NewsItem> {
        public NewsAdapter(Context context, List<NewsItem> news) {
            super(context, 0, news);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            // Get the data item for this position
            NewsItem newsItem = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_item, parent, false);

                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.imageView_thumbnail);
                viewHolder.label = (TextView) convertView.findViewById(R.id.textView_label);
                viewHolder.section = (TextView) convertView.findViewById(R.id.textView_section);
                viewHolder.author = (TextView) convertView.findViewById(R.id.textView_author);
                viewHolder.date = (TextView) convertView.findViewById(R.id.textView_date);

                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);

            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.thumbnail.setImageDrawable(newsItem.getThumbnail());
            viewHolder.label.setText(newsItem.getLabel());
            viewHolder.section.setText(getString(R.string.section_) + newsItem.getSection());
            viewHolder.author.setText(getString(R.string.author_) + newsItem.getAuthor());
            viewHolder.date.setText(getString(R.string.published_) + newsItem.getDate());

            return convertView;
        }
    }
}
