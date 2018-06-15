package bg.berov.sportnews.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bg.berov.sportnews.R;

public final class Util {


    private Util() {
    }


    private static URL convertStringToUrl(String string) {
        URL url = null;

        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static ArrayList<NewsItem> getNews(String url, Context context) {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) convertStringToUrl(url).openConnection();
            urlConnection.setRequestMethod(context.getString(R.string.request_method));
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream, context);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return convertJsonResponseToNewsList(jsonResponse, context);
    }


    private static ArrayList<NewsItem> convertJsonResponseToNewsList(String jsonResponse, Context context) {
        ArrayList<NewsItem> news = new ArrayList<>();
        JSONArray jsonNewsArray = null;

        try {
            jsonNewsArray = new JSONObject(jsonResponse).getJSONObject(context.getString(R.string.response)).getJSONArray(context.getString(R.string.results));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonNewsArray.length() > 0) {
            for (int i = 0; i < jsonNewsArray.length(); i++) {
                JSONObject jsonNews = null;
                String title = context.getString(R.string.empty_string);
                String date = context.getString(R.string.empty_string);
                String section = context.getString(R.string.empty_string);
                String url = context.getString(R.string.empty_string);
                String author = context.getString(R.string.empty_string);
                Drawable thumbnail = null;

                try {
                    jsonNews = jsonNewsArray.getJSONObject(i);
                    title = jsonNews.getString(context.getString(R.string.web_title));
                    date = dateFormater(jsonNews.getString(context.getString(R.string.publication_date)), context);
                    section = jsonNews.getString(context.getString(R.string.section_name));
                    url = jsonNews.getString(context.getString(R.string.web_url));
                    author = jsonNews.getJSONObject(context.getString(R.string.fields)).getString(context.getString(R.string.byline));
                    thumbnail = createDrawableFromUrl(jsonNews.getJSONObject(context.getString(R.string.fields)).getString(context.getString(R.string.thumbnail)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                news.add(new NewsItem(title, author, date, url, section, thumbnail));
            }
        }
        return news;
    }


    private static String readInputStream(InputStream inputStream, Context context) {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(context.getString(R.string.charset)));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            try {
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }


    private static Drawable createDrawableFromUrl(String imageWebAddress) {
        Drawable drawable = null;
        InputStream inputStream = null;

        try {
            inputStream = new URL(imageWebAddress).openStream();
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }


    private static String dateFormater(String dateString, Context context) {
        Date date = null;
        SimpleDateFormat dateFormater = new SimpleDateFormat(context.getString(R.string.input_date_format));

        try {
            date = dateFormater.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateFormater = new SimpleDateFormat(context.getString(R.string.output_date_format));

        return dateFormater.format(date);
    }
}