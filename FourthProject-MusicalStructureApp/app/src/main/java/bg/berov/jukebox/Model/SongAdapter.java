package bg.berov.jukebox.Model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.berov.jukebox.R;


public class SongAdapter extends ArrayAdapter<Song> {


    public SongAdapter(Context context, ArrayList<Song> songList) {
        super(context,0, songList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        // Get the data item for this position
        Song  song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_song_list, parent, false);
        }

        TextView songName = (TextView) listItemView.findViewById(R.id.textView_song_name);
        ImageView songImage = (ImageView) listItemView.findViewById(R.id.imageView_song_image);

        songName.setText(song.getName());
        songImage.setImageResource(song.getImage());

        return listItemView;
    }
}
