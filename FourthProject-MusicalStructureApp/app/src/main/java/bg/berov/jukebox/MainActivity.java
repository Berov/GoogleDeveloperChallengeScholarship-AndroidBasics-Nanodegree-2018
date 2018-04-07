package bg.berov.jukebox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import bg.berov.jukebox.Model.Song;
import bg.berov.jukebox.Model.Util;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Song> songList = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView songs = (TextView) findViewById(R.id.textView_songs);
        TextView artists = (TextView) findViewById(R.id.textView_artists);
        TextView year = (TextView) findViewById(R.id.textView_year);
        TextView genres = (TextView) findViewById(R.id.textView_genres);

        //Generates random content
        if (songList.isEmpty()) {
            for (int i = 0; i < Util.COUNT_OF_SONGS; i++) {
                songList.add(new Song(Util.generateSongName(), Util.generateArtistName(), Util.generateSongYear(), Util.generateMusicGenre(), Util.generateCover(), Util.generateSongLink()));
            }
        }

        songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("songs");
            }
        });

        artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("artists");
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("year");
            }
        });

        genres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("genres");
            }
        });
    }

    private void goToActivity(String message) {
        Intent intent = new Intent(MainActivity.this, SongsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("songs", songList);
        bundle.putString("option", message);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
