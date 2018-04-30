package bg.berov.bulgariatourguide;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bg.berov.bulgariatourguide.model.TouristAttraction;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryItemsFragment extends Fragment {

    private TextView noItems;
    private ListView listView;

    public CategoryItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_items, container, false);
        Bundle bundle = getArguments();


        view.findViewById(R.id.fragment_top_view).setBackgroundColor(bundle.getInt("color"));
        ArrayList<TouristAttraction> itemsList = bundle.getParcelableArrayList("itemsList");


        // Create the adapter to convert the array to views
        AttractionsAdapter adapter = new AttractionsAdapter(getContext(), itemsList);

        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);


        //Sets NO ITEMS text if no items
        if (itemsList.size() == 0) {
            TextView noItems = (TextView) view.findViewById(R.id.textView_no_items);
            noItems.setVisibility(View.VISIBLE);
        }

        //Handle clicked item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // ListView Clicked item value
                TouristAttraction itemValue = (TouristAttraction) listView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), SingleItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Item", itemValue);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }


    //viewHolder for the Atractions adapter
    private static class ViewHolder {
        ImageView image;
        TextView label;
        TextView description;
        LinearLayout topView;
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
                convertView = inflater.inflate(R.layout.item_row_view, parent, false);

                viewHolder.topView = (LinearLayout) convertView.findViewById(R.id.linearLayout_single_item_view);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView_item_image);
                viewHolder.label = (TextView) convertView.findViewById(R.id.textView_item_label);
                viewHolder.description = (TextView) convertView.findViewById(R.id.textView_item_description);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);

            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String shortDescription = attraction.getDescription();
            if (shortDescription.length() > 120) {
                shortDescription = attraction.getDescription().substring(0, 120) + "...";
            }
            viewHolder.description.setText(shortDescription);
            viewHolder.image.setImageResource(attraction.getImageResource());
            viewHolder.label.setText(attraction.getLabel());
            viewHolder.topView.setBackgroundColor(generateColor(position));

            // Return the completed view to render on screen
            return convertView;
        }

        //generates different background color for items in list view
        private int generateColor(int position) {

            int color = Color.argb(64, 255, 0, 0);

            while (position - 8 >= 0) {
                position -= 8;
            }

            switch (position) {
                case 0:
                    color = (Color.argb(64, 255, 0, 0));
                    break;
                case 1:
                    color = (Color.argb(64, 255, 156, 0)); //1
                    break;
                case 2:
                    color = (Color.argb(64, 255, 255, 0)); //2
                    break;
                case 3:
                    color = (Color.argb(64, 60, 241, 14)); //3
                    break;
                case 4:
                    color = (Color.argb(64, 2, 255, 230)); //4
                    break;
                case 5:
                    color = (Color.argb(64, 0, 36, 255)); //5
                    break;
                case 6:
                    color = (Color.argb(64, 197, 0, 255));  //6
                    break;
                case 7:
                    color = (Color.argb(64, 255, 0, 156)); //7
                    break;
            }

            return color;
        }
    }
}
