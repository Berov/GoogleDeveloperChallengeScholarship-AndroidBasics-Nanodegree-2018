package bg.berov.jukebox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bg.berov.jukebox.Model.Song;

public class SingleSongActivity extends AppCompatActivity {
    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_song);

        TextView songLabel = (TextView) findViewById(R.id.textView_song);
        TextView artist = (TextView) findViewById(R.id.textView_artist);
        TextView year = (TextView) findViewById(R.id.textView_year);
        TextView genre = (TextView) findViewById(R.id.textView_genre);
        ImageView cover = (ImageView) findViewById(R.id.imageView_cover);
        ImageView play = (ImageView) findViewById(R.id.imageView_play);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            song = (Song) extras.getSerializable("song");

            songLabel.setText(song.getName());
            cover.setImageResource(song.getImage());
            artist.setText(getResources().getString(R.string.artist_) + song.getArtist());
            year.setText(getResources().getString(R.string.year_) + Integer.toString(song.getYear()));
            genre.setText(getResources().getString(R.string.genres_) + song.getGenre());

            getSupportActionBar().setTitle(song.getName());

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(song.getLink())));
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Responds to the action bar's Up/Home/back button
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
