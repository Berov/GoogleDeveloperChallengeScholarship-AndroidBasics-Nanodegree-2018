package bg.berov.jukebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import bg.berov.jukebox.Model.Song;
import bg.berov.jukebox.Model.SongAdapter;

public class SongsActivity extends AppCompatActivity {

    private ArrayList<Song> songs;
    private ArrayList<String> contextList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

        LinearLayout optionLayout = (LinearLayout) findViewById(R.id.linearLayout_option);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String option = (String) extras.getString("option");
            songs = (ArrayList) extras.getSerializable("songs");

            switch (option) {
                case "songs":
                    showSongs(getResources().getString(R.string.all_songs));
                    break;

                case "artists":

                    getSupportActionBar().setTitle(getResources().getString(R.string.select_artist));

                    Collections.sort(songs, new Comparator<Song>() {
                        public int compare(Song s1, Song s2) {
                            return s1.getArtist().compareTo(s2.getArtist());
                        }
                    });

                    for (int i = 0; i < songs.size(); i++) {
                        if (!contextList.contains(songs.get(i).getArtist())) {
                            contextList.add(songs.get(i).getArtist());
                            final TextView textView = new TextView(this);
                            textView.setTextAppearance(this, R.style.OptionStyle);
                            textView.setText(songs.get(i).getArtist());
                            optionLayout.addView(textView);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    for (Iterator<Song> iterator = songs.iterator(); iterator.hasNext(); ) {
                                        Song song = iterator.next();
                                        if (!song.getArtist().equals(textView.getText().toString())) {
                                            iterator.remove();
                                        }
                                    }

                                    showSongs(textView.getText().toString() + "'s songs");
                                }
                            });
                        }
                    }
                    break;

                case "year":

                    getSupportActionBar().setTitle(getResources().getString(R.string.select_year));

                    Collections.sort(songs);

                    for (int i = 0; i < songs.size(); i++) {
                        if (!contextList.contains(songs.get(i).getYearAsString())) {
                            contextList.add(songs.get(i).getYearAsString());
                            final TextView textView = new TextView(this);
                            textView.setTextAppearance(this, R.style.OptionStyle);
                            textView.setText(songs.get(i).getYearAsString());
                            optionLayout.addView(textView);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    for (Iterator<Song> iterator = songs.iterator(); iterator.hasNext(); ) {
                                        Song song = iterator.next();
                                        if (!song.getYearAsString().equals(textView.getText().toString())) {
                                            iterator.remove();
                                        }
                                    }

                                    showSongs("Songs from " + textView.getText().toString());
                                }
                            });
                        }
                    }
                    break;
                case "genres":

                    getSupportActionBar().setTitle(getResources().getString(R.string.select_genre));
                    for (int i = 0; i < songs.size(); i++) {
                        if (!contextList.contains(songs.get(i).getGenre())) {
                            contextList.add(songs.get(i).getGenre());
                            final TextView textView = new TextView(this);
                            textView.setTextAppearance(this, R.style.OptionStyle);
                            textView.setText(songs.get(i).getGenre());
                            optionLayout.addView(textView);

                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    for (Iterator<Song> iterator = songs.iterator(); iterator.hasNext(); ) {
                                        Song song = iterator.next();
                                        if (!song.getGenre().equals(textView.getText().toString())) {
                                            iterator.remove();
                                        }
                                    }

                                    showSongs(textView.getText().toString());
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }


    private void showSongs(String actionBarLabel) {
        setContentView(R.layout.activity_songs);
        getSupportActionBar().setTitle(actionBarLabel);

        GridView gridView = (GridView) findViewById(R.id.list);
        SongAdapter itemsAdapter = new SongAdapter(this, songs);
        gridView.setAdapter(itemsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Song item = songs.get(position);

                Intent intent = new Intent(SongsActivity.this, SingleSongActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("song", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
