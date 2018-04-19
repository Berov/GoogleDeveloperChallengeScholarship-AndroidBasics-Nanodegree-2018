package bg.berov.bulgariatourguide;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.berov.bulgariatourguide.model.TouristAttraction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SofiaFragment extends Fragment {
    //TODO ?????????????????????????????????????
    private ArrayList<TouristAttraction> itemsList;
//    private ArrayList<TouristAttraction> itemsList = Util.getTouristAttractionArrayList(MainActivity.city,"Museum");
//
//    public static String city;

    public SofiaFragment() {
        // Required empty public constructor
    }


    public static SofiaFragment newInstance(ArrayList<TouristAttraction> list) {
        SofiaFragment myFragment = new SofiaFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("List", list);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsList = getArguments().getParcelableArrayList("List");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sofia, container, false);


        // Create the adapter to convert the array to views

        AttractionsAdapter adapter = new AttractionsAdapter(getContext(), itemsList);

        // Attach the adapter to a ListView
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }


    private static class ViewHolder {
        ImageView image;
        TextView label;
        TextView description;
    }

    //Atractions Adapter
    public class AttractionsAdapter extends ArrayAdapter<TouristAttraction> {


        public AttractionsAdapter(Context context, ArrayList<TouristAttraction> atractions) {
            super(context, 0, atractions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;


            // Get the data item for this position
            TouristAttraction attraction = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.single_item_view, parent, false);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView_item_image);
                viewHolder.label = (TextView) convertView.findViewById(R.id.textView_item_label);
                viewHolder.description = (TextView) convertView.findViewById(R.id.textView_item_description);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);

            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();

            }

            viewHolder.image.setImageResource(attraction.getImageResource());
            viewHolder.label.setText(attraction.getLabel());
            viewHolder.description.setText(attraction.getDescription());

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
