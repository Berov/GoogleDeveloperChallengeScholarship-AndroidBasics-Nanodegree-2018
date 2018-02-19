package bg.berov.forcepointbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView barImage;
    private CardView barView;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barImage = (ImageView) findViewById(R.id.image_view_picture);
        barView = (CardView) findViewById(R.id.card_view_bar_view);
        counter = 1;

        barView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (counter) {
                    case 1:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp1));
                        break;
                    case 2:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp2));
                        break;
                    case 3:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp3));
                        break;
                    case 4:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp4));
                        break;
                    case 5:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp5));
                        break;
                    case 6:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp6));
                        break;
                    case 7:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp7));
                        break;
                    case 8:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp8));
                        break;
                    case 9:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp9));
                        break;
                    case 10:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp10));
                        break;
                    case 11:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp11));
                        break;
                    case 12:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp12));
                        break;
                    case 13:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp13));
                        break;
                    case 14:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp14));
                        break;
                    case 15:
                        barImage.setImageDrawable(getResources().getDrawable(R.drawable.fp15));
                        counter = 0;
                        break;
                }
                counter++;
            }
        });

    }
}
