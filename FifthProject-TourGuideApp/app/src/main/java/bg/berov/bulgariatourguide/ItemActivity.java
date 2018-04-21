package bg.berov.bulgariatourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import bg.berov.bulgariatourguide.model.TouristAttraction;

public class ItemActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView city, label, description;
    private TouristAttraction item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle bundle = getIntent().getExtras();

        if (bundle.getParcelable("Item") != null) {

            item = (TouristAttraction) getIntent().getParcelableExtra("Item");

//            city = (TextView) findViewById(R.id.textView_city);
//            imageView = (ImageView) findViewById(R.id.imageView_item_image);
//            label = (TextView)findViewById(R.id.textView_item_label);
//            description = (TextView)findViewById(R.id.textView_item_details);

        ((TextView)findViewById(R.id.textView_city)).setText(item.getCity());
        ((ImageView)findViewById(R.id.imageView_item_image)).setImageResource(item.getImageResource());
        ((TextView)findViewById(R.id.textView_item_label)).setText(item.getLabel());
        ((TextView)findViewById(R.id.textView_item_details)).setText(item.getDescription());


//            city.setText(item.getCity());


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
