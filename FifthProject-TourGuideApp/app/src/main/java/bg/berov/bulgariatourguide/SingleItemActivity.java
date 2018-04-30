package bg.berov.bulgariatourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import bg.berov.bulgariatourguide.model.TouristAttraction;

public class SingleItemActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();

        if (bundle.getParcelable("Item") != null) {

            TouristAttraction attraction = (TouristAttraction) getIntent().getParcelableExtra("Item");

            ((ImageView) findViewById(R.id.imageView_item_image)).setImageResource(attraction.getImageResource());
            ((TextView) findViewById(R.id.textView_item_label)).setText(attraction.getLabel());
            ((TextView) findViewById(R.id.textView_item_description)).setText(attraction.getDescription());
            ((TextView) findViewById(R.id.textView_item_city)).setText(attraction.getCity() + ", " + getString(R.string.country));
        }
    }

    //Just destroy the activity like "home button" for not recreating the main/parent activity
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
